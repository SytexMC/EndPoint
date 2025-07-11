/*
 * This file is part of EndPoint, licensed under GPL v3.
 *
 * Copyright (c) 2025 Sytex <sytex@duck.com>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.sytex.endpoint.api;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import me.sytex.endpoint.api.exception.GlobalErrorHandler;
import me.sytex.endpoint.api.routes.AutoRoute;
import me.sytex.endpoint.api.routes.Route;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

@Slf4j
public class ApiServer {
  private static Javalin app;

  public static void start(int port, @NotNull String... packages) {
    app = Javalin.create();

    GlobalErrorHandler.configure(app);

    registerRoutes(packages);

    app.start(port);
  }

  private static void registerRoutes(@NotNull String... packages) {
    for (String pkg : packages) {
      Reflections reflections = new Reflections(pkg);
      Set<Class<?>> routeClasses = reflections.getTypesAnnotatedWith(AutoRoute.class);

      for (Class<?> routeClass : routeClasses) {
        try {
          AutoRoute annotation = routeClass.getAnnotation(AutoRoute.class);
          Object instance = routeClass.getDeclaredConstructor().newInstance();

          if (instance instanceof Route<?> route) {
            String path = annotation.path();
            HandlerType method = annotation.method();

            log.info("Registering route: {} {}", method, path);

            switch (method) {
              case GET -> app.get(path, route::handleRequest);
              case POST -> app.post(path, route::handleRequest);
              case PUT -> app.put(path, route::handleRequest);
              case DELETE -> app.delete(path, route::handleRequest);
              case PATCH -> app.patch(path, route::handleRequest);
              default -> throw new IllegalStateException("Unsupported HTTP method: " + method);
            }
          } else {
            log.warn("Class {} is annotated with @AutoRoute but doesn't implement Route interface.", routeClass.getName());
          }
        } catch (InstantiationException | IllegalAccessException e) {
          log.error("Failed to instantiate route class: {}. Ensure the class is not abstract and has a public constructor", routeClass.getName(), e);
        } catch (InvocationTargetException e) {
          log.error("Constructor threw an exception for class: {}. Check the constructor implementation", routeClass.getName(), e.getCause());
        } catch (NoSuchMethodException e) {
          log.error("No default constructor found for class: {}. Add a public no-args constructor", routeClass.getName(), e);
        } catch (Exception e) {
          log.error("Unexpected error registering route class: {}. Please report this issue", routeClass.getName(), e);
        }
      }
    }
  }

  public static void stop() {
    if (app != null) {
      app.stop();
    }
  }
}
