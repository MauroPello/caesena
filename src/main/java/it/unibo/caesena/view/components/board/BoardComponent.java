package it.unibo.caesena.view.components.board;

import java.util.Optional;

import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.tile.TileButton;

public interface BoardComponent<X> {

    void zoomIn();

    void zoomOut();

    void move(Direction direction);

    boolean canZoomIn();

    boolean canZoomOut();

    boolean canMove(Direction direction);

    void draw();

    void removePlacedTile();

    void updateMeeplePrecence();

    <T> TileButton<T> getCurrentTileButton();

    Optional<Pair<Integer, Integer>> getUnlockedTileButtonPosition();

    void placeTile();

    X getComponent();
}
