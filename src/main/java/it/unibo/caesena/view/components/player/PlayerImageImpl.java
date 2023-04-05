package it.unibo.caesena.view.components.player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Optional;

import javax.swing.JPanel;

/**
 * A class implementing PlayerImage only allowing colors as images and squared
 * sizes.
 */
public final class PlayerImageImpl implements PlayerImage<JPanel> {
    private final JPanel mainPanel;
    private Optional<Integer> forcedSize;
    private Color color = Color.black;

    /**
     * Public constructor, by default there is no forced size.
     */
    public PlayerImageImpl() {
        this.forcedSize = Optional.empty();
        this.mainPanel = new JPanel() {
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
                final int newSize = d.width > d.height ? d.height : d.width;
                return new Dimension(newSize, newSize);
            }

            /**
             * {@inheritDoc}
             *
             * Uses {@link java.awt.Graphics2D} to draw a stroke around the player image and
             * draws a rectangle with the same color as the player.
             */
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);

                g.setColor(color);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                final Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(Color.BLACK);
                g2d.drawRect(0, 0, getWidth(), getHeight());
            }
        };
        this.mainPanel.setOpaque(false);
    }

    /**
     * Sets a size to force.
     *
     * @param size to force
     */
    public void forceSize(final int size) {
        this.forcedSize = Optional.ofNullable(size);
        this.mainPanel.setPreferredSize(new Dimension(size, size));
        this.mainPanel.setMinimumSize(new Dimension(size, size));
        this.mainPanel.setMaximumSize(new Dimension(size, size));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColor(final Color color) {
        this.color = color;
        this.mainPanel.repaint();
        this.mainPanel.revalidate();
    }

    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        this.mainPanel.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

}
