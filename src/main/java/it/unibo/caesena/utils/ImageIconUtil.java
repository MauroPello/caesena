package it.unibo.caesena.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

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
        BufferedImage image = null;
        try {
            File file = new File(ClassLoader.getSystemResource(relativePath).toURI());
            image = ImageIO.read(file);
        } catch (IOException | URISyntaxException e ) {
            throw new IllegalStateException("Image path not valid");
        }

        return image;
    }

    public static Image getMeepleImage(Color color) {
        BufferedImage meepleImage = getImageFromRelativePath(ROOT + SEP + "meeple" + SEP + "meepleBlank.png");
        for (int y = 0; y < meepleImage.getHeight(); y++) {
            for (int x = 0; x < meepleImage.getWidth(); x++) {
                int pixel = meepleImage.getRGB(x,y);
                if(pixel != 0) {
                    meepleImage.setRGB(x, y, color.getRGB());
                }
            }
        }
        return meepleImage;
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

    public static Image getTileImageWithMeeple(Color color, TileButton tileButton) {
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
            int meepleSize = (int)((double)tileImage.getHeight(null)/5);
            int scaling = java.awt.Image.SCALE_SMOOTH;
            Image meepleImage = getMeepleImage(color);
            Image scaledMeepleImage = meepleImage.getScaledInstance(meepleSize, meepleSize, scaling);
            TileSection tileSection = tileButton.getPlacedMeepleSection();
            Pair<Integer, Integer> meeplePosition = getMeeplePosition(tileSection, tileImage.getHeight(null)-scaledMeepleImage.getHeight(null));
            finalGraphics.drawImage(scaledMeepleImage, meeplePosition.getX(), meeplePosition.getY(), null);
        }
        finalGraphics.dispose();
        return resultImage;
    }

    private static Pair<Integer, Integer> getMeeplePosition(TileSection section, int max) {
        final int closePadding = max/10;
        final int farPadding = max/5;
        final int centralPadding = max/2;
        return switch (section) {
            case CENTER -> new Pair<Integer,Integer>(centralPadding, centralPadding);
            case DOWN_CENTER -> new Pair<Integer,Integer>(centralPadding, max - closePadding);
            case DOWN_LEFT -> new Pair<Integer,Integer>(farPadding, max - closePadding);
            case DOWN_RIGHT -> new Pair<Integer,Integer>(max - farPadding, max - closePadding);
            case LEFT_CENTER -> new Pair<Integer,Integer>(closePadding, centralPadding);
            case LEFT_DOWN -> new Pair<Integer,Integer>(closePadding, max - farPadding);
            case LEFT_UP -> new Pair<Integer,Integer>(closePadding, farPadding);
            case RIGHT_CENTER -> new Pair<Integer,Integer>(max - closePadding, centralPadding);
            case RIGHT_DOWN -> new Pair<Integer,Integer>(max - closePadding, max - farPadding);
            case RIGHT_UP -> new Pair<Integer,Integer>(max - closePadding, farPadding);
            case UP_CENTER -> new Pair<Integer,Integer>(centralPadding, closePadding);
            case UP_LEFT -> new Pair<Integer,Integer>(farPadding, closePadding);
            case UP_RIGHT -> new Pair<Integer,Integer>(max - farPadding, closePadding);
        };
    }
}
