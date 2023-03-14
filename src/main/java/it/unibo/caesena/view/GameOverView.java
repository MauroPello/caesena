package it.unibo.caesena.view;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.*;
import java.awt.Component;
import java.awt.Font;
import it.unibo.caesena.model.Player;

public class GameOverView extends View{

    private final JPanel finalPanel;
    private final List<Player> players;
    private Font mainFont;

    public GameOverView (GUI userInterface) {
        super(userInterface);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.mainFont = new Font("SansSerif", Font.BOLD, 20);        
        this.finalPanel = new JPanel();
        this.players = userInterface.getController().getPlayers();

        JLabel playersLabel = new JLabel("Game Over: ");
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
        
        this.finalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(finalPanel);
        this.repaint();
        this.validate();
    }
}
