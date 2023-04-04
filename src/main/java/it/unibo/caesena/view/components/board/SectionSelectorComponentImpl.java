package it.unibo.caesena.view.components.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.scene.GameScene;

/**
 * {@inheritDoc}
 *
 * Implements the interface {@link it.unibo.caesena.view.components.board.SectionSelectorComponent}
 * using a {@link javax.swing.JPanel}.
 * It extends {@link it.unibo.caesena.view.components.common.PanelWithBackgroundImage}.
 */
class SectionSelectorComponentImpl extends JPanel implements SectionSelectorComponent<JPanel> {
    private static final long serialVersionUID = 6200143818308185153L;
    private final Map<SectionButton, GridBagConstraints> sectionButtons = new HashMap<>();

    private final GameScene gameScene;

    /**
     * Class constructor.
     *
     * @param gameScene the parent GameScene
     */
    SectionSelectorComponentImpl(final GameScene gameScene) {
        super();
        this.gameScene = gameScene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<TileSection> getSelectedSection() {
        return sectionButtons.keySet().stream()
                .filter(SectionButton::hasBeenSelected)
                .map(SectionButton::getSection)
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Dimension getPreferredSize() {
        final Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Boolean isSectionSelected() {
        return getSelectedSection().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        this.removeAll();
        this.drawSections();
        this.validate();
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.sectionButtons.clear();
        this.removeAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getComponent() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        final BufferedImage tileButton = gameScene.getCurrentTileImage().getAsBufferedImageWithoutMeeple(this.getWidth(),
                this.getHeight());
        graphics.drawImage(tileButton, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    /**
     * Draws all the tile sections to this component.
     */
    private void drawSections() {
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        if (sectionButtons.isEmpty()) {
            populateSectionButtons();
        }
        sectionButtons.entrySet().forEach(e -> this.add(e.getKey(), e.getValue()));
    }

    /**
     * Populates the list of section buttons for each possible section.
     */
    private void populateSectionButtons() {
        for (final var section : TileSection.values()) {
            createButton(section);
        }
    }

    /**
     * Create a TileButton for the provided Section.
     *
     * @param section used to create the corresponding SectionButton.
     */
    private void createButton(final TileSection section) {
        final var coordinates = this.getCoordinates(section);
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = coordinates.getX();
        constraints.gridy = coordinates.getY();
        final SectionButton sectionButton = new SectionButton(section);
        sectionButtons.put(sectionButton, constraints);
    }

    /**
     * Gets the coordinates at which place a tile button section based on a given section.
     * @param section used to find the correct placement
     * @return the coordinates at which place a tile button section based on a given section
     */
    private Pair<Integer, Integer> getCoordinates(final TileSection section) {
        return switch (section) {
            case CENTER -> new Pair<>(2, 2);
            case DOWN_CENTER -> new Pair<>(2, 4);
            case DOWN_LEFT -> new Pair<>(1, 4);
            case DOWN_RIGHT -> new Pair<>(3, 4);
            case LEFT_CENTER -> new Pair<>(0, 2);
            case LEFT_DOWN -> new Pair<>(0, 3);
            case LEFT_UP -> new Pair<>(0, 1);
            case RIGHT_CENTER -> new Pair<>(4, 2);
            case RIGHT_DOWN -> new Pair<>(4, 3);
            case RIGHT_UP -> new Pair<>(4, 1);
            case UP_CENTER -> new Pair<>(2, 0);
            case UP_LEFT -> new Pair<>(1, 0);
            case UP_RIGHT -> new Pair<>(3, 0);
            default -> throw new IllegalStateException("Section is a known section or is null");
        };
    }

    /**
     * Gets the label to place inside a TileButton based on a given section.
     *
     * @param section from which extrapolate the label
     * @return the label rappresenting the given section
     */
    private String getLabelFromSection(final TileSection section) {
        final Controller controller = gameScene.getUserInterface().getController();
        final GameSet gameSet = controller.getCurrentTileGameSetInSection(section);
        return String.valueOf(gameSet.getType().name().toCharArray()[0]);
    }

    /**
     * Gets the action listener that every SectionButton should have.
     *
     * @return the action listener that every SectionButton should have
     */
    private ActionListener getSectionButtonListener() {
        return (e) -> {
            final SectionButton newSectionButton = (SectionButton) e.getSource();
            final Boolean wasSelected = newSectionButton.hasBeenSelected();
            sectionButtons.keySet().stream()
                    .filter(s -> s.shouldBeDrawn())
                    .filter(s -> s.hasBeenSelected())
                    .forEach(s -> s.deselect());
            if (!wasSelected) {
                newSectionButton.select();
            }
        };
    }

    /**
     * This class rappresents a section button, used to pick which section to
     * select. Every SectionButton object rappresent a single section.
     */
    private class SectionButton extends JButton {
        private static final long serialVersionUID = 3246088701705856082L;
        private static final Color UNSELECTED_COLOR = Color.WHITE;
        private static final Color SELECTED_COLOR = Color.GREEN;
        private final TileSection section;
        private boolean selected;
        private final boolean toBeDrawn;

        /**
         * Class constructor.
         *
         * @param section that is rappresented by the SectionButton object
         */
        SectionButton(final TileSection section) {
            super();
            this.section = section;
            final Controller controller = gameScene.getUserInterface().getController();
            final GameSet gameSet = controller.getCurrentTileGameSetInSection(section);
            this.toBeDrawn = gameSet.isMeepleFree() && !gameSet.isClosed();
            if (this.toBeDrawn) {
                selected = false;
                final String buttonLabel = getLabelFromSection(section);
                this.setText(buttonLabel);
                this.addActionListener(getSectionButtonListener());
                this.setOpaque(true);
                this.setBackground(UNSELECTED_COLOR);
            } else {
                this.setContentAreaFilled(false);
                this.setBorderPainted(false);
                this.setBackground(new Color(0, 0, 0, 0));
                this.setOpaque(false);
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
            this.setBackground(SELECTED_COLOR);
            this.setOpaque(true);
            this.repaint();
        }

        /**
         * Deselects the SectionButton.
         */
        public void deselect() {
            selected = false;
            this.setBackground(UNSELECTED_COLOR);
            this.setOpaque(true);
            this.repaint();
        }

        /**
         * Gets the section that has been selected.
         *
         * @return the section that has been selected
         */
        public TileSection getSection() {
            return section;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Dimension getPreferredSize() {
            final Dimension d = super.getPreferredSize();
            final int s = (int) (d.getWidth() < d.getHeight() ? d.getHeight() : d.getWidth());
            return new Dimension(s, s);
        }
    }
}
