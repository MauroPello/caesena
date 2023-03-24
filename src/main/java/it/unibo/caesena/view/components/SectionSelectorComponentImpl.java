package it.unibo.caesena.view.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.model.gameset.GameSet;
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

        for (TileSection section : TileSection.values()) {
            JButton button = new SectionButton(section);
            this.add(button);
        }
    }

    private String getLabelFromSection(GameSet gameSet) {
        return gameSet.getType().name();
    }

    private class SectionButton extends JButton {
        private final TileSection section;

        public SectionButton(TileSection section) {
            super();
            this.section = section;
            String buttonLabel = getLabelFromSection(currentTile.getGameSet(section));
            this.setText(buttonLabel);
            this.addActionListener((e) ->
            {
                currentSectionSelected = Optional.of(this.section);
            });
        }
    }

}
