package it.unibo.caesena.view.components.common;

import it.unibo.caesena.view.BasicComponent;

/**
 * An interface defining a component that only allows numeric values and
 * possibly includes buttons to increase/decrease the contained value.
 *
 * @param <X> the type of the GUI component containing the numeric up down
 */
public interface NumericUpDown<X> extends BasicComponent<X> {

    /**
     * Gets the value contained as an int.
     *
     * @return the value contained as an int.
     */
    int getValueAsInt();

}
