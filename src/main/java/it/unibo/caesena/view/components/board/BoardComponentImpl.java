package it.unibo.caesena.view.components.board;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.tile.TileButton;
import it.unibo.caesena.view.components.tile.TileButtonImpl;
import it.unibo.caesena.view.scene.GameScene;

/**
 * {@inheritDoc}
 *
 * Implements the interface {@link it.unibo.caesena.view.components.board.BoardComponent} using a {@link javax.swing.JPanel}.
 */
final class BoardComponentImpl extends JPanel implements BoardComponent<JPanel> {
    private static final long serialVersionUID = -8835542981559590335L;
    private static final int DEFAULT_ZOOM_LEVEL = 5;
    private static final int MAX_FIELD_SIZE = 50;
    private final GameScene gameScene;
    private final Map<TileButton<JButton>, Pair<Integer, Integer>> allTileButtons;
    private int fieldSize = DEFAULT_ZOOM_LEVEL;
    private int zoom;
    private int horizontalOffset;
    private int verticalOffset;

    /**
     * Class constructor.
     *
     * @param gameScene the parent GameScene
     */
    BoardComponentImpl(final GameScene gameScene) {
        this.gameScene = gameScene;
        this.zoom = 0;
        this.horizontalOffset = 0;
        this.verticalOffset = 0;
        this.allTileButtons = new HashMap<>();
        this.setOpaque(false);
        this.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            this.draw();
        }
        super.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        this.removeAll();
        this.updateMapContent();
        this.fieldSize = DEFAULT_ZOOM_LEVEL - (zoom * 2);
        this.setLayout(new GridLayout(fieldSize, fieldSize));
        getTileButtonsToBeDrawn(this.horizontalOffset, this.verticalOffset, this.zoom)
                .forEach(t -> this.add(t.getComponent()));
        this.repaint();
        this.validate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        final Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void zoomIn() {
        if (canZoomIn()) {
            zoom++;
            draw();
        } else {
            throw new IllegalStateException("Tried to zoom in but was not allowed");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void zoomOut() {
        if (canZoomOut()) {
            zoom--;
            draw();
        } else {
            throw new IllegalStateException("Tried to zoom out but was not allowed");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final Direction direction) {
        if (canMove(direction)) {
            this.verticalOffset += direction.getY();
            this.horizontalOffset += direction.getX();
            draw();
        } else {
            throw new IllegalStateException("Tried to move up but was not allowed");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canZoomIn() {
        return this.fieldSize > 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canZoomOut() {
        return fieldSize < this.getHeight() / MAX_FIELD_SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove(final Direction direction) {
        final int tempVerticalOffset = this.verticalOffset + direction.getY();
        final int tempHorizontalOffset = this.horizontalOffset + direction.getX();
        return getTileButtonsToBeDrawn(tempHorizontalOffset, tempVerticalOffset, this.zoom).stream()
                .anyMatch(x -> x.containsTile());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getComponent() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePlacedTile() {
        if (this.getPlacedUnlockedTile().isPresent()) {
            this.getPlacedUnlockedTile().get().removeTile();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeTile() {
        if (this.getPlacedUnlockedTile().isPresent()) {
            this.getPlacedUnlockedTile().get().lock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<Integer, Integer>> getUnlockedTileButtonPosition() {
        final Optional<TileButton<JButton>> unlockedTileButton = this.getPlacedUnlockedTile();
        if (unlockedTileButton.isPresent()) {
            return Optional.of(allTileButtons.get(unlockedTileButton.get()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public TileButton<JButton> getCurrentTileButton() {
        return findTileButton(gameScene.getUserInterface().getController().getCurrentTile().get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMeeplePresence() {
        allTileButtons.keySet().stream()
                .filter(TileButton::containsTile)
                .filter(t -> t.getMeeple().isPresent())
                .filter(t -> !t.getMeeple().get().isPlaced())
                .forEach(t -> t.unsetMeeple());
    }

    /**
     * Gets a list of the TileButtons that have to be drawn.
     * It does so basing itseft by the horizontal offset, the vertical offset and
     * the zoom level.
     *
     * @param horizontalOffset is the horizontal offset from which to draw the tile
     * @param verticalOffset   is the horizontal offset from which to draw the tile
     * @param zoom             is the level of zoom, it can be seen as the ammount
     *                         of lines/rows to draw
     * @return the list of the TileButtons that have to be drawn
     */
    private List<TileButton<JButton>> getTileButtonsToBeDrawn(final int horizontalOffset, final int verticalOffset,
            final int zoom) {
        final List<TileButton<JButton>> tileButtons = new ArrayList<>();
        final int minimum = zoom - DEFAULT_ZOOM_LEVEL / 2;
        final int maximum = DEFAULT_ZOOM_LEVEL - zoom - DEFAULT_ZOOM_LEVEL / 2;
        for (int i = minimum; i < maximum; i++) {
            for (int j = minimum; j < maximum; j++) {
                tileButtons.add(findTileButton(horizontalOffset + j, verticalOffset + i));
            }
        }
        return tileButtons;
    }

    /**
     * Finds the TileButton that is present at a given pair of coordinates.
     * If the TileButton doesn't exists, this method will create it, but it won't
     * place it or lock it.
     *
     * @param horizontalCoordinate used to find the needed TileButton
     * @param verticalCoordinate   used to find the needed TileButton
     * @return the TileButton that is present at a given pair of coordinates.
     */
    private TileButton<JButton> findTileButton(final int horizontalCoordinate, final int verticalCoordinate) {
        TileButton<JButton> foundTileButton;
        final Pair<Integer, Integer> coordinates = new Pair<>(horizontalCoordinate, verticalCoordinate);
        final Optional<TileButton<JButton>> searchedTileButton = allTileButtons.entrySet().stream()
        .filter(x -> x.getValue().equals(coordinates))
                .map(x -> x.getKey())
                .findFirst();
        if (searchedTileButton.isEmpty()) {
            foundTileButton = new TileButtonImpl(getTileButtonActionListener());
            allTileButtons.put(foundTileButton, coordinates);
        } else {
            foundTileButton = searchedTileButton.get();
        }

        return foundTileButton;
    }

    /**
     * Checks wheter or not there are any new tiles or meeples placed on the board.
     */
    public void updateMapContent() {
        final Controller controller = this.gameScene.getUserInterface().getController();
        final List<Meeple> placedMeeples = controller.getMeeples().stream()
            .filter(m -> m.isPlaced())
            .toList();
        final List<Tile> placedTiles = controller.getPlacedTiles();
        for (var tile : placedTiles) {
            TileButton<JButton> tileButton = findTileButton(tile);
            tileButton.addTile(tile);
            tileButton.lock();
            Optional<Meeple> meepleInTile = placedMeeples.stream()
                .filter(m -> m.getPosition().getX().equals(tile))
                .findFirst();
            if (meepleInTile.isPresent()) {
                tileButton.setMeeple(meepleInTile.get());
            } else if (tileButton.getMeeple().isPresent()) {
                tileButton.unsetMeeple();
            }
        }

    }

    /**
     * Defines the action listener that every TileButton should have.
     *
     * @return the action listener that every TileButton should have
     */
    private ActionListener getTileButtonActionListener() {
        return (e) -> {
            final TileButtonImpl selectedTileButton = (TileButtonImpl) e.getSource();
            final Controller controller = this.gameScene.getUserInterface().getController();
            if (controller.isPositionValidForCurrentTile(this.allTileButtons.get(selectedTileButton))) {
                getPlacedUnlockedTile().ifPresent(TileButton::removeTile);
                selectedTileButton.setTileImage(gameScene.getCurrentTileImage());
            }
        };
    }

    /**
     * Finds the TileButton corrisponding to any tile.
     * If the TileButton doesn't exists, this method will create it, but it won't
     * place it or lock it.
     *
     * @param tile to search the corrisponding TileButton
     * @return the TileButton corrisponding to the given tile
     */
    private TileButton<JButton> findTileButton(final Tile tile) {
        final Pair<Integer, Integer> tilePosition = tile.getPosition().get();
        return findTileButton(tilePosition.getX(), tilePosition.getY());
    }

/**
 * Gets the current TileButton as long as its placed but not locked.
 *
 * @return the current TileButton as long as its placed but not locked
 */
    private Optional<TileButton<JButton>> getPlacedUnlockedTile() {
        return allTileButtons.keySet().stream()
                .filter(k -> !k.isLocked() && k.containsTile())
                .findFirst();
    }
}
