package it.unibo.caesena.view.components.meeple;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.caesena.view.scene.GameScene;
import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.player.PlayerInGameImpl;

/**
 * {@inheritDoc}
 *
 * Implements the interface {@link it.unibo.caesena.view.components.meeple.RemainingMeeplesComponent} using a
 * {@link javax.swing.JPanel}.
 */
public class RemainingMeeplesComponentImpl implements RemainingMeeplesComponent<JPanel> {
    private final Controller controller;
    private final JPanel allMeeplesPanel;
    private final JPanel mainPanel;

    /**
     * RemainingMeeples constructor.
     *
     * @param gameScene the parent GameScene
     */
    public RemainingMeeplesComponentImpl(final GameScene gameScene) {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setOpaque(false);

        this.controller = gameScene.getUserInterface().getController();

        this.allMeeplesPanel = new JPanel();
        this.allMeeplesPanel.setOpaque(false);

        this.mainPanel.add(allMeeplesPanel);
        this.allMeeplesPanel.setVisible(true);
        this.mainPanel.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            update();
        }

        this.mainPanel.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.allMeeplesPanel.removeAll();
        this.allMeeplesPanel.revalidate();
        this.allMeeplesPanel.repaint();

        final PlayerInGameImpl currentPlayer = controller.getCurrentPlayer().get();
        final List<MeepleImage> meeples = controller.getPlayerMeeples(currentPlayer).stream()
            .map(m -> new MeepleImage(m, currentPlayer.getColor().asSwingColor())).toList();
        this.allMeeplesPanel.setLayout(new GridLayout(1, meeples.size()));
        for (final MeepleImage meeple : meeples) {
            final JPanel meeplePanel = new JPanel() {
                @Override
                protected void paintComponent(final Graphics graphics) {
                    super.paintComponent(graphics);
                    if (this.getHeight() > this.getWidth()) {
                        graphics.drawImage(meeple.getImage(), 0, 0, this.getWidth(), this.getWidth(), null);
                    } else {
                        graphics.drawImage(meeple.getImage(), 0, 0, this.getHeight(), this.getHeight(), null);
                    }
                }
            };
            meeplePanel.setOpaque(false);
            this.allMeeplesPanel.add(meeplePanel);
        }
        this.allMeeplesPanel.revalidate();
        this.allMeeplesPanel.repaint();
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
}
