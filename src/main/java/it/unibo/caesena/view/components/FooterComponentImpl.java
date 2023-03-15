package it.unibo.caesena.view.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;

public class FooterComponentImpl extends JPanel implements FooterComponent<JPanel>{

    JButton playerColorButton = new JButton("color");
    JLabel playerMeepleLabel = new JLabel("N° meeple");
    JLabel playerNameLabel = new JLabel("name");
    JLabel playerScoreLabel = new JLabel("score");
    JButton tileImageButton = new JButton("tile");
    JButton rotateButton = new JButton("Rotate");
    JLabel remainingTilesLabel = new JLabel("N° tile");

    public FooterComponentImpl(final Controller Controller)
    {
        super();
        JPanel innerPanel = new JPanel();
        this.setBackground(java.awt.Color.BLACK);
        //innerPanel.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.insets = new Insets(20, 10, 20, 10);
        //constraints.gridwidth = GridBagConstraints.REMAINDER;

        this.setSize(400, 400);
        playerColorButton.setSize(new Dimension(40, 40));
        tileImageButton.setSize(new Dimension(40, 40));
        rotateButton.setSize(200, 200);
        rotateButton.setBounds(100,100,100,100);
        
        innerPanel.add(playerColorButton, constraints);
        constraints.gridx ++;
        innerPanel.add(playerMeepleLabel, constraints);
        constraints.gridx ++;
        innerPanel.add(playerNameLabel, constraints);
        constraints.gridx ++;
        innerPanel.add(playerScoreLabel, constraints);
        constraints.gridx ++;
        innerPanel.add(tileImageButton, constraints);
        constraints.gridx ++;
        innerPanel.add(rotateButton, constraints);
        constraints.gridx ++;
        innerPanel.add(remainingTilesLabel, constraints);

        this.add(innerPanel);
        /*playerColorButton.setEnabled(false);
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
        this.add(remainingTilesLabel);*/

        this.setVisible(true);
        innerPanel.setVisible(true);
    }

    @Override
    public JPanel getComponent() {
       return this;
    }
}
