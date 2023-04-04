package it.unibo.caesena.view.components.common;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import it.unibo.caesena.view.GUI;

/**
 * A class defining a simple modal panel with two different sizes and an image
 * as background.
 */
public class ModalPanel extends PanelWithBackgroundImage {
    private static final long serialVersionUID = -4095427840403035675L;
    private final boolean small;

    /**
     * Public constructor that accepts an image to be set as background and a
     * boolean determining whether the panel should be small or not.
     *
     * @param image to set as background
     * @param small whether the panel should be small or not
     */
    public ModalPanel(final BufferedImage image, final boolean small) {
        super(image);
        this.small = small;
    }

    /**
     * {@inheritDoc}
     *
     * Forces the maximum size to be relative to the screen size.
     */
    @Override
    public Dimension getMaximumSize() {
        if (small) {
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MAXIMUM_RATIO * GUI.SMALL_MODAL_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MAXIMUM_RATIO * GUI.SMALL_MODAL_RATIO));
        } else {
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MAXIMUM_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MAXIMUM_RATIO));
        }
    }

    /**
     * {@inheritDoc}
     *
     * Forces the minimum size to be relative to the screen size.
     */
    @Override
    public Dimension getMinimumSize() {
        if (small) {
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MINIMUM_RATIO * GUI.SMALL_MODAL_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MINIMUM_RATIO * GUI.SMALL_MODAL_RATIO));
        } else {
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_MINIMUM_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_MINIMUM_RATIO));
        }
    }

    /**
     * {@inheritDoc}
     *
     * Forces the preferred size to be relative to the screen size.
     */
    @Override
    public Dimension getPreferredSize() {
        if (small) {
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_PREFERRED_RATIO * GUI.SMALL_MODAL_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_PREFERRED_RATIO * GUI.SMALL_MODAL_RATIO));
        } else {
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * GUI.MODAL_PREFERRED_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * GUI.MODAL_PREFERRED_RATIO));
        }
    }
}
