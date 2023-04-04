package it.unibo.caesena.view.components.common;

/**
 * An interface defining a component that only allows numeric values and
 * possibly includes buttons to increase/decrease the contained value.
 *
 * @param <X> the type of the GUI component containing the numeric up down
 */
public interface NumericUpDown<X> {

    /**
     * Gets the value contained as an int.
     *
     * @return the value contained as an int.
     */
    int getValueAsInt();

    /**
     * Gets the GUI component containing the NumericUpDown.
     *
     * @return the GUI component containing the NumericUpDown
     */
    X getComponent();

}
