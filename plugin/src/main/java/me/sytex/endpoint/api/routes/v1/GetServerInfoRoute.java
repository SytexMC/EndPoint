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

package me.sytex.endpoint.api.routes.v1;

import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import me.sytex.endpoint.api.exception.ApiException;
import me.sytex.endpoint.api.model.ServerInfoModel;
import me.sytex.endpoint.api.response.ApiResponse;
import me.sytex.endpoint.api.routes.AutoRoute;
import me.sytex.endpoint.api.routes.Route;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

@AutoRoute(path = "/api/v1/server", method = HandlerType.GET)
public class GetServerInfoRoute implements Route<ServerInfoModel> {

  @Override
  public ApiResponse<ServerInfoModel> processRequest(@NotNull Context ctx) throws ApiException {
    ServerInfoModel info = ServerInfoModel.builder()
        .software(Bukkit.getName())
        .version(Bukkit.getVersion())
        .motd(MiniMessage.miniMessage().serialize(Bukkit.motd()))
        .build();

    return ApiResponse.<ServerInfoModel>builder()
        .success(true)
        .timestamp(System.currentTimeMillis())
        .context("Server info retrieved successfully.")
        .data(info)
        .build();
  }
}
