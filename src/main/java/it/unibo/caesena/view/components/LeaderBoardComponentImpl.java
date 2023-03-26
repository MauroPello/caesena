package it.unibo.caesena.view.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Player;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class LeaderBoardComponentImpl extends JPanel implements LeaderBoardComponent<JPanel>{

    List<JLabel> labelList = new ArrayList<>();
    List<Player> playerList = new ArrayList<>();
    Controller controller;

    public LeaderBoardComponentImpl(Controller controller)
    {
        super();
        JPanel innerPanel = new JPanel();

        this.setBackground(java.awt.Color.BLACK);
        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridy = 1;
        constraints.gridx = 0;

        this.controller = controller;
        this.playerList = this.controller.getPlayers();
        for (int i = 0; i < playerList.size(); i++) {
            JLabel nuova = new JLabel();
            nuova.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            labelList.add(nuova);
            innerPanel.add(nuova, constraints);
            constraints.gridy++;
        }
        updateLeaderBoard();
        this.add(innerPanel);
        innerPanel.setVisible(true);
        this.setVisible(true);
    }

    @Override
    public void updateLeaderBoard()
    {
        //testLeaderBoard();

        this.playerList = this.controller.getPlayers();
        playerList = playerList.stream().sorted((x, y) -> x.getScore() - y.getScore()).toList();

        for (int i = 0; i < playerList.size(); i++) {
            labelList.get(i).setText(playerList.get(i).getScore()+" "+playerList.get(i).getName());
        }
    }

    //metodo creato unicamente per simulare dei punteggi
    public void testLeaderBoard()
    {
        for (Player player : controller.getPlayers()) {
            player.setScore(new Random().nextInt(100000));
        }
    }

    @Override
    public JPanel getComponent() {
        return this;
    }
}
