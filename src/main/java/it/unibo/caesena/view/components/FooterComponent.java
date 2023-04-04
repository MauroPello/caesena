package it.unibo.caesena.view.components;

/**
 * TODO
 */
public interface FooterComponent<X> {

    /**
     * Updates the current tile image.
     */
    void updateCurrentTile();

    /**
     * Gets the component which represents the footer.
     * 
     * @return the component which represents the footer.
     */
    X getComponent();

    /**
     * Updates every component in the footer.
     */
    void update();
}
