package it.unibo.caesena.model.gameset;

import java.util.List;

import it.unibo.caesena.model.meeple.Meeple;

/**
 * An interface defining a GameSet
 */
public interface GameSet {

    /**
     * 
     * @param meeple to be placed
     * @return if meeple is added in current GameSet
     */
    boolean addMeeple(Meeple meeple);

    /**
     * 
     * @return if current GameSet have meeple
     */
    boolean isMeepleFree();

    /**
     * 
     * @return if current GameSet is properly close
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
     * Set points of current GameSet
     * 
     * @param points for current GameSet
     */
    void setPoints(int points);

    /**
     * Add points in current GameSet
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
