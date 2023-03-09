package it.unibo.caesena.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.view.components.Spinner;

public class StartView extends View {

    private final static int MIN_PLAYERS = 2;
    private final static int MAX_PLAYERS = 8;

    private final JPanel playersNumPanel;
    private final Spinner playersNumSpinner;

    private Font mainFont;

    public StartView(GUI userInterface) {
        super(userInterface);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.mainFont = new Font("SansSerif", Font.BOLD, 20);

        playersNumPanel = new JPanel();
        JLabel playersLabel = new JLabel("Players:");
        playersLabel.setFont(mainFont);
        playersNumPanel.add(playersLabel);

        playersNumSpinner = new Spinner(MIN_PLAYERS, MIN_PLAYERS, MAX_PLAYERS, 1);
        playersNumSpinner.setSize(new Dimension(50, 50));
        playersNumSpinner.getTextField().setFont(mainFont);
        playersNumPanel.add(playersNumSpinner);
        
        playersNumPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(playersNumPanel);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener((e) -> {
            userInterface.showGameView();
        });
        this.add(startButton);
    }
}
