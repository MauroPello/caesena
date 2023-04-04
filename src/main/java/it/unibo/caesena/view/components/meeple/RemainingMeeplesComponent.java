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
