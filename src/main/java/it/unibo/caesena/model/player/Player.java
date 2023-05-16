package it.unibo.caesena.model.player;

import java.util.List;

public class Player {

    private final String name;
    // private final List<Player> followers;
    // private final List<Player> following;
    // TODO private final List<PlayerInGame> playersInGame;

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
