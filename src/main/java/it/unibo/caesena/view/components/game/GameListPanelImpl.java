package it.unibo.caesena.view.components.game;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unibo.caesena.model.Game;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.server.Server;
import it.unibo.caesena.view.UserInterface;

public class GameListPanelImpl implements GameListPanel<JPanel> {

    private final JPanel mainPanel;
    private final JPanel contentPanel;
    private final UserInterface ui;

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

        Player player = new Player();
        List<Game> games = this.ui.getController().getOpenGames(player);
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
    }

    private Object getGameList() {
        return null;
    }

}
