package it.unibo.caesena.view.components;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;

public class BoardComponentImpl extends JPanel implements BoardComponent<JPanel> {
    private final static int DEFAULT_ZOOM_LEVEL = 5;
    private final Controller controller;
    private final Set<TileButtonImpl> allTileButtons;
    private JPanel currentTileButtonsContainer;
    private int currentFieldSize = DEFAULT_ZOOM_LEVEL;
    private int currentZoom = 0;
    private int currentHorizontalOffset = 0;
    private int currentVerticalOffset = 0;
    private Optional<SectionSelectorComponentImpl> currentOverlayedTile = Optional.empty();
    private Optional<TileButtonImpl> currentTileButtonPlaced = Optional.empty();

    public BoardComponentImpl(Controller controller) {
        this.controller = controller;
        this.allTileButtons = new HashSet<>();
        this.drawBoard();
    }

    private void drawBoard() {
        currentOverlayedTile = Optional.empty();
        this.removeAll();
        this.currentTileButtonsContainer = getSquareJPanel();
        int minimum = this.currentZoom - DEFAULT_ZOOM_LEVEL/2;
        int maximum = DEFAULT_ZOOM_LEVEL - currentZoom - DEFAULT_ZOOM_LEVEL/2;
        this.currentFieldSize = DEFAULT_ZOOM_LEVEL - (currentZoom * 2);
        this.currentTileButtonsContainer.setLayout(new GridLayout(this.currentFieldSize, this.currentFieldSize));
        updateTileButtonList();
        for (int i = minimum; i < maximum; i++) {
            for (int j = minimum; j < maximum; j++) {
                int horizontalCoordinate = currentHorizontalOffset + j;
                int verticalCoordinate = currentVerticalOffset + i;
                TileButtonImpl fieldCell = findTileButton(horizontalCoordinate, verticalCoordinate);
                currentTileButtonsContainer.add(fieldCell);
            }
        }
        this.add(currentTileButtonsContainer);
        this.repaint();
        this.validate();
    }



    private void undrawBoard() {
        this.removeAll();
        this.repaint();
        this.validate();
    }

    private void updateTileButtonList() {
        List<Tile> placedTiles = controller.getPlacedTiles();
        for (Tile tile : placedTiles) {
            TileButton button = findTileButton(tile);
            button.addTile(tile);
            button.lockTile();
        }
    }

    private TileButtonImpl findTileButton(int horizontalCoordinate, int verticalCoordinate) {
        TileButtonImpl searchedTile;
        Pair<Integer, Integer> coordinatesAsPair = new Pair<Integer,Integer>(horizontalCoordinate, verticalCoordinate);
        Optional<TileButtonImpl> searchedTileOptional = allTileButtons.stream()
            .filter(x -> x.getPosition().equals(coordinatesAsPair))
            .findFirst();
        if (searchedTileOptional.isEmpty()) {
            searchedTile = new TileButtonImpl(horizontalCoordinate, verticalCoordinate, getTileButtonActionListener());
            allTileButtons.add(searchedTile);
        } else {
            searchedTile = searchedTileOptional.get();
        }
        return searchedTile;
    }

    private TileButton findTileButton(Tile tile) {
        Pair<Integer, Integer> tilePosition = tile.getPosition().get();
        return findTileButton(tilePosition.getX(), tilePosition.getY());
    }

    private JPanel getSquareJPanel() {
        return new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            public Dimension getPreferredSize() {
                Dimension d = this.getParent().getSize();
                int newSize = d.width > d.height ? d.height : d.width;
                newSize = newSize == 0 ? 100 : newSize;
                return new Dimension(newSize, newSize);
            }
        };
    }

    private ActionListener getTileButtonActionListener() {
        return (e) -> {
            TileButtonImpl selectedTileButton = (TileButtonImpl)e.getSource();
            if (this.controller.isValidPositionForCurrentTile(selectedTileButton.getPosition())) {
                if (currentTileButtonPlaced.isPresent()){
                    TileButtonImpl lastTileButtonPlaced = currentTileButtonPlaced.get();
                    if (!lastTileButtonPlaced.isLocked()) {
                        lastTileButtonPlaced.removeTile();
                    }
                }
                currentTileButtonPlaced = Optional.of(selectedTileButton);
                currentTileButtonPlaced.get().addTile(this.controller.getCurrentTile());
            }
        };
    }

    @Override
    public void zoomIn() {
        if (canZoomIn()) {
            currentZoom++;
            drawBoard();
            repaint();
        }
        else {
            throw new IllegalStateException("Tried to zoom in but was not allowed");
        }
    }

    @Override
    public void zoomOut() {
        if(canZoomOut()) {
            currentZoom--;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to zoom out but was not allowed");
        }
    }

    @Override
    public void moveUp() {
        if(canMoveUp()) {
            currentVerticalOffset--;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move up but was not allowed");
        }
    }

    @Override
    public void moveDown() {
        if(canMoveUp()) {
            currentVerticalOffset++;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move down but was not allowed");
        }
    }

    @Override
    public void moveLeft() {
        if(canMoveUp()) {
            currentHorizontalOffset--;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move laft but was not allowed");
        }
    }

    @Override
    public void moveRight() {
        if(canMoveUp()) {
            currentHorizontalOffset++;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move laft but was not allowed");
        }
    }

    @Override
    public boolean canZoomIn() {
        return true;
    }
    //TODO tutti sti controlli
    @Override
    public boolean canZoomOut() {
        return true;
    }

    @Override
    public boolean canMoveUp() {
        return true;
    }

    @Override
    public boolean canMoveDown() {
        return true;
    }

    @Override
    public boolean canMoveLeft() {
        return true;
    }

    @Override
    public boolean canMoveRight() {
        return true;
    }

    @Override
    public JPanel getComponent() {
       return this;
    }

    @Override
    public void lockTile() {
        currentTileButtonPlaced.get().lockTile();
    }

    @Override
    public TileButton getCurrentlySelectedTileButton() {
        return currentTileButtonPlaced.get();
    }

    @Override
    public void placeMeeple(Tile tile) {
        undrawBoard();
        drawOverlayedTile();
    }

    private void drawOverlayedTile() {
        var overlayedTile = new SectionSelectorComponentImpl(this.controller.getCurrentTile(), this.currentTileButtonsContainer.getSize());
        this.currentOverlayedTile = Optional.of(overlayedTile);
        this.add(overlayedTile);
        this.validate();
        this.repaint();
    }

    @Override
    public void endTurn() {
        if (this.currentOverlayedTile.isPresent()) {
            var section = this.currentOverlayedTile.get().getSelectedSection();
            Optional<Meeple> meeple = this.controller.getCurrentPlayerMeeples().stream().filter(m -> !m.isPlaced()).findFirst();
            if (meeple.isPresent()) {
                if (this.controller.placeMeeple(meeple.get(), section)) {
                    this.currentTileButtonPlaced.get().addMeeple(meeple.get());
                } else {
                    throw new IllegalStateException("Tried to add meeple but gameSet already had at least one");
                }
            } else {
                throw new IllegalStateException("Tried to add meeple but run out of them");
            }
        }
        drawBoard();
    }
}
