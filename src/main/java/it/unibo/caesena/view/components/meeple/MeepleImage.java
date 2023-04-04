package it.unibo.caesena.view.components.meeple;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.GrayFilter;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.ResourceUtil;

/**
 * This class rappresents the image of the meeples.
 */
public class MeepleImage {
    private final Meeple meeple;
    private final Image normalImage;
    private final Image blurredImage;

    /**
     * Class constructor.
     *
     * @param meeple corrispondig to the image
     * @param color  color of the player that owns the meeple
     */
    public MeepleImage(final Meeple meeple, final Color color) {
        this.meeple = meeple;
        final BufferedImage image = ResourceUtil.getBufferedImage("meepleBlank.png", List.of("meeple"));
        this.normalImage = colorAllPixels(image, color);
        this.blurredImage = GrayFilter.createDisabledImage(image);
    }

    /**
     * Gets the meeple associeted with the image.
     *
     * @return the meeple associeted with the image
     */
    public Meeple getMeeple() {
        return this.meeple;
    }

    /**
     * Gets the image.
     * Can be either the normal image or the blurred one depending on the fact that
     * the meeple is placed or not.
     *
     * @return the image
     */
    public Image getImage() {
        final BufferedImage outImage;
        final Image tmpImage;
        if (meeple.isPlaced()) {
            tmpImage = this.blurredImage;
        } else {
            tmpImage = this.normalImage;
        }
        outImage = new BufferedImage(tmpImage.getWidth(null), tmpImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        outImage.getGraphics().drawImage(tmpImage, 0, 0, null);
        return outImage;
    }

    /**
     * Gets the image.
     * Can be either the normal image or the blurred one depending on the fact that
     * the meeple is placed or not.
     *
     * @return the image
     */
    public Image getNormalImage() {
        final BufferedImage outImage;
        outImage = new BufferedImage(this.normalImage.getWidth(null), this.normalImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        outImage.getGraphics().drawImage(this.normalImage, 0, 0, null);
        return outImage;
    }

    /**
     * Paints all the pixel of an image in the given color.
     * It ignores the transparent pixels and paints black the white ones.
     *
     * @param image to be painted
     * @param color to paint with
     * @return a image where all the pixel of an image in the given color
     */
    private BufferedImage colorAllPixels(final BufferedImage image, final Color color) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getRGB(x, y);
                if (pixel != 0) {
                    if (pixel == -1) {
                        image.setRGB(x, y, Color.black.getRGB());
                    } else if (pixel != 0) {
                        image.setRGB(x, y, color.getRGB());
                    }
                }
            }
        }
        return image;
    }
}
