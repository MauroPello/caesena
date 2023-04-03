package it.unibo.caesena.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.caesena.utils.ResourceUtil;

public class PauseView extends JPanel implements View<JPanel> {
    private static final long serialVersionUID = 3027253762177464276L;
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255, 80);
    private final GUI userInterface;

    public PauseView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;

        this.setBackground(BACKGROUND_COLOR);

        final JPanel mainPanel = new JPanel() {
            private final BufferedImage image = ResourceUtil.getBufferedImage("background_Modal.png", List.of());

            @Override
            public Dimension getMaximumSize() {
                return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MAXIMUM_RATIO),
                (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MAXIMUM_RATIO));
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MINIMUM_RATIO),
                (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MINIMUM_RATIO));
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_PREFERRED_RATIO),
                (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_PREFERRED_RATIO));
            }

            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH), 0, 0, this.getWidth(), this.getHeight(), null);
            }        
        };
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JLabel titleLbl = new JLabel(LocaleHelper.getViewTitle("PauseView", false));
        titleLbl.setFont(GUI.BIG_BOLD_FONT);
        titleLbl.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING * 10, 0, GUI.DEFAULT_PADDING, 0));
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(GUI.DEFAULT_PADDING, 0, GUI.DEFAULT_PADDING, 0);

        final JButton resumeGameButton = new JButton(LocaleHelper.getResumeGameText());
        resumeGameButton.setFont(GUI.MEDIUM_BOLD_FONT);
        resumeGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeGameButton.addActionListener((e) -> {
            userInterface.togglePauseView();
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(resumeGameButton, gridBagConstraints);

        final JButton backToStartMenuButton = new JButton(LocaleHelper.getBackToStartMenuText());
        backToStartMenuButton.setFont(GUI.MEDIUM_BOLD_FONT);
        backToStartMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToStartMenuButton.addActionListener((e) -> {
            userInterface.showBackToStartViewDialog();
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        buttonsPanel.add(backToStartMenuButton, gridBagConstraints);

        final JButton exitButton = new JButton(LocaleHelper.getExitApplicationText());
        exitButton.setFont(GUI.MEDIUM_BOLD_FONT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener((e) -> {
            userInterface.showExitDialog();
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        buttonsPanel.add(exitButton, gridBagConstraints);

        mainPanel.setPreferredSize(new Dimension(
            (int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_PREFERRED_RATIO * GUI.SMALL_MODAL_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_PREFERRED_RATIO * GUI.SMALL_MODAL_RATIO)));
        mainPanel.setMinimumSize(new Dimension(
            (int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MINIMUM_RATIO * GUI.SMALL_MODAL_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MINIMUM_RATIO * GUI.SMALL_MODAL_RATIO)));
        mainPanel.setMaximumSize(new Dimension(
            (int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MAXIMUM_RATIO * GUI.SMALL_MODAL_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MAXIMUM_RATIO * GUI.SMALL_MODAL_RATIO)));

        mainPanel.add(titleLbl);
        mainPanel.add(buttonsPanel);
        this.setLayout(new GridBagLayout());
        this.add(mainPanel);
        this.setVisible(false);
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
    }
}
