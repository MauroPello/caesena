package it.unibo.caesena.view.components;

import it.unibo.caesena.model.tile.Tile;

public interface BoardComponent<X> {

    void zoomIn();

    void zoomOut();

    void moveUp();

    void moveDown();

    void moveLeft();

    void moveRight();

    void lockTile();

    void placeMeeple(Tile tile);

    boolean canZoomIn();

    boolean canZoomOut();

    boolean canMoveUp();

    boolean canMoveDown();

    boolean canMoveLeft();

    boolean canMoveRight();

    TileButton getCurrentlySelectedTileButton();

    X getComponent();
}
