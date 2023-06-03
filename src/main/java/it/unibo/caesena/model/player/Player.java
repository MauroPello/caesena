package it.unibo.caesena.model.player;

import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "players")
@Table(name = "players")
@Access(AccessType.FIELD)
public class Player {

    @Id
    private String name;

    @OneToMany(mappedBy = "player")
    private List<PlayerInGameImpl> playersInGame;

    public Player() {}

    public Player(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Player other = (Player) obj;
        return name.equals(other.getName());
    }

}
