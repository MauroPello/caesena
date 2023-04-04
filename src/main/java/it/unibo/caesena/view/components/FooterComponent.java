package it.unibo.caesena.view.components;

/**
 * This interface rappresents a footer to be used in Scene.
 * 
 * This component contains these inner components:
 * - TileImage
 * - RemainingMeepleComponent
 * 
 * the rest of the footer is made by JPanels and JLabels.
 * the informations for the update are taken by the GameScene
 * 
 *
 * @param <X> the type of the GameScene component
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
