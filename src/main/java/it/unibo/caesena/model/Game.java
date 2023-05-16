package it.unibo.caesena.model;

import java.util.List;

import it.unibo.caesena.model.player.PlayerInGame;
import it.unibo.caesena.model.server.Server;
import it.unibo.caesena.model.tile.Tile;

public class Game {
    private final int gameID;
    private final Server server;
    private final List<Expansion> expansions;
    private final List<Tile> tiles;
    private final List<PlayerInGame> playersInGame;

    public Game(int gameID, Server server, List<Expansion> expansions, List<Tile> tiles,
            List<PlayerInGame> playersInGame) {
        this.gameID = gameID;
        this.server = server;
        this.expansions = expansions;
        this.tiles = tiles;
        this.playersInGame = playersInGame;
    }
}
