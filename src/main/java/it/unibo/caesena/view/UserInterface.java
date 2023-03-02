package it.unibo.caesena.view;

import javax.swing.JFrame;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.controller.ControllerImpl;

public class UserInterface extends JFrame {
    
    private final Controller controller;

    public UserInterface() {
        super();
        this.controller = new ControllerImpl();
        // TODO set options for JFrame

    }

    public void showStartView() {

    }

    public void showGameView() {

    }

    public void showPauseView() {

    }

    public void showGameOverView() {

    }

    
}
