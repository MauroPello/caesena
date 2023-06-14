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
        players.forEach(s -> playerChooser.addItem(s.getName()));
        playerChooser.setFont(GUI.MEDIUM_BOLD_FONT);
        playerChooser.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                selectedPlayer = ui.getController().getPlayerByID((String) arg0.getItem());
                update();
            }

        });
        if (!players.isEmpty()) {
            selectedPlayer = players.get(0);
            playerChooser.setSelectedItem(selectedPlayer.getName());
            update();
        }
        this.contentPanel.add(playerChooser);
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
            this.innerContentPanel.removeAll();
            this.innerContentPanel.add((new GameList(selectedPlayer, ui)).getComponent());
            this.mainPanel.repaint();
            this.mainPanel.revalidate();
        }
    }

}
