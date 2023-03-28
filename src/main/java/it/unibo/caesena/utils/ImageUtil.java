package it.unibo.caesena.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class ImageUtil {

    public static BufferedImage getImageFromRelativePath(String relativePath) {
        BufferedImage image = null;
        try {
            File file = new File(ClassLoader.getSystemResource(relativePath).toURI());
            image = ImageIO.read(file);
        } catch (IOException | URISyntaxException e ) {
            throw new IllegalStateException("Image path not valid");
        }

        return image;
    }
}
