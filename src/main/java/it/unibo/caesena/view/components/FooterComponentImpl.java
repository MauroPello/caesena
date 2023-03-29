package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

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
    TileImage tileImage;
    GridBagConstraints constraints;

    //DA TESTARE PER MEEPLE COMPONENT
    RemainingMeeplesComponent<JPanel> meepleComponent;
    PlayerImage<JPanel> playerImageComponent;

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

        this.setBackground(Color.ORANGE);

        this.innerPanel = getRectangularJPanel();

        this.tileImage = gameView.getCurrentTileImage();
        this.tileImageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                final BufferedImage tileBufferedImage = tileImage.getAsBufferedImage(this.getWidth(), this.getHeight());
                g.drawImage(tileBufferedImage, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        tileImageLabel.setPreferredSize(new Dimension(40, 40));
        tileImageLabel.setMinimumSize(new Dimension(40, 40));
        this.tileImageLabel.setBorder(new LineBorder(java.awt.Color.BLACK));

        this.meepleComponent = new RemainingMeeplesComponentImpl(gameView);
        this.playerImageComponent = new PlayerImageImpl(40, 40);
        this.playerImageComponent.setColor(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));

        innerPanel.add(meepleComponent.getComponent());
        innerPanel.add(playerImageComponent.getComponent());
        innerPanel.add(playerMeepleLabel);
        innerPanel.add(playerNameLabel);
        innerPanel.add(playerScoreLabel);
        innerPanel.add(tileImageLabel);
        innerPanel.add(rotateButton);
        innerPanel.add(remainingTilesLabel);

        innerPanel.setVisible(true);
        this.add(innerPanel);

        updateFooter();

        this.setVisible(true);

        rotateButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.removePlacedTile();
                userInterface.getController().rotateCurrentTile();
                tileImage.rotate();
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
        if (!tileImage.getTile().equals(userInterface.getController().getCurrentTile())) {
            tileImage = new TileImage(userInterface.getController().getCurrentTile());
        }
        tileImageLabel.revalidate();
        tileImageLabel.repaint();
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
        playerImageComponent.setColor(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));
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
