package it.unibo.caesena.view.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
        this.setLayout(new BorderLayout());
        JPanel upPanel = new JPanel();
        this.add(upPanel, BorderLayout.NORTH);
        JPanel leftPanel = new JPanel() {
            @Override
            public Component add(Component component){
                super.add(component);
                super.add(Box.createVerticalStrut(10));
                return null;
            }
        };
        leftPanel.setLayout(new GridLayout(0,1));
        this.add(leftPanel, BorderLayout.EAST);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0,1));
        this.add(rightPanel, BorderLayout.WEST);
        JPanel downPanel = new JPanel();
        this.add(downPanel, BorderLayout.SOUTH);
        JPanel centerPanel = new JPanel();
        this.add(centerPanel, BorderLayout.CENTER);

        int index = 0;
        for (TileSection section : TileSection.values()) {
            JButton button = new SectionButton(section);
            JPanel correctJPanel = switch (index) {
                case 0 -> upPanel;
                case 1 -> upPanel;
                case 2 -> upPanel;
                case 3 -> leftPanel;
                case 4 -> leftPanel;
                case 5 -> leftPanel;
                case 6 -> downPanel;
                case 7 -> downPanel;
                case 8 -> downPanel;
                case 9 -> rightPanel;
                case 10 -> rightPanel;
                case 11 -> rightPanel;
                case 12 -> centerPanel;
                default -> throw new IllegalStateException("Index value not valid");
            };
            correctJPanel.add(button);
            index++;
        }
    }

    private String getLabelFromSection(TileSection section) {
        //return currentTile.getGameSet(section).getType().toString() + "-" + section.name();
        return "x";
    }

    private class SectionButton extends JButton {
        private final TileSection section;

        public SectionButton(TileSection section) {
            super();
            this.section = section;
            // TODO cambiare sta roba ovviamente
            String buttonLabel = getLabelFromSection(section);
            this.setText(buttonLabel);
            this.addActionListener((e) ->
            {
                currentSectionSelected = Optional.of(this.section);
            });
        }
    }

}
