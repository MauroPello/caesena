package it.unibo.caesena.model.gameset;

/**
 * This is an enum class that allows us to identify the various
 * types of gamesets and its relative initial and final scores.
 */
public enum GameSetType {
    /**
     * GameSet of type field.
     */
    FIELD(0, 1),
    /**
     * GameSet of type city.
     */
    CITY(2, 2),
    /**
     * GameSet of type monastery.
     */
    MONASTERY(1, 1),
    /**
     * GameSet of type road.
     */
    ROAD(1, 1),
    /**
     * GameSet of type junction.
     */
    JUNCTION(0, 1);

    private final int startingPoints;
    private final int endGameRatio;

    /**
     * Class constructor.
     *
     * @param startingPoints points related to GameSet initialization.
     * @param endGameRatio ratio of related points at the end of the game
     */
    GameSetType(final int startingPoints, final int endGameRatio) {
        this.startingPoints = startingPoints;
        this.endGameRatio = endGameRatio;
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
}
