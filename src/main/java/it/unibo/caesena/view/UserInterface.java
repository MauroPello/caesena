package it.unibo.caesena.view;

import javax.swing.JFrame;

import it.unibo.caesena.controller.Controller;

public class UserInterface extends JFrame {
    
    private final Controller controller;
    private final View startView;
    private final View gameView;
    private final View pauseView;
    private final View gameOverView;

    public UserInterface(final Controller controller) {
        super();
        this.controller = controller;
        // TODO set options for JFrame
        this.startView = new StartView(this);
        this.gameView = new GameView(this);
        this.pauseView = new PauseView(this);
        this.gameOverView = new GameOverView(this);
    }

    public void showStartView() {
        hideAllViews();
        startView.setVisible(true);

    }

    public void showGameView() {
        hideAllViews();
        gameView.setVisible(true);

    }

    public void showPauseView() {
        hideAllViews();
        pauseView.setVisible(true);

    }

    public void showGameOverView() {
        hideAllViews();
        gameOverView.setVisible(true);

    }

    private void hideAllViews() {
        startView.setVisible(false);
        gameView.setVisible(false);
        pauseView.setVisible(false);
        gameOverView.setVisible(false);
    }
}
