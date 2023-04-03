package it.unibo.caesena.view.components.meeple;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.GrayFilter;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.ResourceUtil;

public class MeepleImage {
    private final Meeple meeple;
    private final Image normalImage;
    private final Image blurredImage;

    public MeepleImage(final Meeple meeple, final Color color) {
        this.meeple = meeple;
        final BufferedImage image = ResourceUtil.getBufferedImage("meepleBlank.png", List.of("meeple"));
        this.normalImage = colorAllPixels(image, color);
        this.blurredImage = GrayFilter.createDisabledImage(image);
    }

    public Meeple getMeeple() {
        return this.meeple;
    }

    public Image getImage() {
        if (meeple.isPlaced()) {
            return blurredImage;
        } else {
            return normalImage;
        }
    }

    public Image getNormalImage() {
        return normalImage;
    }

    private BufferedImage colorAllPixels(final BufferedImage image, final Color color) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getRGB(x, y);
                if (pixel != 0) {
                    if(pixel == -1){
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
