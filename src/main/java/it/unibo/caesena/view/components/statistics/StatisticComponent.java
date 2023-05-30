package it.unibo.caesena.view.components.statistics;

import javax.swing.JPanel;

import it.unibo.caesena.model.Statistic;
import it.unibo.caesena.view.BasicComponent;

import java.awt.*;
import javax.swing.*;

/**
 * Per quelle statistiche che sono solo una riga
 */
public class StatisticComponent implements BasicComponent<JPanel>{

    private final JPanel mainPanel;
    private final JPanel tablePanel;

    public StatisticComponent(Statistic statistic) {
        this.mainPanel = new JPanel(new BorderLayout());
        this.tablePanel = new JPanel(new GridLayout(0,2));
        final JLabel title = new JLabel(statistic.getTitle());
        mainPanel.setOpaque(false);
        this.mainPanel.add(title, BorderLayout.NORTH);
        this.mainPanel.add(tablePanel, BorderLayout.CENTER);
        if (statistic.getHeader().isPresent()) {
            final JLabel name = new JLabel(statistic.getHeader().get().getX());
            final JLabel value = new JLabel(statistic.getHeader().get().getY());
            name.setBackground(Color.MAGENTA);
            value.setBackground(Color.MAGENTA);
            name.setOpaque(true);
            value.setOpaque(true);
            this.tablePanel.add(name);
            this.tablePanel.add(value);
        }
        for (var row : statistic) {
            final JLabel name = new JLabel(row.getX());
            final JLabel value = new JLabel(row.getY());
            this.tablePanel.add(name);
            this.tablePanel.add(value);
        }
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public JPanel getComponent() {
        return mainPanel;
    }

}
