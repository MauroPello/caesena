package it.unibo.caesena.view.components.game;

import it.unibo.caesena.model.Game;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.server.Server;
import it.unibo.caesena.view.BasicComponent;
import it.unibo.caesena.view.UserInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class GameList implements BasicComponent<JPanel> {

    private final Player player;
    private final UserInterface ui;
    private final JPanel mainPanel;
    private final Map<JButton, Game> gameButtonMap;

    public GameList(Player player, UserInterface ui) {
        this.player = player;
        this.ui = ui;
        this.gameButtonMap = new HashMap<>();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout(0, 5));

        var games = this.ui.getController().getOpenGames(this.player);

        String[] header = { "Game ID", "Players", "Remaining Tiles", "Server", "" };

        this.mainPanel.add(new JLabel(header[0]));
        this.mainPanel.add(new JLabel(header[1]));
        this.mainPanel.add(new JLabel(header[2]));
        this.mainPanel.add(new JLabel(header[3]));
        this.mainPanel.add(new JLabel(header[4]));

        for (Game game : games) {

            JLabel playersLabel = new JLabel();
            List<PlayerInGameImpl> playersInGame = game.getPlayersInGame();
            String playersLabelText = playersInGame.stream()
                    .map(p -> p.getPlayer().getName())
                    .collect(Collectors.joining(", "));
            playersLabel.setText(playersLabelText);
            JLabel serverLabel = new JLabel();
            Server server = game.getServer();
            serverLabel.setText(server.toString());
            JLabel gameIDLabel = new JLabel(String.valueOf(game.getGameID()));
            var tiles = this.ui.getController().getTilesFromGame(game);
            String remainingTiles = tiles.stream().filter(t -> t.isPlaced()).count() + "/" + tiles.size();
            JLabel remainingTilesLabel = new JLabel(remainingTiles);
            JButton joinButton = new JButton("join");
            joinButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    JButton selectedButton = (JButton) arg0.getSource();
                    ui.getController().joinGame(gameButtonMap.get(selectedButton).getGameID());
                }

            });
            gameButtonMap.put(joinButton, game);

            this.mainPanel.add(gameIDLabel);
            this.mainPanel.add(playersLabel);
            this.mainPanel.add(remainingTilesLabel);
            this.mainPanel.add(serverLabel);
            this.mainPanel.add(joinButton);
        }
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

}
