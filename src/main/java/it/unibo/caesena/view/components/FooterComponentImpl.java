package it.unibo.caesena.view.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import it.unibo.caesena.utils.ImageIconUtil;
import it.unibo.caesena.view.GUI;

public class FooterComponentImpl extends JPanel implements FooterComponent<JPanel>{

    JPanel playerColorPanel = new JPanel();
    JLabel tileImageLabel;
    JLabel playerMeepleLabel = new JLabel("N° meeple");
    JLabel playerNameLabel = new JLabel("name");
    JLabel playerScoreLabel = new JLabel("score");
    JButton rotateButton = new JButton("Rotate");
    JLabel remainingTilesLabel = new JLabel("N° tile");

    GUI userInterface;

    public FooterComponentImpl(final GUI userInterface) {
        super();
        this.userInterface = userInterface;
        JPanel innerPanel = new JPanel();
        this.setBackground(java.awt.Color.BLACK);
        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.insets = new Insets(20, 10, 20, 10);

        this.setSize(400, 400);
        playerColorPanel.setSize(new Dimension(40, 40));
        rotateButton.setSize(200, 200);
        rotateButton.setBounds(100,100,100,100);
        
        ImageIcon icon = new ImageIcon(userInterface.getController().getCurrentTile().getImageResourcesPath());
        this.tileImageLabel = new JLabel(icon);
        tileImageLabel.setPreferredSize(new Dimension(40, 40));
        tileImageLabel.setMinimumSize(new Dimension(40, 40));
        this.tileImageLabel.setBorder(new LineBorder(java.awt.Color.BLACK));

        //
        playerColorPanel.setPreferredSize(new Dimension(40, 40));
        playerColorPanel.setMinimumSize(new Dimension(40, 40));
        this.playerColorPanel.setBorder(new LineBorder(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer())));
        this.playerColorPanel.setBackground(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));
        //this.playerColorPanel.setBorder(new LineBorder(Color.GREEN));
        //

        Image resized = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        tileImageLabel.setIcon(new ImageIcon(resized));

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

        this.setVisible(true);
        innerPanel.setVisible(true);

        updateFooter();

        rotateButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.getController().rotateCurrentTile();
                updateCurrentTile();
            }
            
        });

    }

    @Override
    public JPanel getComponent() {
       return this;
    }

    @Override
    public void updateCurrentTile() {
        ImageIcon icon = new ImageIcon(userInterface.getController().getCurrentTile().getImageResourcesPath());
        
        double angle = 90 * userInterface.getController().getCurrentTile().getRotationCount();

        ImageIcon newIcon = ImageIconUtil.rotate(icon, angle);

        tileImageLabel.setIcon(new ImageIcon(newIcon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
    }

    @Override
    public void updateCurrentPlayerMeeples() {
        playerMeepleLabel.setText("M: "+userInterface.getController().getCurrentPlayerMeeples().stream().filter(m -> !m.isPlaced()).count());
    }

    @Override
    public void updateRemainingTiles() {
        remainingTilesLabel.setText(userInterface.getController().getNotPlacedTiles().size()+"");
    }

    @Override
    public void updateFooter() {
        updateCurrentTile();
        playerNameLabel.setText(userInterface.getController().getCurrentPlayer().getName());
        playerScoreLabel.setText("S: "+userInterface.getController().getCurrentPlayer().getScore()+"");
        updateCurrentPlayerMeeples();
        updateRemainingTiles();
        this.playerColorPanel.setBorder(new LineBorder(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer())));
        this.playerColorPanel.setBackground(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));
    }
}
