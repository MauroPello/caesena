package it.unibo.caesena.view.components.player;

import it.unibo.caesena.view.BasicComponent;

import java.awt.Color;

/**
 * An interface describing the image of a player.
 *
 * @param <X> the type of the GUI component containing the player image
 */
public interface PlayerImage<X> extends BasicComponent<X> {

    /**
     * Sets the color for the player image.
     *
     * @param color to be set for the player image
     */
    void setColor(Color color);

}
