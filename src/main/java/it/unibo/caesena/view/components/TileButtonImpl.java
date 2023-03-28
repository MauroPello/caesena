package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JButton;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;

public class TileButtonImpl extends JButton implements TileButton<JButton> {
    private Optional<Tile> containedTile;
    private Optional<Meeple> placedMeeple;
    private Optional<TileSection> placedMeepleSection;
    private Color playerColor;

    public TileButtonImpl(ActionListener onClickActionListener, Color playerColor) {
        super();
        this.playerColor = playerColor;
        this.containedTile = Optional.empty();
        this.placedMeeple = Optional.empty();
        this.addActionListener(onClickActionListener);
        this.setContentAreaFilled(false);
        this.setFocusable(false);
    }

    @Override
    public void addTile(Tile tile) {
        this.containedTile = Optional.of(tile);
    }

    @Override
    public Tile getContainedTile() {
        return containedTile.orElseThrow(() -> new IllegalStateException("tried to get contained tile but there was none"));
    }

    @Override
    public TileSection getPlacedMeepleSection() {
        return placedMeepleSection.orElseThrow(() -> new IllegalStateException("tried to get placed meeple but there was none"));
    }

    @Override
    public void addMeeple(Meeple meeple, TileSection section) {
        this.placedMeeple = Optional.of(meeple);
        this.placedMeepleSection = Optional.of(section);
    }

    @Override
    public void removeTile() {
        this.containedTile = Optional.empty();
    }

    @Override
    public void removeMeeple() {
        this.placedMeeple = Optional.empty();
    }

    @Override
    public boolean containsTile() {
        return this.containedTile.isPresent();
    }

    @Override
    public boolean containsMeeple(){
        return placedMeeple.isPresent();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (this.containsTile()) {
            TileImage tileImage = new TileImage(getContainedTile(), playerColor);
            if (this.containsMeeple())  {
                tileImage.addMeeple(this.placedMeeple.get(), getPlacedMeepleSection());
            }
            g.drawImage(tileImage.getAsBufferedImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    @Override
    public JButton getComponent() {
        return this;
    }

    @Override
    public boolean isLocked() {
        return containedTile.isPresent() && containedTile.get().isPlaced();
    }
}
