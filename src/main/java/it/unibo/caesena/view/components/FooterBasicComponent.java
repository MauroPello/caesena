package it.unibo.caesena.view.components;

import it.unibo.caesena.view.UpdatableComponent;

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
public interface FooterBasicComponent<X> extends UpdatableComponent<X> {

    /**
     * Updates the current tile image.
     */
    void updateCurrentTile();
}
