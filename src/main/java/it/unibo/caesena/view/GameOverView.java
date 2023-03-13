package it.unibo.caesena.view;

import java.util.List;
import javax.swing.*;
import java.awt.Component;
import java.awt.Font;
import it.unibo.caesena.model.Player;

public class GameOverView extends View{

    private final JPanel finalPanel;
    private final List<Player> numberOfPlayers;
    private Font mainFont;

    public GameOverView (GUI userInterface) {
        super(userInterface);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.mainFont = new Font("SansSerif", Font.BOLD, 20);        
        this.finalPanel = new JPanel();
        this.numberOfPlayers = userInterface.getController().getPlayers();

        JLabel playersLabel = new JLabel("Game Over: ");
        playersLabel.setFont(mainFont);
        playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.finalPanel.add(playersLabel);
        
        for (int i = 0; i < this.numberOfPlayers.size(); i++) {
            JLabel newLabel = new JLabel(this.numberOfPlayers.get(i).toString());
            newLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.finalPanel.add(newLabel);
        }
        
        this.finalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(finalPanel);
        this.repaint();
        this.validate();
    }
}
