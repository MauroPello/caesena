package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.JPanel;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.GameView;

public class BoardComponentImpl extends JPanel implements BoardComponent<JPanel> {
    private final static int DEFAULT_ZOOM_LEVEL = 5;
    private final GUI gui;
    private final Set<TileButton> allTileButtons;
    private JPanel tileButtonsContainer;
    private int fieldSize = DEFAULT_ZOOM_LEVEL;
    private int zoom = 0;
    private int horizontalOffset = 0;
    private int verticalOffset = 0;
    private boolean showBoard = true;
    private Optional<SectionSelectorComponent> overlayedTile = Optional.empty();
    private Optional<TileButton> tileButtonPlaced = Optional.empty();

    public BoardComponentImpl(final GameView gameView) {
        this.gui = gameView.getUserInterface();
        this.allTileButtons = new HashSet<>();
        this.drawBoard();
        this.setBackground(Color.CYAN);
    }

    @Override
    public final void updateComponents() {
        if (showBoard) {
            drawBoard();
        } else {
            drawOverlayedTile();
        }
    }

    public final void drawBoard() {
        overlayedTile = Optional.empty();
        this.removeAll();
        this.tileButtonsContainer = getSquareJPanel();
        final int minimum = this.zoom - DEFAULT_ZOOM_LEVEL / 2;
        final int maximum = DEFAULT_ZOOM_LEVEL - zoom - DEFAULT_ZOOM_LEVEL / 2;
        this.fieldSize = DEFAULT_ZOOM_LEVEL - (zoom * 2);
        this.tileButtonsContainer.setLayout(new GridLayout(this.fieldSize, this.fieldSize));
        updateTileButtonList();
        for (int i = minimum; i < maximum; i++) {
            for (int j = minimum; j < maximum; j++) {
                final int horizontalCoordinate = horizontalOffset + j;
                final int verticalCoordinate = verticalOffset + i;
                final TileButton fieldCell = findTileButton(horizontalCoordinate, verticalCoordinate);
                // TODO cast forse un po' bruttino, capiamo come fare
                tileButtonsContainer.add((TileButtonImpl) fieldCell);
            }
        }
        this.add(tileButtonsContainer);
        this.repaint();
        this.validate();
    }

    private void updateTileButtonList() {
        final List<Tile> placedTiles = gui.getController().getPlacedTiles();
        for (final Tile tile : placedTiles) {
            final TileButton button = findTileButton(tile);
            button.addTile(tile);
            button.lockTile();
        }
    }

    private TileButton findTileButton(final int horizontalCoordinate, final int verticalCoordinate) {
        TileButton searchedTile;
        final Pair<Integer, Integer> coordinatesAsPair = new Pair<Integer, Integer>(horizontalCoordinate, verticalCoordinate);
        final Optional<TileButton> searchedTileOptional = allTileButtons.stream()
                .filter(x -> x.getPosition().equals(coordinatesAsPair))
                .findFirst();
        if (searchedTileOptional.isEmpty()) {
            searchedTile = new TileButtonImpl(horizontalCoordinate, verticalCoordinate, this);
            allTileButtons.add(searchedTile);
        } else {
            searchedTile = searchedTileOptional.get();
        }
        return searchedTile;
    }

    private TileButton findTileButton(final Tile tile) {
        final Pair<Integer, Integer> tilePosition = tile.getPosition().get();
        return findTileButton(tilePosition.getX(), tilePosition.getY());
    }

    private JPanel getSquareJPanel() {
        return new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public Dimension getPreferredSize() {
                final Dimension d = this.getParent().getSize();
                int newSize = d.width > d.height ? d.height : d.width;
                newSize = newSize == 0 ? 100 : newSize;
                return new Dimension(newSize, newSize);
            }
        };
    }

    @Override
    public final void zoomIn() {
        if (canZoomIn()) {
            zoom++;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to zoom in but was not allowed");
        }
    }

    @Override
    public final void zoomOut() {
        if (canZoomOut()) {
            zoom--;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to zoom out but was not allowed");
        }
    }

    @Override
    public final void move(final Direction direction) {
        if (canMove(direction)) {
            switch (direction) {
                case DOWN:
                    verticalOffset++;
                    break;
                case LEFT:
                    horizontalOffset--;
                    break;
                case RIGHT:
                    horizontalOffset++;
                    break;
                case UP:
                    verticalOffset--;
                    break;
                default:
                    break;
            }
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move up but was not allowed");
        }
    }

    @Override
    public final boolean canZoomIn() {
        return true;
    }

    @Override
    public final boolean canZoomOut() {
        return true;
    }

    @Override
    public final boolean canMove(final Direction direction) {
        return true;
    }

    @Override
    public final JPanel getComponent() {
        return this;
    }

    @Override
    public final void lockTile() {
        tileButtonPlaced.get().lockTile();
    }

    @Override
    public final TileButton getCurrentlySelectedTileButton() {
        return tileButtonPlaced.get();
    }

    private void drawOverlayedTile() {
        this.removeAll();
        final var overlayedTile = new SectionSelectorComponentImpl(this.gui.getController().getCurrentTile(),
                this.tileButtonsContainer.getSize());
        this.overlayedTile = Optional.of(overlayedTile);
        this.add(overlayedTile);
        this.validate();
        this.repaint();
    }

    @Override
    public final void endTurn() {
        if (this.overlayedTile.isPresent() && overlayedTile.get().isSectionSelected()) {
            final var section = this.overlayedTile.get().getSelectedSection();
            final var currentPlayer = this.gui.getController().getCurrentPlayer();
            final List<Meeple> meeples = this.gui.getController().getNotPlacedPlayerMeeples(currentPlayer);
            if (!meeples.isEmpty()) {
                if (this.gui.getController().placeMeeple(meeples.get(0), section)) {
                    this.tileButtonPlaced.get().addMeeple(meeples.get(0), section);
                } else {
                    throw new IllegalStateException("Tried to add meeple but gameSet already had at least one");
                }
            } else {
                throw new IllegalStateException("Tried to add meeple but run out of them");
            }
        }
        if (!showBoard) {
            toggleBoardContent();
        }
        this.tileButtonPlaced = Optional.empty();
        updateComponents();
    }

    @Override
    public final GUI getGUI() {
        return this.gui;
    }

    @Override
    public final TileButton getPlacedTileButton() {
        return tileButtonPlaced
                .orElseThrow(() -> new IllegalStateException("Tried to get placed TileButton but wasn't placed"));
    }

    @Override
    public final boolean isTileButtonPlaced() {
        return tileButtonPlaced.isPresent();
    }

    @Override
    public final void setPlacedTileButton(final TileButton tileButton) {
        if (!this.tileButtonPlaced.isPresent() || !this.tileButtonPlaced.get().isLocked()) {
            this.tileButtonPlaced = Optional.of(tileButton);
        }
    }

    @Override
    public final void toggleBoardContent() {
        if (showBoard) {
            drawOverlayedTile();
        } else {
            drawBoard();
        }
        showBoard = !showBoard;
    }

    @Override
    public final void removePlacedTile() {
        if (!this.tileButtonPlaced.isEmpty()) {
            this.tileButtonPlaced.get().removeTile();
            this.tileButtonPlaced = Optional.empty();
            updateComponents();
        }
    }
}
