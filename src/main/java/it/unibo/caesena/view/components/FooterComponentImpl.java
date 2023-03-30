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

import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.GameView;
import it.unibo.caesena.view.LocaleHelper;

public class FooterComponentImpl extends JPanel implements FooterComponent<JPanel> {

    JPanel tileImagePanel;
    JButton rotateButton;
    JLabel playerNameLabel = new JLabel("name");
    JLabel playerScoreLabel = new JLabel("score");
    JLabel remainingTilesLabel = new JLabel("N° tile");

    GUI userInterface;

    TileImage tileImage;
    GridBagConstraints constraints;

    RemainingMeeplesComponent<JPanel> meepleComponent;
    PlayerImage<JPanel> playerImageComponent;

    public FooterComponentImpl(final GameView gameView) {
        super();
        this.userInterface = gameView.getUserInterface();

        this.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        this.setBackground(Color.ORANGE);

        this.tileImage = gameView.getCurrentTileImage();
        this.tileImagePanel = new JPanel() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final BufferedImage tileBufferedImage = tileImage.getAsBufferedImageWithoutMeeple(this.getWidth(),
                        this.getHeight());
                g.setColor(Color.BLACK);
                if (this.getHeight() > this.getWidth()) {
                    g.drawRect(0, 0, this.getWidth(), this.getWidth());
                    g.drawImage(tileBufferedImage, 0, 0, this.getWidth(), this.getWidth(), null);
                } else {
                    g.drawRect(0, 0, this.getHeight(), this.getHeight());
                    g.drawImage(tileBufferedImage, 0, 0, this.getHeight(), this.getHeight(), null);
                }
            }
        };
        this.tileImagePanel.setOpaque(false);

        this.meepleComponent = new RemainingMeeplesComponentImpl(gameView);
        this.playerImageComponent = new PlayerImageImpl();
        this.playerImageComponent
                .setColor(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));

        this.rotateButton = new JButton() {
            @Override
            protected void paintComponent(final Graphics graphics) {
                super.paintComponent(graphics);
                final BufferedImage image = ResourceUtil.getBufferedImage("rotate-right.png", List.of());
                if (this.getHeight() > this.getWidth()) {
                    graphics.drawImage(image, 0, 0, this.getWidth(), this.getWidth(), null);
                } else {
                    graphics.drawImage(image, 0, 0, this.getHeight(), this.getHeight(), null);
                }
            }
        };
        rotateButton.setContentAreaFilled(false);
        rotateButton.setOpaque(false);
        rotateButton.setBorderPainted(false);

        constraints.weighty = 1.0;
        constraints.weightx = 0.15;
        this.add(playerImageComponent.getComponent(), constraints);
        constraints.weightx = 0.1;
        this.add(playerNameLabel, constraints);
        constraints.weightx = 0.2;
        this.add(meepleComponent.getComponent(), constraints);
        constraints.weightx = 0.05;
        this.add(playerScoreLabel, constraints);
        constraints.weightx = 0.15;
        this.add(tileImagePanel, constraints);
        constraints.weightx = 0.05;
        this.add(rotateButton, constraints);
        constraints.weightx = 0.1;
        this.add(remainingTilesLabel, constraints);

        updateFooter();

        this.setVisible(true);

        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
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
        tileImagePanel.revalidate();
        tileImagePanel.repaint();
    }

    @Override
    public void updateRemainingTiles() {
        remainingTilesLabel.setText("Remaining Tiles: " + userInterface.getController().getNotPlacedTiles().size());
        // TODO localehelper
    }

    @Override
    public void updateFooter() {
        updateCurrentTile();
        meepleComponent.update();
        playerImageComponent.setColor(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));
        playerNameLabel.setText(userInterface.getController().getCurrentPlayer().getName());
        playerScoreLabel
                .setText(LocaleHelper.getScoreText() + userInterface.getController().getCurrentPlayer().getScore());
        updateRemainingTiles();
        if (userInterface.getController().getCurrentTile().isPlaced()) {
            rotateButton.setEnabled(false);
        } else {
            rotateButton.setEnabled(true);
        }
        this.revalidate();
        this.repaint();
    }
}
