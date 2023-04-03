package it.unibo.caesena.view.components.common;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PanelWithBackgroundImage extends JPanel {
    
    private final BufferedImage image;

    public PanelWithBackgroundImage(final BufferedImage image) {
        super();
        this.image = image;
    }

    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        double ratioWidht = (double)this.getWidth() / (double)image.getWidth();
        double ratioHeight = (double)this.getHeight() / (double)image.getHeight();
        double width = image.getWidth() * (ratioHeight > ratioWidht ? ratioHeight : ratioWidht);
        double height = image.getHeight() * (ratioHeight > ratioWidht ? ratioHeight : ratioWidht);
        graphics.drawImage(image, 0, 0,
                (int) Math.round(width),
                (int) Math.round(height), null);
    }
}
