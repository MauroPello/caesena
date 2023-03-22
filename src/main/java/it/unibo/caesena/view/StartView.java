package it.unibo.caesena.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unibo.caesena.view.components.NumericUpDown;
import it.unibo.caesena.view.components.PlayerInput;

public class StartView extends View {

    private final static int MIN_PLAYERS = 2;
    private final static int MAX_PLAYERS = 8;

    private final List<PlayerInput> playerInputs;
    private final JPanel playersPanel;

    public StartView(GUI userInterface) {
        super(userInterface);
        this.playerInputs = new ArrayList<>();
        this.setLayout(new GridBagLayout());
        this.setBackground(java.awt.Color.BLACK);

        Font mainFont = new Font("SansSerif", Font.BOLD, 20);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel playersNumPanel = new JPanel();
        JLabel playersLabel = new JLabel("Players:");
        playersLabel.setFont(mainFont);
        playersNumPanel.add(playersLabel);

        NumericUpDown playersNum = new NumericUpDown(MIN_PLAYERS, MIN_PLAYERS, MAX_PLAYERS, 1);
        playersNum.setSize(new Dimension(50, 50));
        playersNum.addChangeListener((e) -> {
            if (playersNum.getNumberAsInt() < this.playerInputs.size()) {
                while(this.playerInputs.size() > playersNum.getNumberAsInt()) {
                    removePlayerInput();
                }
            } else {
                while(this.playerInputs.size() < playersNum.getNumberAsInt()) {
                    addPlayerInput();
                }
            }

            this.repaint();
            this.validate();
        });
        playersNum.getTextField().setFont(mainFont);
        playersNumPanel.add(playersNum);

        playersNumPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(playersNumPanel);

        this.playersPanel = new JPanel();
        this.playersPanel.setLayout(new BoxLayout(this.playersPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < MIN_PLAYERS; i++) {
            addPlayerInput();
        }
        JScrollPane playersScrollPane = new JScrollPane(playersPanel);
        playersScrollPane.setAutoscrolls(true);
        playersScrollPane.setBorder(null);
        mainPanel.add(playersScrollPane);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener((e) -> {
            for (var playerInput : this.playerInputs) {
                var player = playerInput.getPlayerData();
                userInterface.addPlayer(player.getX(), player.getY());
            }

            userInterface.startGame();
        });
        JPanel startGamePanel = new JPanel();
        startGamePanel.add(startButton);
        startGamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(startGamePanel);

        this.add(mainPanel);
    }

    private void addPlayerInput() {
        var playerPanel = new PlayerInput();

        this.playersPanel.add(playerPanel);
        this.playerInputs.add(playerPanel);
    }

    private void removePlayerInput() {
        this.playersPanel.remove(this.playerInputs.remove(this.playerInputs.size() - 1));
    }
}
