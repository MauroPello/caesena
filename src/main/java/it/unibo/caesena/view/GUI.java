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
import java.util.Optional;

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
import it.unibo.caesena.view.scene.GameOverScene;
import it.unibo.caesena.view.scene.GameScene;
import it.unibo.caesena.view.scene.PauseScene;
import it.unibo.caesena.view.scene.StartScene;
import it.unibo.caesena.view.scene.Scene;

/**
 * Implementation of UserInterface using Java Swing.
 */
public class GUI implements UserInterface {
    private static final float BIG_FONT_RATIO = 0.015f;
    private static final float MEDIUM_FONT_RATIO = 0.011f;
    private static final float SMALL_FONT_RATIO = 0.009f;
    /**
     * Default padding ratio.
     */
    public static final float DEFAULT_PADDING_RATIO = 0.005f;
    /**
     * User's screen width in pixels.
     */
    public static final float SCREEN_WIDTH = (float) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    /**
     * User's screen height in pixels.
     */
    public static final float SCREEN_HEIGHT = (float) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    /**
     * Big normal font.
     */
    public static final Font BIG_NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN,
            (int) Math.round(SCREEN_WIDTH * BIG_FONT_RATIO));
    /**
     * Big bold font.
     */
    public static final Font BIG_BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD,
            (int) Math.round(SCREEN_WIDTH * BIG_FONT_RATIO));
    /**
     * Medium normal font.
     */
    public static final Font MEDIUM_NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN,
            (int) Math.round(SCREEN_WIDTH * MEDIUM_FONT_RATIO));
    /**
     * Medium bold font.
     */
    public static final Font MEDIUM_BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD,
            (int) Math.round(SCREEN_WIDTH * MEDIUM_FONT_RATIO));
    /**
     * Small normal font.
     */
    public static final Font SMALL_NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN,
            (int) Math.round(SCREEN_WIDTH * SMALL_FONT_RATIO));
    /**
     * Small bold font.
     */
    public static final Font SMALL_BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD,
            (int) Math.round(SCREEN_WIDTH * SMALL_FONT_RATIO));
    /**
     * Default padding beetween components.
     */
    public static final int DEFAULT_PADDING = (int) Math
            .round(SCREEN_HEIGHT > SCREEN_WIDTH ? SCREEN_WIDTH * DEFAULT_PADDING_RATIO
                    : SCREEN_HEIGHT * DEFAULT_PADDING_RATIO);
    private static final float MINIMUM_SIZE_RATIO = 0.3f;
    private static final int MINIMUM_WIDTH = 400;
    private static final int MINIMUM_HEIGHT = 400;
    private Scene<JPanel> startScene;
    private Scene<JPanel> gameScene;
    private Scene<JPanel> pauseScene;
    private Scene<JPanel> gameOverScene;
    private boolean forceScenesReset;
    private Optional<Controller> controller;
    private final JFrame mainFrame;

    /**
     * Public constructor used to set UIManager and JFrame properties.
     */
    public GUI() {
        this.mainFrame = new JFrame();

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            Runtime.getRuntime().exit(0);
        }
        UIManager.put("OptionPane.messageFont", MEDIUM_NORMAL_FONT);
        UIManager.put("OptionPane.buttonFont", MEDIUM_BOLD_FONT);
        UIManager.put("OptionPane.questionIcon", new ImageIcon());

        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.mainFrame.addWindowListener(new WindowAdapter() {
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
        this.mainFrame.setMinimumSize(new Dimension(Math.round(width), Math.round(height)));

        this.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setLocationByPlatform(true);

        this.mainFrame.setIconImage(ResourceUtil.getBufferedImage("logo.png", List.of()));
        this.mainFrame.setVisible(true);

        this.forceScenesReset = true;
        this.controller = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final Controller controller) {
        this.controller = Optional.of(controller);
        this.controller.get().addUserInterface(this);
    }

    /**
     * Shows only startScene.
     */
    public void showStartScene() {
        this.mainFrame.setTitle(LocaleHelper.getSceneTitle("StartScene", true));
        this.startScene.setVisible(true);
        this.gameScene.setVisible(false);
        this.pauseScene.setVisible(false);
        this.gameOverScene.setVisible(false);
        this.mainFrame.setContentPane(startScene.getComponent());
        this.mainFrame.validate();
        this.mainFrame.repaint();
    }

    /**
     * Shows only gameScene and pauseScene, also sets up an Action to switch between
     * gameScene and pauseScene by pressing ESC.
     */
    public void showGameScene() {
        this.mainFrame.setTitle(LocaleHelper.getSceneTitle("GameScene", true));
        final JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new OverlayLayout(gamePanel));

        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "togglePauseScene");
        gamePanel.getActionMap().put("togglePauseScene", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                togglePauseScene();
            }
        });

        this.startScene.setVisible(false);
        this.gameScene.setVisible(true);
        this.pauseScene.setVisible(false);
        this.gameOverScene.setVisible(false);
        gamePanel.add(this.pauseScene.getComponent());
        gamePanel.add(this.gameScene.getComponent());

        this.mainFrame.setContentPane(gamePanel);
        this.mainFrame.validate();
        this.mainFrame.repaint();
    }

    /**
     * Toggles the pauseScene.
     */
    public void togglePauseScene() {
        this.pauseScene.setVisible(!this.pauseScene.isVisible());
        setEnabledAllComponents(gameScene.getComponent(), !this.pauseScene.isVisible());
        setEnabledAllComponents(pauseScene.getComponent(), this.pauseScene.isVisible());
    }

    /**
     * Shows only gameOverScene.
     */
    private void showGameOverScene() {
        this.mainFrame.setTitle(LocaleHelper.getSceneTitle("GameOverScene", true));

        this.startScene.setVisible(false);
        this.gameScene.setVisible(false);
        this.pauseScene.setVisible(false);
        this.gameOverScene.setVisible(true);
        this.mainFrame.setContentPane(gameOverScene.getComponent());
        this.mainFrame.validate();
        this.mainFrame.repaint();
    }

    /**
     * Shows the exit dialog.
     */
    public void showExitDialog() {
        final int result = JOptionPane.showConfirmDialog(this.mainFrame, LocaleHelper.getConfirmExitText(),
                LocaleHelper.getExitDialogTitle(), JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            exit();
        }
    }

    /**
     * Shows the back to startScene dialog.
     */
    public void showBackTostartSceneDialog() {
        final int result = JOptionPane.showConfirmDialog(this.mainFrame, LocaleHelper.getConfirmBackToStartMenuText(),
                LocaleHelper.getBackToStartMenuText(), JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            this.forceScenesReset = true;
        }
    }

    /**
     * Resets all the scenes.
     */
    private void resetScenes() {
        this.forceScenesReset = false;
        this.startScene = new StartScene(this);
        this.gameScene = new GameScene(this);
        this.pauseScene = new PauseScene(this);
        this.gameOverScene = new GameOverScene(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        this.controller.ifPresent(Controller::exitGame);
        this.controller.ifPresent(Controller::close);
        Runtime.getRuntime().exit(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Controller getController() {
        return this.controller.get();
    }

    /**
     * Sets the property enabled to all components inside the given Container.
     *
     * @param container the container of which components setEnabled will be called.
     * @param enabled   value to be used when calling setEnabled for every component.
     */
    private void setEnabledAllComponents(final Container container, final boolean enabled) {
        for (final var component : container.getComponents()) {
            component.setEnabled(enabled);
            if (component instanceof Container) {
                setEnabledAllComponents((Container) component, enabled);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (forceScenesReset) {
            resetScenes();
        }

        if (this.controller.isPresent()) {
            if (controller.get().isGameOver()) {
                if (!gameOverScene.isVisible()) {
                    showGameOverScene();
                }

                gameOverScene.update();
            } else if (controller.get().hasGameStarted()) {
                if (!gameScene.isVisible()) {
                    showGameScene();
                }

                gameScene.update();
            } else {
                if (!startScene.isVisible()) {
                    showStartScene();
                }

                startScene.update();
            }
        }
    }
}
