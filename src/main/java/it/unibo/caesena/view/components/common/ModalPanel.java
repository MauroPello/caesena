package it.unibo.caesena.view.components.common;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import it.unibo.caesena.view.BasicComponent;
import it.unibo.caesena.view.GUI;

import javax.swing.*;

/**
 * A class defining a simple modal panel with two different sizes and an image
 * as background.
 */
public class ModalPanel extends JPanelWithBackgroundImage implements BasicComponent<JPanel> {
    private static final long serialVersionUID = -4095427840403035675L;
    private static final float MODAL_PREFERRED_RATIO = 0.4f;
    private static final float MODAL_MAXIMUM_RATIO = 0.5f;
    private static final float MODAL_MINIMUM_RATIO = 0.2f;
    private static final float SMALL_MODAL_RATIO = 0.5f;
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
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * MODAL_MAXIMUM_RATIO * SMALL_MODAL_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * MODAL_MAXIMUM_RATIO * SMALL_MODAL_RATIO));
        } else {
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * MODAL_MAXIMUM_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * MODAL_MAXIMUM_RATIO));
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
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * MODAL_MINIMUM_RATIO * SMALL_MODAL_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * MODAL_MINIMUM_RATIO * SMALL_MODAL_RATIO));
        } else {
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * MODAL_MINIMUM_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * MODAL_MINIMUM_RATIO));
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
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * MODAL_PREFERRED_RATIO * SMALL_MODAL_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * MODAL_PREFERRED_RATIO * SMALL_MODAL_RATIO));
        } else {
            return new Dimension((int) Math.round(GUI.SCREEN_WIDTH * MODAL_PREFERRED_RATIO),
            (int) Math.round(GUI.SCREEN_HEIGHT * MODAL_PREFERRED_RATIO));
        }
    }
}
