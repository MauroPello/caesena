package it.unibo.caesena.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import it.unibo.caesena.view.components.NumericUpDown;
import it.unibo.caesena.view.components.NumericUpDownImpl;
import it.unibo.caesena.view.components.PlayerInput;
import it.unibo.caesena.view.components.PlayerInputImpl;

public class StartView extends JPanel implements View<JPanel> {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 8;
    private static final int DEFAULT_SIZE = 20;

    private final GUI userInterface;
    private final List<PlayerInput<JPanel>> playerInputs;
    private final JPanel playersPanel;

    public StartView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;
        userInterface.getController().resetGame();
        this.playerInputs = new ArrayList<>();

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JPanel playersNumPanel = new JPanel();
        final JLabel playersLabel = new JLabel(LocaleHelper.getPlayersText());
        playersLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, DEFAULT_SIZE));
        playersNumPanel.add(playersLabel);

        final NumericUpDown<JSpinner> playersNum = new NumericUpDownImpl(MIN_PLAYERS, MIN_PLAYERS, MAX_PLAYERS, 1);
        playersNum.getComponent().addChangeListener((e) -> {
            if (playersNum.getValueAsInt() < this.playerInputs.size()) {
                while (this.playerInputs.size() > playersNum.getValueAsInt()) {
                    removePlayerInput();
                }
            } else {
                while (this.playerInputs.size() < playersNum.getValueAsInt()) {
                    addPlayerInput();
                }
            }

            this.repaint();
            this.validate();
        });
        playersNumPanel.add(playersNum.getComponent());

        playersNumPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(playersNumPanel);

        this.playersPanel = new JPanel();
        this.playersPanel.setLayout(new BoxLayout(this.playersPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < MIN_PLAYERS; i++) {
            addPlayerInput();
        }
        final JScrollPane playersScrollPane = new JScrollPane(playersPanel);
        playersScrollPane.setAutoscrolls(true);
        playersScrollPane.setBorder(null);
        mainPanel.add(playersScrollPane);

        final JButton startButton = new JButton(LocaleHelper.getStartGameText());
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

        this.add(mainPanel);
    }

    /**
     * add new player 
     */
    private void addPlayerInput() {
        final PlayerInput<JPanel> playerPanel = new PlayerInputImpl();

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
