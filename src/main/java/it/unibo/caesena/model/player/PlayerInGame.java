package it.unibo.caesena.model.player;

import java.util.Set;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.meeple.MeepleImpl;

/**
 * This interface models a player. It needs to have a Name, a Color and a
 * current score.
 */
public interface PlayerInGame {

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

    public Set<MeepleImpl> getMeeples();
}
