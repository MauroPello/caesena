package it.unibo.caesena.view;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PauseView extends JPanel implements View<JPanel> {

    private final GUI userInterface;

    public PauseView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;
        
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(255, 255, 255, 80));
        
        Font mainFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLbl = new JLabel(LocaleHelper.getViewTitle("PauseView", false));
        titleLbl.setFont(mainFont);
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLbl);

        JButton resumeGameButton = new JButton(LocaleHelper.getResumeGameText());
        resumeGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeGameButton.addActionListener((e) -> {
            userInterface.togglePauseView();            
        });
        mainPanel.add(resumeGameButton);

        JButton backToStartMenuButton = new JButton(LocaleHelper.getBackToStartMenuText());
        backToStartMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToStartMenuButton.addActionListener((e) -> {
            userInterface.showBackToStartViewDialog();
        });
        mainPanel.add(backToStartMenuButton);

        JButton exitButton = new JButton(LocaleHelper.getExitApplicationText());
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener((e) -> {
            userInterface.showExitDialog();
        });
        mainPanel.add(exitButton);

        this.add(mainPanel);
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public GUI getUserInterface() {
        return this.userInterface;
    }
}
