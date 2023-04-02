package it.unibo.caesena.view.components;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import java.util.List;
import java.util.Optional;

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
    private static final long serialVersionUID = -7370776246509188750L;
    private static final float INTERNAL_PADDING_RATIO = 0.02f;
    private static final int MINIMUM_PADDING = 3;
    private final GameView gameView;
    private final GUI userInterface;
    private final JPanel tileImagePanel;
    private final JButton rotateButton;
    private final JLabel playerNameLabel;
    private final JLabel remainingTilesLabel;
    private final JPanel playerNameMeeplesPanel;
    private final JPanel tilesLeaderboardPanel;
    private final RemainingMeeplesComponent<JPanel> meepleComponent;
    private final PlayerImage<JPanel> playerImageComponent;
    private final LeaderBoardComponent<JPanel> leaderboard;
    private Optional<TileImage> tileImage;
    private int innerPaddingSize;

    /**
     *
     * FooterComponent constructor.
     *
     * @param gameView
     */
    public FooterComponentImpl(final GameView gameView) {
        super();

        this.gameView = gameView;
        this.userInterface = gameView.getUserInterface();

        final JPanel innerPanel = new JPanel();

        this.setBackground(Color.ORANGE);
        this.setLayout(new BorderLayout());
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

        this.tileImage = Optional.empty();
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
                final int newSize = d.width > d.height ? d.height : d.width;
                return new Dimension(newSize, newSize);
            }

            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);

                if (tileImage.isPresent()) {
                    final BufferedImage tileBufferedImage = tileImage.get()
                            .getAsBufferedImageWithoutMeeple(this.getWidth(), this.getHeight());

                    final Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(4));
                    g.drawImage(tileBufferedImage, 0, 0, this.getWidth(), this.getHeight(), null);
                    g2d.drawRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        this.tileImagePanel.setOpaque(false);
        this.tileImagePanel.setAlignmentY(Component.CENTER_ALIGNMENT);

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
                newSize = newSize / 2;
                return new Dimension(newSize, newSize);
            }

            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final BufferedImage image = ResourceUtil.getBufferedImage("rotate-right.png", List.of());
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        this.rotateButton.setContentAreaFilled(false);
        this.rotateButton.setOpaque(false);
        this.rotateButton.setBorderPainted(false);

        this.playerNameMeeplesPanel = new JPanel();
        playerNameMeeplesPanel.setOpaque(false);
        playerNameMeeplesPanel.setLayout(new BoxLayout(playerNameMeeplesPanel, BoxLayout.Y_AXIS));

        this.playerNameLabel = new JLabel();
        playerNameLabel.setFont(GUI.MEDIUM_BOLD_FONT);
        playerNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerNameMeeplesPanel.add(playerNameLabel);
        this.meepleComponent = new RemainingMeeplesComponentImpl(gameView);
        meepleComponent.getComponent().setAlignmentX(Component.CENTER_ALIGNMENT);
        playerNameMeeplesPanel.add(meepleComponent.getComponent());

        this.tilesLeaderboardPanel = new JPanel();
        tilesLeaderboardPanel.setOpaque(false);
        tilesLeaderboardPanel.setLayout(new BoxLayout(tilesLeaderboardPanel, BoxLayout.Y_AXIS));

        this.remainingTilesLabel = new JLabel();
        remainingTilesLabel.setFont(GUI.MEDIUM_BOLD_FONT);
        remainingTilesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tilesLeaderboardPanel.add(remainingTilesLabel);
        this.leaderboard = new LeaderBoardComponentImpl(gameView.getUserInterface());
        leaderboard.getComponent().setAlignmentX(Component.CENTER_ALIGNMENT);
        tilesLeaderboardPanel.add(leaderboard.getComponent());
        
        this.playerImageComponent = new PlayerImageImpl();
        this.playerImageComponent.getComponent().setAlignmentY(Component.CENTER_ALIGNMENT);
        innerPanel.add(playerImageComponent.getComponent());
        innerPanel.add(playerNameMeeplesPanel);
        innerPanel.add(rotateButton);
        innerPanel.add(tileImagePanel);
        innerPanel.add(tilesLeaderboardPanel);
        innerPanel.setOpaque(false);
        this.add(innerPanel, BorderLayout.CENTER);

        innerPanel.setVisible(true);

        rotateButton.addActionListener(rotateButtonEventListener());

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                updatePadding();
            }
        });

        this.validate();
        this.repaint();
        super.setVisible(false);
    }

    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            this.leaderboard.getComponent().setVisible(true);
            this.meepleComponent.getComponent().setVisible(true);
            this.update();
            this.updatePadding();
        }

        super.setVisible(visible);
    }

    private void updatePadding() {
        final Dimension frameSize = userInterface.getSize();
        if (frameSize.getHeight() > frameSize.getWidth()) {
            innerPaddingSize = (int) Math.round(frameSize.getWidth() * INTERNAL_PADDING_RATIO);
        } else {
            innerPaddingSize = (int) Math.round(frameSize.getHeight() * INTERNAL_PADDING_RATIO);
        }
        innerPaddingSize = innerPaddingSize < MINIMUM_PADDING ? MINIMUM_PADDING : innerPaddingSize;
        this.setBorder(BorderFactory.createEmptyBorder(innerPaddingSize, innerPaddingSize, innerPaddingSize,
                innerPaddingSize));
        this.playerImageComponent.getComponent()
                .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, innerPaddingSize));
        this.playerNameMeeplesPanel.setBorder(BorderFactory.createEmptyBorder(0, innerPaddingSize, 0, innerPaddingSize));
        this.rotateButton.setBorder(BorderFactory.createEmptyBorder(0, innerPaddingSize, 0, innerPaddingSize));
        this.tileImagePanel.setBorder(BorderFactory.createEmptyBorder(0, innerPaddingSize, 0, innerPaddingSize));
        this.tilesLeaderboardPanel.setBorder(BorderFactory.createEmptyBorder(0, innerPaddingSize, 0, 0));
    }

    private ActionListener rotateButtonEventListener() {
        return (e) -> {
            if (tileImage.isPresent()) {
                gameView.removePlacedTile();
                userInterface.getController().rotateCurrentTile();
                this.tileImage.get().rotate();
                tileImagePanel.repaint();
            }
        };
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    public void updateCurrentTile() {
        final TileImage currentTileImage = gameView.getCurrentTileImage();
        if (tileImage.isEmpty() || !tileImage.get().equals(currentTileImage)) {
            this.tileImage = Optional.of(currentTileImage);
        }
        tileImagePanel.repaint();
    }

    @Override
    public void update() {
        updateCurrentTile();
        meepleComponent.update();
        leaderboard.update();
        final var color = userInterface.getController().getCurrentPlayer().getColor();
        playerImageComponent.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
        playerNameLabel.setText(userInterface.getController().getCurrentPlayer().getName());
        remainingTilesLabel.setText(
                LocaleHelper.getRemainingTilesText() + userInterface.getController().getNotPlacedTiles().size());
        if (tileImage.get().getTile().isPlaced()) {
            rotateButton.setEnabled(false);
        } else {
            rotateButton.setEnabled(true);
        }
        this.revalidate();
        this.repaint();
    }
}
