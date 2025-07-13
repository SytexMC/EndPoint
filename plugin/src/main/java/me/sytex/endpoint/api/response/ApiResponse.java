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
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {

  @SerializedName("success")
  private final boolean success;

  @SerializedName("timestamp")
  private final long timestamp;

  @Nullable
  @SerializedName("context")
  private final String context;

  @Nullable
  @SerializedName("data")
  private final T data;

  @Nullable
  @SerializedName("meta")
  private final Map<String, Object> meta;
}