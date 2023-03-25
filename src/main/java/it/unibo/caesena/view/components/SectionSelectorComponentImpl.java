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
        SectionButton upLeftButton = new SectionButton(TileSection.UpLeft);
        SectionButton upCenterButton = new SectionButton(TileSection.UpCenter);
        SectionButton upRightButton = new SectionButton(TileSection.UpRight);
        SectionButton leftUpButton = new SectionButton(TileSection.LeftUp);
        SectionButton leftCenterButton = new SectionButton(TileSection.LeftCenter);
        SectionButton leftDownButton = new SectionButton(TileSection.LeftDown);
        SectionButton rightUpButton = new SectionButton(TileSection.RightUp);
        SectionButton rightCenterButton = new SectionButton(TileSection.RightCenter);
        SectionButton rightDownButton = new SectionButton(TileSection.RightDown);
        SectionButton downLeftButton = new SectionButton(TileSection.DownLeft);
        SectionButton downCenterButton = new SectionButton(TileSection.DownCenter);
        SectionButton downRightButton = new SectionButton(TileSection.DownRight);
        SectionButton centerButton = new SectionButton(TileSection.Center);
        GridBagConstraints constr = new GridBagConstraints();
        constr.weightx = 1;
        constr.weighty = 1;
        constr.gridx = 1;
        constr.gridy = 0;
        container.add(upLeftButton, constr);
        constr.gridx = 2;
        constr.gridy = 0;
        container.add(upCenterButton, constr);
        constr.gridx = 3;
        constr.gridy = 0;
        container.add(upRightButton, constr);
        constr.gridx = 0;
        constr.gridy = 1;
        container.add(leftUpButton, constr);
        constr.gridx = 4;
        constr.gridy = 1;
        container.add(rightUpButton, constr);
        constr.gridx = 0;
        constr.gridy = 2;
        container.add(leftCenterButton, constr);
        constr.gridx = 2;
        constr.gridy = 2;
        container.add(centerButton, constr);
        constr.gridx = 4;
        constr.gridy = 2;
        container.add(rightCenterButton, constr);
        constr.gridx = 0;
        constr.gridy = 3;
        container.add(leftDownButton, constr);
        constr.gridx = 4;
        constr.gridy = 3;
        container.add(rightDownButton, constr);
        constr.gridx = 1;
        constr.gridy = 4;
        container.add(downLeftButton, constr);
        constr.gridx = 2;
        constr.gridy = 4;
        container.add(downCenterButton, constr);
        constr.gridx = 3;
        constr.gridy = 4;
        container.add(downRightButton, constr);
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
