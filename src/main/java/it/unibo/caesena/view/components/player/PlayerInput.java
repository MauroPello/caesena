package it.unibo.caesena.view.components.player;

import java.awt.Color;

import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.BasicComponent;

/**
 * An interface defining a component that allows to enter all the needed
 * information for a player that wants to take part in the game.
 *
 * @param <X> the type of the GUI component containing the player input
 */
public interface PlayerInput<X> extends BasicComponent<X> {

    /**
     * Sets the forced size of the PlayerImageImpl component.
     *
     * @param size to be forced
     */
    void setPlayerImageSize(int size);

    /**
     * Gets all the entered information by the player.
     *
     * @return all the entered information by the player
     */
    Pair<String, Color> getPlayerData();
}
