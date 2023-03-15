package it.unibo.caesena.view.components;

public interface BoardComponent<X> {

    void zoomIn();

    void zoomOut();

    void moveUp();

    void moveDown();

    void moveLeft();

    void moveRight();

    void lockTile();

    boolean canZoomIn();

    boolean canZoomOut();

    boolean canMoveUp();

    boolean canMoveDown();

    boolean canMoveLeft();

    boolean canMoveRight();

    TileButton getCurrentlySelectedTileButton();

    X getComponent();
}
