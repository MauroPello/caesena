package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PlayerImageImpl extends JPanel implements PlayerImage<JPanel> {

    public PlayerImageImpl(final int defaultWidth, final int defaultHeight) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
        this.setMinimumSize(new Dimension(defaultWidth, defaultHeight));
    }
    
    @Override
    public void setColor(final Color color) {
        this.setBackground(color);
        this.setForeground(color);
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

}
