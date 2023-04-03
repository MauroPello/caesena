package it.unibo.caesena.view.components.common;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import it.unibo.caesena.view.GUI;

public class ModalPanel extends PanelWithBackgroundImage {

    private final boolean small;

    public ModalPanel(final BufferedImage image, final boolean small) {
        super(image);
        this.small = small;
    }

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
