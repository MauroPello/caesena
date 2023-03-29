package it.unibo.caesena.model.gameset;

public enum GameSetType {
    FIELD(0, 1),
    CITY(2, 2),
    MONASTERY(1, 1),
    ROAD(1, 1),
    JUNCTION(0, 1);

    private final int startingPoints;
    private final int endGameRatio;

    GameSetType(final int startingPoints, final int endGameRatio) {
        this.startingPoints = startingPoints;
        this.endGameRatio = endGameRatio;
    }

    /**
     * 
     * @return starting points of a specific GameSet
     */
    public int getStartingPoints() {
        return this.startingPoints;
    }

    /**
     * 
     * @return division of points at the end of the game
     */
    public int getEndGameRatio() {
        return this.endGameRatio;
    }
}
