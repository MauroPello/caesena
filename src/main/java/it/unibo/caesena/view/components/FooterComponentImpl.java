package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.GameView;
import it.unibo.caesena.view.LocaleHelper;

public class FooterComponentImpl extends JPanel implements FooterComponent<JPanel>{

    JLabel tileImageLabel;
    JButton rotateButton;
    JLabel playerNameLabel = new JLabel("name");
    JLabel playerScoreLabel = new JLabel("score");
    JLabel remainingTilesLabel = new JLabel("N° tile");

    GUI userInterface;

    TileImage tileImage;
    GridBagConstraints constraints;

    RemainingMeeplesComponent<JPanel> meepleComponent;
    PlayerImage<JPanel> playerImageComponent;

    // private JPanel getRectangularJPanel() {
    //     return new JPanel() {
    //         private static final long serialVersionUID = 1L;
    //         @Override
    //         public Dimension getPreferredSize() {
    //             //constraints.insets = new Insets(20, 10, 20, 10);

    //             Dimension innerDimension = new Dimension(this.getParent().getWidth(), this.getParent().getHeight());
    //             //this è l'innerpanel
    //             Dimension externDimension = new Dimension(this.getParent().getWidth(), this.getParent().getParent().getHeight());
    //             //this.getParent() è l'exter panel del footer

    //             int innerNewW = innerDimension.width;
    //             int externNewH = externDimension.height;

    //             int componentsHeight = (externNewH-250)/2+1;

    //             return new Dimension(innerNewW-50, componentsHeight);

    //             //-300 è la distanza dai bordi di this (il panel esterno)
    //             //modificando la dimensione di this la weight di questo Jpanel sarà sempre dinamicamente
    //             //la lunghezza di this-400
    //         }
    //     };
    // }

    public FooterComponentImpl(final GameView gameView) {
        super();
        this.userInterface = gameView.getUserInterface();

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        this.setBackground(Color.ORANGE);

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
        this.tileImageLabel.setBorder(new LineBorder(Color.BLACK));

        this.meepleComponent = new RemainingMeeplesComponentImpl(gameView);
        this.playerImageComponent = new PlayerImageImpl();
        this.playerImageComponent.setColor(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));

        this.rotateButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                BufferedImage image = ResourceUtil.getBufferedImage("rotate-right.png", List.of());
                graphics.drawImage(image, 0, 0, this.getHeight(), this.getHeight(), null);
            }
        };
        rotateButton.setContentAreaFilled(false);
        rotateButton.setOpaque(false);
        rotateButton.setBorderPainted(false);

        constraints.weightx = 0.3;
        this.add(playerImageComponent.getComponent(), constraints);
        constraints.weightx = 0.1;
        this.add(playerNameLabel, constraints);
        constraints.weightx = 0.5;
        this.add(meepleComponent.getComponent(), constraints);
        constraints.weightx = 0.1;
        this.add(playerScoreLabel, constraints);
        constraints.weightx = 0.3;
        this.add(tileImageLabel, constraints);
        constraints.weightx = 0.3;
        this.add(rotateButton, constraints);
        constraints.weightx = 0.5;
        this.add(remainingTilesLabel, constraints);

        updateFooter();

        this.setVisible(true);

        rotateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                tileImage = gameView.getCurrentTileImage();
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
    public void updateRemainingTiles() {
        remainingTilesLabel.setText("Remaining Tiles: " + userInterface.getController().getNotPlacedTiles().size());
        //TODO localehelper
    }

    @Override
    public void updateFooter() {
        updateCurrentTile();
        meepleComponent.update();
        playerImageComponent.setColor(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));
        playerNameLabel.setText(userInterface.getController().getCurrentPlayer().getName());
        playerScoreLabel.setText(LocaleHelper.getScoreText()+userInterface.getController().getCurrentPlayer().getScore());
        updateRemainingTiles();
        if(userInterface.getController().getCurrentTile().isPlaced()) {
            rotateButton.setEnabled(false);
        } else {
            rotateButton.setEnabled(true);
        }
    }
}
