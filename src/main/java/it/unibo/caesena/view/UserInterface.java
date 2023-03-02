package it.unibo.caesena.view;

import javax.swing.JFrame;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.controller.ControllerImpl;

public class UserInterface extends JFrame {
    
    private final Controller controller;
    private final View startView;
    private final View gameView;
    private final View pauseView;
    private final View gameOverView;

    public UserInterface() {
        super();
        this.controller = new ControllerImpl();
        // TODO set options for JFrame
        this.startView = new StartView();
        this.gameView = new GameView();
        this.pauseView = new PauseView();
        this.gameOverView = new GameOverView();
    }

    public void showStartView() {
        hideAllViews();
        startView.show();

    }

    public void showGameView() {
        hideAllViews();
        gameView.show();

    }

    public void showPauseView() {
        hideAllViews();
        pauseView.show();

    }

    public void showGameOverView() {
        hideAllViews();
        gameOverView.show();

    }

    private void hideAllViews() {
        startView.hide();
        gameView.hide();
        pauseView.hide();
        gameOverView.hide();
    }
}
