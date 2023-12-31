package it.unibo.caesena.view.components.board;

import java.util.Optional;

import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.UpdatableComponent;
import it.unibo.caesena.view.components.tile.TileButton;

/**
 * This interface rappresents the board of the game.
 * It will contain TileButtons and has to be able to zoom and move around the
 * board.
 *
 * @param <X> the type of the GUI component
 */
public interface BoardComponent<X> extends UpdatableComponent<X> {

    /**
     * Zooms in.
     */
    void zoomIn();

    /**
     * Zooms out.
     */
    void zoomOut();

    /**
     * Moves in the given direction.
     *
     * @param direction in which to move
     */
    void move(Direction direction);

    /**
     * Determinates if it's possibile to zoom in.
     *
     * @return true if it's possible to zoom in, false otherwise
     */
    boolean canZoomIn();

    /**
     * Determinates if it's possibile to zoom out.
     *
     * @return true if it's possible to zoom out, false otherwise
     */
    boolean canZoomOut();

    /**
     * Determinates if it's possibile to move in the given direction.
     *
     * @param direction in which to move
     * @return true if it's possible to zoom out, false otherwise
     */
    boolean canMove(Direction direction);

    /**
     * Remove the curretly placed tile.
     */
    void removePlacedTile();

    /**
     * Gets the TileButton of the current tile being played.
     *
     * @param <T> the type of the TileButton
     * @return the TileButton of the current tile being played
     */
    <T> TileButton<T> getCurrentTileButton();

    /**
     * Gets the position of the current tile but if is not locked.
     *
     * @return the position of the current tile but if is not locked
     */
    Optional<Pair<Integer, Integer>> getUnlockedTileButtonPosition();

    /**
     * Places the current tile.
     */
    void placeTile();

}
