package it.unibo.caesena.view.components;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.view.GUI;

public interface BoardComponent<X> {

    void zoomIn();

    void zoomOut();

    void moveUp();

    void moveDown();

    void moveLeft();

    void moveRight();

    void lockTile();

    void placeMeeple(Tile tile);

    void endTurn();

    boolean canZoomIn();

    boolean canZoomOut();

    boolean canMoveUp();

    boolean canMoveDown();

    boolean canMoveLeft();

    boolean canMoveRight();

    TileButton getCurrentlySelectedTileButton();

    GUI getGUI();

    boolean isTileButtonPlaced();

    TileButton getPlacedTileButton();

    void setPlacedTileButton(TileButton tileButton);

    X getComponent();
}
