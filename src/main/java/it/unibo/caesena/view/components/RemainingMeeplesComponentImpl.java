package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.GameView;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Player;

public class RemainingMeeplesComponentImpl extends JPanel implements RemainingMeeplesComponent<JPanel> {

    private final GUI userInterface;
    private final Controller controller;
    private final JLabel meepleLabel;
    private final JPanel allMeeplesPanel;
    private final Map<Player, List<MeepleImage>> meeples;

    public RemainingMeeplesComponentImpl(final GameView gameView) {
        super();

        this.userInterface = gameView.getUserInterface();
        this.controller = userInterface.getController();

        this.meeples = new HashMap<>();
        for (final var player : controller.getPlayers()) {
            final Color color = userInterface.getPlayerColor(player);
            meeples.put(player, controller.getPlayerMeeples(player).stream()
                .map(m -> new MeepleImage(m, color)).toList());
        }

        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        meepleLabel = new JLabel();
        meepleLabel.setFont(GUI.MEDIUM_BOLD_FONT);
        meepleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(meepleLabel);

        this.allMeeplesPanel = new JPanel();
        this.allMeeplesPanel.setOpaque(false);

        this.update();

        this.add(allMeeplesPanel);
        this.allMeeplesPanel.setVisible(true);
        this.setVisible(true);
    }

    @Override
    public void update() {
        this.allMeeplesPanel.removeAll();
        this.allMeeplesPanel.revalidate();
        this.allMeeplesPanel.repaint();

        final Player currentPlayer = controller.getCurrentPlayer();
        this.allMeeplesPanel.setLayout(new GridLayout(1, meeples.get(currentPlayer).size()));
        this.meepleLabel.setText(LocaleHelper.getRemainingMeeplesText() + meeples.get(currentPlayer).stream().filter(m -> !m.getMeeple().isPlaced()).count());
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

    @Override
    public JPanel getComponent() {
        return this;
    }
}
