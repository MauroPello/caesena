package it.unibo.caesena.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.view.components.PlayerImageImpl;

public class GameOverView extends JPanel implements View<JPanel> {

    private static final int DEFAULT_SIZE = 20;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private final GUI userInterface;
    private final JPanel mainPanel;
    private final List<Player> players;
    private Font mainFont;

    public GameOverView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;

        this.setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout());
        this.mainFont = new Font(Font.SANS_SERIF, Font.BOLD, DEFAULT_SIZE);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.players = userInterface.getController().getPlayers();

        final JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        final JLabel playersLabel = new JLabel(LocaleHelper.getViewTitle("GameOverView", false));
        playersLabel.setFont(mainFont);
        playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playersPanel.add(playersLabel);

        final PriorityQueue<Player> queue = new PriorityQueue<>(
                new Comparator<Player>() {
                    public int compare(final Player a, final Player b) {
                        return Integer.compare(b.getScore(), a.getScore());
                    }
                });

        for (final Player player : players) {
            queue.add(player);
        }

        for (final var player : queue) {
            final JPanel volatailePanel = new JPanel();

            final JLabel volataileLabel = new JLabel();
            volataileLabel.setText("Nome: " + player.getName() + " Score: " + player.getScore());
            volatailePanel.add(volataileLabel);
            
            final var playerColorPanel = new PlayerImageImpl(WIDTH, HEIGHT);
            playerColorPanel.setColor(userInterface.getPlayerColor(player));
            volatailePanel.add(playerColorPanel);
            
            playersPanel.add(volatailePanel);
        }
        this.mainPanel.add(playersPanel);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        final JButton backToStartMenuButton = new JButton(LocaleHelper.getBackToStartMenuText());
        backToStartMenuButton.addActionListener(e -> userInterface.showBackToStartViewDialog());
        backToStartMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(backToStartMenuButton);

        final JButton exitButton = new JButton(LocaleHelper.getExitApplicationText());
        exitButton.addActionListener(e -> userInterface.showExitDialog());
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(exitButton);

        this.mainPanel.add(buttonPanel);
        this.add(mainPanel);
        this.repaint();
        this.validate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final JPanel getComponent() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public final GUI getUserInterface() {
        return this.userInterface;
    }
}
