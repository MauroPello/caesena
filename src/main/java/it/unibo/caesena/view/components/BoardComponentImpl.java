package it.unibo.caesena.view.components;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;

public class BoardComponentImpl extends JPanel implements BoardComponent<JPanel> {
    private final static int DEFAULT_ZOOM_LEVEL = 5;
    private final Controller controller;
    private final Set<TileButton> allTileButtons;
    private JPanel currentTileButtonsContainer;
    private int currentFieldSize = DEFAULT_ZOOM_LEVEL;
    private int currentZoom = 0;
    private int currentHorizontalOffset = 0;
    private int currentVerticalOffset = 0;
    private Optional<TileButton> currentTileButtonPlaced = Optional.empty();

    public BoardComponentImpl(Controller controller) {
        this.controller = controller;
        this.allTileButtons = new HashSet<>();
        this.drawBoard();
    }

    private void drawBoard() {
        this.removeAll();
        this.currentTileButtonsContainer = getSquareJPanel();
        int minimum = this.currentZoom - DEFAULT_ZOOM_LEVEL/2;
        int maximum = DEFAULT_ZOOM_LEVEL - currentZoom - DEFAULT_ZOOM_LEVEL/2;
        this.currentFieldSize = DEFAULT_ZOOM_LEVEL - (currentZoom * 2);
        this.currentTileButtonsContainer.setLayout(new GridLayout(this.currentFieldSize, this.currentFieldSize));
        updateTileButtonList();
        for (int i = minimum; i < maximum; i++) {
            for (int j = minimum; j < maximum; j++) {
                int horizontalCoordinate = currentHorizontalOffset + i;
                int verticalCoordinate = currentVerticalOffset + j;
                TileButton fieldCell = findTileButton(horizontalCoordinate, verticalCoordinate);
                currentTileButtonsContainer.add(fieldCell);
            }
        }
        this.add(currentTileButtonsContainer);
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

    private TileButton findTileButton(int horizontalCoordinate, int verticalCoordinate) {
        TileButton searchedTile;
        Pair<Integer, Integer> coordinatesAsPair = new Pair<Integer,Integer>(horizontalCoordinate, verticalCoordinate);
        Optional<TileButton> searchedTileOptional = allTileButtons.stream()
            .filter(x -> x.getPosition().equals(coordinatesAsPair))
            .findFirst();
        if (searchedTileOptional.isEmpty()) {
            searchedTile = new TileButton(horizontalCoordinate, verticalCoordinate, getTileButtonActionListener());
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

    public String getCurrentTileImageResourcePath() {
        return this.controller.getCurrentTile().getImageResourcesPath();
    }

    public TileButton getCurrentlyPlacedTileButton() {
        return currentTileButtonPlaced.get();
    }

    public void setCurrentlyPlacedTileButton(TileButton tileButton) {
        currentTileButtonPlaced = Optional.of(tileButton);
    }

    private ActionListener getTileButtonActionListener() {
        return (e) -> {
            TileButton selectedTileButton = (TileButton)e.getSource();
            if (this.controller.isValidPositionForCurrentTile(selectedTileButton.getPosition())) {
                if (currentTileButtonPlaced.isPresent()){
                    TileButton lastTileButtonPlaced = currentTileButtonPlaced.get();
                    if (!currentTileButtonPlaced.equals(selectedTileButton) && !lastTileButtonPlaced.isLocked()) {
                        lastTileButtonPlaced.removeTile();
                    }
                }
                currentTileButtonPlaced = Optional.of(selectedTileButton);
                currentTileButtonPlaced.get().addTile(this.controller.getCurrentTile());
            }
        };
    }

    //TODO implementare
    // private void setMeeple(TileButton tileButton) {
    //     tileButton.setMeeple();
    // }

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
            currentHorizontalOffset--;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move up but was not allowed");
        }
    }

    @Override
    public void moveDown() {
        if(canMoveUp()) {
            currentHorizontalOffset++;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move down but was not allowed");
        }
    }

    @Override
    public void moveLeft() {
        if(canMoveUp()) {
            currentVerticalOffset--;
            drawBoard();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move laft but was not allowed");
        }
    }

    @Override
    public void moveRight() {
        if(canMoveUp()) {
            currentVerticalOffset++;
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

}
