package it.unibo.caesena.view.components.meeple;

/**
 * This interface rappresents a meeple component to be used in Scene.
 *
 * This component shows as many meeples image as the remaining ones
 *
 * @param <X> the type of the GameScene component
 */
public interface RemainingMeeplesComponent<X> {

    /**
     * Sets whether or not the component should be visible.
     *
     * @param visible boolean representing whether or not the component should be
     *                visible
     */
    void setVisible(boolean visible);

    /**
     * Updates the current tile image.
     */
    void update();

    /**
     * Gets the component which represents the footer.
     *
     * @return the component which represents the footer.
     */
    X getComponent();
}
