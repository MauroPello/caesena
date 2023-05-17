package it.unibo.caesena.model.server;

import java.util.List;

import it.unibo.caesena.model.Game;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Servers")
@Table(name = "Servers")
@Access(AccessType.FIELD)
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int serverID;

    @ManyToOne
    private final Region region;

    @OneToMany(mappedBy = "server")
    private final List<Game> games;

    private final boolean active;
    private final int maxGames;

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
