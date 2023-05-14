package it.unibo.caesena.view.components;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unibo.caesena.view.UserInterface;

public class StatisticsPanelImpl implements StatisticsPanel<JPanel> {

    private final JPanel mainPanel;
    private final JPanel contentPanel;

    public StatisticsPanelImpl(final UserInterface userInterface) {
        this.mainPanel = new JPanel();
        this.mainPanel.setOpaque(false);

        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS));
        this.contentPanel.setOpaque(false);
        final JScrollPane contentScrollPane = new JScrollPane(contentPanel);
        contentScrollPane.setOpaque(false);
        contentScrollPane.getViewport().setOpaque(false);
        contentScrollPane.setAutoscrolls(true);
        contentScrollPane.setBorder(null);
        this.mainPanel.add(contentScrollPane);

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
