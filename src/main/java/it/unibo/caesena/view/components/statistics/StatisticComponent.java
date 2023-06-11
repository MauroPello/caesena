package it.unibo.caesena.view.components.statistics;

import javax.swing.JPanel;

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

        this.mainPanel = new JPanel(new BorderLayout());
        this.tablePanel = new JPanel(new GridLayout(0,2));
        final JLabel title = new JLabel(statistic.getTitle());
        title.setFont(GUI.BIG_NORMAL_FONT);
        mainPanel.setOpaque(false);
        this.mainPanel.add(title, BorderLayout.NORTH);
        this.mainPanel.add(tablePanel, BorderLayout.CENTER);
        if (statistic.getHeader().isPresent()) {
            setColumnTitle(statistic.getHeader().get().getX());
            setColumnTitle(statistic.getHeader().get().getY());
        }
        for (var row : statistic) {
            setCell(row.getX());
            setCell(row.getY());
        }
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
