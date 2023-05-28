package it.unibo.caesena.view.components.game;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.UserInterface;

public class GameListPanelImpl implements GameListPanel<JPanel> {

    private final JPanel mainPanel;
    private final JPanel contentPanel;
    private final JPanel innerContentPanel;
    private final UserInterface ui;
    private Player selectedPlayer;

    public GameListPanelImpl(final UserInterface userInterface) {
        this.ui = userInterface;
        this.mainPanel = new JPanel();
        this.mainPanel.setOpaque(false);
        this.innerContentPanel = new JPanel();
        this.innerContentPanel.setOpaque(false);

        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS));
        this.contentPanel.setOpaque(false);

        final JComboBox<String> playerChooser = new JComboBox<>();
        final List<Player> players = userInterface.getController().getAllPlayers();
        if (!players.isEmpty()) {
            players.forEach(s -> playerChooser.addItem(s.getName()));
            playerChooser.setSelectedIndex(0);
            playerChooser.setFont(GUI.MEDIUM_BOLD_FONT);
            this.contentPanel.add(playerChooser);
        }

        playerChooser.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                selectedPlayer = ui.getController().getPlayerByID((String) arg0.getItem());
                update();
            }

        });
        this.contentPanel.add(this.innerContentPanel);
        this.mainPanel.add(contentPanel);
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
        if (selectedPlayer != null) {
            // var games = this.ui.getController().getOpenGames(selectedPlayer);

            // for (int i = 0; i < gameList.getRowCount(); i++) {
            // gameList.removeRow(i);
            // }

            // for (Game game : games) {
            // JPanel gameInfo = new JPanel();
            // JLabel playersLabel = new JLabel();
            // List<PlayerInGameImpl> playersInGame = game.getPlayersInGame();
            // String playersLabelText = playersInGame.stream()
            // .map(p -> p.getPlayer().getName())
            // .collect(Collectors.joining(", "));
            // playersLabel.setText(playersLabelText);
            // JLabel serverLabel = new JLabel();
            // Server server = game.getServer();
            // serverLabel.setText(server.toString());

            // gameInfo.add(playersLabel);
            // gameInfo.add(serverLabel);
            // Object[] data = { String.valueOf(game.getGameID()), playersLabelText, "Non
            // l'ho fatto ancora",
            // server.toString() };
            // gameList.addRow(data);
            this.innerContentPanel.removeAll();
            this.innerContentPanel.add((new GameList(selectedPlayer, ui)).getComponent());
            this.mainPanel.repaint();
            this.mainPanel.revalidate();
        }
    }

}
