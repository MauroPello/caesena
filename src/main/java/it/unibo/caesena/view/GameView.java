package it.unibo.caesena.view;

import javax.swing.JButton;

import it.unibo.caesena.controller.Controller;

public class GameView implements View {
    private final static int DEFAULT_ZOOM_LEVEL = 10;

    private Controller controller; //TODO final?
    private int currentZoomLevel = DEFAULT_ZOOM_LEVEL;

    @Override
    public void show() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    public void drawField() {
        for (int i = 0; i < currentZoomLevel; i++) {
            
        }
    }

    public void drawMapControls() {
        
    }

    public void drawTableTop() {

    }

}
