package it.unibo.caesena.view;

import java.awt.Toolkit;
import java.awt.Dimension;

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
        
        this.startView = new StartView(this);
        this.gameView = new GameView(this);
        this.pauseView = new PauseView(this);
        this.gameOverView = new GameOverView(this);
        
        // Default options for userInterface
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.setSize(sw / 3, sh / 3);
        this.setLocationByPlatform(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        showGameOverView();
    }

    public void showStartView() {
        hideAllViews();
        startView.setVisible(true);
        this.setContentPane(startView);
    }

    public void showGameView() {
        hideAllViews();
        gameView.setVisible(true);
        this.setContentPane(gameView);
    }

    public void showPauseView() {
        hideAllViews();
        pauseView.setVisible(true);
        this.setContentPane(pauseView);
    }

    public void showGameOverView() {
        hideAllViews();
        gameOverView.setVisible(true);
        this.setContentPane(gameOverView);
    }

    private void hideAllViews() {
        startView.setVisible(false);
        gameView.setVisible(false);
        pauseView.setVisible(false);
        gameOverView.setVisible(false);
    }
}
