package it.unibo.caesena.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Color;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.TileButton;
import java.awt.event.*;

public class GameView extends View {
    private final static int DEFAULT_ZOOM_LEVEL = 5;
    private int currentZoomOffset = (DEFAULT_ZOOM_LEVEL/2)+1;
    private int currentZoomLevel = DEFAULT_ZOOM_LEVEL;
    private Controller controller;
    private Set<TileButton> tileButtons = new HashSet<>();

    public GameView(UserInterface userInterface) {
        super(userInterface);
        this.controller = getUserInterface().getController();

        this.setLayout(new BorderLayout());
        this.add(getField(),BorderLayout.CENTER);
        this.add(new JButton("South"),BorderLayout.SOUTH);
        this.add(getMapControls(),BorderLayout.EAST);
        //DEBUG IN ATTESA DELLE ALTRE VIEW
        Color color1 = Color.createColor("FF0000", "Red");
        Color color2 = Color.createColor("00FF00", "Green");
        this.controller.addPlayer("Giocatore1", color1);
        this.controller.addPlayer("Giocatore2", color2);
        //FINE DEBUG
        this.controller.startGame();
        getFirstTile();
   }

    private void getFirstTile() {
        Tile firstTile = controller.getCurrentTile();
        TileButton firstTileButton = tileButtons.stream()
            .filter(x -> x.getPosition().equals(getCenterPosition()))
            .findFirst()
            .get();
        setTile(firstTileButton, firstTile.getImageResourcesPath());
    }

    private Pair<Integer, Integer> getCenterPosition() {
        final int value = DEFAULT_ZOOM_LEVEL/2;
        return new Pair<Integer,Integer>(value, value);
    }

    private Component getField() { // c'è tutta la questione delle immagini storte ecc.
        JPanel OuterPanel = new JPanel();
        JPanel field = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            public Dimension getPreferredSize() {
                Dimension d = this.getParent().getSize();
                int newSize = d.width > d.height ? d.height : d.width;
                newSize = newSize == 0 ? 100 : newSize;
                return new Dimension(newSize, newSize);
            }
        };
        OuterPanel.add(field);
        field.setSize(10, 10);
        field.setLayout(new GridLayout(DEFAULT_ZOOM_LEVEL, DEFAULT_ZOOM_LEVEL));
        for (int i = 0; i < DEFAULT_ZOOM_LEVEL; i++) {
            for (int j = 0; j < DEFAULT_ZOOM_LEVEL; j++) {
                TileButton fieldCell = new TileButton(j, i);//trovare altro valore
                field.add(fieldCell);
                tileButtons.add(fieldCell);
                fieldCell.addActionListener(OnSelection());
                fieldCell.addComponentListener(OnResizeOrShown());
            }
        }
        return OuterPanel;
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

    private Component getMapControls() {
        JPanel OuterPanel = new JPanel();
        JPanel innerPanel = new JPanel();

        OuterPanel.setBackground(java.awt.Color.BLACK);
        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 1;

        JButton zoomInButton = new JButton("ZOOM +");
        JButton zoomOutButton = new JButton("ZOOM -");
        JButton moveUpButton = new JButton("UP");
        JButton moveLeftButton = new JButton("LEFT");
        JButton moveRightButton = new JButton("RIGHT");
        JButton moveDownButton = new JButton("DOWN");
        JButton placeTile = new JButton("PLACETILE");
        JButton placeMeeple = new JButton("PLACEMEEPLE");
        JButton endTurnButton = new JButton("ENDTURN");

        innerPanel.add(zoomInButton, constraints);
        constraints.gridy ++;
        innerPanel.add(zoomOutButton, constraints);
        constraints.gridy ++;
        innerPanel.add(moveUpButton, constraints);
        constraints.gridy ++;
        constraints.gridx --;
        innerPanel.add(moveLeftButton, constraints);
        constraints.gridx ++;
        constraints.gridx ++;
        innerPanel.add(moveRightButton, constraints);
        constraints.gridx --;
        constraints.gridy ++;
        innerPanel.add(moveDownButton, constraints);
        constraints.gridy ++;
        innerPanel.add(placeTile, constraints);
        constraints.gridy ++;
        innerPanel.add(placeMeeple, constraints);
        constraints.gridy ++;
        innerPanel.add(endTurnButton, constraints);
        constraints.gridy ++;

        zoomInButton.addActionListener(zoomInEventListener());
        zoomOutButton.addActionListener(zoomOutEventListener());

        OuterPanel.add(innerPanel);
        return OuterPanel;
    }

    private ActionListener zoomInEventListener() {
        return (e) -> {
            var border = getBorderAtOffest(currentZoomOffset);
            tileButtons.stream()
                .filter(x -> x.isVisible())
                .filter(x -> border.contains(x.getPosition()))
                .forEach(x -> x.setVisible(false));
            currentZoomOffset--;
            //repaint();
        };
    }


    private ActionListener zoomOutEventListener() {
    return (e) -> {
        tileButtons.stream()
            .filter(x -> !x.isVisible())
            .filter(x -> getBorderAtOffest(currentZoomOffset+1).contains(x.getPosition()))
            .forEach(x -> x.setVisible(true));
            currentZoomOffset++;
            //repaint();
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

    private boolean tileButtonIsInBound(TileButton tileButton) {
        var centerRowOrCol = getCenterPosition().getX();
        var firstRowOrCol = centerRowOrCol - (currentZoomOffset);
        var lastRowOrCol = centerRowOrCol + (currentZoomOffset);
        var position = tileButton.getPosition();
        return position.getX().intValue() >= firstRowOrCol
            && position.getY().intValue() >= firstRowOrCol
            && position.getX().intValue() <= lastRowOrCol
            && position.getY().intValue() <= lastRowOrCol;
    }

    private Set<Pair<Integer,Integer>> getNewVisibleCells() {
        var centerRowOrCol = getCenterPosition().getX();
        var firstRowOrCol = centerRowOrCol - (currentZoomOffset);
        var lastRowOrCol = centerRowOrCol + (currentZoomOffset);
        return tileButtons.stream()
            .map(x -> x.getPosition())
            .filter(x -> x.getX().intValue() >= firstRowOrCol && x.getY().intValue() >= firstRowOrCol)
            .filter(x -> x.getX().intValue() <= lastRowOrCol && x.getY().intValue() <= lastRowOrCol)
            .collect(Collectors.toSet());
    }

    private void getTableTop() {

    }

    private void setTile(TileButton tileButton, String Image) {
        tileButton.setActualTile(Image);
    }

    private void setMeeple(TileButton tileButton) {
        tileButton.setMeeple();
    }

}
