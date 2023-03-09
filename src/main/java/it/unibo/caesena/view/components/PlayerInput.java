package it.unibo.caesena.view.components;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerInput extends JPanel {
    
    public PlayerInput() {
        this.add(new JTextField("Nome: "));
        this.add(new JTextField("Color: "));
    }

}
