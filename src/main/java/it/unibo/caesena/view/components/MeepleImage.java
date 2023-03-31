package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.GrayFilter;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.ResourceUtil;
import net.coobird.thumbnailator.Thumbnails;

public class MeepleImage {
    private Meeple meeple;
    private Image normalImage;
    private Image blurredImage;

    MeepleImage(Meeple meeple, Color color, int meepleSize) {
        this.meeple = meeple;
        try {
			BufferedImage image = Thumbnails.of(ResourceUtil.getBufferedImage("meepleBlank.png", List.of("meeple"))).size(meepleSize, meepleSize).asBufferedImage();
            this.normalImage = setColorForAllPixels(image, color);
            this.blurredImage = GrayFilter.createDisabledImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    MeepleImage(Meeple meeple, Color color) {
        this.meeple = meeple;
        BufferedImage image = ResourceUtil.getBufferedImage("meepleBlank.png", List.of("meeple"));
        this.normalImage = setColorForAllPixels(image, color);
        this.blurredImage = GrayFilter.createDisabledImage(image);
    }

    public Meeple getMeeple() {
        return this.meeple;
    }

    public Image getImage() {
        if(meeple.isPlaced()) {
           return blurredImage;
        } else {
           return normalImage;
        }
    }

    public Image getNormalImage() {
        return normalImage;
    }

    private BufferedImage setColorForAllPixels(final BufferedImage image, Color color) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getRGB(x, y);
                if (pixel != 0) {
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }
        return image;
    }
}
