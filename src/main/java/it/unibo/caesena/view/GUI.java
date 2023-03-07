package it.unibo.caesena.view;

import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.JFrame;

import it.unibo.caesena.controller.Controller;

public class GUI extends JFrame implements UserInterface {
    
    private final Controller controller;
    private final View startView;
    private final View gameView;
    private final View pauseView;
    private final View gameOverView;

    public GUI(final Controller controller) {
        super();
        this.controller = controller;
        
        this.startView = new StartView(this);
        this.gameView = new GameView(this);
        this.pauseView = new PauseView(this);
        this.gameOverView = new GameOverView(this);
        
        // TODO background and size options
        // TODO default options
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.setSize(sw / 3, sh / 3);
        this.setLocationByPlatform(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        showGameView();
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

    public void exit() {
        this.controller.exitGame();
        // chiudi app
    }

    public Controller getController() {
        return this.controller;
    }
}
