package it.unibo.caesena.view.components;

import javax.swing.JPanel;

import it.unibo.caesena.view.UserInterface;

public class StatisticsPanelImpl implements StatisticsPanel<JPanel> {

    private final JPanel mainPanel;

    public StatisticsPanelImpl(final UserInterface userInterface) {
        this.mainPanel = new JPanel();
        this.mainPanel.setOpaque(false);

        // code

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
