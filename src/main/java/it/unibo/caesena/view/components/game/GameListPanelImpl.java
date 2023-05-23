package it.unibo.caesena.view.components.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unibo.caesena.model.Game;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.server.Server;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.UserInterface;

public class GameListPanelImpl implements GameListPanel<JPanel> {

    private final JPanel mainPanel;
    private final JPanel contentPanel;
    private final UserInterface ui;
    private Player selectedPlayer;

    public GameListPanelImpl(final UserInterface userInterface) {
        this.mainPanel = new JPanel();
        this.mainPanel.setOpaque(false);
        this.ui = userInterface;

        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS));
        this.contentPanel.setOpaque(false);
        final JScrollPane contentScrollPane = new JScrollPane(contentPanel);
        contentScrollPane.setOpaque(false);
        contentScrollPane.getViewport().setOpaque(false);
        contentScrollPane.setAutoscrolls(true);
        contentScrollPane.setBorder(null);
        this.mainPanel.add(contentScrollPane);

        final JComboBox<String> playerChooser = new JComboBox<>();
        final List<Player> players = userInterface.getController().getAllPlayers();
        players.forEach(s -> playerChooser.addItem(s.getName()));
        playerChooser.setSelectedIndex(0);
        playerChooser.setFont(GUI.MEDIUM_BOLD_FONT);
        contentPanel.add(playerChooser);
        playerChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                selectedPlayer=ui.getController().getPlayerByID((String) arg0.getSource());
                update();
            }

        });
        update();
        this.mainPanel.setVisible(false);
    }

    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        if (!isVisible()) {
            update();
        }

        this.mainPanel.setVisible(visible);
    }

    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

    @Override
    public void update() {
        var games = this.ui.getController().getOpenGames(selectedPlayer);
        for (Game game : games) {
            JPanel gameInfo = new JPanel();
            JLabel playersLabel = new JLabel();
            List<PlayerInGameImpl> playersInGame = game.getPlayersInGame();
            String playersLabelText = playersInGame.stream()
                .map(p -> p.getPlayer().getName())
                .collect(Collectors.joining(", "));
            playersLabel.setText(playersLabelText);
            JLabel serverLabel = new JLabel();
            Server server = game.getServer();
            serverLabel.setText(server.toString());

            gameInfo.add(playersLabel);
            gameInfo.add(serverLabel);
            this.contentPanel.add(gameInfo);
        }
    }

    private Object getGameList() {
        return null;
    }

}
