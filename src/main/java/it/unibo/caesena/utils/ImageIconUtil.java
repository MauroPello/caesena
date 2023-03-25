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
        } catch (IOException e) {
            throw new IllegalStateException("Path not valid");
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            case Center -> new Pair<Integer,Integer>(centralPadding, centralPadding);
            case DownCenter -> new Pair<Integer,Integer>(centralPadding, max - closePadding);
            case DownLeft -> new Pair<Integer,Integer>(farPadding, max - closePadding);
            case DownRight -> new Pair<Integer,Integer>(max - farPadding, max - closePadding);
            case LeftCenter -> new Pair<Integer,Integer>(closePadding, centralPadding);
            case LeftDown -> new Pair<Integer,Integer>(closePadding, max - farPadding);
            case LeftUp -> new Pair<Integer,Integer>(closePadding, farPadding);
            case RightCenter -> new Pair<Integer,Integer>(max - closePadding, centralPadding);
            case RightDown -> new Pair<Integer,Integer>(max - closePadding, max - farPadding);
            case RightUp -> new Pair<Integer,Integer>(max - closePadding, farPadding);
            case UpCenter -> new Pair<Integer,Integer>(centralPadding, closePadding);
            case UpLeft -> new Pair<Integer,Integer>(farPadding, closePadding);
            case UpRight -> new Pair<Integer,Integer>(max - farPadding, closePadding);
        };
    }
}
