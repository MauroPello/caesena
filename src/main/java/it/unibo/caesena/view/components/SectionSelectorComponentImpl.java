package it.unibo.caesena.view.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.ImageIconUtil;

public class SectionSelectorComponentImpl extends JPanel implements SectionSelectorComponent {
    private final Tile currentTile;
    private Optional<TileSection> currentSectionSelected = Optional.empty();

    public SectionSelectorComponentImpl(Tile tile, Dimension dimension) {
        super();
        this.currentTile = tile;
        this.setSize(dimension);
        redraw();
        this.setVisible(true);
    }

    @Override
    public TileSection getSelectedSection() {
        return currentSectionSelected.orElseThrow(()->new IllegalStateException("Tried to get section but none was selected"));
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Image image = ImageIconUtil.getTileImage(this.currentTile);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    private void redraw() {
        this.removeAll();
        this.drawSections();
        this.validate();
        this.repaint();
    }

    private void drawSections() {
        JPanel container = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return getTotalSize();
            }
        };
        container.setLayout(new GridBagLayout());
        container.setSize(this.getSize());
        container.setOpaque(false);
        this.add(container);
        for (var section : TileSection.values()) {
            createButton(container, section);
        }
    }

    private void createButton(JPanel container, TileSection section) {
        int x = 0;
        int y = 0;
        switch (section){
            case Center:
                x = 2;
                y = 2;
                break;
            case DownCenter:
                x = 1;
                y = 4;
                break;
            case DownLeft:
                x = 1;
                y = 4;
                break;
            case DownRight:
                x = 3;
                y = 4;
                break;
            case LeftCenter:
                x = 0;
                y = 2;
                break;
            case LeftDown:
                x = 0;
                y = 3;
                break;
            case LeftUp:
                x = 0;
                y = 1;
                break;
            case RightCenter:
                x = 4;
                y = 2;
                break;
            case RightDown:
                x = 4;
                y = 3;
                break;
            case RightUp:
                x = 4;
                y = 1;
                break;
            case UpCenter:
                x = 2;
                y = 0;
                break;
            case UpLeft:
                x = 1;
                y = 0;
                break;
            case UpRight:
                x = 3;
                y = 0;
                break;
        }
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = x;
        constraints.gridy = y;
        SectionButton sectionButton = new SectionButton(section);
        container.add(sectionButton, constraints);
    }

    private String getLabelFromSection(TileSection section) {
        return String.valueOf(currentTile.getGameSet(section).getType().name().toCharArray()[0]);
    }

    private class SectionButton extends JButton {
        private final TileSection section;

        public SectionButton(TileSection section) {
            super();
            this.section = section;
            String buttonLabel = getLabelFromSection(section);
            this.setText(buttonLabel);
            this.addActionListener((e) ->
            {
                currentSectionSelected = Optional.of(this.section);
            });
        }

        @Override
        public Graphics getGraphics() {
            return super.getGraphics();
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            int s = (int)(d.getWidth()<d.getHeight() ? d.getHeight() : d.getWidth());
            return new Dimension (s,s);
        }
    }

    private Dimension getTotalSize() {
        return this.getSize();
    }
}
