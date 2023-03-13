package it.unibo.caesena.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.utils.Color;
import it.unibo.caesena.view.components.NumericUpDown;
import it.unibo.caesena.view.components.PlayerInput;

public class StartView extends View {

    private final static int MIN_PLAYERS = 2;
    private final static int MAX_PLAYERS = 8;

    private final JPanel playersNumPanel;
    private final NumericUpDown playersNum;
    private final JPanel playerSelectionPanel;
    private final List<PlayerInput> playerPanels;

    private Font mainFont;

    public StartView(GUI userInterface) {
        super(userInterface);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.playerPanels = new ArrayList<>();

        this.mainFont = new Font("SansSerif", Font.BOLD, 20);

        this.playersNumPanel = new JPanel();
        JLabel playersLabel = new JLabel("Players:");
        playersLabel.setFont(mainFont);
        this.playersNumPanel.add(playersLabel);

        this.playersNum = new NumericUpDown(MIN_PLAYERS, MIN_PLAYERS, MAX_PLAYERS, 1);
        this.playersNum.setSize(new Dimension(50, 50));
        this.playersNum.addChangeListener((e) -> {
            if (this.playersNum.getNumberAsInt() < this.playerPanels.size()) {
                while(this.playerPanels.size() > this.playersNum.getNumberAsInt()) {
                    removePlayerInput();
                }
            } else {
                while(this.playerPanels.size() < this.playersNum.getNumberAsInt()) {
                    addPlayerInput();
                }
            }

            this.repaint();
            this.validate();
        });
        this.playersNum.getTextField().setFont(mainFont);
        this.playersNumPanel.add(this.playersNum);

        this.playersNumPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(playersNumPanel);

        this.playerSelectionPanel = new JPanel();
        this.playerSelectionPanel.setLayout(new BoxLayout(this.playerSelectionPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < MIN_PLAYERS; i++) {
            addPlayerInput();
        }
        this.add(this.playerSelectionPanel);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener((e) -> {
            for (var playerInput : this.playerPanels) {
                var player = playerInput.getPlayerData();
                userInterface.getController().addPlayer(player.getX(), Color.createCustomColor(player.getY()));
            }

            userInterface.showGameOverView();
        });
        this.add(startButton);
    }

    private void addPlayerInput() {
        var playerPanel = new PlayerInput();

        playerSelectionPanel.add(playerPanel);
        this.playerPanels.add(playerPanel);
    }

    private void removePlayerInput() {
        playerSelectionPanel.remove(playerPanels.remove(this.playerPanels.size() - 1));
    }
}
