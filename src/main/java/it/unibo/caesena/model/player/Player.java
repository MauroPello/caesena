package it.unibo.caesena.model.player;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Players")
@Table(name = "Players")
@Access(AccessType.FIELD)
public class Player {

    @Id
    private final String name;

    @ManyToMany
    @JoinColumn(name = "fk_follower")
    private final List<Player> followers;

    @ManyToMany
    @JoinColumn(name = "fk_following")
    private final List<Player> following;

    @OneToMany(mappedBy = "player")
    private List<PlayerInGameImpl> playersInGame;

    public Player(final String name) {
        this.name = name;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    public List<Player> getFollowers() {
        return followers;
    }

    public List<Player> getFollowing() {
        return following;
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
