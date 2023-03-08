package it.unibo.caesena.view.components;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;
import java.awt.event.*;

public class BoardComponentImpl extends JPanel implements BoardComponent<JPanel> {
    private final static int DEFAULT_ZOOM_LEVEL = 11;
    private final Controller controller;
    private final Set<TileButton> visibleTileButtons;
    private final Set<TileButton> allTileButtons;
    private JPanel currentTileButtonsContainer;
    private int currentFieldSize = DEFAULT_ZOOM_LEVEL;
    private int currentZoom = 0;
    private int currentHorizontalOffset = 0;
    private int currentVerticalOffset = 0;

    //TODO capire se ha senso avere Tile, un'interfaccia del model, direttamente nella view.
    public BoardComponentImpl(Controller controller, Tile tile) {
        this.controller = controller;
        this.visibleTileButtons = new HashSet<>();
        this.allTileButtons = new HashSet<>();
        this.initField();
        this.setFirstTile(tile);
    }

    private void setFirstTile(Tile tile) {
        Pair<Integer, Integer> centerPosition = getCenterPosition();
        TileButton firstTileButton = allTileButtons.stream()
            .filter(x -> x.getPosition().equals(centerPosition))
            .findFirst().get();
        setTile(firstTileButton, tile.getImageResourcesPath());
    }

    private void setVisibleField() {
        visibleTileButtons.clear();
        this.removeAll();
        this.currentTileButtonsContainer = getSquareJPanel();

        int minimum = this.currentZoom;
        int maximum = DEFAULT_ZOOM_LEVEL - currentZoom;
        this.currentFieldSize = DEFAULT_ZOOM_LEVEL - (currentZoom * 2);

        this.currentTileButtonsContainer.setLayout(new GridLayout(this.currentFieldSize, this.currentFieldSize));
        for (int i = minimum; i < maximum; i++) {
            for (int j = minimum; j < maximum; j++) {
                int horizontalCoordinate = currentHorizontalOffset + i;
                int verticalCoordinate = currentVerticalOffset + j;
                TileButton fieldCell = findTileButton(horizontalCoordinate, verticalCoordinate);
                visibleTileButtons.add(fieldCell);
                currentTileButtonsContainer.add(fieldCell);
            }
        }
        this.add(currentTileButtonsContainer);
        this.repaint();
        this.validate();
    }

    private TileButton findTileButton(int horizontalCoordinate, int verticalCoordinate) {
        TileButton searchedTile;
        Pair<Integer, Integer> coordinatesAsPair = new Pair<Integer,Integer>(horizontalCoordinate, verticalCoordinate);
        Optional<TileButton> searchedTileOptional = allTileButtons.stream()
            .filter(x -> x.getPosition().equals(coordinatesAsPair))
            .findFirst();
        if (searchedTileOptional.isEmpty()) { //TODO non va
            searchedTile = new TileButton(horizontalCoordinate, verticalCoordinate, this);
            searchedTile.addComponentListener(OnResizeOrShown());
            allTileButtons.add(searchedTile);
        } else {
            searchedTile = searchedTileOptional.get();
        }
        return searchedTile;
    }

    private void initField() {
        for (int i = 0; i < DEFAULT_ZOOM_LEVEL; i++) {
            for (int j = 0; j < DEFAULT_ZOOM_LEVEL; j++) {
                TileButton fieldCell = new TileButton(j, i, this);
                allTileButtons.add(fieldCell);
                fieldCell.addComponentListener(OnResizeOrShown());
            }
        }
        setVisibleField();
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

    private Pair<Integer, Integer> getCenterPosition() {
        final int value = DEFAULT_ZOOM_LEVEL/2;
        return new Pair<Integer,Integer>(value, value);
    }

    public String getCurrentTileImageResourcePath() {
        return this.controller.getCurrentTile().getImageResourcesPath();
    }

    //TODO guardare shrink icon che diceva pello
    private ComponentListener OnResizeOrShown() {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                TileButton tileButton = (TileButton)e.getSource();
                tileButton.resize();
            }
            @Override
            public void componentShown(ComponentEvent e) {
                TileButton tileButton = (TileButton)e.getSource();
                tileButton.resize();
            }
        };
    }

    private void setTile(TileButton tileButton, String Image) {
        tileButton.setActualTile(Image);
    }

    //TODO implementare
    // private void setMeeple(TileButton tileButton) {
    //     tileButton.setMeeple();
    // }

    @Override
    public void zoomIn() {
        if (canZoomIn()) {
            currentZoom++;
            setVisibleField();
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
            setVisibleField();
            repaint();
        } else {
            throw new IllegalStateException("Tried to zoom out but was not allowed");
        }
    }

    @Override
    public void moveUp() {
        if(canMoveUp()) {
            currentHorizontalOffset--;
            setVisibleField();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move up but was not allowed");
        }
    }

    @Override
    public void moveDown() {
        if(canMoveUp()) {
            currentHorizontalOffset++;
            setVisibleField();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move down but was not allowed");
        }
    }

    @Override
    public void moveLeft() {
        if(canMoveUp()) {
            currentVerticalOffset--;
            setVisibleField();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move laft but was not allowed");
        }
    }

    @Override
    public void moveRight() {
        if(canMoveUp()) {
            currentVerticalOffset++;
            setVisibleField();
            repaint();
        } else {
            throw new IllegalStateException("Tried to move laft but was not allowed");
        }
    }

    @Override
    public boolean canZoomIn() {
        return visibleTileButtons.size() != 1;
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

}
