package it.unibo.caesena.model.server;

import java.util.List;

import it.unibo.caesena.model.Game;

public class Server {
    private final int serverID;
    private final int regionID;
    private final boolean active;
    private final int maxGames;
    private final List<Game> games;

    public Server(int serverID, int regionID, boolean active, int maxGames, List<Game> games) {
        this.serverID = serverID;
        this.regionID = regionID;
        this.active = active;
        this.maxGames = maxGames;
        this.games = games;
    }

    public int getServerID() {
        return serverID;
    }

    public int getRegionID() {
        return regionID;
    }

    public boolean isActive() {
        return active;
    }

    public int getMaxGames() {
        return maxGames;
    }

    public List<Game> getGames() {
        return games;
    }
}
