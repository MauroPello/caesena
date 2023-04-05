package it.unibo.caesena.view;

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
