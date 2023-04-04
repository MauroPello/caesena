package it.unibo.caesena.view.scene;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
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

import it.unibo.caesena.model.Color;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.components.common.ModalPanel;
import it.unibo.caesena.view.components.common.NumericUpDown;
import it.unibo.caesena.view.components.common.NumericUpDownImpl;
import it.unibo.caesena.view.components.common.PanelWithBackgroundImage;
import it.unibo.caesena.view.components.player.PlayerInputImpl;

/**
 * A class defining the start menu for the game.
 */
public class StartScene implements Scene<JPanel> {
    private static final float GAME_IMAGE_RATIO = 0.6f;
    private static final float PLAYER_IMAGE_RATIO = 0.05f;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 6;
    private final GUI userInterface;
    private final JPanel mainPanel;
    private final List<PlayerInputImpl> playerInputs;
    private final JPanel playersPanel;
    private final NumericUpDown<JSpinner> playersNum;
    private final int playerInputImageSize;

    private Optional<ChangeListener> playersNumChangeListener;

    /**
     * Public constructor that sets up the components and places them.
     *
     * @param userInterface the interface in which this scene is displayed
     */
    public StartScene(final GUI userInterface) {
        this.userInterface = userInterface;
        this.playerInputs = new ArrayList<>();

        this.mainPanel = new PanelWithBackgroundImage(ResourceUtil.getBufferedImage("background_StartScene.png", List.of()));
        this.mainPanel.setLayout(new GridBagLayout());
        this.mainPanel.setOpaque(false);

        final JPanel modal = new ModalPanel(ResourceUtil.getBufferedImage("background_Modal.png", List.of()), false);
        modal.setLayout(new BoxLayout(modal, BoxLayout.Y_AXIS));
        modal.setOpaque(false);

        final JPanel imagePanel = new JPanel() {
            private final BufferedImage image = ResourceUtil.getBufferedImage("caesena.png", List.of());

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getPreferredSize() {
                final Dimension d = this.getParent().getSize();
                final double height = (image.getHeight() * d.getWidth()) / image.getWidth();
                return new Dimension((int) Math.round(d.width * GAME_IMAGE_RATIO),
                        (int) Math.round(height * GAME_IMAGE_RATIO));
            }

            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),
                    0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        imagePanel.setOpaque(false);
        final var imagePanelWithPadding = new JPanel();
        imagePanelWithPadding.setOpaque(false);
        imagePanelWithPadding.add(imagePanel);
        imagePanelWithPadding.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING * 4, 0, 0, 0));
        modal.add(imagePanelWithPadding);

        final JPanel playersNumPanel = new JPanel();
        playersNumPanel.setOpaque(false);
        final JLabel playersLabel = new JLabel(LocaleHelper.getPlayersText());
        playersLabel.setFont(GUI.MEDIUM_BOLD_FONT);
        playersNumPanel.add(playersLabel);

        playersNum = new NumericUpDownImpl(MIN_PLAYERS, MIN_PLAYERS, MAX_PLAYERS, 1);
        playersNumChangeListener = Optional.empty();
        playersNumPanel.add(playersNum.getComponent());

        playersNumPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        modal.add(playersNumPanel);

        this.playersPanel = new JPanel();
        this.playersPanel.setLayout(new BoxLayout(this.playersPanel, BoxLayout.Y_AXIS));
        final JScrollPane playersScrollPane = new JScrollPane(playersPanel);
        playersPanel.setOpaque(false);
        playersScrollPane.setOpaque(false);
        playersScrollPane.getViewport().setOpaque(false);
        playersScrollPane.setAutoscrolls(true);
        playersScrollPane.setBorder(null);
        modal.add(playersScrollPane);

        final JButton startButton = new JButton(LocaleHelper.getStartGameText());
        startButton.setFont(GUI.BIG_BOLD_FONT);
        startButton.addActionListener((e) -> {
            for (final var playerInput : this.playerInputs) {
                final var player = playerInput.getPlayerData();
                final var color = player.getY();
                userInterface.getController().addPlayer(player.getX(),
                    new Color(color.getRed(), color.getGreen(), color.getBlue()));
            }

            userInterface.getController().startGame();
        });
        final JPanel startGamePanel = new JPanel();
        startGamePanel.setOpaque(false);
        startGamePanel.add(startButton);
        startGamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGamePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, GUI.DEFAULT_PADDING * 4, 0));
        modal.add(startGamePanel);

        if (GUI.SCREEN_HEIGHT > GUI.SCREEN_WIDTH) {
            playerInputImageSize = (int) Math.round(GUI.SCREEN_WIDTH * PLAYER_IMAGE_RATIO);
        } else {
            playerInputImageSize = (int) Math.round(GUI.SCREEN_HEIGHT * PLAYER_IMAGE_RATIO);
        }

        this.mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                imagePanel.revalidate();
                imagePanel.repaint();
            }
        });

        this.mainPanel.add(modal);
        this.setVisible(false);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
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

    /**
     * Adds a new player input to be filled.
     */
    private void addPlayerInput() {
        final PlayerInputImpl playerPanel = new PlayerInputImpl();
        playerPanel.setColorPanelSize(playerInputImageSize);
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


    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }
}
