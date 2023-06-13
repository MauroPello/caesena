package it.unibo.caesena.view.components.statistics;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import it.unibo.caesena.model.Statistic;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.UserInterface;

public class StatisticsPanelImpl implements StatisticsPanel<JPanel> {

    private final JPanel mainPanel;
    private final JPanel contentPanel;

    public StatisticsPanelImpl(final UserInterface userInterface) {
        this.mainPanel = new JPanel();
        this.mainPanel.setOpaque(false);
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS));
        this.contentPanel.setOpaque(false);

        List<Statistic> statistics = userInterface.getController().getStatistics();
        for (var stat : statistics) {
            final StatisticComponent statComp = new StatisticComponent(stat);
            this.contentPanel.add(statComp.getComponent());
        }

        final JScrollPane contentScrollPane = new JScrollPane(this.contentPanel);
        contentScrollPane.setOpaque(false);
        contentScrollPane.getViewport().setOpaque(false);
        contentScrollPane.setAutoscrolls(true);
        contentScrollPane.setBorder(null);
        this.mainPanel.add(contentScrollPane);
        this.mainPanel.setVisible(false);
        this.mainPanel.setBorder(new EmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING * 15, GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING * 15));
        this.mainPanel.validate();
        this.mainPanel.repaint();
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
