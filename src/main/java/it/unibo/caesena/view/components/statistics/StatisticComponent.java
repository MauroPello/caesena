package it.unibo.caesena.view.components.statistics;

import it.unibo.caesena.model.Statistic;
import it.unibo.caesena.view.BasicComponent;
import it.unibo.caesena.view.GUI;

import java.awt.*;
import javax.swing.*;

/**
 * Per quelle statistiche che sono solo una riga
 */
public class StatisticComponent implements BasicComponent<JPanel>{

    private final JPanel mainPanel;
    private final JPanel tablePanel;

    public StatisticComponent(Statistic statistic) {
        final JPanel contentPanel = new JPanel(new BorderLayout());
        this.tablePanel = new JPanel(new GridLayout(0,2));
        final JLabel title = new JLabel(statistic.getTitle());
        title.setFont(GUI.BIG_NORMAL_FONT);
        contentPanel.setOpaque(false);
        contentPanel.add(title, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
        if (statistic.getHeader().isPresent()) {
            setColumnTitle(statistic.getHeader().get().getX());
            setColumnTitle(statistic.getHeader().get().getY());
        }
        for (var row : statistic) {
            setCell(row.getX());
            setCell(row.getY());
        }

        final JScrollPane contentScrollPane = new JScrollPane(contentPanel);
        contentScrollPane.setOpaque(false);
        contentScrollPane.getViewport().setOpaque(false);
        contentScrollPane.setAutoscrolls(true);
        contentScrollPane.setBorder(null);

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setOpaque(false);
        this.mainPanel.add(contentScrollPane);
    }

    private void setColumnTitle(String name) {
        final JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(GUI.MEDIUM_BOLD_FONT);
        nameLabel.setBackground(Color.GREEN);
        nameLabel.setOpaque(true);
        this.tablePanel.add(nameLabel);
    }

    private void setCell(String name) {
        final JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(GUI.MEDIUM_NORMAL_FONT);
        this.tablePanel.add(nameLabel);
    }

    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        this.mainPanel.setVisible(visible);
    }

    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

}
