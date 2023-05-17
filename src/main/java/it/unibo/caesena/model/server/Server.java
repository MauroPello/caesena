package it.unibo.caesena.model.server;

import java.util.List;

import it.unibo.caesena.model.Game;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Servers")
@Table(name = "Servers")
@Access(AccessType.FIELD)
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final int serverID;
    @ManyToOne
    @JoinColumn(name = "fk_region")
    private final Region region;
    private final boolean active;
    private final int maxGames;
    @OneToMany
    @JoinColumn(name = "fk_game")
    private final List<Game> games;

    public Server(int serverID, Region region, boolean active, int maxGames, List<Game> games) {
        this.serverID = serverID;
        this.region = region;
        this.active = active;
        this.maxGames = maxGames;
        this.games = games;
    }

    public int getServerID() {
        return serverID;
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

    public Region getRegion() {
        return region;
    }
}