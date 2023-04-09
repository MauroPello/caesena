package it.unibo.caesena.view.components.player;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.UserInterface;

/**
 * {@inheritDoc}
 *
 * Implements the interface {@link it.unibo.caesena.view.components.player.LeaderBoardComponent} using a
 * {@link javax.swing.JPanel}.
 */
public final class LeaderBoardComponentImpl implements LeaderBoardComponent<JPanel> {
    private static final float PLAYER_IMAGE_RATIO = 0.01f;
    private final UserInterface userInterface;
    private final JPanel playersPanel;
    private final JPanel mainPanel;
    private final int playerImageSize;

    /**
     * Class constructor.
     *
     * @param userInterface the parent GUI
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "This component will always need access to the UserInterface "
        + "he's placed in as it retrieves information from it, like player scores")
    public LeaderBoardComponentImpl(final UserInterface userInterface) {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

        this.userInterface = userInterface;

        if (GUI.SCREEN_HEIGHT > GUI.SCREEN_WIDTH) {
            playerImageSize = (int) Math.round(GUI.SCREEN_WIDTH * PLAYER_IMAGE_RATIO);
        } else {
            playerImageSize = (int) Math.round(GUI.SCREEN_HEIGHT * PLAYER_IMAGE_RATIO);
        }

        final JLabel titleLabel = new JLabel(LocaleHelper.getLeaderboardName());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(GUI.MEDIUM_BOLD_FONT);

        this.mainPanel.add(titleLabel);
        final JSeparator separator = new JSeparator();
        separator.setForeground(Color.black);
        this.mainPanel.add(separator);

        this.playersPanel = new JPanel();
        this.playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        final JScrollPane playersScrollPane = new JScrollPane(playersPanel);
        playersScrollPane.setAutoscrolls(true);
        playersScrollPane.setBorder(null);
        this.mainPanel.add(playersScrollPane);
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
        playersPanel.removeAll();
        playersPanel.revalidate();
        playersPanel.repaint();

        final List<Player> players = userInterface.getController().getPlayers().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore())).toList();

        for (final var player : players) {
            final var playerPanel = new JPanel();

            final PlayerImage<JPanel> playerColorPanel = new PlayerImageImpl();
            playerColorPanel.setColor(player.getColor().asSwingColor());
            playerColorPanel.forceSize(playerImageSize);

            final JLabel playerLabel = new JLabel(player.getName() + " " + player.getScore());
            playerLabel.setFont(GUI.SMALL_NORMAL_FONT);
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            playerPanel.add(playerColorPanel.getComponent());
            playerPanel.add(playerLabel);
            playersPanel.add(playerPanel);
        }
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
