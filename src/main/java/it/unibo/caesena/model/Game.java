package it.unibo.caesena.model;

import java.util.List;
import org.hibernate.Session;

import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.server.Server;
import it.unibo.caesena.model.tile.TileImpl;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Games")
@Table(name = "Games")
@Access(AccessType.FIELD)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameID;

    @OneToMany(mappedBy = "game", cascade=CascadeType.ALL)
    private List<PlayerInGameImpl> playersInGame;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Expansion> expansions;

    @OneToMany(mappedBy = "game",cascade=CascadeType.ALL)
    private List<TileImpl> tiles;

    @ManyToOne
    private Server server;

    private boolean concluded;

    public Game() {}

    public Game(final Session session, final Server server) {
        this.concluded = false;
        this.server = server;
    }

    public void setConcluded(final boolean concluded) {
        this.concluded = concluded;
    }

    public List<PlayerInGameImpl> getPlayersInGame() {
        return playersInGame;
    }

    public List<Expansion> getExpansions() {
        return expansions;
    }

    public Server getServer() {
        return server;
    }

    public int getGameID() {
        return gameID;
    }

    public boolean isConcluded() {
        return concluded;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + gameID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (gameID != other.gameID)
            return false;
        return true;
    }

}
