package it.unibo.caesena.view.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.awt.Image;
//import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.utils.Color;

public class FooterComponentImpl extends JPanel implements FooterComponent<JPanel>{

    //JButton playerColorButton = new JButton("color");
    JPanel playerColorPanel = new JPanel();
    JLabel tileImageLabel;

    JLabel playerMeepleLabel = new JLabel("N° meeple");
    JLabel playerNameLabel = new JLabel("name");
    JLabel playerScoreLabel = new JLabel("score");
    JButton rotateButton = new JButton("Rotate");
    JLabel remainingTilesLabel = new JLabel("N° tile");

    private Color playerColor;

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
        playerColorPanel.setSize(new Dimension(40, 40));
        rotateButton.setSize(200, 200);
        rotateButton.setBounds(100,100,100,100);
        
        this.playerColorPanel.addPropertyChangeListener("background", (p) -> {
            // playerColor = (Color)p.getNewValue();
            // playerColorPanel.setBackground((Color)Color.createCustomColor(Controller.getCurrentPlayer().getColor().getHex()));
            Player ciao = Controller.getCurrentPlayer();
            //playerColorPanel.setBackground();
        });
        
        String imagePath = "";
        try {
            imagePath = Paths.get(ClassLoader.getSystemResource(Controller.getCurrentTile().getImageResourcesPath()).toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(imagePath);
        //Image img = icon.getImage();
        //img = img.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);

        this.tileImageLabel = new JLabel(icon);
        tileImageLabel.setPreferredSize(new Dimension(40, 40));
        tileImageLabel.setMinimumSize(new Dimension(40, 40));
        this.tileImageLabel.setBorder(new LineBorder(java.awt.Color.BLACK));

        Image resized = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        tileImageLabel.setIcon(new ImageIcon(resized));
        //System.out.println(icon.toString());

        innerPanel.add(playerColorPanel, constraints);
        constraints.gridx ++;
        innerPanel.add(playerMeepleLabel, constraints);
        constraints.gridx ++;
        innerPanel.add(playerNameLabel, constraints);
        constraints.gridx ++;
        innerPanel.add(playerScoreLabel, constraints);
        constraints.gridx ++;
        innerPanel.add(tileImageLabel, constraints);
        constraints.gridx ++;
        innerPanel.add(rotateButton, constraints);
        constraints.gridx ++;
        innerPanel.add(remainingTilesLabel, constraints);

        this.add(innerPanel);

        //playerColorButton.setBackground(Controller.getCurrentPlayer().getColor());

        rotateButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.rotateCurrentTile();
            }
            
        });

        /*
         * 
            private void gameSetupDebug() {
            Color color1 = Color.createColor("FF0000", "Red");
            Color color2 = Color.createColor("00FF00", "Green");
            this.controller.addPlayer("Giocatore1", color1);
            this.controller.addPlayer("Giocatore2", color2);
    }
         * 
         */


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
