package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.ResourceUtil;
import net.coobird.thumbnailator.Thumbnails;

public class MeepleImage {

    private final Color color;
    private Meeple meeple;
    private BufferedImage normalImage;
    private BufferedImage blurredImage;

    MeepleImage(Meeple meeple, Color color, int meepleSize) {
        this.meeple = meeple;
        this.color = color;
        try {
			BufferedImage image = Thumbnails.of(ResourceUtil.getBufferedImage("meepleBlank.png", List.of("meeple"))).size(meepleSize, meepleSize).asBufferedImage();
            this.normalImage = setColorForAllPixels(image);
            this.blurredImage = this.normalImage;
            //this.blurredImage = setColorForAllPixels(ResourceUtil.getBufferedImage("meepleBlank.png", List.of("meeple")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public BufferedImage getAsBufferedImage() {
        if(meeple.isPlaced()) {
            return normalImage;
        } else {
            return blurredImage;
        }
    }

    private BufferedImage setColorForAllPixels(final BufferedImage image) {
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

    public BufferedImage resize(final int height, final int width) {
        try {
            if(meeple.isPlaced()) {
                return Thumbnails.of(normalImage).size(width, height).asBufferedImage();
            } else {
                return Thumbnails.of(blurredImage).size(width, height).asBufferedImage();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return getAsBufferedImage();
        }
    }
}
