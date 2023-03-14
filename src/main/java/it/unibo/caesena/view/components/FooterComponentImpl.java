package it.unibo.caesena.view.components;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;

public class FooterComponentImpl implements FooterComponent{
    
    private JPanel footer = new JPanel();

    JButton playerColorButton = new JButton();
    JLabel playerMeepleLabel = new JLabel();
    JLabel playerNameLabel = new JLabel();
    JLabel playerScoreLabel = new JLabel();
    JButton tileImageButton = new JButton();
    JButton rotateButton = new JButton("Rotate");
    JLabel remainingTilesLabel = new JLabel();

    public FooterComponentImpl(final Controller Controller)
    {
        playerColorButton.setSize(new Dimension(40, 40));
        tileImageButton.setSize(new Dimension(40, 40));
        rotateButton.setSize(new Dimension(40, 40));

        footer.setLayout(new BoxLayout(footer, BoxLayout.X_AXIS));
        
        playerColorButton.setEnabled(false);
        footer.add(playerColorButton);

        footer.add(playerMeepleLabel);
        footer.add(playerNameLabel);
        footer.add(playerScoreLabel);

        tileImageButton.setEnabled(false);
        footer.add(tileImageButton);
        
        rotateButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.rotateCurrentTile();
            }
            
        });
        footer.add(rotateButton);
        footer.add(remainingTilesLabel);
    }
}
