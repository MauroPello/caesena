package it.unibo.caesena.view.components;

import java.util.Optional;

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

    boolean hasPlacedTileButtonInCurrentTurn();

    TileButton<JButton> getLastTileButton();

    Pair<Integer, Integer> getTileButtonPosition(TileButton<JButton> tileButton);

    Optional<TileButton<JButton>> getPlacedUnlockedTile();

    void placeTile();

    void endTurn();

    X getComponent();
}
