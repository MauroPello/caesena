package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PlayerImageImpl extends JPanel implements PlayerImage<JPanel> {

    private Optional<Integer> forcedSize;
    private Color color = Color.black;

    public PlayerImageImpl() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.forcedSize = Optional.empty();
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
