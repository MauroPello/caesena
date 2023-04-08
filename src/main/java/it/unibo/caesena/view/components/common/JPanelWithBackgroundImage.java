package it.unibo.caesena.view.components.common;

import it.unibo.caesena.view.BasicComponent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * A class defining a JPanel with an Image as background.
 */
public class JPanelWithBackgroundImage extends JPanel implements BasicComponent<JPanel> {
    private static final long serialVersionUID = -3728080344667767964L;

    private final transient BufferedImage image;
    /**
     * Whether or not the image should keep its aspect ratio when resizing the panel.
     */
    private final boolean keepImageAspectRatio;

    /**
     * Public constructor that accepts an image to be placed as background.
     * Makes a copy of the passed image because it's mutable.
     *
     * @param image                to set as background
     * @param keepImageAspectRatio whether or not the image should keep its aspect
     *                             ratio when resizing the panel
     */
    public JPanelWithBackgroundImage(final BufferedImage image, final boolean keepImageAspectRatio) {
        super();
        this.keepImageAspectRatio = keepImageAspectRatio;

        this.image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D bGr = this.image.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();
    }

    /**
     * {@inheritDoc}
     *
     * Places the image in the background without stretching it.
     */
    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        double width = this.getWidth();
        double height = this.getHeight();
        if (keepImageAspectRatio) {
            final double ratioWidth = (double) this.getWidth() / (double) image.getWidth();
            final double ratioHeight = (double) this.getHeight() / (double) image.getHeight();
            width = image.getWidth() * (ratioHeight > ratioWidth ? ratioHeight : ratioWidth);
            height = image.getHeight() * (ratioHeight > ratioWidth ? ratioHeight : ratioWidth);
        }

        graphics.drawImage(image, 0, 0,
                (int) Math.round(width),
                (int) Math.round(height), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a bigger JPanel which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
    public JPanel getComponent() {
        return this;
    }
}
