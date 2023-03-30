package it.unibo.caesena.view.components;

public interface FooterComponent<X> {

    /**
     * 
     * update the JPanel with the current tile image.
     */
    void updateCurrentTile();

    /**
     * 
     * @return ComponentFoooter.
     */
    X getComponent();

    /**
     * 
     * update every footer's component.
     */
    void updateFooter();

    /**
     * 
     * update the number of remaining tiles.
     */
    void updateRemainingTiles();
}
