package it.unibo.caesena.view.components.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSectionType;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.UserInterface;
import it.unibo.caesena.view.components.meeple.MeepleImage;
import net.coobird.thumbnailator.Thumbnails;

/**
 * This class rappresent the image of a tile.
 * Its also responsible of overlaying any placed meeple in the correct
 * {@link it.unibo.caesena.model.tile.TileSection}.
 */
public class TileImage {

    private final Tile tile;
    private BufferedImage temporaryImage;
    private boolean somethingChanged;
    private Optional<MeepleImage> meepleImage;
    private final UserInterface ui;

    /**
     * Class contructor.
     *
     * @param tile of which to manage the image
     */
    public TileImage(final Tile tile, UserInterface ui) {
        this.somethingChanged = true;
        this.tile = tile;
        this.meepleImage = Optional.empty();
        this.ui = ui;
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
                final BufferedImage imageName = ResourceUtil.getBufferedImage(tile.getTileType().getName() + ".png",
                        List.of("tiles"));
                temporaryImage = Thumbnails.of(imageName).size(width, height).rotate(tile.getRotationCount() * 90)
                        .asBufferedImage();
                if (meepleImage.isPresent()) {
                    temporaryImage = getTileImageWithMeeple(temporaryImage);
                }
            }
            final BufferedImage outImage;
            outImage = new BufferedImage(this.temporaryImage.getWidth(null),
                    this.temporaryImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            outImage.getGraphics().drawImage(this.temporaryImage, 0, 0, null);
            return outImage;
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
            final BufferedImage imageName = ResourceUtil.getBufferedImage(tile.getTileType().getName() + ".png",
                    List.of("tiles"));
            image = Thumbnails.of(imageName).size(width, height).rotate(tile.getRotationCount() * 90).asBufferedImage();
            return image;
        } catch (final IOException e) {
            return null;
        }
    }

    /**
     * Adds a meeple.
     *
     * @param meeple to be add
     */
    public void addMeeple(final MeepleImpl meeple) {
        this.meepleImage = Optional.of(new MeepleImage(meeple, meeple.getColor().asSwingColor()));
        somethingChanged = true;
    }

    /**
     * Removes the meeple in the tile image.
     */
    public void removeMeeple() {
        this.meepleImage = Optional.empty();
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
        if (this.meepleImage.isPresent()) {
            final int meepleSize = (int) ((double) image.getHeight(null) / 5);
            final Pair<Integer, Integer> meeplePosition = getMeeplePosition(image.getHeight(null) - meepleSize);
            finalGraphics.drawImage(meepleImage.get().getNormalImage(), meeplePosition.getX(), meeplePosition.getY(),
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
        if (meepleImage.isPresent() && meepleImage.get().getMeeple().isPlaced()) {

            TileSectionType type = ui.getController().getTileSectionFromMeeple(meepleImage.get().getMeeple()).get()
                    .getType();
            if (type.getName().equals("CENTER")) {
                return new Pair<Integer, Integer>(centralPadding, centralPadding);
            }
            if (type.getName().equals("DOWN_CENTER")) {
                return new Pair<Integer, Integer>(centralPadding, max - closePadding);
            }
            if (type.getName().equals("DOWN_LEFT")) {
                return new Pair<Integer, Integer>(farPadding, max - closePadding);
            }
            if (type.getName().equals("DOWN_RIGHT")) {
                return new Pair<Integer, Integer>(max - farPadding, max - closePadding);
            }
            if (type.getName().equals("LEFT_CENTER")) {
                return new Pair<Integer, Integer>(closePadding, centralPadding);
            }
            if (type.getName().equals("LEFT_DOWN")) {
                return new Pair<Integer, Integer>(closePadding, max - farPadding);
            }
            if (type.getName().equals("LEFT_UP")) {
                return new Pair<Integer, Integer>(closePadding, farPadding);
            }
            if (type.getName().equals("RIGHT_CENTER")) {
                return new Pair<Integer, Integer>(max - closePadding, centralPadding);
            }
            if (type.getName().equals("RIGHT_DOWN")) {
                return new Pair<Integer, Integer>(max - closePadding, max - farPadding);
            }
            if (type.getName().equals("RIGHT_UP")) {
                return new Pair<Integer, Integer>(max - closePadding, farPadding);
            }
            if (type.getName().equals("UP_CENTER")) {
                return new Pair<Integer, Integer>(centralPadding, closePadding);
            }
            if (type.getName().equals("UP_LEFT")) {
                return new Pair<Integer, Integer>(farPadding, closePadding);
            }
            if (type.getName().equals("UP_RIGHT")) {
                return new Pair<Integer, Integer>(max - farPadding, closePadding);
            }
            // }
            // }
        }

        throw new IllegalStateException("Section is a known section or is null");
    }
}
