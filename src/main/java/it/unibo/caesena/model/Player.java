package it.unibo.caesena.model;

/**
 * This interface models a player. It needs to have a Name, a Color and a
 * current score.
 */
public interface Player {

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    String getName();

    /**
     * Gets the color of the player.
     *
     * @return the color of the player
     */
    Color getColor();

    /**
     * Gets the current score of the player.
     *
     * @return the current score of the player
     */
    int getScore();

    /**
     * Sets a new score, if higher than the current.
     *
     * @param newScore for the player
     */
    void setScore(int newScore);

    /**
     * Adds the given amount to the current score of the player.
     * Beware that the given amount has to be positive or 0.
     *
     * @param amount to add to the current score
     */
    void addScore(int amount);
}
