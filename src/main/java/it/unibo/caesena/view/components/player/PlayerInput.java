package it.unibo.caesena.view.components.player;

import java.awt.Color;

import it.unibo.caesena.utils.Pair;

/**
 * An interface defining a component that allows to enter all the needed
 * information for a player that wants to take part in the game.
 */
public interface PlayerInput<X> {

    /**
     * Gets all the entered information by the player.
     *
     * @return all the entered information by the player
     */
    Pair<String, Color> getPlayerData();

    /**
     * Gets the GUI component containing the NumericUpDown.
     *
     * @return the GUI component containing the NumericUpDown
     */
    X getComponent();

}
