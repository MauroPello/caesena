package it.unibo.caesena.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.utils.ResourceUtil;

public class GUI extends JFrame implements UserInterface {
    private static final long serialVersionUID = 8950849192853252728L;
    public static final float MODAL_PREFERRED_RATIO = 0.4f;
    public static final float MODAL_MAXIMUM_RATIO = 0.5f;
    public static final float MODAL_MINIMUM_RATIO = 0.2f;
    public static final float SCREEN_WIDTH = (float) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final float SCREEN_HEIGHT = (float) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final Font BIG_NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, (int) Math.round(SCREEN_WIDTH * 0.009f));
    public static final Font BIG_BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD, (int) Math.round(SCREEN_WIDTH * 0.009f));
    public static final Font MEDIUM_NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, (int) Math.round(SCREEN_WIDTH * 0.008f));
    public static final Font MEDIUM_BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD, (int) Math.round(SCREEN_WIDTH * 0.008f));
    public static final Font SMALL_NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, (int) Math.round(SCREEN_WIDTH * 0.007f));
    public static final Font SMALL_BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD, (int) Math.round(SCREEN_WIDTH * 0.007f));
    public static final int DEFAULT_PADDING = (int) Math.round(SCREEN_HEIGHT > SCREEN_WIDTH ? SCREEN_WIDTH * 0.005f : SCREEN_HEIGHT * 0.005f);
    public static final float SMALL_MODAL_RATIO = 0.5f;
    private static final float MINIMUM_SIZE_RATIO = 0.3f;
    private static final int MINIMUM_WIDTH = 200;
    private static final int MINIMUM_HEIGHT = 200;
    private final Controller controller;
    private View<JPanel> startView;
    private View<JPanel> gameView;
    private View<JPanel> pauseView;
    private View<JPanel> gameOverView;

    public GUI(final Controller controller) {
        super();

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.exit(ABORT);
        }
        UIManager.put("OptionPane.messageFont", MEDIUM_NORMAL_FONT);
        UIManager.put("OptionPane.buttonFont", MEDIUM_BOLD_FONT);
        UIManager.put("OptionPane.questionIcon", new ImageIcon());

        this.controller = controller;


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

        this.setIconImage(ResourceUtil.getBufferedImage("logo.png", List.of()));
        this.setVisible(true);

        resetViews();

        this.controller.addUserInterface(this);
    }

    /**
     * Shows only startview.
     */
    public void showStartView() {
        this.setTitle(LocaleHelper.getViewTitle("StartView", true));
        this.startView.setVisible(true);
        this.gameView.setVisible(false);
        this.pauseView.setVisible(false);
        this.gameOverView.setVisible(false);
        this.setContentPane(startView.getComponent());
        this.validate();
        this.repaint();
    }

    /**
     * Shows only GameView and PauseView if called.
     */
    public void showGameView() {
        this.setTitle(LocaleHelper.getViewTitle("GameView", true));
        final JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new OverlayLayout(gamePanel));

        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "togglePauseView");
        gamePanel.getActionMap().put("togglePauseView", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                togglePauseView();
            }
        });

        this.startView.setVisible(false);
        this.gameView.setVisible(true);
        this.pauseView.setVisible(false);
        this.gameOverView.setVisible(false);
        gamePanel.add(this.pauseView.getComponent());
        gamePanel.add(this.gameView.getComponent());

        this.setContentPane(gamePanel);
        this.validate();
        this.repaint();
    }

    /**
     * Shows PauseView.
     */
    public void togglePauseView() {
        this.pauseView.setVisible(!this.pauseView.isVisible());
        setEnabledAllComponents(gameView.getComponent(), !this.pauseView.isVisible());
        setEnabledAllComponents(pauseView.getComponent(), this.pauseView.isVisible());
    }

    /**
     * Shows only GameOverView.
     */
    private void showGameOverView() {
        this.setTitle(LocaleHelper.getViewTitle("GameOverView", true));

        this.startView.setVisible(false);
        this.gameView.setVisible(false);
        this.pauseView.setVisible(false);
        this.gameOverView.setVisible(true);
        this.setContentPane(gameOverView.getComponent());
        this.validate();
        this.repaint();
    }

    /**
     * If called, show exit dialog.
     */
    public void showExitDialog() {
        final int result = JOptionPane.showConfirmDialog(this, LocaleHelper.getConfirmExitText(),
                LocaleHelper.getExitDialogTitle(), JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            exit();
        }
    }

    /**
     * If called, show backtoStart dialog.
     */
    public void showBackToStartViewDialog() {
        final int result = JOptionPane.showConfirmDialog(this, LocaleHelper.getConfirmBackToStartMenuText(),
                LocaleHelper.getBackToStartMenuText(), JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            resetViews();
            this.controller.resetGame();
        }
    }

    private void resetViews() {
        this.startView = new StartView(this);
        this.gameView = new GameView(this);
        this.pauseView = new PauseView(this);
        this.gameOverView = new GameOverView(this);
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
     * @param container specifies the type of containers.
     * @param enabled   to enable or disable containers.
     */
    private void setEnabledAllComponents(final Container container, final boolean enabled) {
        for (final var component : container.getComponents()) {
            component.setEnabled(enabled);
            if (component instanceof Container) {
                setEnabledAllComponents((Container) component, enabled);
            }
        }
    }

    @Override
    public void update() {
        if (controller.isGameOver()) {
            if (!gameOverView.isVisible()) {
                showGameOverView();
            }

            gameOverView.update();
        } else if (!controller.getPlacedTiles().isEmpty()) {
            if (!gameView.isVisible()) {
                showGameView();
            }

            gameView.update();
        } else {
            if (!startView.isVisible()) {
                showStartView();
            }

            startView.update();
        }
    }
}
