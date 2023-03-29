package it.unibo.caesena.view.components;

import javax.swing.JButton;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;

public interface BoardComponent<X> {

    void zoomIn();

    void zoomOut();

    void move(Direction direction);

    boolean canZoomIn();

    boolean canZoomOut();

    boolean canMove(Direction Direction);

    void draw();

    void removePlacedTile();
    //TODO controlla se servono
    boolean isTileButtonPlaced();

    TileButton<JButton> getPlacedTileButton();

    Pair<Integer, Integer> getTileButtonPosition(TileButton<JButton> tileButton);

    X getComponent();
}
