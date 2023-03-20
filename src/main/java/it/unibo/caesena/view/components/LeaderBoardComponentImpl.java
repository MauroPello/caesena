package it.unibo.caesena.view.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Player;

public class LeaderBoardComponentImpl extends JPanel implements LeaderBoardComponent<JPanel>{

    List<JLabel> labelList = new ArrayList<>();
    List<Player> playerList = new ArrayList<>();
    Controller controller;

    public LeaderBoardComponentImpl(Controller controller)
    {
        this.controller = controller;
        this.playerList = this.controller.getPlayers();
        for (Player player : playerList) {
            labelList.add(new JLabel());
        }
        updateLeaderBoard();
    }

    public void updateLeaderBoard()
    {
        playerList = playerList.stream().sorted((x, y) -> x.getScore() - y.getScore()).toList();

        for (int i = 0; i < playerList.size(); i++) {
            labelList.get(i).setText(playerList.get(i).getScore()+"");
        }
    }
}
