package it.unibo.caesena.view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
    private static final int INTERNAL_PADDING_RATIO = 50;
    private static final int MINIMUM_PADDING = 3;

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

        JPanel innerPanel = new JPanel();

        //TODO da cancellare dopo aver inserito l'immagine di background
        this.setBackground(Color.ORANGE);
        this.setLayout(new BorderLayout());
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

        this.playerNameLabel = new JLabel();
        this.playerScoreLabel = new JLabel();
        this.remainingTilesLabel = new JLabel();

        this.tileImage = gameView.getCurrentTileImage();
        this.tileImagePanel = new JPanel() {

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getPreferredSize() {
                final Dimension d = this.getParent().getSize();
                int newSize = d.width > d.height ? d.height : d.width;
                return new Dimension(newSize, newSize);
            }

            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);

                final BufferedImage tileBufferedImage = tileImage
                .getAsBufferedImageWithoutMeeple(this.getWidth(), this.getHeight());

                if (this.getHeight() > this.getWidth()) {
                    g.drawImage(tileBufferedImage, 0, 0, this.getWidth(), this.getWidth(), null);
                } else {
                    g.drawImage(tileBufferedImage, 0, 0, this.getHeight(), this.getHeight(), null);
                }
            }
        };
        this.tileImagePanel.setOpaque(false);
        this.tileImagePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.tileImagePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        this.meepleComponent = new RemainingMeeplesComponentImpl(gameView);
        this.playerImageComponent = new PlayerImageImpl();
        this.playerImageComponent
                .setColor(userInterface.getPlayerColor(userInterface.getController().getCurrentPlayer()));
        this.playerImageComponent.getComponent().setAlignmentY(Component.CENTER_ALIGNMENT);

        this.rotateButton = new JButton() {

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getPreferredSize() {
                final Dimension d = this.getParent().getSize();
                int newSize = d.width > d.height ? d.height : d.width;
                return new Dimension(newSize, newSize);
            }

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

        innerPanel.add(playerImageComponent.getComponent());
        innerPanel.add(playerNameLabel);
        innerPanel.add(meepleComponent.getComponent());
        innerPanel.add(playerScoreLabel);
        innerPanel.add(tileImagePanel);
        innerPanel.add(rotateButton);
        innerPanel.add(remainingTilesLabel);
        innerPanel.setOpaque(false);
        this.add(innerPanel, BorderLayout.CENTER);

        this.setVisible(true);
        innerPanel.setVisible(true);
        
        rotateButton.addActionListener(rotateButtonEventListener());
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updatePadding();
            }
        });

        this.validate();
        this.repaint();
        this.updateFooter();
        this.updatePadding();
    }

    private void updatePadding() {
        final Dimension frameSize = userInterface.getSize();
        int borderSize;
        if (frameSize.getHeight() > frameSize.getWidth()) {
            borderSize = (int) Math.round(frameSize.getWidth() / INTERNAL_PADDING_RATIO);
        } else {
            borderSize = (int) Math.round(frameSize.getHeight() / INTERNAL_PADDING_RATIO);
        }
        borderSize = borderSize < MINIMUM_PADDING ? MINIMUM_PADDING : borderSize;
        this.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
    }

    private ActionListener rotateButtonEventListener(){
        return (e) -> {
            gameView.removePlacedTile();
            userInterface.getController().rotateCurrentTile();
            this.tileImage.rotate();
            tileImagePanel.repaint();
        };
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    public void updateCurrentTile() {
        final TileImage currentTileImage = gameView.getCurrentTileImage();
        if (!tileImage.equals(currentTileImage)) {
            this.tileImage = currentTileImage;
        }
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
