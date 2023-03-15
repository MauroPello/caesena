package it.unibo.caesena.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PauseView extends View {

    public PauseView(final GUI userInterface) {
        super(userInterface);
        this.setLayout(new GridBagLayout());
        this.setBackground(java.awt.Color.BLACK);
        this.setOpaque(false);
        
        Font mainFont = new Font("SansSerif", Font.BOLD, 20);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("Pause menu");
        lbl.setFont(mainFont);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lbl);

        JButton btn1 = new JButton("Go back to playing");
        btn1.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn1.addActionListener((e) -> {
            userInterface.togglePauseView();            
        });
        mainPanel.add(btn1);
        JButton btn2 = new JButton("Exit and start new game");
        btn2.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btn2);
        btn2.addActionListener((e) -> {
            userInterface.showStartView();
        });
        JButton btn3 = new JButton("Exit and close game");
        btn3.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn3.addActionListener((e) -> {
            userInterface.exit();
        });
        mainPanel.add(btn3);

        this.add(mainPanel);
    }
}
