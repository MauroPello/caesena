package it.unibo.caesena.view.components.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.caesena.controller.Controller;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.model.tile.TileSectionType;
import it.unibo.caesena.view.BasicComponent;
import it.unibo.caesena.view.scene.GameScene;

/**
 * This class rappresents a section button, used to pick which section to
 * select. Every SectionButton object rappresent a single section.
 */
public class SectionButton implements BasicComponent<JButton> {
    private static final Color UNSELECTED_COLOR = Color.WHITE;
    private static final Color SELECTED_COLOR = Color.GREEN;
    private final TileSectionType section;
    private final GameScene gameScene;
    private final boolean toBeDrawn;
    private final JButton button;
    private boolean selected;

    /**
     * Class constructor.
     *
     * @param section that is rappresented by the SectionButton object
     * @param gameScene the parent GameScene
     * @param onClickActionListener action listener that specifies what to do in case of a click
     */
    SectionButton(final TileSectionType section, final GameScene gameScene, final ActionListener onClickActionListener) {
        this.button = new JButton() {
            @Override
            public Dimension getPreferredSize() {
                final Dimension d = super.getPreferredSize();
                final int s = (int) (d.getWidth() < d.getHeight() ? d.getHeight() : d.getWidth());
                return new Dimension(s, s);
            }
        };

        this.gameScene = gameScene;
        this.section = section;
        final Controller controller = gameScene.getUserInterface().getController();
        final GameSet gameSet = controller.getCurrentTileGameSetInSection(section);
        this.toBeDrawn = gameSet.isMeepleFree() && !gameSet.isClosed();
        if (this.toBeDrawn) {
            selected = false;
            final String buttonLabel = getLabelFromSection(section);
            this.button.setText(buttonLabel);
            this.button.addActionListener(onClickActionListener);
            this.button.setOpaque(true);
            this.button.setBackground(UNSELECTED_COLOR);
        } else {
            this.button.setContentAreaFilled(false);
            this.button.setBorderPainted(false);
            this.button.setBackground(new Color(0, 0, 0, 0));
            this.button.setOpaque(false);
        }
    }

    /**
     * Specifies if the SectionButton should be drawn.
     * Namely if a Section is selectable.
     *
     * @return true if the SectionButton should be drawn
     */
    public boolean shouldBeDrawn() {
        return this.toBeDrawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.button.isVisible();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        this.button.setVisible(visible);
    }

    /**
     * Gets the Section button as a JButton.
     *
     * @return the Section button as a JButton
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a JPanel which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
    public JButton getComponent() {
        return this.button;
    }

    /**
     * Speficied if the section has been selected.
     *
     * @return true if the section has been selected, false otherwise
     */
    public boolean hasBeenSelected() {
        return selected;
    }

    /**
     * Selects the SectionButton.
     */
    public void select() {
        selected = true;
        this.button.setBackground(SELECTED_COLOR);
        this.button.setOpaque(true);
        this.button.repaint();
    }

    /**
     * Deselects the SectionButton.
     */
    public void deselect() {
        selected = false;
        this.button.setBackground(UNSELECTED_COLOR);
        this.button.setOpaque(true);
        this.button.repaint();
    }

    /**
     * Gets the section that has been selected.
     *
     * @return the section that has been selected
     */
    public TileSectionType getSection() {
        return this.section;
    }

    /**
     * Gets the label to place inside a TileButton based on a given section.
     *
     * @param section from which extrapolate the label
     * @return the label rappresenting the given section
     */
    private String getLabelFromSection(final TileSection section) {
        final Controller controller = this.gameScene.getUserInterface().getController();
        final GameSet gameSet = controller.getCurrentTileGameSetInSection(section);
        return String.valueOf(gameSet.getType().getName().toCharArray()[0]);
    }

}
