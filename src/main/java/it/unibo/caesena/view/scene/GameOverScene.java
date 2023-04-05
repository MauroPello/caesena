package it.unibo.caesena.view.scene;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.components.common.ModalPanel;
import it.unibo.caesena.view.components.common.JPanelWithBackgroundImage;
import it.unibo.caesena.view.components.player.PlayerImageImpl;

/**
 * A class defining the scene to be showed at the of the game.
 */
public class GameOverScene implements Scene<JPanel> {
    private static final float PLAYER_IMAGE_RATIO = 0.02f;
    private final GUI userInterface;
    private final JPanel mainPanel;
    private final int playerImageSize;
    private final JPanel playersPanels;

    /**
     * Public constructor that sets up the components and places them.
     *
     * @param userInterface the interface in which this scene is displayed
     */
    public GameOverScene(final GUI userInterface) {
        this.mainPanel = new JPanelWithBackgroundImage(ResourceUtil.getBufferedImage("background_GameOverScene.jpeg", List.of()));
        this.userInterface = userInterface;

        final JPanel modal = new ModalPanel(ResourceUtil.getBufferedImage("background_Modal.png", List.of()), false);
        modal.setOpaque(false);
        modal.setLayout(new BoxLayout(modal, BoxLayout.Y_AXIS));
        if (GUI.SCREEN_HEIGHT > GUI.SCREEN_WIDTH) {
            playerImageSize = (int) Math.round(GUI.SCREEN_WIDTH * PLAYER_IMAGE_RATIO);
        } else {
            playerImageSize = (int) Math.round(GUI.SCREEN_HEIGHT * PLAYER_IMAGE_RATIO);
        }

        this.mainPanel.setBackground(Color.BLACK);
        this.mainPanel.setLayout(new GridBagLayout());

        final JLabel playersLabel = new JLabel(LocaleHelper.getSceneTitle("GameOverScene", false));
        playersLabel.setFont(GUI.BIG_BOLD_FONT);
        playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playersLabel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING * 8, 0, 0, 0));
        modal.add(playersLabel);

        this.playersPanels = new JPanel();
        playersPanels.setOpaque(false);
        this.playersPanels.setLayout(new BoxLayout(this.playersPanels, BoxLayout.Y_AXIS));
        this.playersPanels.setAlignmentX(Component.CENTER_ALIGNMENT);
        modal.add(this.playersPanels);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, GUI.DEFAULT_PADDING * 8, 0));


        final JButton backToStartMenuButton = new JButton(LocaleHelper.getBackToStartMenuText());
        backToStartMenuButton.setFont(GUI.MEDIUM_BOLD_FONT);
        backToStartMenuButton.addActionListener(e -> userInterface.showBackTostartSceneDialog());
        backToStartMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(backToStartMenuButton);

        final JButton exitButton = new JButton(LocaleHelper.getExitApplicationText());
        exitButton.setFont(GUI.MEDIUM_BOLD_FONT);
        exitButton.addActionListener(e -> userInterface.showExitDialog());
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(exitButton);

        modal.add(buttonPanel);

        this.mainPanel.add(modal);
        this.mainPanel.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            update();
        }

        this.mainPanel.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final JPanel getComponent() {
        return this.mainPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public final GUI getUserInterface() {
        return this.userInterface;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.playersPanels.removeAll();

        final List<Player> players = new ArrayList<>(userInterface.getController().getPlayers());
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        for (final var player : players) {
            final JPanel volatailePanel = new JPanel();
            volatailePanel.setOpaque(false);

            final JLabel volataileLabel = new JLabel();
            volataileLabel.setFont(GUI.MEDIUM_NORMAL_FONT);
            volataileLabel.setText(LocaleHelper.getNameText() + player.getName() + " " + LocaleHelper.getScoreText()
                    + player.getScore());
            volatailePanel.add(volataileLabel);

            final var playerColorPanel = new PlayerImageImpl();
            playerColorPanel.setColor(player.getColor().asSwingColor());
            playerColorPanel.forceSize(playerImageSize);
            volatailePanel.add(playerColorPanel.getComponent());

            this.playersPanels.add(volatailePanel);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }
}
