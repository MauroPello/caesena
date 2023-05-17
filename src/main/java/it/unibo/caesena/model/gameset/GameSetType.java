package it.unibo.caesena.model.gameset;

import java.util.ArrayList;
import java.util.List;

import it.unibo.caesena.model.Expansion;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * This is an enum class that allows us to identify the various
 * types of gamesets and its relative initial and final scores.
 */
@Entity(name = "GameSetTypes")
@Table(name = "GameSetTypes")
@Access(AccessType.FIELD)
public class GameSetType {

    @Transient
    public static List<GameSetType> values = new ArrayList<>();

    @Id
    private final String name;

    @OneToMany(mappedBy = "type")
    private List<GameSetImpl> gameSets;

    @ManyToOne
    private Expansion expansion;

    private final int startingPoints;
    private final int endGameRatio;

    /**
     * Class constructor.
     *
     * @param startingPoints points related to GameSet initialization.
     * @param endGameRatio ratio of related points at the end of the game
     */
    public GameSetType(final String name, final int startingPoints, final int endGameRatio) {
        this.startingPoints = startingPoints;
        this.endGameRatio = endGameRatio;
        this.name = name;
        GameSetType.values.add(this);
    }

    /**
     *
     * @return name of a specific GameSet.
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return starting points of a specific GameSet.
     */
    public int getStartingPoints() {
        return this.startingPoints;
    }

    /**
     *
     * @return division of points at the end of the game.
     */
    public int getEndGameRatio() {
        return this.endGameRatio;
    }

    public static GameSetType getFromName(final String name) {
        return values.stream().filter(s -> s.getName().equals(name)).findFirst().get();
    }
}
