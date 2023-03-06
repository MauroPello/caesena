package it.unibo.caesena.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Color;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.TileButton;

public class GameView extends View {
    private final static int DEFAULT_ZOOM_LEVEL = 5;

    private int currentZoomLevel = DEFAULT_ZOOM_LEVEL;
    private Controller controller;
    private Set<TileButton> tileButtons = new HashSet<>();

    public GameView(UserInterface userInterface) {
        super(userInterface);
        this.controller = getUserInterface().getController();

        this.setLayout(new BorderLayout());
        this.add(getField(),BorderLayout.CENTER);
        this.add(new JButton("South"),BorderLayout.SOUTH);
        this.add(new JButton("East"),BorderLayout.EAST);
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

    private Component getField() {
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
        field.setLayout(new GridLayout(getHorizontalNumber(), getVerticalNumber()));
        for (int i = 0; i < currentZoomLevel; i++) {
            for (int j = 0; j < currentZoomLevel; j++) {
                TileButton fieldCell = new TileButton(j, i);
                field.add(fieldCell);
                tileButtons.add(fieldCell);
                fieldCell.addActionListener( (e) -> {
                    Tile tile = controller.getCurrentTile();
                    TileButton tileButton = (TileButton)e.getSource();
                    setTile(tileButton, tile.getImageResourcesPath());
                    repaint();
                });
            }
        }
        return OuterPanel;
    }

    private int getVerticalNumber() {
        return currentZoomLevel;
    }

    private int getHorizontalNumber() {
        return currentZoomLevel;
    }

    private void drawMapControls() {

    }

    private void drawTableTop() {

    }

    private void setTile(TileButton tileButton, String Image) {
        tileButton.setActualTile(Image);
    }

    private void setMeeple(TileButton tileButton) {
        tileButton.setMeeple();
    }

}
