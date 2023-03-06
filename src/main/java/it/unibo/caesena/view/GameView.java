package it.unibo.caesena.view;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.view.components.TileButton;

public class GameView extends View {
    private final static int DEFAULT_ZOOM_LEVEL = 10;

    private int currentZoomLevel = DEFAULT_ZOOM_LEVEL;
    private Controller controller;
    private Set<TileButton> tileButtons;

    public GameView(UserInterface userInterface) {
        super(userInterface);
        controller = getUserInterface().getController();

        this.setLayout(new BorderLayout());
        this.add(getField(),BorderLayout.CENTER);
        this.add(new JButton("South"),BorderLayout.SOUTH);
        this.add(new JButton("East"),BorderLayout.EAST);
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
                JButton fieldCell = new JButton();
                field.add(fieldCell);
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

    public void drawMapControls() {

    }

    public void drawTableTop() {

    }

}
