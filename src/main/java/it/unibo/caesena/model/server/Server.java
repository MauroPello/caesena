package it.unibo.caesena.model.server;

import java.util.List;

import it.unibo.caesena.model.Game;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "servers")
@Table(name = "servers")
@Access(AccessType.FIELD)
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Region region;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
    private List<Game> games;

    @Column(name = "max_games")
    private int maxGames;

    private boolean active;

    public Server() {}

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public int getMaxGames() {
        return maxGames;
    }

    public Region getRegion() {
        return region;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        Server other = (Server) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return region.toString() + " #" + id;
    }

}
