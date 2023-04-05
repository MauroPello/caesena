package it.unibo.caesena.view.components.player;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;

/**
 * {@inheritDoc}
 *
 * Implements the interface {@link it.unibo.caesena.view.components.player.LeaderBoardComponent} using a
 * {@link java.swing.JPanel}.
 */
public final class LeaderBoardComponentImpl extends JPanel implements LeaderBoardComponent<JPanel> {
    private static final long serialVersionUID = 4401972577759091952L;
    private static final float PLAYER_IMAGE_RATIO = 0.01f;
    private final GUI userInterface;
    private final JPanel playersPanel;
    private final int playerImageSize;

    /**
     * Class constructor.
     *
     * @param userInterface the parent GUI
     */
    public LeaderBoardComponentImpl(final GUI userInterface) {
        super();

        this.userInterface = userInterface;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (GUI.SCREEN_HEIGHT > GUI.SCREEN_WIDTH) {
            playerImageSize = (int) Math.round(GUI.SCREEN_WIDTH * PLAYER_IMAGE_RATIO);
        } else {
            playerImageSize = (int) Math.round(GUI.SCREEN_HEIGHT * PLAYER_IMAGE_RATIO);
        }

        final JLabel titleLabel = new JLabel(LocaleHelper.getLeaderboardName());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(GUI.BIG_BOLD_FONT);

        this.add(titleLabel);
        final JSeparator separator = new JSeparator();
        separator.setForeground(Color.black);
        this.add(separator);

        this.playersPanel = new JPanel();
        this.playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        final JScrollPane playersScrollPane = new JScrollPane(playersPanel);
        playersScrollPane.setAutoscrolls(true);
        playersScrollPane.setBorder(null);
        this.add(playersScrollPane);
        super.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            update();
        }

        super.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        playersPanel.removeAll();
        playersPanel.revalidate();
        playersPanel.repaint();

        final List<Player> players = userInterface.getController().getPlayers().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore())).toList();

        for (final var player : players) {
            final var playerPanel = new JPanel();

            final var playerColorPanel = new PlayerImageImpl();
            playerColorPanel.setColor(player.getColor().asSwingColor());
            playerColorPanel.forceSize(playerImageSize);

            final JLabel playerLabel = new JLabel(player.getName() + " " + player.getScore());
            playerLabel.setFont(GUI.MEDIUM_NORMAL_FONT);
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            playerPanel.add(playerColorPanel);
            playerPanel.add(playerLabel);
            playersPanel.add(playerPanel);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getComponent() {
        return this;
    }
}
