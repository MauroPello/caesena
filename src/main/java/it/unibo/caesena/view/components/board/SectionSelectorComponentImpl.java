package it.unibo.caesena.view.components.board;

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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.scene.GameScene;

/**
 * {@inheritDoc}
 *
 * Implements the interface {@link SectionSelectorComponent}
 * using a {@link javax.swing.JPanel}.
 * It extends {@link it.unibo.caesena.view.components.common.JPanelWithBackgroundImage}.
 */
class SectionSelectorComponentImpl implements SectionSelectorComponent<JPanel> {
    private final Map<SectionButton, GridBagConstraints> sectionButtons = new HashMap<>();
    private final GameScene gameScene;
    private final JPanel mainPanel;

    /**
     * Class constructor.
     *
     * @param gameScene the parent GameScene
     */
    SectionSelectorComponentImpl(final GameScene gameScene) {
        this.gameScene = gameScene;
        this.mainPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                final Dimension d = this.getParent().getSize();
                int newSize = d.width > d.height ? d.height : d.width;
                newSize = newSize == 0 ? 100 : newSize;
                return new Dimension(newSize, newSize);
            }

            @Override
            public void paintComponent(final Graphics graphics) {
                super.paintComponent(graphics);
                final BufferedImage tileButton = gameScene.getCurrentTileImage().getAsBufferedImageWithoutMeeple(this.getWidth(),
                        this.getHeight());
                graphics.drawImage(tileButton, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
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
    public final Boolean isSectionSelected() {
        return getSelectedSection().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        this.mainPanel.removeAll();
        this.drawSections();
        this.mainPanel.validate();
        this.mainPanel.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.sectionButtons.clear();
        this.mainPanel.removeAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        this.mainPanel.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a bigger JPanel which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
    public JPanel getComponent() {
        return this.mainPanel;
    }

    /**
     * Draws all the tile sections to this component.
     */
    private void drawSections() {
        this.mainPanel.setLayout(new GridBagLayout());
        this.mainPanel.setOpaque(false);
        if (sectionButtons.isEmpty()) {
            populateSectionButtons();
        }
        sectionButtons.entrySet().forEach(e -> this.mainPanel.add(e.getKey().getComponent(), e.getValue()));
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
        final SectionButton sectionButton = new SectionButton(section, this.gameScene, getSectionButtonListener());
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
     * Gets the action listener that every SectionButton should have.
     *
     * @return the action listener that every SectionButton should have
     */
    private ActionListener getSectionButtonListener() {
        return (e) -> {
            final JButton button = (JButton) e.getSource();
            final SectionButton newSectionButton = sectionButtons.keySet().stream()
                    .filter(b -> b.getComponent().equals(button)).findFirst().get();
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
}
