package it.unibo.caesena.view;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.*;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;

import it.unibo.caesena.model.Player;

public class GameOverView extends JPanel implements View<JPanel> {

    private final GUI userInterface;
    private final JPanel finalPanel;
    private final List<Player> players;
    private Font mainFont;

    public GameOverView (GUI userInterface) {
        super();
        this.userInterface = userInterface;

        this.setLayout(new GridBagLayout());
        this.mainFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);        
        this.finalPanel = new JPanel();
        this.finalPanel.setLayout(new BoxLayout(this.finalPanel, BoxLayout.Y_AXIS));
        this.players = userInterface.getController().getPlayers();

        JLabel playersLabel = new JLabel(LocaleHelper.getViewTitle("GameOverView", false));
        playersLabel.setFont(mainFont);
        playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.finalPanel.add(playersLabel);

        PriorityQueue<Player> queue = new PriorityQueue<> (
            new Comparator<Player>() {
                public int compare (Player a, Player b) {
                    return Integer.compare(b.getScore(), a.getScore());
                }
            }
        );

        for (Player player : players) {
            queue.add(player);
        }
        
        for (var player : queue) {
            JLabel newLabel = new JLabel(player.toString());
            newLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.finalPanel.add(newLabel);
        }

        JButton backToStartMenuButton = new JButton(LocaleHelper.getBackToStartMenuText());
        backToStartMenuButton.addActionListener(e -> userInterface.showBackToStartViewDialog());
        backToStartMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.finalPanel.add(backToStartMenuButton);

        JButton exitButton = new JButton(LocaleHelper.getExitApplicationText());
        exitButton.addActionListener(e -> userInterface.showExitDialog());
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.finalPanel.add(exitButton);

        this.add(finalPanel);
        this.repaint();
        this.validate();
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    public GUI getUserInterface() {
        return this.userInterface;
    }
}
