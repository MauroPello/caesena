package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.List;
import java.util.Optional;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.ResourceUtil;

import net.coobird.thumbnailator.Thumbnails;

public class TileImage {

    private final Tile tile;
    private int rotationCount;
    private Color color;
    private Optional<Pair<Meeple, TileSection>> meeple;
    private BufferedImage temporaryImage;
    private boolean somethingChanged = true;

    public TileImage(final Tile tile, final Color color) {
        this.tile = tile;
        this.color = color;
        this.rotationCount = 0;
        this.meeple = Optional.empty();
    }

    public TileImage(final Tile tile) {
        this.tile = tile;
        this.rotationCount = 0;
        this.meeple = Optional.empty();
    }

    public void rotate() {
        this.rotationCount = (this.rotationCount + 1) % 4;
        somethingChanged = true;
    }

    public Tile getTile() {
        return this.tile;
    }

    private boolean shouldUpdateImage() {
        if (somethingChanged) {
            somethingChanged = false;
        }
        return !somethingChanged;
    }

    public BufferedImage getAsBufferedImage(final int width, final int height) {

        try {
            if (shouldUpdateImage()) {
                BufferedImage image_name = ResourceUtil.getBufferedImage(tile.getTileType().name() + ".png",
                        List.of("tiles"));
                temporaryImage = Thumbnails.of(image_name).size(width, height).rotate(90 * rotationCount)
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

    public BufferedImage getAsBufferedImageWithoutMeeple(final int width, final int height) {
        BufferedImage image;
        try {
            BufferedImage image_name = ResourceUtil.getBufferedImage(tile.getTileType().name() + ".png",
                    List.of("tiles"));
            image = Thumbnails.of(image_name).size(width, height).rotate(90 * rotationCount).asBufferedImage();
            return image;
        } catch (final IOException e) {
            throw new IllegalStateException("Image path not valid");
        }
    }

    public void addMeeple(final Meeple meeple, final TileSection section) {
        this.meeple = Optional.of(new Pair<>(meeple, section));
        somethingChanged = true;
    }

    public void removeMeeple() {
        this.meeple = Optional.empty();
        somethingChanged = true;
    }

    private BufferedImage getTileImageWithMeeple(final BufferedImage image) {
        final Graphics2D finalGraphics = image.createGraphics();
        finalGraphics.drawImage(image, 0, 0, null);
        if (this.meeple.isPresent()) {
            final int meepleSize = (int) ((double) image.getHeight(null) / 5);
            final MeepleImage meepleimage = new MeepleImage(this.meeple.get().getX(), this.color);
            meepleimage.resize(meepleSize, meepleSize);
            final Pair<Integer, Integer> meeplePosition = getMeeplePosition(image.getHeight(null) - meepleSize);
            finalGraphics.drawImage(meepleimage.getAsBufferedImage(), meeplePosition.getX(), meeplePosition.getY(),
                    meepleSize, meepleSize, null);
        }
        finalGraphics.dispose();
        return image;
    }

    private Pair<Integer, Integer> getMeeplePosition(final int max) {
        final int closePadding = max / 10;
        final int farPadding = max / 5;
        final int centralPadding = max / 2;
        return switch (meeple.get().getY()) {
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
