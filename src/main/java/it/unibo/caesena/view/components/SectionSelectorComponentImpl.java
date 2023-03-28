package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;

public class SectionSelectorComponentImpl extends JPanel implements SectionSelectorComponent {
    private final Tile currentTile;
    private final List<SectionButton> sectionButtons = new ArrayList<>();

    private class SectionButton extends JButton {
        private final TileSection section;
        private Color bgColor;
        private boolean selected;

        public SectionButton(final TileSection section) {
            super();
            this.section = section;
            if (currentTile.getGameSet(section).isMeepleFree() && !currentTile.getGameSet(section).isClosed()) {
                this.bgColor = Color.WHITE;
                this.selected = false;
                final String buttonLabel = getLabelFromSection(section);
                this.setText(buttonLabel);
                this.addActionListener(getSectionButtonListener());
                this.deselect();
            } else {
                this.setContentAreaFilled(false);
                this.setBorderPainted(false);
                this.setBackground(new Color(0, 0, 0, 0));
                this.setOpaque(false);
            }

        }

        public boolean isSelected() {
            return selected;
        }

        public void select() {
            selected = true;
            bgColor = Color.GREEN;
            this.setBackground(bgColor);
            this.validate();
        }

        public void deselect() {
            selected = false;
            bgColor = Color.WHITE;
            this.setBackground(bgColor);
            this.validate();
        }

        public TileSection getSection(){
            return section;
        }

        @Override
        public Graphics getGraphics() {
            return super.getGraphics();
        }

        @Override
        public Dimension getPreferredSize() {
            final Dimension d = super.getPreferredSize();
            final int s = (int)(d.getWidth()<d.getHeight() ? d.getHeight() : d.getWidth());
            return new Dimension (s,s);
        }
    }


    public SectionSelectorComponentImpl(final Tile tile, final Dimension dimension) {
        super();
        this.currentTile = tile;
        this.setSize(dimension);
        redraw();
        this.setVisible(true);
    }

    @Override
    public TileSection getSelectedSection() {
        return sectionButtons.stream()
            .filter(x -> x.isSelected())
            .findFirst().orElseThrow(() -> new IllegalStateException("Tried to access selected section, but none was")).getSection();
    }

    @Override
    public Dimension getPreferredSize() {
        final Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }

    @Override
    public Boolean isSectionSelected() {
        return sectionButtons.stream().anyMatch(x -> x.isSelected());
    }

    @Override
    protected void paintComponent(final Graphics g)
    {
        super.paintComponent(g);
        final Image image = new TileImage(this.currentTile).getAsBufferedImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    private void redraw() {
        this.removeAll();
        this.drawSections();
        this.validate();
        this.repaint();
    }

    private void drawSections() {
        final JPanel container = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return getTotalSize();
            }
        };
        container.setLayout(new GridBagLayout());
        container.setSize(this.getSize());
        container.setOpaque(false);
        this.add(container);
        for (final var section : TileSection.values()) {
            createButton(container, section);
        }
    }

    private void createButton(final JPanel container, final TileSection section) {
        int x = 0;
        int y = 0;
        switch (section){
            case CENTER:
                x = 2;
                y = 2;
                break;
            case DOWN_CENTER:
                x = 2;
                y = 4;
                break;
            case DOWN_LEFT:
                x = 1;
                y = 4;
                break;
            case DOWN_RIGHT:
                x = 3;
                y = 4;
                break;
            case LEFT_CENTER:
                x = 0;
                y = 2;
                break;
            case LEFT_DOWN:
                x = 0;
                y = 3;
                break;
            case LEFT_UP:
                x = 0;
                y = 1;
                break;
            case RIGHT_CENTER:
                x = 4;
                y = 2;
                break;
            case RIGHT_DOWN:
                x = 4;
                y = 3;
                break;
            case RIGHT_UP:
                x = 4;
                y = 1;
                break;
            case UP_CENTER:
                x = 2;
                y = 0;
                break;
            case UP_LEFT:
                x = 1;
                y = 0;
                break;
            case UP_RIGHT:
                x = 3;
                y = 0;
                break;
        }
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = x;
        constraints.gridy = y;
        final SectionButton sectionButton = new SectionButton(section);
        container.add(sectionButton, constraints);
        sectionButtons.add(sectionButton);
    }

    private String getLabelFromSection(final TileSection section) {
        return String.valueOf(currentTile.getGameSet(section).getType().name().toCharArray()[0]);
    }

    private ActionListener getSectionButtonListener() {
        return (e) -> {
            final SectionButton newSectionButton = (SectionButton)e.getSource();
            final Boolean wasSelected = newSectionButton.isSelected();
            sectionButtons.stream().forEach(x -> x.deselect());
            if(!wasSelected) {
                newSectionButton.select();
            }
        };
    }

    private Dimension getTotalSize() {
        return this.getSize();
    }
}
