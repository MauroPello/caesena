package it.unibo.caesena.model.gameset;

import java.util.List;

import it.unibo.caesena.model.meeple.Meeple;

/**
 * An interface defining a GameSet.
 * Defines a set of tiles containing roads, towns, monasteries, and fields,
 * each of which contains a score and, optionally, meeples.
 */
public interface GameSet {

    /**
     * Adds a meeple to the current gameset
     *
     * @param meeple meeple to be placed
     */
    void addMeeple(Meeple meeple);

    /**
     * 
     * @return Returns true if meeples are present, false otherwise
     */
    boolean isMeepleFree();

    /**
     * After checking that the gameset has not already been closed and that meeples are present in it,
     * a map is created with which its owner is associated for each meeple,
     * and for each meeple found, it increments its integer (quantity) value.
     * Then, via streams, the owner having the most meeples is found,
     * and before closing, points from that GameSet are added to the relevant player.
     *
     * @return true if gameset was closed, false otherwise
     */
    boolean close();

    /**
     * 
     * @return type of current GameSet (city, road, ...)
     */
    GameSetType getType();

    /**
     * 
     * @return if current GameSet is close
     */
    boolean isClosed();

    /**
     * 
     * @return points of current GameSet
     */
    int getPoints();

    /**
     * Set points of current GameSet.
     * 
     * @param points for current GameSet
     */
    void setPoints(int points);

    /**
     * Add points in current GameSet.
     * 
     * @param points added with those already present
     */
    void addPoints(int points);

    /**
     * 
     * @return list of meeples in current GameSet
     */
    List<Meeple> getMeeples();

}
