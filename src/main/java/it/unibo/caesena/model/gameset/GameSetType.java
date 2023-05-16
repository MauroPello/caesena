package it.unibo.caesena.model.gameset;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an enum class that allows us to identify the various
 * types of gamesets and its relative initial and final scores.
 */
public class GameSetType {

    public static List<GameSetType> values = new ArrayList<>();

    private final String name;
    private final int startingPoints;
    private final int endGameRatio;
    // private final Expansion expansion;

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
