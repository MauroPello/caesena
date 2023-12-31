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
import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.tile.TileSectionType;
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
    public final Optional<TileSectionType> getSelectedSection() {
        return sectionButtons.keySet().stream()
                .filter(SectionButton::hasBeenSelected)
                .map(SectionButton::getSection)
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isSectionSelected() {
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
        for (final var section : gameScene.getUserInterface().getController().getAllTileSectionTypes()) {
            createButton(section);
        }
    }

    /**
     * Create a TileButton for the provided Section.
     *
     * @param section used to create the corresponding SectionButton.
     */
    private void createButton(final TileSectionType section) {
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
    private Pair<Integer, Integer> getCoordinates(final TileSectionType section) {
        Controller controller = gameScene.getUserInterface().getController();
        if (section.equals(controller.getTileSectionTypeFromName("CENTER"))) {
            return new Pair<>(2, 2);
        }
        if (section.equals(controller.getTileSectionTypeFromName("DOWN_CENTER"))) {
            return new Pair<>(2, 4);
        }
        if (section.equals(controller.getTileSectionTypeFromName("DOWN_LEFT"))) {
            return new Pair<>(1, 4);
        }
        if (section.equals(controller.getTileSectionTypeFromName("DOWN_RIGHT"))) {
            return new Pair<>(3, 4);
        }
        if (section.equals(controller.getTileSectionTypeFromName("LEFT_CENTER"))) {
            return new Pair<>(0, 2);
        }
        if (section.equals(controller.getTileSectionTypeFromName("LEFT_DOWN"))) {
            return new Pair<>(0, 3);
        }
        if (section.equals(controller.getTileSectionTypeFromName("LEFT_UP"))) {
            return new Pair<>(0, 1);
        }
        if (section.equals(controller.getTileSectionTypeFromName("RIGHT_CENTER"))) {
            return new Pair<>(4, 2);
        }
        if (section.equals(controller.getTileSectionTypeFromName("RIGHT_DOWN"))) {
            return new Pair<>(4, 3);
        }
        if (section.equals(controller.getTileSectionTypeFromName("RIGHT_UP"))) {
            return new Pair<>(4, 1);
        }
        if (section.equals(controller.getTileSectionTypeFromName("UP_CENTER"))) {
            return new Pair<>(2, 0);
        }
        if (section.equals(controller.getTileSectionTypeFromName("UP_LEFT"))) {
            return new Pair<>(1, 0);
        }
        if (section.equals(controller.getTileSectionTypeFromName("UP_RIGHT"))) {
            return new Pair<>(3, 0);
        }
        throw new IllegalStateException("Section is a known section or is null");
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
                    .filter(SectionButton::shouldBeDrawn)
                    .filter(SectionButton::hasBeenSelected)
                    .forEach(SectionButton::deselect);
            if (!wasSelected) {
                newSectionButton.select();
            }
        };
    }
}
