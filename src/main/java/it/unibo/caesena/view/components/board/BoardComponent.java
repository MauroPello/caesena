package it.unibo.caesena.view.components.board;

import java.util.Optional;

import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.tile.TileButton;

/**
 * This interface rappresents the board of the game.
 * It will contain TileButtons and has to be able to zoom and move around the
 * board.
 *
 * @param <X> the type of the GUI component
 */
public interface BoardComponent<X> {

    /**
     * Sets whether or not the component should be visible.
     *
     * @param visible boolean representing whether or not the component should be
     *                visible
     */
    void setVisible(boolean visible);

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
     * Draws the board, can be seen as a refresh of the board.
     */
    void draw();

    /**
     * Remove the curretly placed tile.
     */
    void removePlacedTile();

    /**
     * Updates the presence of meeples.
     * This is necessary to sync different user interfaces.
     */
    void updateMeeplePresence();

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

    /**
     * Gets the Board as a specific component of a GUI framework, such as JPanel.
     *
     * @return the Board as a specific component of a GUI framework.
     */
    X getComponent();
}
