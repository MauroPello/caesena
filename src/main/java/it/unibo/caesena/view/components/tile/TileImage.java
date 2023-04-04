package it.unibo.caesena.view.components.tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.components.meeple.MeepleImage;
import net.coobird.thumbnailator.Thumbnails;

/**
 * This class rappresent the image of a tile.
 * Its also responsible of overlaying any placed meeple in the correct
 * {@link it.unibo.caesena.model.tile.TileSection}.
 */
public class TileImage {

    private final Tile tile;
    private Optional<Meeple> meeple;
    private BufferedImage temporaryImage;
    private boolean somethingChanged = true;

    /**
     * Class contructor.
     *
     * @param tile of which to manage the image
     */
    public TileImage(final Tile tile) {
        this.tile = tile;
        this.meeple = Optional.empty();
    }

    /**
     * Gets the tile of which this object is the image.
     *
     * @return the tile of which this object is the image
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Decides if an image should be update of not, for perfomance porpouses.
     *
     * @return true if the image should be updated, false otherwise
     */
    private boolean shouldUpdateImage() {
        if (somethingChanged) {
            somethingChanged = false;
        }
        return !somethingChanged;
    }

    /**
     * Gets the image as a {@link java.awt.image.BufferedImage}.
     * It also overlay any placed meeple.
     *
     * @param width  of the new {@link java.awt.image.BufferedImage}
     * @param height of the new {@link java.awt.image.BufferedImage}
     * @return the image as a {@link java.awt.image.BufferedImage}
     */
    public BufferedImage getAsBufferedImage(final int width, final int height) {

        try {
            if (shouldUpdateImage()) {
                final BufferedImage imageName = ResourceUtil.getBufferedImage(tile.getTileType().name() + ".png",
                        List.of("tiles"));
                temporaryImage = Thumbnails.of(imageName).size(width, height).rotate(tile.getRotationCount() * 90)
                        .asBufferedImage();
                if (meeple.isPresent()) {
                    temporaryImage = getTileImageWithMeeple(temporaryImage);
                }
            }
            return temporaryImage;
        } catch (final IOException e) {
            throw new IllegalStateException("Image path not valid", e);
        }
    }

    /**
     * Gets the image as a {@link java.awt.image.BufferedImage}.
     * It DOESN'T overlay any placed meeple.
     *
     * @param width  of the new {@link java.awt.image.BufferedImage}
     * @param height of the new {@link java.awt.image.BufferedImage}
     * @return the image as a {@link java.awt.image.BufferedImage}
     */
    public BufferedImage getAsBufferedImageWithoutMeeple(final int width, final int height) {
        BufferedImage image;
        try {
            final BufferedImage imageName = ResourceUtil.getBufferedImage(tile.getTileType().name() + ".png",
                    List.of("tiles"));
            image = Thumbnails.of(imageName).size(width, height).rotate(tile.getRotationCount() * 90).asBufferedImage();
            return image;
        } catch (final IOException e) {
            return null;
        }
    }

    /**
     * Gets the placed meeple.
     *
     * @return the placed meeple
     */
    public Optional<Meeple> getMeeple() {
        return this.meeple;
    }

    /**
     * Adds a meeple.
     *
     * @param meeple to be add
     */
    public void addMeeple(final Meeple meeple) {
        this.meeple = Optional.of(meeple);
        somethingChanged = true;
    }

    /**
     * Removes the meeple in the tile image.
     */
    public void removeMeeple() {
        this.meeple = Optional.empty();
        somethingChanged = true;
    }

    /**
     * Overlays the given image with a meeple, if present.
     *
     * @param image to be overlayed
     * @return the overlayed image as a {@link java.awt.image.BufferedImage}
     */
    private BufferedImage getTileImageWithMeeple(final BufferedImage image) {
        final Graphics2D finalGraphics = image.createGraphics();
        finalGraphics.drawImage(image, 0, 0, null);
        if (this.meeple.isPresent()) {
            final int meepleSize = (int) ((double) image.getHeight(null) / 5);
            final int red = meeple.get().getOwner().getColor().getRed();
            final int green = meeple.get().getOwner().getColor().getGreen();
            final int blue = meeple.get().getOwner().getColor().getBlue();
            final Color color = new Color(red, green, blue);
            final MeepleImage meepleimage = new MeepleImage(this.meeple.get(), color);
            final Pair<Integer, Integer> meeplePosition = getMeeplePosition(image.getHeight(null) - meepleSize);
            finalGraphics.drawImage(meepleimage.getNormalImage(), meeplePosition.getX(), meeplePosition.getY(),
                    meepleSize, meepleSize, null);
        }
        finalGraphics.dispose();
        return image;
    }

    /**
     * Gets the coordinates in pixel at which to place a
     * {@link it.unibo.caesena.model.meeple.Meeple} based on a specific
     * {@link it.unibo.caesena.model.tile.TileSection}.
     *
     * @param max is the max value a meeple can be placed at
     * @return the coordinated in pixel
     */
    private Pair<Integer, Integer> getMeeplePosition(final int max) {
        final int closePadding = max / 10;
        final int farPadding = max / 5;
        final int centralPadding = max / 2;
        return switch (meeple.get().getPosition().getY()) {
            case CENTER -> new Pair<Integer, Integer>(centralPadding, centralPadding);
            case DOWN_CENTER -> new Pair<Integer, Integer>(centralPadding, max - closePadding);
            case DOWN_LEFT -> new Pair<Integer, Integer>(farPadding, max - closePadding);
            case DOWN_RIGHT -> new Pair<Integer, Integer>(max - farPadding, max - closePadding);
            case LEFT_CENTER -> new Pair<Integer, Integer>(closePadding, centralPadding);
            case LEFT_DOWN -> new Pair<Integer, Integer>(closePadding, max - farPadding);
            case LEFT_UP -> new Pair<Integer, Integer>(closePadding, farPadding);
            case RIGHT_CENTER -> new Pair<Integer, Integer>(max - closePadding, centralPadding);
            case RIGHT_DOWN -> new Pair<Integer, Integer>(max - closePadding, max - farPadding);
            case RIGHT_UP -> new Pair<Integer, Integer>(max - closePadding, farPadding);
            case UP_CENTER -> new Pair<Integer, Integer>(centralPadding, closePadding);
            case UP_LEFT -> new Pair<Integer, Integer>(farPadding, closePadding);
            case UP_RIGHT -> new Pair<Integer, Integer>(max - farPadding, closePadding);
        };
    }
}