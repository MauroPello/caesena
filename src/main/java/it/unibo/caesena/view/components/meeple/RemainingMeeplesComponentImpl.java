package it.unibo.caesena.view.components.meeple;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import it.unibo.caesena.view.scene.GameScene;
import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Player;

/**
 * {@inheritDoc}
 *
 * Implements the interface {@link it.unibo.caesena.view.components.meeple.RemainingMeeplesComponent} using a
 * {@link java.swing.JPanel}.
 */
public class RemainingMeeplesComponentImpl implements RemainingMeeplesComponent<JPanel> {
    private final Map<Player, List<MeepleImage>> meeples;
    private final Controller controller;
    private final JPanel allMeeplesPanel;
    private final JPanel mainPanel;

    /**
     * RemainingMeeples constructor.
     *
     * @param gameScene
     */
    public RemainingMeeplesComponentImpl(final GameScene gameScene) {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setOpaque(false);

        this.controller = gameScene.getUserInterface().getController();
        this.meeples = new HashMap<>();

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
    public void setVisible(final boolean visible) {
        if (visible) {
            for (final var player : controller.getPlayers()) {
                meeples.put(player, controller.getPlayerMeeples(player).stream()
                    .map(m -> new MeepleImage(m, player.getColor().asSwingColor())).toList());
            }
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


        final Player currentPlayer = controller.getCurrentPlayer().get();
        this.allMeeplesPanel.setLayout(new GridLayout(1, meeples.get(currentPlayer).size()));
        for (final MeepleImage meeple : meeples.get(currentPlayer)) {
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }
}
