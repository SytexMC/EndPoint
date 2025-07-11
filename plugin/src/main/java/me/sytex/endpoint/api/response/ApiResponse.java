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

package me.sytex.endpoint.api.response;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApiResponse<T> {

  @Getter @SerializedName("success")
  private final boolean success;

  @Getter @SerializedName("data")
  private final T data;

  @Getter @SerializedName("message")
  private final String message;

  @Getter @SerializedName("timestamp")
  private final long timestamp;

  @Getter @SerializedName("meta")
  private final Map<String, Object> meta;

  private ApiResponse(boolean success, @Nullable T data, @Nullable Map<String, Object> meta, @Nullable String message) {
    this.success = success;
    this.data = data;
    this.message = message;
    this.timestamp = System.currentTimeMillis();
    this.meta = meta != null ? meta : new HashMap<>();
  }

  public static <T> @NotNull ApiResponse<T> success(@NotNull T data) {
    return new ApiResponse<>(true, data, null, null);
  }

  public static <T> @NotNull ApiResponse<T> success(@NotNull T data, @NotNull String message) {
    return new ApiResponse<>(true, data, null, message);
  }

  public static <T> @NotNull ApiResponse<T> error(@NotNull String message) {
    return new ApiResponse<>(false, null, null, message);
  }
}
