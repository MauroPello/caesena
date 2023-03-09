package it.unibo.caesena.view.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.caesena.utils.Pair;

public class PlayerInput extends JPanel {
    
    private final static int TEXT_FIELD_COLUMNS = 4;

    private final JTextField playerName;
    private final JTextField playerColor;

    public PlayerInput() {
        super();
        
        this.add(new JLabel("Name: "));
        this.playerName = new JTextField();
        this.playerName.setColumns(TEXT_FIELD_COLUMNS);
        this.add(playerName);
        
        this.add(new JLabel("Color: "));
        this.playerColor = new JTextField();
        this.playerColor.setColumns(TEXT_FIELD_COLUMNS);
        this.add(playerColor);

    }

    public Pair<String, String> getPlayerData() {
        return new Pair<>(playerName.getText(), playerColor.getText());
    }

}
