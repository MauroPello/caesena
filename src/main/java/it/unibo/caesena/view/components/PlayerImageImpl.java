package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PlayerImageImpl extends JPanel implements PlayerImage<JPanel> {

    private Color color = Color.black;

    public PlayerImageImpl(final int defaultWidth, final int defaultHeight) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
        this.setMinimumSize(new Dimension(defaultWidth, defaultHeight));
    }

    public PlayerImageImpl() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, this.getHeight(), this.getHeight());
    }
    
    @Override
    public void setColor(final Color color) {
        this.color = color;
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

}
