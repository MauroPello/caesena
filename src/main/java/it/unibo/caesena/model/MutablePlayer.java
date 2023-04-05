package it.unibo.caesena.model;

public interface MutablePlayer extends Player {
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
