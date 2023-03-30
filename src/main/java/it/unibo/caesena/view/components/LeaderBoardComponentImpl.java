package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.view.GUI;

public class LeaderBoardComponentImpl extends JPanel implements LeaderBoardComponent<JPanel> {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    List<JLabel> labelList = new ArrayList<>();
    List<Player> playerList = new ArrayList<>();
    private final GUI userInterface;

    public LeaderBoardComponentImpl(final GUI userInterface) {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.userInterface = userInterface;
        this.playerList = this.userInterface.getController().getPlayers();

        final JLabel titleLabel = new JLabel("Leaderboard:");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        this.add(titleLabel);
        final JSeparator separator = new JSeparator();
        separator.setForeground(Color.black);
        this.add(separator);

        for (final var player : playerList) {
            final var playerPanel = new JPanel();

            final var playerColorPanel = new PlayerImageImpl(WIDTH, HEIGHT);
            playerColorPanel.setColor(userInterface.getPlayerColor(player));

            final JLabel playerLabel = new JLabel();
            playerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelList.add(playerLabel);

            playerPanel.add(playerColorPanel);
            playerPanel.add(playerLabel);
            this.add(playerPanel);
        }
        updateLeaderBoard();
        this.setVisible(true);
    }

    @Override
    public void updateLeaderBoard() {
        this.playerList = new ArrayList<>(this.userInterface.getController().getPlayers());
        playerList.sort((x, y) -> Integer.compare(y.getScore(), x.getScore()));

        for (int i = 0; i < playerList.size(); i++) {
            labelList.get(i).setText(playerList.get(i).getName() + " " + playerList.get(i).getScore());
        }
    }

    @Override
    public JPanel getComponent() {
        return this;
    }
}
