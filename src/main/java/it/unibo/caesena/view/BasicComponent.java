package it.unibo.caesena.view;

/**
 * An interface representing a basic Component used in the view package.
 *
 * @param <X> the type of the GUI component used
 */
public interface BasicComponent<X> {
    /**
     * Gets whether or not the component is visible.
     *
     * @return whether or not the component is visible
     */
    boolean isVisible();

    /**
     * Sets whether or not the component should be visible.
     *
     * @param visible boolean representing whether or not the component should be
     *                visible
     */
    void setVisible(boolean visible);

    /**
     * Gets the GUI component.
     *
     * @return the GUI component
     */
    X getComponent();
}
