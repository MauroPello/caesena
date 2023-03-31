package it.unibo.caesena.view.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Optional;

import javax.swing.JPanel;

public class PlayerImageImpl extends JPanel implements PlayerImage<JPanel> {

    private Optional<Integer> forcedSize;
    private Color color = Color.black;

    public PlayerImageImpl() {
        this.forcedSize = Optional.empty();
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4));

        if (this.getHeight() > this.getWidth()) {
            g.setColor(color);
            g.fillRect(0, 0, this.getWidth(), this.getWidth());
            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, getWidth(), getWidth());
        } else {
            g.setColor(color);
            g.fillRect(0, 0, this.getHeight(), this.getHeight());
            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, getHeight(), getHeight());
        }
    }
    
    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        if (forcedSize.isPresent()) {
            return new Dimension(forcedSize.get(), forcedSize.get());
        }

        final Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        return new Dimension(newSize, newSize);
    }

    public void forceSize(final int size) {
        this.forcedSize = Optional.ofNullable(size);
        this.setPreferredSize(new Dimension(size, size));
        this.setMinimumSize(new Dimension(size, size));
        this.setMaximumSize(new Dimension(size, size));
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
