package it.unibo.caesena.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PauseView extends JPanel implements View<JPanel> {

    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255, 80);
    private final GUI userInterface;
    private static final int DEFAULT_SIZE = 20;

    public PauseView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;

        this.setLayout(new GridBagLayout());
        this.setBackground(BACKGROUND_COLOR);

        final Font mainFont = new Font(Font.SANS_SERIF, Font.BOLD, DEFAULT_SIZE);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JLabel titleLbl = new JLabel(LocaleHelper.getViewTitle("PauseView", false));
        titleLbl.setFont(mainFont);
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLbl);

        final JButton resumeGameButton = new JButton(LocaleHelper.getResumeGameText());
        resumeGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeGameButton.addActionListener((e) -> {
            userInterface.togglePauseView();
        });
        mainPanel.add(resumeGameButton);

        final JButton backToStartMenuButton = new JButton(LocaleHelper.getBackToStartMenuText());
        backToStartMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToStartMenuButton.addActionListener((e) -> {
            userInterface.showBackToStartViewDialog();
        });
        mainPanel.add(backToStartMenuButton);

        final JButton exitButton = new JButton(LocaleHelper.getExitApplicationText());
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener((e) -> {
            userInterface.showExitDialog();
        });
        mainPanel.add(exitButton);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainPanel.setPreferredSize(new Dimension((int) Math.round(screenSize.getWidth() * GUI.MODAL_PREFERRED_RATIO / 2), (int) Math.round(screenSize.getHeight() * GUI.MODAL_PREFERRED_RATIO / 2)));
        mainPanel.setMinimumSize(new Dimension((int) Math.round(screenSize.getWidth() * GUI.MODAL_MINIMUM_RATIO / 2), (int) Math.round(screenSize.getHeight() * GUI.MODAL_MINIMUM_RATIO / 2)));
        mainPanel.setMaximumSize(new Dimension((int) Math.round(screenSize.getWidth() * GUI.MODAL_MAXIMUM_RATIO / 2), (int) Math.round(screenSize.getHeight() * GUI.MODAL_MAXIMUM_RATIO / 2)));

        this.add(mainPanel);
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
