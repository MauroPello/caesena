package it.unibo.caesena.view;

import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.JFrame;

import it.unibo.caesena.controller.Controller;

public class GUI extends JFrame implements UserInterface {

    private  Controller controller;
    private View startView;
    private View gameView;
    private View pauseView;
    private View gameOverView;

    public GUI(final Controller controller) {
        super();
        this.controller = controller;

        // TODO background and size options
        // TODO default options
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.setSize(sw / 3, sh / 3);
        this.setLocationByPlatform(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        showStartView();
    }

    public void showStartView() {
        this.startView = new StartView(this);
        hideAllViews();
        startView.setVisible(true);
        this.setContentPane(startView);
        this.validate();
        this.repaint();
    }

    public void showGameView() {
        this.gameView = new GameView(this);
        hideAllViews();
        // TODO cambia sta roba
        ((GameView)gameView).start();
        gameView.setVisible(true);
        this.setContentPane(gameView);
        this.validate();
        this.repaint();
    }

    public void showPauseView() {
        this.pauseView = new PauseView(this);
        hideAllViews();
        pauseView.setVisible(true);
        this.setContentPane(pauseView);
        this.validate();
        this.repaint();
    }

    public void showGameOverView() {
        this.gameOverView = new GameOverView(this);
        hideAllViews();
        gameOverView.setVisible(true);
        this.setContentPane(gameOverView);
        this.validate();
        this.repaint();
    }

    private void hideAllViews() {
        // startView.setVisible(false);
        // gameView.setVisible(false);
        // pauseView.setVisible(false);
        // gameOverView.setVisible(false);
    }

    public void exit() {
        this.controller.exitGame();
        // chiudi app
    }

    public Controller getController() {
        return this.controller;
    }
}
