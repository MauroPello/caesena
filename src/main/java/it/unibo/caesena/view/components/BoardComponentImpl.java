package it.unibo.caesena.view.components;

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
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.GameView;

public class BoardComponentImpl extends JPanel implements BoardComponent<JPanel> {
    private final static int DEFAULT_ZOOM_LEVEL = 5;
    private final GameView gameView;
    private final Map<TileButton<JButton>, Pair<Integer, Integer>> allTileButtons;
    private int fieldSize = DEFAULT_ZOOM_LEVEL;
    private int zoom = 0;
    private int horizontalOffset = 0;
    private int verticalOffset = 0;
    private Optional<TileButton<JButton>> lastTileButtonLocked;

    public BoardComponentImpl(final GameView gameView) {
        this.gameView = gameView;
        this.lastTileButtonLocked = Optional.empty();
        this.allTileButtons = new HashMap<>();
        this.setFirstTileButton();
        this.draw();
    }

    @Override
    public void draw() {
        this.removeAll();
        this.fieldSize = DEFAULT_ZOOM_LEVEL - (zoom * 2);
        this.setLayout(new GridLayout(fieldSize, fieldSize));
        final List<TileButton<JButton>> tileButtonsToBeDrawn = getTileButtonsToBeDrawn(this.horizontalOffset, this.verticalOffset, this.zoom);
        for (final TileButton<JButton> tileButton : tileButtonsToBeDrawn) {
            this.add(tileButton.getComponent());
        }
        this.repaint();
        this.validate();
    }

    @Override
    public Dimension getPreferredSize() {
        final Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }

    @Override
    public void zoomIn() {
        if (canZoomIn()) {
            zoom++;
            draw();
        }
        else {
            throw new IllegalStateException("Tried to zoom in but was not allowed");
        }
    }

    @Override
    public void zoomOut() {
        if (canZoomOut()) {
            zoom--;
            draw();
        } else {
            throw new IllegalStateException("Tried to zoom out but was not allowed");
        }
    }

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

    @Override
    public boolean canZoomIn() {
        return this.fieldSize > 1;
    }

    @Override
    public boolean canZoomOut() {
        return fieldSize < this.getHeight() / 50;
    }

    @Override
    public boolean canMove(final Direction direction) {
        final int tempVerticalOffset = this.verticalOffset + direction.getY();
        final int tempHorizontalOffset = this.verticalOffset + direction.getX();
        return getTileButtonsToBeDrawn(tempHorizontalOffset, tempVerticalOffset, this.zoom).stream()
                .anyMatch(x -> x.containsTile());
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    public TileButton<JButton> getLastTileButton() {
        return this.lastTileButtonLocked.get();
    }

    @Override
    public boolean hasPlacedTileButtonInCurrentTurn() {
        return this.lastTileButtonLocked.isPresent();
    }

    @Override
    public void removePlacedTile() {
        if (!this.getPlacedUnlockedTile().isEmpty()) {
            this.getPlacedUnlockedTile().get().removeTile();
        }
    }

    @Override
    public void placeTile() {
        if (this.getPlacedUnlockedTile().isPresent()) {
            this.lastTileButtonLocked = this.getPlacedUnlockedTile();
            this.getPlacedUnlockedTile().ifPresent(TileButton::lock);
        }
    }

    @Override
    public void endTurn() {
        this.lastTileButtonLocked = Optional.empty();
    }

    private List<TileButton<JButton>> getTileButtonsToBeDrawn(final int horizontalOffset, final int verticalOffset,
            final int zoom) {
        final List<TileButton<JButton>> tileButtons = new ArrayList<>();
        final int minimum = zoom - DEFAULT_ZOOM_LEVEL / 2;
        final int maximum = DEFAULT_ZOOM_LEVEL - zoom - DEFAULT_ZOOM_LEVEL / 2;
        for (int i = minimum; i < maximum; i++) {
            for (int j = minimum; j < maximum; j++) {
                final int horizontalCoordinate = horizontalOffset + j;
                final int verticalCoordinate = verticalOffset + i;
                final TileButton<JButton> fieldCell = findTileButton(horizontalCoordinate, verticalCoordinate);
                tileButtons.add(fieldCell);
            }
        }
        return tileButtons;
    }

    private void setFirstTileButton() {
        final Controller controller = this.gameView.getUserInterface().getController();
        final var placedTile = controller.getPlacedTiles();
        for (final Tile tile : placedTile) {
            final TileButton<JButton> button = findTileButton(tile).get();
            button.addTile(new TileImage(tile));
            button.lock();
        }
    }

    private TileButton<JButton> findTileButton(final int horizontalCoordinate, final int verticalCoordinate) {
        TileButton<JButton> searchedTile;
        final Pair<Integer, Integer> coordinates = new Pair<>(horizontalCoordinate, verticalCoordinate);
        final Optional<TileButton<JButton>> searchedTileOptional = allTileButtons.entrySet().stream()
                .filter(x -> x.getValue().equals(coordinates))
                .map(x -> x.getKey())
                .findFirst();
        if (searchedTileOptional.isEmpty()) {
            searchedTile = new TileButtonImpl(getTileButtonActionListener(), this.gameView);
            allTileButtons.put(searchedTile, coordinates);
        } else {
            searchedTile = searchedTileOptional.get();
        }
        return searchedTile;
    }

    private ActionListener getTileButtonActionListener() {
        return (e) -> {
            final TileButtonImpl selectedTileButton = (TileButtonImpl) e.getSource();
            final Controller controller = this.gameView.getUserInterface().getController();
            if (getPlacedUnlockedTile().isEmpty() || !getPlacedUnlockedTile().get().isLocked()
                    && controller.isPositionValidForCurrentTile(this.allTileButtons.get(selectedTileButton))) {
                if (this.getPlacedUnlockedTile().isPresent()) {
                    final TileButton<JButton> lastTileButtonPlaced = this.getPlacedUnlockedTile().get();
                    if (!lastTileButtonPlaced.isLocked()) {
                        lastTileButtonPlaced.removeTile();
                    }
                }
                selectedTileButton.addTile();
            }
        };
    }



    private Optional<TileButton<JButton>> findTileButton(final Tile tile) {
        if (!tile.isPlaced()) {
            return Optional.empty();
        } else {
            final Pair<Integer, Integer> tilePosition = tile.getPosition().get();
            return Optional.of(findTileButton(tilePosition.getX(), tilePosition.getY()));
        }
    }

    public Optional<TileButton<JButton>> getPlacedUnlockedTile() {
        return allTileButtons.keySet().stream()
                .filter(k -> !k.isLocked() && k.containsTile())
                .findFirst();
    }

    @Override
    public Optional<Pair<Integer, Integer>> getUnlockedTileButtonPosition() {
        Optional<TileButton<JButton>> unlockedTileButton = this.getPlacedUnlockedTile();
        if (unlockedTileButton.isPresent()) {
            return Optional.of(allTileButtons.get(unlockedTileButton.get()));
        } else {
            return Optional.empty();
        }
    }
}
