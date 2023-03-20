package it.unibo.caesena.view.components;

public interface FooterComponent<X> {
    void updateCurrentTile();
    
    X getComponent();

    void updateFooter();

    void updateCurrentPlayerMeeples();

    public void updateRemainingTiles();
}
