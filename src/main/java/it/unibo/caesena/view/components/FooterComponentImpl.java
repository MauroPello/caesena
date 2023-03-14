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

public class FooterComponentImpl extends JPanel implements FooterComponent<JPanel>{

    JButton playerColorButton = new JButton();
    JLabel playerMeepleLabel = new JLabel();
    JLabel playerNameLabel = new JLabel();
    JLabel playerScoreLabel = new JLabel();
    JButton tileImageButton = new JButton();
    JButton rotateButton = new JButton("Rotate");
    JLabel remainingTilesLabel = new JLabel();

    public FooterComponentImpl(final Controller Controller)
    {
        super();

        playerColorButton.setSize(new Dimension(40, 40));
        tileImageButton.setSize(new Dimension(40, 40));
        rotateButton.setSize(new Dimension(40, 40));

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        playerColorButton.setEnabled(false);
        this.add(playerColorButton);

        this.add(playerMeepleLabel);
        this.add(playerNameLabel);
        this.add(playerScoreLabel);

        tileImageButton.setEnabled(false);
        this.add(tileImageButton);
        
        rotateButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.rotateCurrentTile();
            }
            
        });
        this.add(rotateButton);
        this.add(remainingTilesLabel);

        this.setVisible(true);
    }

    @Override
    public JPanel getComponent() {
       return this;
    }
}
