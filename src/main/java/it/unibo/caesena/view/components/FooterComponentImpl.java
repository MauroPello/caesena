package it.unibo.caesena.view.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.GameView;

public class FooterComponentImpl extends JPanel implements FooterComponent<JPanel>{

    JLabel tileImageLabel;
    JLabel playerMeepleLabel = new JLabel("N° meeple");
    JLabel playerNameLabel = new JLabel("name");
    JLabel playerScoreLabel = new JLabel("score");
    JButton rotateButton = new JButton("Rotate");
    JLabel remainingTilesLabel = new JLabel("N° tile");

    GUI userInterface;

    JPanel innerPanel;
    GridBagConstraints constraints;

    //DA TESTARE PER MEEPLE COMPONENT
    RemainingMeeplesComponent<JPanel> meepleComponent;

    private JPanel getRectangularJPanel() {
        return new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            public Dimension getPreferredSize() {
                //constraints.insets = new Insets(20, 10, 20, 10);

                Dimension innerDimension = new Dimension(this.getParent().getWidth(), this.getParent().getHeight());
                //this è l'innerpanel
                Dimension externDimension = new Dimension(this.getParent().getWidth(), this.getParent().getParent().getHeight());
                //this.getParent() è l'exter panel del footer

                int innerNewW = innerDimension.width;
                int externNewH = externDimension.height;

                int componentsHeight = (externNewH-250)/2+1;

                return new Dimension(innerNewW-50, componentsHeight);

                //-300 è la distanza dai bordi di this (il panel esterno)
                //modificando la dimensione di this la weight di questo Jpanel sarà sempre dinamicamente
                //la lunghezza di this-400
            }
        };
    }

    public FooterComponentImpl(final GameView gameView) {
        super();
        this.userInterface = gameView.getUserInterface();
        JPanel innerPanel = new JPanel();
        this.innerPanel = innerPanel;

        this.setBackground(java.awt.Color.ORANGE);
        innerPanel.setLayout(new GridBagLayout());

        //da commentare se si vuole il classico setUP
        //innerPanel.setPreferredSize(getSize());

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 1;
        //constraints.insets = new Insets(20, 10, 20, 10);S

        ImageIcon icon = new ImageIcon(ResourceUtil.getBufferedImage(userInterface.getController().getCurrentTile().getTileType().name() + ".png", List.of("tiles")));
        this.tileImageLabel = new JLabel(icon);
        tileImageLabel.setPreferredSize(new Dimension(40, 40));
        tileImageLabel.setMinimumSize(new Dimension(40, 40));
        this.tileImageLabel.setBorder(new LineBorder(java.awt.Color.BLACK));

        innerPanel = getRectangularJPanel();

        Image resized = icon.getImage().getScaledInstance(getRectangularJPanel().getHeight()-1, getRectangularJPanel().getHeight()-1, java.awt.Image.SCALE_SMOOTH);
        //Image resized = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        tileImageLabel.setIcon(new ImageIcon(resized));

        //DA TESTARE PER MEEPLE COMPONENT
        this.meepleComponent = new RemainingMeeplesComponentImpl(gameView);

        innerPanel.add(meepleComponent.getComponent(), constraints);
        constraints.gridx ++;
        //DA TESTARE PER MEEPLE COMPONENT

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
                gameView.removePlacedTile();
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
        Image rotatedPreviewTile = new TileImage(userInterface.getController().getCurrentTile()).getAsBufferedImage();
        tileImageLabel.setIcon(new ImageIcon(rotatedPreviewTile.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
    }

    @Override
    public void updateCurrentPlayerMeeples() {
        var currentPlayer = this.userInterface.getController().getCurrentPlayer();
        playerMeepleLabel.setText("M: "+userInterface.getController().getNotPlacedPlayerMeeples(currentPlayer).size());

        meepleComponent.update();
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
        if(userInterface.getController().getCurrentTile().isPlaced()) {
            rotateButton.setEnabled(false);
        } else {
            rotateButton.setEnabled(true);
        }
    }
}
