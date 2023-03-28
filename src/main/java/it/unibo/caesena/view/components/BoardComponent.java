package it.unibo.caesena.view.components;

import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.view.GUI;

public interface BoardComponent<X> {

    void zoomIn();

    void zoomOut();

    void move(Direction direction);

    void lockTile();

    void endTurn();

    boolean canZoomIn();

    boolean canZoomOut();

    boolean canMove(Direction Direction);

    void updateComponents();

    void removePlacedTile();

    TileButton getCurrentlySelectedTileButton();

    GUI getGUI();

    boolean isTileButtonPlaced();

    TileButton getPlacedTileButton();

    void setPlacedTileButton(TileButton tileButton);

    void toggleBoardContent();

    X getComponent();
}
