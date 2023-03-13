package it.unibo.caesena.view;

import java.util.List;
import javax.swing.*;

import java.awt.Component;

import it.unibo.caesena.model.Player;

public class GameOverView extends View{

    private final JPanel finalPanel;
    private final List<Player> numberOfPlayers;

    public GameOverView (GUI userInterface) {
        super(userInterface);
        
        this.finalPanel = new JPanel();
        this.numberOfPlayers = userInterface.getController().getPlayers();

        JLabel playersLabel = new JLabel("Players: ");
        this.finalPanel.add(playersLabel);

        this.finalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        for (int i = 0; i < this.numberOfPlayers.size(); i++) {
            JLabel newLabel = new JLabel(this.numberOfPlayers.get(i).toString());
            this.finalPanel.add(newLabel);
        }
        
        this.add(finalPanel);
        this.repaint();
        this.validate();
    }
}
