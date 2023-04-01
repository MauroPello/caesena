package it.unibo.caesena.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.components.NumericUpDown;
import it.unibo.caesena.view.components.NumericUpDownImpl;
import it.unibo.caesena.view.components.PlayerInput;
import it.unibo.caesena.view.components.PlayerInputImpl;

public class StartView extends JPanel implements View<JPanel> {
    private static final float GAME_IMAGE_RATIO = 0.6f;
    private static final float PLAYER_IMAGE_RATIO = 0.05f;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 6;

    private final GUI userInterface;
    private final List<PlayerInput<JPanel>> playerInputs;
    private final JPanel playersPanel;
    private final NumericUpDown<JSpinner> playersNum;
    private final JPanel mainPanel;
    private final int playerInputImageSize;

    public StartView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;
        userInterface.getController().resetGame();
        this.playerInputs = new ArrayList<>();

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        mainPanel.add(new JPanel());
        final JPanel imagePanel = new JPanel() {
            final BufferedImage image = ResourceUtil.getBufferedImage("caesena.png", List.of());

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
                double height = (image.getHeight() * d.getWidth()) / image.getWidth();
                return new Dimension((int) Math.round(d.width * GAME_IMAGE_RATIO), (int) Math.round(height * GAME_IMAGE_RATIO));
            }

            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        mainPanel.add(imagePanel);

        final JPanel playersNumPanel = new JPanel();
        final JLabel playersLabel = new JLabel(LocaleHelper.getPlayersText());
        playersLabel.setFont(GUI.MEDIUM_BOLD_FONT);
        playersNumPanel.add(playersLabel);

        playersNum = new NumericUpDownImpl(MIN_PLAYERS, MIN_PLAYERS, MAX_PLAYERS, 1);
        playersNum.getComponent().addChangeListener((e) -> updatePlayerPanel());
        playersNumPanel.add(playersNum.getComponent());

        playersNumPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(playersNumPanel);

        this.playersPanel = new JPanel();
        this.playersPanel.setLayout(new BoxLayout(this.playersPanel, BoxLayout.Y_AXIS));
        final JScrollPane playersScrollPane = new JScrollPane(playersPanel);
        playersScrollPane.setAutoscrolls(true);
        playersScrollPane.setBorder(null);
        mainPanel.add(playersScrollPane);

        final JButton startButton = new JButton(LocaleHelper.getStartGameText());
        startButton.setFont(GUI.BIG_BOLD_FONT);
        startButton.addActionListener((e) -> {
            for (final var playerInput : this.playerInputs) {
                final var player = playerInput.getPlayerData();
                userInterface.addPlayer(player.getX(), player.getY());
            }

            userInterface.startGame();
        });
        final JPanel startGamePanel = new JPanel();
        startGamePanel.add(startButton);
        startGamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(startGamePanel);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainPanel.setPreferredSize(new Dimension((int) Math.round(screenSize.getWidth() * GUI.MODAL_PREFERRED_RATIO), (int) Math.round(screenSize.getHeight() * GUI.MODAL_PREFERRED_RATIO)));
        mainPanel.setMinimumSize(new Dimension((int) Math.round(screenSize.getWidth() * GUI.MODAL_MINIMUM_RATIO), (int) Math.round(screenSize.getHeight() * GUI.MODAL_MINIMUM_RATIO)));
        mainPanel.setMaximumSize(new Dimension((int) Math.round(screenSize.getWidth() * GUI.MODAL_MAXIMUM_RATIO), (int) Math.round(screenSize.getHeight() * GUI.MODAL_MAXIMUM_RATIO)));
        if (screenSize.getHeight() > screenSize.getWidth()) {
            playerInputImageSize = (int) Math.round(screenSize.getWidth() * PLAYER_IMAGE_RATIO);
        } else {
            playerInputImageSize = (int) Math.round(screenSize.getHeight() * PLAYER_IMAGE_RATIO);
        }

        this.add(mainPanel);
        updatePlayerPanel();
    }

    private void updatePlayerPanel() {
        if (playersNum.getValueAsInt() < this.playerInputs.size()) {
            while (this.playerInputs.size() > playersNum.getValueAsInt()) {
                removePlayerInput();
            }
        } else {
            while (this.playerInputs.size() < playersNum.getValueAsInt()) {
                addPlayerInput();
            }
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * add new player
     */
    private void addPlayerInput() {
        final PlayerInput<JPanel> playerPanel = new PlayerInputImpl();
        playerPanel.setColorPanelSize(playerInputImageSize);

        this.playersPanel.add(playerPanel.getComponent());
        this.playerInputs.add(playerPanel);
    }

    /**
     * remove player
     */
    private void removePlayerInput() {
        this.playersPanel.remove(this.playerInputs.remove(this.playerInputs.size() - 1).getComponent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final JPanel getComponent() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public final GUI getUserInterface() {
        return this.userInterface;
    }
}
