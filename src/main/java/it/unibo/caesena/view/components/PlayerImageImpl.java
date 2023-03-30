package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PlayerImageImpl extends JPanel implements PlayerImage<JPanel> {

    private Color color = Color.black;

    public PlayerImageImpl(final int defaultWidth, final int defaultHeight) {
        this.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
        this.setMinimumSize(new Dimension(defaultWidth, defaultHeight));
        this.setOpaque(false);
    }

    public PlayerImageImpl() {
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (this.getHeight() > this.getWidth()) {
            g.setColor(color);
            g.fillRect(0, 0, this.getWidth(), this.getWidth());
        } else {
            g.setColor(color);
            g.fillRect(0, 0, this.getHeight(), this.getHeight());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        final Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        return new Dimension(newSize, newSize);
    }

    @Override
    public void setColor(final Color color) {
        this.color = color;
        this.repaint();
        this.revalidate();
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

}
