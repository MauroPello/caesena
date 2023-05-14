package it.unibo.caesena.view.components;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.scene.StartScene;

public class StatisticsPanelImpl implements StatisticsPanel<JPanel> {

    private final JPanel mainPanel;

    public StatisticsPanelImpl(final StartScene startScene) {
        this.mainPanel = new JPanel();
        this.mainPanel.setOpaque(false);

        final JButton backButton = new JButton(LocaleHelper.getBackText());
        backButton.setFont(GUI.MEDIUM_BOLD_FONT);
        backButton.addActionListener((e) -> {
            startScene.backToButtonsMenu();
        });
        final JPanel startGamePanel = new JPanel();
        startGamePanel.setOpaque(false);
        startGamePanel.add(backButton);
        startGamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGamePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, GUI.DEFAULT_PADDING * 4, 0));
        this.mainPanel.add(startGamePanel);
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

}
