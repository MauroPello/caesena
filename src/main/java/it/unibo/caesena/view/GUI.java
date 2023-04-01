package it.unibo.caesena.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.utils.ResourceUtil;

public class GUI extends JFrame implements UserInterface {
    public static final float MODAL_PREFERRED_RATIO = 0.3f;
    public static final float MODAL_MAXIMUM_RATIO = 0.5f;
    public static final float MODAL_MINIMUM_RATIO = 0.1f;
    public static final float SMALL_MODAL_RATIO = 0.5f;
    private static final float MINIMUM_SIZE_RATIO = 0.2f;
    private static final int MINIMUM_WIDTH = 200;
    private static final int MINIMUM_HEIGHT = 200;
    private static boolean MY_GAME_DEBUG_VIEW = true;
    private static boolean MY_GAME_DEBUG_OVER_VIEW = false;
    private final Controller controller;
    private View<JPanel> startView;
    private View<JPanel> gameView;
    private View<JPanel> pauseView;
    private View<JPanel> gameOverView;
    private JPanel gamePanel;
    private final Map<Player, Color> players;

    public GUI(final Controller controller) {
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (final Exception e) {
            System.out.println(e);
        }

        this.controller = controller;
        this.players = new HashMap<>();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                showExitDialog();
            }
        });

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float width = screenSize.width * MINIMUM_SIZE_RATIO;
        width = width < MINIMUM_WIDTH ? MINIMUM_WIDTH : width;
        float height = screenSize.height * MINIMUM_SIZE_RATIO;
        height = height < MINIMUM_HEIGHT ? MINIMUM_HEIGHT : height;
        this.setMinimumSize(new Dimension(Math.round(width), Math.round(height)));

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setLocationByPlatform(true);

        // TODO set immagine logo
        this.setIconImage(ResourceUtil.getBufferedImage("TILE_BACK.png", List.of("tiles")));
        this.setVisible(true);

        // TODO rimuovere
        if (MY_GAME_DEBUG_VIEW || MY_GAME_DEBUG_OVER_VIEW) {
            this.addPlayer("Giocatore1", Color.RED);
            this.addPlayer("Giocatore2", Color.GREEN);
            this.startGame();
            if (MY_GAME_DEBUG_OVER_VIEW) {
                this.showGameOverView();
            }
        } else {
            this.showStartView();
        }
    }

    /**
     * show only startview
     */
    public void showStartView() {
        this.setTitle(LocaleHelper.getViewTitle("StartView", true));
        this.startView = new StartView(this);
        this.pauseView = null;
        this.gameView = null;
        this.gameOverView = null;
        this.gamePanel = null;

        this.startView.setVisible(true);
        this.setContentPane(startView.getComponent());
        this.validate();
        this.repaint();
    }

    /**
     * shows only GameView and PauseView if called
     */
    public void startGame() {
        this.setTitle(LocaleHelper.getViewTitle("GameView", true));
        this.startView = null;
        this.gameView = new GameView(this);
        this.pauseView = new PauseView(this);
        this.gamePanel = new JPanel();
        this.gamePanel.setLayout(new OverlayLayout(this.gamePanel));

        this.gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "togglePauseView");
        this.gamePanel.getActionMap().put("togglePauseView", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                togglePauseView();
            }
        });

        this.gameView.setVisible(true);
        this.pauseView.setVisible(false);
        this.gamePanel.add(this.pauseView.getComponent());
        this.gamePanel.add(this.gameView.getComponent());

        this.setContentPane(gamePanel);
        this.validate();
        this.repaint();
    }

    /**
     * show PauseView
     */
    public void togglePauseView() {
        this.pauseView.setVisible(!this.pauseView.isVisible());
        setEnabledAllComponents(gameView.getComponent(), !this.pauseView.isVisible());
        setEnabledAllComponents(pauseView.getComponent(), this.pauseView.isVisible());
    }

    /**
     * show only GameOverView
     */
    public void showGameOverView() {
        this.setTitle(LocaleHelper.getViewTitle("GameOverView", true));
        this.gameOverView = new GameOverView(this);

        this.gameView.setVisible(false);
        this.pauseView.setVisible(false);
        this.gameOverView.setVisible(true);

        this.setContentPane(gameOverView.getComponent());
        this.validate();
        this.repaint();
    }

    /**
     * if called, show exit dialog
     */
    public void showExitDialog() {
        final int result = JOptionPane.showConfirmDialog(this, LocaleHelper.getConfirmExitText(),
                LocaleHelper.getExitDialogTitle(), JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            exit();
        }
    }

    /**
     * if called, show backtoStart dialog
     */
    public void showBackToStartViewDialog() {
        final int result = JOptionPane.showConfirmDialog(this, LocaleHelper.getConfirmBackToStartMenuText(),
                LocaleHelper.getBackToStartMenuText(), JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            showStartView();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        this.controller.exitGame();
        System.exit(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Controller getController() {
        return this.controller;
    }

    /**
     *
     * @param container specifies the type of containers
     * @param enabled   to enable or disable containers
     */
    private void setEnabledAllComponents(final Container container, final boolean enabled) {
        for (final var component : container.getComponents()) {
            component.setEnabled(enabled);
            if (component instanceof Container) {
                setEnabledAllComponents(((Container) component), enabled);
            }
        }
    }

    /**
     *
     * @param name  to add
     * @param color to add on relative name
     */
    public void addPlayer(final String name, final Color color) {
        this.players.put(this.controller.addPlayer(name), color);
    }

    /**
     *
     * @param player
     * @return color of player
     */
    public Color getPlayerColor(final Player player) {
        return this.players.get(player);
    }
}
