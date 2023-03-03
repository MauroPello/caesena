package it.unibo.caesena.view;

import javax.swing.JButton;

import it.unibo.caesena.controller.Controller;

public class GameView extends View {
    private final static int DEFAULT_ZOOM_LEVEL = 10;

    private Controller controller; //TODO final?
    private int currentZoomLevel = DEFAULT_ZOOM_LEVEL;

    public GameView(UserInterface userInterface) {
        super(userInterface);
    }

    public void drawField() {
        for (int i = 0; i < currentZoomLevel; i++) {
            for (int j = 0; j < currentZoomLevel; j++) {
                JButton fieldCell = new JButton();
                fieldCell.setSize(10, 10);
                
            }   
        }
    }

    public void drawMapControls() {
        
    }

    public void drawTableTop() {

    }

}
