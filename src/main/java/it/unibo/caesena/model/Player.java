package it.unibo.caesena.model;

public interface Player {

    /**
     * 
     * @return name of the player
     */
    String getName();

    /**
     * 
     * @return score of the player
     */
    int getScore();

    /**
     * 
     * @param newScore set new score for current player
     */
    void setScore(int newScore);

    /**
     * 
     * @param amount add score for current player
     */
    void addScore(int amount);
}
