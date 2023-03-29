package it.unibo.caesena.view.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
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
        JPanel innerPanel = new JPanel();

        this.setBackground(java.awt.Color.BLACK);
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        this.controller = controller;
        this.playerList = this.controller.getPlayers();

        JLabel titleLabel = new JLabel("Leaderboard:");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        innerPanel.add(titleLabel);
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.black);
        innerPanel.add(separator);

        innerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        for (int i = 0; i < playerList.size(); i++) {
            JLabel nuova = new JLabel();
            nuova.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            nuova.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelList.add(nuova);
            innerPanel.add(nuova);
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

        this.playerList = new ArrayList<>(this.controller.getPlayers());
        playerList.sort((x, y) -> Integer.compare(y.getScore(), x.getScore()));

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
