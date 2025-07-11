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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import me.sytex.endpoint.api.exception.ApiException;
import me.sytex.endpoint.api.model.PlayerModel;
import me.sytex.endpoint.api.response.ApiResponse;
import me.sytex.endpoint.api.routes.AutoRoute;
import me.sytex.endpoint.api.routes.Route;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@AutoRoute(path = "/api/v1/players", method = HandlerType.GET)
public class GetPlayersRoute implements Route<List<PlayerModel>> {

  @Override
  public ApiResponse<List<PlayerModel>> processRequest(@NotNull Context ctx) throws ApiException {
    List<PlayerModel> players = new ArrayList<>();

    for (Player player : Bukkit.getOnlinePlayers()) {
      players.add(PlayerModel.builder()
          .name(player.getName())
          .uuid(player.getUniqueId())
          .ip(Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress())
          .client(player.getClientBrandName())
          .build());
    }

    return ApiResponse.success(players);
  }
}

