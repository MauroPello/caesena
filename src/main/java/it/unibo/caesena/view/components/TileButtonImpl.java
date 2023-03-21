package it.unibo.caesena.view.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.ImageIconUtil;
import it.unibo.caesena.utils.Pair;

public class TileButtonImpl extends JButton implements TileButton {
    private final Pair<Integer, Integer> position;
    private Optional<Tile> containedTile;
    private Optional<Meeple> placedMeeple;
    private boolean locked = false;

    public TileButtonImpl(int x, int y, ActionListener onSelection) {
        super();
        this.containedTile = Optional.empty();
        this.placedMeeple = Optional.empty();
        this.position = new Pair<Integer,Integer>(x, y);
        this.addActionListener(onSelection);
        this.setContentAreaFilled(false);
        this.setFocusable(false);
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    @Override
    public void addTile(Tile tile) {
        this.containedTile = Optional.of(tile);
    }

    @Override
    public void lockTile() {
        if (containedTile.isPresent()) {
            locked = true;
        } else {
            throw new IllegalStateException("Can't lock tile since it's not present");
        }
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public Tile getContainedTile() {
        return containedTile.orElseThrow(() -> new IllegalStateException("tried to get contained tile but there was none"));
    }

    @Override
    public void addMeeple(Meeple meeple) {
        this.placedMeeple = Optional.of(meeple);
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
        return this.placedMeeple.isPresent();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (containedTile.isPresent()) {
            double angle = 90 * containedTile.get().getRotationCount();
            Image image = ImageIconUtil.rotate(new ImageIcon(containedTile.get().getImageResourcesPath()), angle).getImage();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
