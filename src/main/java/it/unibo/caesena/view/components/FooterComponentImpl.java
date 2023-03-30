package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

/**
* 
* FooterComponent implementation.
*/
public class FooterComponentImpl extends JPanel implements FooterComponent<JPanel> {
    private GameView gameView;
    private GUI userInterface;

    private JPanel tileImagePanel;
    private JButton rotateButton;
    private JLabel playerNameLabel;
    private JLabel playerScoreLabel;
    private JLabel remainingTilesLabel;

    private TileImage tileImage;

    private RemainingMeeplesComponent<JPanel> meepleComponent;
    private PlayerImage<JPanel> playerImageComponent;

    /**
     * 
     * FooterComponent constructor.
     * @param gameView
     */
    public FooterComponentImpl(final GameView gameView) {
        super();

        this.gameView = gameView;
        this.userInterface = gameView.getUserInterface();

        //TODO da cancellare dopo aver inserito l'immagine di background
        this.setBackground(Color.ORANGE);
        this.setLayout(new GridBagLayout());

        this.playerNameLabel = new JLabel();
        this.playerScoreLabel = new JLabel();
        this.remainingTilesLabel = new JLabel();

        this.tileImage = gameView.getCurrentTileImage();
        this.tileImagePanel = new JPanel() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);

                final BufferedImage tileBufferedImage = tileImage
                .getAsBufferedImageWithoutMeeple(this.getWidth(), this.getHeight());

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
        this.rotateButton.setContentAreaFilled(false);
        this.rotateButton.setOpaque(false);
        this.rotateButton.setBorderPainted(false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

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

        this.updateFooter();

        this.setVisible(true);

        rotateButton.addActionListener(rotateButtonEventListener());
    }

    private ActionListener rotateButtonEventListener(){
        return (e) -> {
            tileImage = gameView.getCurrentTileImage();
            gameView.removePlacedTile();
            userInterface.getController().rotateCurrentTile();
            tileImage.rotate();
            updateCurrentTile();
        };
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
        remainingTilesLabel.setText(LocaleHelper.getRemainingTilesText() + userInterface.getController().getNotPlacedTiles().size());
    }

    @Override
    public void updateFooter() {
        updateCurrentTile();
        meepleComponent.update();
        playerImageComponent.setColor(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));
        playerNameLabel.setText(userInterface.getController().getCurrentPlayer().getName());
        playerScoreLabel.setText(LocaleHelper.getScoreText() + userInterface.getController().getCurrentPlayer().getScore());
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
