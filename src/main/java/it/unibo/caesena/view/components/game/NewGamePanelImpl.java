package it.unibo.caesena.view.components.game;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Color;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.components.common.NumericUpDown;
import it.unibo.caesena.view.components.common.NumericUpDownImpl;
import it.unibo.caesena.view.components.player.PlayerInput;
import it.unibo.caesena.view.components.player.PlayerInputImpl;
import it.unibo.caesena.view.scene.StartScene;
import it.unibo.caesena.view.GUI;

public class NewGamePanelImpl implements NewGamePanel<JPanel> {

    private static final float PLAYER_IMAGE_RATIO = 0.05f;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 6;

    private final Controller controller;
    private final JPanel mainPanel;
    private final List<PlayerInput<JPanel>> playerInputs;
    private final JPanel playersPanel;
    private final NumericUpDown<JSpinner> playersNum;
    private final int playerInputImageSize;

    private Optional<ChangeListener> playersNumChangeListener;

    public NewGamePanelImpl(final StartScene startScene) {
        this.controller = startScene.getUserInterface().getController();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setOpaque(false);

        this.playerInputs = new ArrayList<>();

        final JPanel playersNumPanel = new JPanel();
        playersNumPanel.setOpaque(false);
        final JLabel playersLabel = new JLabel(LocaleHelper.getPlayersText());
        playersLabel.setFont(GUI.BIG_BOLD_FONT);
        playersNumPanel.add(playersLabel);

        playersNum = new NumericUpDownImpl(MIN_PLAYERS, MIN_PLAYERS, MAX_PLAYERS, 1);
        this.playersNumChangeListener = Optional.empty();
        playersNumPanel.add(playersNum.getComponent());

        playersNumPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.mainPanel.add(playersNumPanel);

        this.playersPanel = new JPanel();
        this.playersPanel.setLayout(new BoxLayout(this.playersPanel, BoxLayout.Y_AXIS));
        final JScrollPane playersScrollPane = new JScrollPane(playersPanel);
        playersPanel.setOpaque(false);
        playersScrollPane.setOpaque(false);
        playersScrollPane.getViewport().setOpaque(false);
        playersScrollPane.setAutoscrolls(true);
        playersScrollPane.setBorder(null);
        this.mainPanel.add(playersScrollPane);

        final JButton startButton = new JButton(LocaleHelper.getStartGameText());
        startButton.setFont(GUI.MEDIUM_BOLD_FONT);
        startButton.addActionListener((e) -> {
            for (final var playerInput : this.playerInputs) {
                final var player = playerInput.getPlayerData();
                final var color = player.getY();
                controller.addPlayer(player.getX(),
                    new Color(color.getRed(), color.getGreen(), color.getBlue()));
            }

            controller.startGame();
        });
        final JButton backButton = new JButton(LocaleHelper.getBackText());
        backButton.setFont(GUI.MEDIUM_BOLD_FONT);
        backButton.addActionListener((e) -> {
            startScene.backToButtonsMenu();
        });
        final JPanel startGamePanel = new JPanel();
        startGamePanel.setOpaque(false);
        startGamePanel.add(startButton);
        startGamePanel.add(backButton);
        startGamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGamePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, GUI.DEFAULT_PADDING * 4, 0));
        this.mainPanel.add(startGamePanel);

        if (GUI.SCREEN_HEIGHT > GUI.SCREEN_WIDTH) {
            playerInputImageSize = (int) Math.round(GUI.SCREEN_WIDTH * PLAYER_IMAGE_RATIO);
        } else {
            playerInputImageSize = (int) Math.round(GUI.SCREEN_HEIGHT * PLAYER_IMAGE_RATIO);
        }

        this.mainPanel.setVisible(false);
    }

    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        if (!isVisible()) {
            update();
            if (this.playersNumChangeListener.isEmpty()) {
                this.playersNumChangeListener = Optional.of((e) -> update());
            }
            if (!Arrays.asList(playersNum.getComponent().getChangeListeners()).contains(playersNumChangeListener.get())) {
                playersNum.getComponent().addChangeListener(playersNumChangeListener.get());
            }
        }

        this.mainPanel.setVisible(visible);
    }

    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

    @Override
    public void update() {
        if (playersNum.getValueAsInt() < this.playerInputs.size()) {
            while (this.playerInputs.size() > playersNum.getValueAsInt()) {
                removePlayerInput();
            }
        } else {
            while (this.playerInputs.size() < playersNum.getValueAsInt()) {
                addPlayerInput();
            }
        }

        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    /**
     * Adds a new player input to be filled.
     */
    private void addPlayerInput() {
        final PlayerInput<JPanel> playerPanel = new PlayerInputImpl();
        playerPanel.setPlayerImageSize(playerInputImageSize);
        playerPanel.getComponent().setOpaque(false);

        this.playerInputs.add(playerPanel);
        this.playersPanel.add(playerPanel.getComponent());
    }

    /**
     * Removes the last player input in the playerInputs list.
     */
    private void removePlayerInput() {
        this.playersPanel.remove(this.playerInputs.remove(this.playerInputs.size() - 1).getComponent());
    }

}
