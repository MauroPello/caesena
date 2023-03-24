package it.unibo.caesena.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.view.components.TileButton;

public class ImageIconUtil {
    private static final String SEP = File.separator;
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "caesena" + SEP + "images" + SEP;

    // https://coderanch.com/t/467131/java/Rotating-ImageIcon
    public static BufferedImage rotate(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage dest = new BufferedImage(height, width, image.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.toRadians(angle), height / 2, width / 2);
        graphics2D.drawRenderedImage(image, null);
        graphics2D.dispose();

        return dest;
    }

    public static BufferedImage getImageFromRelativePath(String relativePath) {
        BufferedImage image;
        try {
            File file = new File(ClassLoader.getSystemResource(relativePath).getFile());
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new IllegalStateException("Path not valid");
        }

        return image;
    }

    public static Image getTileImage(Tile tile) {
        BufferedImage tileImage = getImageFromRelativePath(tile.getImageResourcesPath());
        double angle = 90 * tile.getRotationCount();
        tileImage = rotate(tileImage, angle);
        BufferedImage resultImage = new BufferedImage(tileImage.getHeight(null), tileImage.getWidth(null), BufferedImage.TYPE_INT_ARGB); //cambiare meepleImage senza senso
        Graphics2D finalGraphics = resultImage.createGraphics();
        finalGraphics.drawImage(tileImage, 0, 0, null);
        finalGraphics.dispose();
        return resultImage;
    }

    public static Image getTileImageWithMeeple(Color color, TileSection section, TileButton tileButton) {
        if (!tileButton.containsTile()) {
            throw new IllegalStateException("Tried to get tile image but tile wasn't present on TileButton");
        }

        BufferedImage tileImage = getImageFromRelativePath(tileButton.getContainedTile().getImageResourcesPath());
        double angle = 90 * tileButton.getContainedTile().getRotationCount();
        tileImage = rotate(tileImage, angle);
        BufferedImage resultImage = new BufferedImage(tileImage.getHeight(null), tileImage.getWidth(null), BufferedImage.TYPE_INT_ARGB); //cambiare meepleImage senza senso
        Graphics2D finalGraphics = resultImage.createGraphics();
        finalGraphics.drawImage(tileImage, 0, 0, null);
        if (tileButton.containsMeeple()) {
            BufferedImage meepleImage = getImageFromRelativePath(ROOT + SEP + "meeple" + SEP + "meepleBlank.png");
            for (int y = 0; y < meepleImage.getHeight(); y++) {
                for (int x = 0; x < meepleImage.getWidth(); x++) {
                    int pixel = meepleImage.getRGB(x,y);
                    if(pixel != 0) {
                        meepleImage.setRGB(x, y, color.getRGB());
                    }
                }
            }
            int meepleSize = tileImage.getHeight(null)/3;
            finalGraphics.drawImage(meepleImage.getScaledInstance(meepleSize, meepleSize, java.awt.Image.SCALE_SMOOTH), 100, 100, null);
        }
        finalGraphics.dispose();
        return resultImage;
    }
}
