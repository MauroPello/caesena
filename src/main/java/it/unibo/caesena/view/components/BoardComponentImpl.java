package it.unibo.caesena.view.components;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.awt.*;
import javax.swing.JPanel;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.GUI;

public class BoardComponentImpl extends JPanel implements BoardComponent<JPanel> {
    private final static int DEFAULT_ZOOM_LEVEL = 5;
    private final GUI gui;
    private final Set<TileButton> allTileButtons;
    private JPanel currentTileButtonsContainer;
    private int currentFieldSize = DEFAULT_ZOOM_LEVEL;
    private int currentZoom = 0;
    private int currentHorizontalOffset = 0;
    private int currentVerticalOffset = 0;
    private boolean showBoard = true;
    private Optional<SectionSelectorComponent> currentOverlayedTile = Optional.empty();
    //TODO rimuovi questo, basta usare uno stream su alltilebuttons
    private Optional<TileButton> currentTileButtonPlaced = Optional.empty();

    public BoardComponentImpl(GUI gui) {
        this.gui = gui;
        this.allTileButtons = new HashSet<>();
        this.drawBoard();
        //TODO rimuovere
        this.setBackground(Color.CYAN);
    }

    @Override
    public void updateComponents() {
        if (showBoard) {
            drawBoard();
        } else {
            drawOverlayedTile();
        }
    }


    public void drawBoard() {
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
                TileButton fieldCell = findTileButton(horizontalCoordinate, verticalCoordinate);
                //TODO cast forse un po' bruttino, capiamo come fare
                currentTileButtonsContainer.add((TileButtonImpl)fieldCell);
            }
        }
        this.add(currentTileButtonsContainer);
        this.repaint();
        this.validate();
    }

    private void updateTileButtonList() {
        List<Tile> placedTiles = gui.getController().getPlacedTiles();
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
            searchedTile = new TileButtonImpl(horizontalCoordinate, verticalCoordinate, this);
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

    private void drawOverlayedTile() {
        this.removeAll();
        var overlayedTile = new SectionSelectorComponentImpl(this.gui.getController().getCurrentTile(), this.currentTileButtonsContainer.getSize());
        this.currentOverlayedTile = Optional.of(overlayedTile);
        this.add(overlayedTile);
        this.validate();
        this.repaint();
    }

    @Override
    public void endTurn() {
        if (this.currentOverlayedTile.isPresent() && currentOverlayedTile.get().isSectionSelected()) {
            var section = this.currentOverlayedTile.get().getSelectedSection();
            Optional<Meeple> meeple = this.gui.getController().getCurrentPlayerMeeples().stream().filter(m -> !m.isPlaced()).findFirst();
            if (meeple.isPresent()) {
                if (this.gui.getController().placeMeeple(meeple.get(), section)) {
                    this.currentTileButtonPlaced.get().addMeeple(meeple.get(), section);
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
    }



    @Override
    public GUI getGUI() {
        return this.gui;
    }

    @Override
    public TileButton getPlacedTileButton() {
        return currentTileButtonPlaced.orElseThrow(()->new IllegalStateException("Tried to get placed TileButton but wasn't placed"));
    }

    @Override
    public boolean isTileButtonPlaced() {
        return currentTileButtonPlaced.isPresent();
    }

    @Override
    public void setPlacedTileButton(TileButton tileButton) {
        this.currentTileButtonPlaced = Optional.of(tileButton);
    }

    @Override
    public void toggleBoardContent() {
        if (showBoard) {
            drawOverlayedTile();
        } else {
            drawBoard();
        }
        showBoard = !showBoard;
    }
}
