package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.ImageIconUtil;

public class SectionSelectorComponentImpl extends JPanel implements SectionSelectorComponent {
    private final Tile currentTile;
    private Optional<SectionButton> currentSectionButtonSelected = Optional.empty();

    public SectionSelectorComponentImpl(Tile tile, Dimension dimension) {
        super();
        this.currentTile = tile;
        this.setSize(dimension);
        redraw();
        this.setVisible(true);
    }

    @Override
    public TileSection getSelectedSection() {
        return currentSectionButtonSelected.orElseThrow(()->new IllegalStateException("Tried to get section but none was selected")).getSection();
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

    private ActionListener getSectionButtonListener() {
        return (e) -> {
            if (currentSectionButtonSelected.isPresent()) {
                currentSectionButtonSelected.get().deselect();
            }
            SectionButton newSectionButton = (SectionButton)e.getSource();
            newSectionButton.select();
            currentSectionButtonSelected = Optional.of(newSectionButton);
        };
    }

    private class SectionButton extends JButton {
        private final TileSection section;
        private Color bgColor = Color.WHITE;

        public SectionButton(TileSection section) {
            super();
            this.section = section;
            String buttonLabel = getLabelFromSection(section);
            this.setText(buttonLabel);
            this.addActionListener(getSectionButtonListener());
            this.deselect();
        }

        public void select() {
            bgColor = Color.GREEN;
            this.setBackground(bgColor);
            this.validate();
        }

        public void deselect() {
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
            Dimension d = super.getPreferredSize();
            int s = (int)(d.getWidth()<d.getHeight() ? d.getHeight() : d.getWidth());
            return new Dimension (s,s);
        }
    }

    private Dimension getTotalSize() {
        return this.getSize();
    }

    @Override
    public Boolean isSectionSelected() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSectionSelected'");
    }
}