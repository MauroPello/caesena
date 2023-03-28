package it.unibo.caesena.view.components;

import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionListener;

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
    //private JPanel tileButtonsContainer;//TODO serve?
    private final LayoutManager gridLayoutManager;
    private int fieldSize = DEFAULT_ZOOM_LEVEL;
    private int zoom = 0;
    private int horizontalOffset = 0;
    private int verticalOffset = 0;

    public BoardComponentImpl(final GameView gameView) {
        this.gridLayoutManager = new GridLayout(fieldSize, fieldSize);
        this.setLayout(this.gridLayoutManager);
        this.gameView = gameView;
        this.allTileButtons = new HashMap<>();
        this.draw();
        //TODO rimuovere
        this.setBackground(Color.CYAN);
    }

    private Optional<TileButton<JButton>> getCurrentlyUsedTileButton() {
        Tile currentTile = this.gameView.getUserInterface().getController().getCurrentTile();
        return allTileButtons.keySet().stream()
            .filter(tb -> tb.containsTile())
            .filter(tb -> tb.getContainedTile().equals(currentTile))
            .findFirst();
    }

    @Override
    public Pair<Integer, Integer> getTileButtonPosition(TileButton<JButton> tileButton) {
        return allTileButtons.get(tileButton);
    }

    public void draw() {
        this.removeAll();
        //this.tileButtonsContainer = getSquareJPanel();
        int minimum = this.zoom - DEFAULT_ZOOM_LEVEL/2;
        int maximum = DEFAULT_ZOOM_LEVEL - zoom - DEFAULT_ZOOM_LEVEL/2;
        this.fieldSize = DEFAULT_ZOOM_LEVEL - (zoom * 2);
        //this.tileButtonsContainer.setLayout(new GridLayout(this.fieldSize, this.fieldSize));
        updateTileButtonList();
        for (int i = minimum; i < maximum; i++) {
            for (int j = minimum; j < maximum; j++) {
                int horizontalCoordinate = horizontalOffset + j;
                int verticalCoordinate = verticalOffset + i;
                TileButton<JButton> fieldCell = findTileButton(horizontalCoordinate, verticalCoordinate);
                this.add(fieldCell.getComponent());
            }
        }
        //this.add(tileButtonsContainer);
        this.repaint();
        this.validate();
    }

    private void updateTileButtonList() {
        List<Tile> placedTiles = gameView.getUserInterface().getController().getPlacedTiles();
        for (Tile tile : placedTiles) {
            TileButton<JButton> button = findTileButton(tile);
            button.addTile(tile);
        }
    }

    private TileButton<JButton> findTileButton(int horizontalCoordinate, int verticalCoordinate) {
        TileButton<JButton> searchedTile;
        Pair<Integer, Integer> coordinates = new Pair<Integer,Integer>(horizontalCoordinate, verticalCoordinate);
        Optional<TileButton<JButton>> searchedTileOptional = allTileButtons.entrySet().stream()
            .filter(x -> x.getValue().equals(coordinates))
            .map(x -> x.getKey())
            .findFirst();
        if (searchedTileOptional.isEmpty()) {
            searchedTile = new TileButtonImpl(getTileButtonActionListener(), this);
            allTileButtons.put(searchedTile, coordinates);
        } else {
            searchedTile = searchedTileOptional.get();
        }
        return searchedTile;
    }

    //TODO rifare
    private ActionListener getTileButtonActionListener() {
        return (e) -> {
            TileButtonImpl selectedTileButton = (TileButtonImpl)e.getSource();
            Controller controller = this.gameView.getUserInterface().getController();
            if (controller.isValidPositionForCurrentTile(this.allTileButtons.get(selectedTileButton))) {
                if (this.isTileButtonPlaced()){
                    TileButton<JButton> lastTileButtonPlaced = this.getCurrentlyUsedTileButton().get();
                    if (!lastTileButtonPlaced.isLocked()) {
                        lastTileButtonPlaced.removeTile();
                    }
                }
                this.add(selectedTileButton);
                selectedTileButton.addTile(controller.getCurrentTile());
                this.draw();
            }
        };
    }

    private TileButton<JButton> findTileButton(Tile tile) {
        Pair<Integer, Integer> tilePosition = tile.getPosition().get();
        return findTileButton(tilePosition.getX(), tilePosition.getY());
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }

    @Override
    public void zoomIn() {
        if (canZoomIn()) {
            zoom++;
            draw();
            repaint();
        }
        else {
            throw new IllegalStateException("Tried to zoom in but was not allowed");
        }
    }

    @Override
    public void zoomOut() {
        if(canZoomOut()) {
            zoom--;
            draw();
            repaint();
        } else {
            throw new IllegalStateException("Tried to zoom out but was not allowed");
        }
    }

    @Override
    public void move(Direction direction) {
        if(canMove(direction)) {
            switch(direction) {
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
            draw();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move up but was not allowed");
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
    public boolean canMove(Direction Direction) {
        return true;
    }

    @Override
    public JPanel getComponent() {
       return this;
    }

    @Override
    public TileButton<JButton> getPlacedTileButton() {
        return getCurrentlyUsedTileButton().orElseThrow(()->new IllegalStateException("Tried to get placed TileButton but wasn't placed"));
    }

    @Override
    public boolean isTileButtonPlaced() {
        return getCurrentlyUsedTileButton().isPresent();
    }

    @Override
    public void removePlacedTile() {
        if (!this.getCurrentlyUsedTileButton().isEmpty()) {
            this.getCurrentlyUsedTileButton().get().removeTile();
        }
        //TODO vedere se questi refresh dovrebbe farli questo componente o un componente esterno
        draw();
    }
}
