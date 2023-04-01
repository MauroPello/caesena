package it.unibo.caesena.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PauseView extends JPanel implements View<JPanel> {
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255, 80);
    private static final int DEFAULT_SIZE = 20;
    private static final int PADDING_SIZE = 10;
    private final GUI userInterface;

    public PauseView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;

        this.setBackground(BACKGROUND_COLOR);
        final Font mainFont = new Font(Font.SANS_SERIF, Font.BOLD, DEFAULT_SIZE);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JLabel titleLbl = new JLabel(LocaleHelper.getViewTitle("PauseView", false));
        titleLbl.setFont(mainFont);
        titleLbl.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, 0, PADDING_SIZE, 0));
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(PADDING_SIZE, 0, PADDING_SIZE, 0);

        final JButton resumeGameButton = new JButton(LocaleHelper.getResumeGameText());
        resumeGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeGameButton.addActionListener((e) -> {
            userInterface.togglePauseView();
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(resumeGameButton, gridBagConstraints);

        final JButton backToStartMenuButton = new JButton(LocaleHelper.getBackToStartMenuText());
        backToStartMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToStartMenuButton.addActionListener((e) -> {
            userInterface.showBackToStartViewDialog();
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        buttonsPanel.add(backToStartMenuButton, gridBagConstraints);

        final JButton exitButton = new JButton(LocaleHelper.getExitApplicationText());
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener((e) -> {
            userInterface.showExitDialog();
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        buttonsPanel.add(exitButton, gridBagConstraints);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainPanel.setPreferredSize(new Dimension((int) Math.round(screenSize.getWidth() * GUI.MODAL_PREFERRED_RATIO * GUI.SMALL_MODAL_RATIO), (int) Math.round(screenSize.getHeight() * GUI.MODAL_PREFERRED_RATIO * GUI.SMALL_MODAL_RATIO)));
        mainPanel.setMinimumSize(new Dimension((int) Math.round(screenSize.getWidth() * GUI.MODAL_MINIMUM_RATIO * GUI.SMALL_MODAL_RATIO), (int) Math.round(screenSize.getHeight() * GUI.MODAL_MINIMUM_RATIO * GUI.SMALL_MODAL_RATIO)));
        mainPanel.setMaximumSize(new Dimension((int) Math.round(screenSize.getWidth() * GUI.MODAL_MAXIMUM_RATIO * GUI.SMALL_MODAL_RATIO), (int) Math.round(screenSize.getHeight() * GUI.MODAL_MAXIMUM_RATIO * GUI.SMALL_MODAL_RATIO)));

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
