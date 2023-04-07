package it.unibo.caesena.view.components;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.border.CompoundBorder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.UserInterface;
import it.unibo.caesena.view.components.common.JPanelWithBackgroundImage;
import it.unibo.caesena.view.components.meeple.RemainingMeeplesComponent;
import it.unibo.caesena.view.components.meeple.RemainingMeeplesComponentImpl;
import it.unibo.caesena.view.components.player.LeaderBoardComponent;
import it.unibo.caesena.view.components.player.LeaderBoardComponentImpl;
import it.unibo.caesena.view.components.player.PlayerImage;
import it.unibo.caesena.view.components.player.PlayerImageImpl;
import it.unibo.caesena.view.components.tile.TileImage;
import it.unibo.caesena.view.scene.GameScene;

/**
 * {@inheritDoc}
 *
 * Implements the interface {@link FooterComponent} using a
 * {@link it.unibo.caesena.view.components.common.JPanelWithBackgroundImage}.
 */
public class FooterComponentImpl implements FooterComponent<JPanel> {
    private static final float INTERNAL_PADDING_RATIO = 0.02f;
    private static final int MINIMUM_PADDING = 3;
    private static final float SPACER_RATIO = 0.2f;
    private final GameScene gameScene;
    private final UserInterface userInterface;
    private final JPanel tileImagePanel;
    private final JButton rotateButton;
    private final JLabel playerNameLabel;
    private final JLabel remainingTilesLabel;
    private final JPanel playerNameMeeplesPanel;
    private final JPanel tilesLeaderboardPanel;
    private final JPanel spacerPanel;
    private final RemainingMeeplesComponent<JPanel> meepleComponent;
    private final PlayerImage<JPanel> playerImageComponent;
    private final LeaderBoardComponent<JPanel> leaderboard;
    private final JPanelWithBackgroundImage mainPanel;
    private Optional<TileImage> tileImage;
    private int innerPaddingSize;

    /**
     * FooterComponent constructor.
     *
     * @param gameScene the parent GameScene
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "This component will always need access to the GameScene "
        + "he's placed in as it uses its methods and needs to send and retrieve information from it")
    public FooterComponentImpl(final GameScene gameScene) {
        this.mainPanel = new JPanelWithBackgroundImage(ResourceUtil.getBufferedImage("background_Footer.png", List.of()));
        this.mainPanel.setLayout(new BorderLayout());

        this.gameScene = gameScene;
        this.userInterface = gameScene.getUserInterface();

        final JPanel innerPanel = new JPanel();
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
        playerNameMeeplesPanel.setLayout(new GridBagLayout());
        playerNameMeeplesPanel.setOpaque(false);

        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        this.playerNameLabel = new JLabel();
        playerNameLabel.setFont(GUI.MEDIUM_BOLD_FONT);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        playerNameMeeplesPanel.add(playerNameLabel, gridBagConstraints);
        this.meepleComponent = new RemainingMeeplesComponentImpl(gameScene);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        playerNameMeeplesPanel.add(meepleComponent.getComponent(), gridBagConstraints);

        this.tilesLeaderboardPanel = new JPanel();
        tilesLeaderboardPanel.setOpaque(false);
        tilesLeaderboardPanel.setLayout(new BoxLayout(tilesLeaderboardPanel, BoxLayout.Y_AXIS));

        this.remainingTilesLabel = new JLabel();
        remainingTilesLabel.setFont(GUI.MEDIUM_BOLD_FONT);
        remainingTilesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tilesLeaderboardPanel.add(remainingTilesLabel);
        this.leaderboard = new LeaderBoardComponentImpl(this.userInterface);
        tilesLeaderboardPanel.add(leaderboard.getComponent());

        this.playerImageComponent = new PlayerImageImpl();
        innerPanel.add(playerImageComponent.getComponent());
        innerPanel.add(playerNameMeeplesPanel);
        this.spacerPanel = new JPanel();
        this.spacerPanel.setOpaque(false);
        innerPanel.add(spacerPanel);
        innerPanel.add(tilesLeaderboardPanel);
        innerPanel.add(rotateButton);
        innerPanel.add(tileImagePanel);
        innerPanel.setOpaque(false);
        this.mainPanel.add(innerPanel, BorderLayout.CENTER);

        innerPanel.setVisible(true);

        rotateButton.addActionListener(rotateButtonEventListener());

        this.mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                updateSize();
            }
        });

        this.mainPanel.setOpaque(false);
        this.mainPanel.setVisible(false);
    }

    /**
     * {@inheritDoc}
     *
     * When the footer is set to visible it automatically updates its components.
     */
    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            this.leaderboard.setVisible(true);
            this.meepleComponent.setVisible(true);
            this.update();
            this.updateSize();
        }

        this.mainPanel.setVisible(visible);
    }

    /**
     * updates the size of the FooterComponent.
     */
    private void updateSize() {
        final Dimension frameSize = gameScene.getComponent().getSize();
        if (frameSize.getHeight() > frameSize.getWidth()) {
            innerPaddingSize = (int) Math.round(frameSize.getWidth() * INTERNAL_PADDING_RATIO);
        } else {
            innerPaddingSize = (int) Math.round(frameSize.getHeight() * INTERNAL_PADDING_RATIO);
        }
        innerPaddingSize = innerPaddingSize < MINIMUM_PADDING ? MINIMUM_PADDING : innerPaddingSize;
        this.mainPanel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(innerPaddingSize, innerPaddingSize,
            innerPaddingSize, innerPaddingSize)));
        this.playerImageComponent.getComponent()
                .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, innerPaddingSize));
        this.playerNameMeeplesPanel.setBorder(BorderFactory.createEmptyBorder(0, innerPaddingSize, 0, innerPaddingSize));
        this.rotateButton.setBorder(BorderFactory.createEmptyBorder(0, innerPaddingSize, 0, innerPaddingSize));
        this.tileImagePanel.setBorder(BorderFactory.createEmptyBorder(0, innerPaddingSize, 0, innerPaddingSize));
        this.tilesLeaderboardPanel.setBorder(BorderFactory.createEmptyBorder(0, innerPaddingSize, 0, 0));

        double width = frameSize.getWidth();
        double height = frameSize.getHeight();
        if (width == 0 || height == 0) {
            width = GUI.SCREEN_WIDTH;
            height = GUI.SCREEN_HEIGHT;
        }

        if (width > height) {
            width *= SPACER_RATIO;
            height *= SPACER_RATIO;
            spacerPanel.setVisible(true);
            spacerPanel.setMinimumSize(new Dimension((int) Math.round(width), (int) Math.round(height)));
            spacerPanel.setMaximumSize(new Dimension((int) Math.round(width), (int) Math.round(height)));
            spacerPanel.setPreferredSize(new Dimension((int) Math.round(width), (int) Math.round(height)));
        } else {
            spacerPanel.setVisible(false);
        }
    }

    /**
     * allows to rotate the current tileImage and his image.
     * @return ActionListener that allows to rotate the current tileImage and his image
     */
    private ActionListener rotateButtonEventListener() {
        return (e) -> {
            if (tileImage.isPresent()) {
                gameScene.removePlacedTile();
                userInterface.getController().rotateCurrentTile();
                tileImagePanel.repaint();
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a bigger JPanel which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
    public JPanel getComponent() {
        return this.mainPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCurrentTile() {
        final TileImage currentTileImage = gameScene.getCurrentTileImage();
        if (tileImage.isEmpty() || !tileImage.get().equals(currentTileImage)) {
            this.tileImage = Optional.of(currentTileImage);
        }
        tileImagePanel.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        updateCurrentTile();
        meepleComponent.update();
        leaderboard.update();
        playerImageComponent.setColor(userInterface.getController().getCurrentPlayer().get().getColor().asSwingColor());
        playerNameLabel.setText(userInterface.getController().getCurrentPlayer().get().getName());
        remainingTilesLabel.setText(
                LocaleHelper.getRemainingTilesText() + userInterface.getController().getNotPlacedTiles().size());
        if (tileImage.get().getTile().isPlaced()) {
            rotateButton.setEnabled(false);
        } else {
            rotateButton.setEnabled(true);
        }
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }
}
