package it.unibo.caesena.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.components.PlayerImageImpl;

public class GameOverView extends JPanel implements View<JPanel> {
    private static final long serialVersionUID = 1981212936830265900L;
    private static final float PLAYER_IMAGE_RATIO = 0.02f;
    private final GUI userInterface;
    private final int playerImageSize;
    private final JPanel playersPanels;
    private final BufferedImage backgroundImage;

    public GameOverView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;
        this.backgroundImage = ResourceUtil.getBufferedImage("background_GameOverView.jpeg", List.of());
        final JPanel mainPanel = new JPanel();

        mainPanel.setPreferredSize(new Dimension(
                (int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_PREFERRED_RATIO),
                (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_PREFERRED_RATIO)));
        mainPanel.setMinimumSize(new Dimension(
                (int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MINIMUM_RATIO),
                (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MINIMUM_RATIO)));
        mainPanel.setMaximumSize(new Dimension(
                (int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MAXIMUM_RATIO),
                (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MAXIMUM_RATIO)));
        if (GUI.SCREEN_HEIGHT > GUI.SCREEN_WIDTH) {
            playerImageSize = (int) Math.round(GUI.SCREEN_WIDTH * PLAYER_IMAGE_RATIO);
        } else {
            playerImageSize = (int) Math.round(GUI.SCREEN_HEIGHT * PLAYER_IMAGE_RATIO);
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
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        double ratioWidht = (double)this.getWidth() / (double)backgroundImage.getWidth();
        double ratioHeight = (double)this.getHeight() / (double)backgroundImage.getHeight();
        double width = backgroundImage.getWidth() * (ratioHeight > ratioWidht ? ratioHeight : ratioWidht);
        double height = backgroundImage.getHeight() * (ratioHeight > ratioWidht ? ratioHeight : ratioWidht);
        graphics.drawImage(backgroundImage, 0, 0,
                (int) Math.round(width),
                (int) Math.round(height), null);
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
            final var color = player.getColor();
            playerColorPanel.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
            playerColorPanel.forceSize(playerImageSize);
            volatailePanel.add(playerColorPanel);

            this.playersPanels.add(volatailePanel);
        }
    }
}
