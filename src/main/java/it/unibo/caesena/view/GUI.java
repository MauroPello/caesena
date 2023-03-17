package it.unibo.caesena.view;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.utils.Color;

public class GUI extends JFrame implements UserInterface {
    // TODO rimuovere
    // RAGA È SOLO PER DEBUG, SE ATTIVO UNO DISATTIVATE GLI ALTRI!!
    private static boolean DEBUG_GAME_VIEW = true;
    private static boolean DEBUG_GAME_OVER_VIEW = false;
    private Controller controller;
    private View startView;
    private View gameView;
    private View pauseView;
    private View gameOverView;
    private JPanel gamePanel;

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

        //TODO rimuovere
        if (DEBUG_GAME_VIEW || DEBUG_GAME_OVER_VIEW) {
            Color color1 = Color.createColor("FF0000", "Red");
            Color color2 = Color.createColor("00FF00", "Green");
            this.controller.addPlayer("Giocatore1", color1);
            this.controller.addPlayer("Giocatore2", color2);
            this.startGame();
            if (DEBUG_GAME_OVER_VIEW) {
                this.showGameOverView();
            }
        } else {
            this.showStartView();
        }
    }

    public void showStartView() {
        this.startView = new StartView(this);
        this.pauseView = null;
        this.gameView = null;
        this.gameOverView = null;
        this.gamePanel = null;

        this.startView.setVisible(true);
        this.setContentPane(startView);
        this.validate();
        this.repaint();
    }

    public void startGame() {
        this.startView = null;
        this.gameView = new GameView(this);
        this.pauseView = new PauseView(this);
        this.gamePanel = new JPanel();
        this.gamePanel.setLayout(new OverlayLayout(this.gamePanel));

        this.gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "togglePauseView");
        this.gamePanel.getActionMap().put("togglePauseView", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                togglePauseView();
            }
        });

        // TODO cambia sta roba
        ((GameView)gameView).start();
        this.gameView.setVisible(true);
        this.pauseView.setVisible(false);
        this.gamePanel.add(this.pauseView);
        this.gamePanel.add(this.gameView);

        this.setContentPane(gamePanel);
        this.validate();
        this.repaint();
    }

    public void togglePauseView() {
        this.pauseView.setVisible(!this.pauseView.isVisible());
        setEnabledAllComponents(gameView, !this.pauseView.isVisible());
        setEnabledAllComponents(pauseView, this.pauseView.isVisible());
    }

    public void showGameOverView() {
        this.gameOverView = new GameOverView(this);

        this.gameView.setVisible(false);
        this.pauseView.setVisible(false);
        this.gameOverView.setVisible(true);

        this.setContentPane(gameOverView);
        this.validate();
        this.repaint();
    }

    public void exit() {
        this.controller.exitGame();
        // chiudi app
    }

    public Controller getController() {
        return this.controller;
    }

    private void setEnabledAllComponents(Container container, boolean enabled) {
        for (var component : container.getComponents()) {
            component.setEnabled(enabled);
            if (component instanceof Container) {
                setEnabledAllComponents(((Container)component), enabled);
            }
        }
    }
}
