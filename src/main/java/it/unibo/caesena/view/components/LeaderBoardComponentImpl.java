package it.unibo.caesena.view.components;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Player;


public class LeaderBoardComponentImpl extends JPanel implements LeaderBoardComponent<JPanel>{

    List<JLabel> labelList = new ArrayList<>();
    List<Player> playerList = new ArrayList<>();
    Controller controller;

    public LeaderBoardComponentImpl(Controller controller)
    {
        super();

        this.setBackground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.controller = controller;
        this.playerList = this.controller.getPlayers();

        JLabel titleLabel = new JLabel("Leaderboard:");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        this.add(titleLabel);
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.black);
        this.add(separator);

        for (var player : playerList) {
            JLabel playerLabel = new JLabel();
            playerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelList.add(playerLabel);
            this.add(playerLabel);
        }
        updateLeaderBoard();
        this.setVisible(true);
    }

    @Override
    public void updateLeaderBoard()
    {
        this.playerList = new ArrayList<>(this.controller.getPlayers());
        playerList.sort((x, y) -> Integer.compare(y.getScore(), x.getScore()));

        for (int i = 0; i < playerList.size(); i++) {
            labelList.get(i).setText(playerList.get(i).getName()+" "+playerList.get(i).getScore());
        }
    }

    @Override
    public JPanel getComponent() {
        return this;
    }
}
