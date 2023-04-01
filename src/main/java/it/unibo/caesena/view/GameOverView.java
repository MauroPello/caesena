package it.unibo.caesena.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.view.components.PlayerImageImpl;

public class GameOverView extends JPanel implements View<JPanel> {
    private static final long serialVersionUID = 1981212936830265900L;
    private static final float PLAYER_IMAGE_RATIO = 0.02f;
    private final GUI userInterface;
    private final int playerImageSize;
    private final JPanel playersPanels;

    public GameOverView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;
        final JPanel mainPanel = new JPanel();

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainPanel.setPreferredSize(new Dimension(
            (int) Math.round(screenSize.getWidth() * GUI.MODAL_PREFERRED_RATIO),
            (int) Math.round(screenSize.getHeight() * GUI.MODAL_PREFERRED_RATIO)));
        mainPanel.setMinimumSize(new Dimension(
            (int) Math.round(screenSize.getWidth() * GUI.MODAL_MINIMUM_RATIO),
            (int) Math.round(screenSize.getHeight() * GUI.MODAL_MINIMUM_RATIO)));
        mainPanel.setMaximumSize(new Dimension(
            (int) Math.round(screenSize.getWidth() * GUI.MODAL_MAXIMUM_RATIO),
            (int) Math.round(screenSize.getHeight() * GUI.MODAL_MAXIMUM_RATIO)));
        if (screenSize.getHeight() > screenSize.getWidth()) {
            playerImageSize = (int) Math.round(screenSize.getWidth() * PLAYER_IMAGE_RATIO);
        } else {
            playerImageSize = (int) Math.round(screenSize.getHeight() * PLAYER_IMAGE_RATIO);
        }

        this.setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JLabel playersLabel = new JLabel(LocaleHelper.getViewTitle("GameOverView", false));
        playersLabel.setFont(GUI.BIG_BOLD_FONT);
        playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(playersLabel);

        this.playersPanels = new JPanel();
        this.playersPanels.setLayout(new BoxLayout(this.playersPanels, BoxLayout.Y_AXIS));
        this.playersPanels.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(this.playersPanels);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        final JButton backToStartMenuButton = new JButton(LocaleHelper.getBackToStartMenuText());
        backToStartMenuButton.setFont(GUI.MEDIUM_BOLD_FONT);
        backToStartMenuButton.addActionListener(e -> userInterface.showBackToStartViewDialog());
        backToStartMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(backToStartMenuButton);

        final JButton exitButton = new JButton(LocaleHelper.getExitApplicationText());
        exitButton.setFont(GUI.MEDIUM_BOLD_FONT);
        exitButton.addActionListener(e -> userInterface.showExitDialog());
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel);

        this.add(mainPanel);
        super.setVisible(false);
    }

    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            update();
        }

        super.setVisible(visible);
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

    @Override
    public void update() {
        this.playersPanels.removeAll();

        final List<Player> players = new ArrayList<>(userInterface.getController().getPlayers());
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        for (final var player : players) {
            final JPanel volatailePanel = new JPanel();

            final JLabel volataileLabel = new JLabel();
            volataileLabel.setFont(GUI.MEDIUM_NORMAL_FONT);
            volataileLabel.setText(LocaleHelper.getNameText() + player.getName() + " " + LocaleHelper.getScoreText()
                    + player.getScore());
            volatailePanel.add(volataileLabel);

            final var playerColorPanel = new PlayerImageImpl();
            playerColorPanel.setColor(userInterface.getPlayerColor(player));
            playerColorPanel.forceSize(playerImageSize);
            volatailePanel.add(playerColorPanel);

            this.playersPanels.add(volatailePanel);
        }
    }
}
