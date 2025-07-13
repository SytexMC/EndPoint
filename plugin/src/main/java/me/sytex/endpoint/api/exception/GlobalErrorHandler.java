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

package me.sytex.endpoint.api.exception;

import io.javalin.Javalin;
import me.sytex.endpoint.api.response.ApiResponse;
import org.jetbrains.annotations.NotNull;

public class GlobalErrorHandler {

  public static void configure(@NotNull Javalin app) {
    app.exception(ApiException.class, (e, ctx) -> {
      ApiResponse<?> response = ApiResponse.builder()
          .success(false)
          .timestamp(System.currentTimeMillis())
          .context(e.getMessage())
          .build();

      ctx.status(e.getStatusCode()).json(response);
    });

    app.exception(Exception.class, (e, ctx) -> {
      ApiResponse<?> response = ApiResponse.builder()
          .success(false)
          .timestamp(System.currentTimeMillis())
          .context("Internal Server Error!")
          .build();

      ctx.status(500).json(response);
    });

    app.error(404, ctx -> {
      ApiResponse<?> response = ApiResponse.builder()
          .success(false)
          .timestamp(System.currentTimeMillis())
          .context("Resource not found!")
          .build();

      ctx.json(response);
    });
  }
}
