package it.unibo.caesena.view.components;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;
import java.awt.event.*;

public class BoardComponentImpl extends JPanel implements BoardComponent {
    private final static int DEFAULT_ZOOM_LEVEL = 5;
    private final Controller controller;
    private final Set<TileButton> tileButtons;
    private int currentZoomOffset = (DEFAULT_ZOOM_LEVEL/2)+1;

    //TODO capire se ha senso avere Tile, un'interfaccia del model, direttamente nella view.
    public BoardComponentImpl(Controller controller, Tile tile) {
        this.controller = controller;
        this.tileButtons = new HashSet<>();
        JPanel field = getSquareJPanel();
        this.add(field);
        field.setLayout(new GridLayout(DEFAULT_ZOOM_LEVEL, DEFAULT_ZOOM_LEVEL));
        this.populateField(field);
        this.setFirstTile(tile);
    }

    private void setFirstTile(Tile tile) {
        Pair<Integer, Integer> centerPosition = getCenterPosition();
        TileButton firstTileButton = tileButtons.stream()
            .filter(x -> x.getPosition().equals(centerPosition))
            .findFirst().get();
        setTile(firstTileButton, tile.getImageResourcesPath());
    }

    private void populateField(JPanel field) {
        for (int i = 0; i < DEFAULT_ZOOM_LEVEL; i++) {
            for (int j = 0; j < DEFAULT_ZOOM_LEVEL; j++) {
                TileButton fieldCell = new TileButton(j, i);//trovare altro valore
                field.add(fieldCell);
                tileButtons.add(fieldCell);
                fieldCell.addActionListener(OnSelection());
                fieldCell.addComponentListener(OnResizeOrShown());
            }
        }
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

    private Set<Pair<Integer, Integer>> getBorderAtOffest(int offset) {
        Set<Pair<Integer, Integer>> border = new HashSet<>();
        var centerRowOrCol = getCenterPosition().getX();
        var firstRowOrCol = centerRowOrCol - (offset - 1);
        var lastRowOrCol = centerRowOrCol + (offset - 1);
        for (int i = firstRowOrCol; i < lastRowOrCol+1; i++) {
            border.add(new Pair<Integer, Integer>(i, firstRowOrCol));
            border.add(new Pair<Integer, Integer>(i, lastRowOrCol));
        }
        for (int i = firstRowOrCol; i < lastRowOrCol+1; i++) {
            border.add(new Pair<Integer, Integer>(firstRowOrCol, i));
            border.add(new Pair<Integer, Integer>(lastRowOrCol, i));
        }
        return border;
    }

    private Pair<Integer, Integer> getCenterPosition() {
        final int value = DEFAULT_ZOOM_LEVEL/2;
        return new Pair<Integer,Integer>(value, value);
    }

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

    private ActionListener OnSelection() {
        return (e) -> {
            Tile tile = controller.getCurrentTile();
            TileButton tileButton = (TileButton)e.getSource();
            setTile(tileButton, tile.getImageResourcesPath());
            tileButton.resize();
            repaint();
        };
    }

    private void setTile(TileButton tileButton, String Image) {
        tileButton.setActualTile(Image);
    }

    //TODO implementare
    // private void setMeeple(TileButton tileButton) {
    //     tileButton.setMeeple();
    // }

    public ActionListener zoomInEventListener() {
        return (e) -> zoomIn();
    }

    public ActionListener zoomOutEventListener() {
        return (e) -> zoomOut();
    }

    @Override
    public void zoomIn() {
        var border = getBorderAtOffest(currentZoomOffset);
            tileButtons.stream()
                .filter(x -> x.isVisible())
                .filter(x -> border.contains(x.getPosition()))
                .forEach(x -> x.setVisible(false));
            currentZoomOffset--;
    }

    @Override
    public void zoomOut() {
        tileButtons.stream()
            .filter(x -> !x.isVisible())
            .filter(x -> getBorderAtOffest(currentZoomOffset+1).contains(x.getPosition()))
            .forEach(x -> x.setVisible(true));
            currentZoomOffset++;
    }

    @Override
    public void moveUp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveUp'");
    }

    @Override
    public void moveDown() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveDown'");
    }

    @Override
    public void moveLeft() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveLeft'");
    }

    @Override
    public void moveRight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveRight'");
    }

}
