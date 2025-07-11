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

package me.sytex.endpoint.api.routes;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import me.sytex.endpoint.api.exception.ApiException;
import me.sytex.endpoint.api.response.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface Route<T> {
  Logger log = LoggerFactory.getLogger(Route.class);

  ApiResponse<T> processRequest(@NotNull Context ctx) throws ApiException;

  default void handleRequest(@NotNull Context ctx) {
    try {
      ApiResponse<T> response = processRequest(ctx);
      sendJsonResponse(ctx, 200, response);
    } catch (ValidationException e) {
      handleValidationError(ctx, e);
    } catch (ApiException e) {
      handleApiError(ctx, e);
    } catch (Exception e) {
      handleInternalError(ctx, e);
    }
  }

  private void handleValidationError(Context ctx, ValidationException e) {
    log.debug("Validation error: {}", e.getMessage());
    sendJsonResponse(ctx, 400, ApiResponse.error(e.getMessage()));
  }

  private void handleApiError(Context ctx, ApiException e) {
    log.warn("API error: {}", e.getMessage());
    sendJsonResponse(ctx, e.getStatusCode(), ApiResponse.error(e.getMessage()));
  }

  private void handleInternalError(Context ctx, Exception e) {
    log.error("Internal server error", e);
    sendJsonResponse(ctx, 500, ApiResponse.error("Internal Server Error"));
  }

  private void sendJsonResponse(Context ctx, int status, ApiResponse<?> response) {
    ctx.contentType("application/json");
    ctx.status(status).json(response);
  }
}

