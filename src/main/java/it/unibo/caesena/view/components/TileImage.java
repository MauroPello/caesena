package it.unibo.caesena.view.components;

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

import net.coobird.thumbnailator.Thumbnails;

/**
 * manages the tile images.
 */
public class TileImage {

    private final Tile tile;
    private Color color;
    private Optional<Meeple> meeple;
    private BufferedImage temporaryImage;
    private boolean somethingChanged = true;

    /**
     * make the tile image by the tile and set the color fort the meeple.
     *
     * @param tile
     * @param color
     */
    public TileImage(final Tile tile, final Color color) {
        this.tile = tile;
        this.meeple = Optional.empty();
    }

    /**
     * make the tile image by the tile.
     *
     * @param tile
     */
    public TileImage(final Tile tile) {
        this.tile = tile;
        this.meeple = Optional.empty();
    }

    /**
     * @return the current tile.
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * return a boolea based on the need to update the image.
     *
     * @return true if nothing changed
     */
    private boolean shouldUpdateImage() {
        if (somethingChanged) {
            somethingChanged = false;
        }
        return !somethingChanged;
    }

    /**
     * @param width
     * @param height
     * @return the tile image, with the meeple above in case it is present.
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
     * @param width
     * @param height
     * @return the tile image without the meeple above it.
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
     * add the meeple in the tile image.
     *
     * @param meeple
     * @param section
     */
    public void addMeeple(final Meeple meeple, final Color color) {
        this.meeple = Optional.of(meeple);
        this.color = color;
        somethingChanged = true;
    }

    /**
     * remove the meeple in the tile image.
     */
    public void removeMeeple() {
        this.meeple = Optional.empty();
        somethingChanged = true;
    }

    private BufferedImage getTileImageWithMeeple(final BufferedImage image) {
        final Graphics2D finalGraphics = image.createGraphics();
        finalGraphics.drawImage(image, 0, 0, null);
        if (this.meeple.isPresent()) {
            final int meepleSize = (int) ((double) image.getHeight(null) / 5);
            final MeepleImage meepleimage = new MeepleImage(this.meeple.get(), this.color);
            final Pair<Integer, Integer> meeplePosition = getMeeplePosition(image.getHeight(null) - meepleSize);
            finalGraphics.drawImage(meepleimage.getNormalImage(), meeplePosition.getX(), meeplePosition.getY(),
                    meepleSize, meepleSize, null);
        }
        finalGraphics.dispose();
        return image;
    }

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
