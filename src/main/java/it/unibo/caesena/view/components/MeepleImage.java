package it.unibo.caesena.view.components;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import it.unibo.caesena.utils.ResourceUtil;

public class MeepleImage {

    private BufferedImage image;
    private Color color;

    MeepleImage(Color color) {
        this.color = color;
        this.image = setColorForAllPixels(ResourceUtil.getBufferedImage("meepleBlank.png", List.of("meeple")));
    }

    public BufferedImage getAsBufferedImage() {
        return image;
    }

    private BufferedImage setColorForAllPixels(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x,y);
                if(pixel != 0) {
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }
        return image;
    }


    public Image resize(int height, int width) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
