package it.unibo.caesena.view.components;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JButton;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.view.GameView;

public class TileButtonImpl extends JButton implements TileButton<JButton> {
    private final GameView gameView;
    private boolean hasTile;
    private Optional<Meeple> meeple;
    private TileSection section;
    private TileImage tileImage;
    private boolean locked;
    // private boolean containedTile = false;
    // private boolean containedMeeple = false;
    private boolean containedTile;
    private boolean containedMeeple;

    public TileButtonImpl(final ActionListener onClickActionListener, final GameView gameView) {
        super();
        this.gameView = gameView;
        this.hasTile = false;
        this.locked = false;
        this.addActionListener(onClickActionListener);
        this.setContentAreaFilled(false);
        this.setFocusable(false);
    }

    @Override
    public void addTile() {
        this.meeple = Optional.empty();
        this.hasTile = true;
        this.tileImage = gameView.getCurrentTileImage();
    }

    @Override
    public void addTile(final TileImage tileImage) {
        this.meeple = Optional.empty();
        this.hasTile = true;
        this.tileImage = tileImage;
    }

    @Override
    public void removeTile() {
        this.hasTile = false;
    }

    @Override
    public boolean containsTile() {
        return this.hasTile;
    }

    private boolean somethingHasChanged() {
        if (!(containedTile ^ containsTile())) {
            containedTile = containsTile();
            return true;
        }
        if (!(containedMeeple ^ containsMeeple())) {
            containedMeeple = containsMeeple();
            return true;
        }
        return true;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (somethingHasChanged() && this.containsTile()) {

            if (this.containsMeeple()) {
                this.tileImage.addMeeple(this.meeple.get(), this.section);
            } else {
                this.tileImage.removeMeeple();
            }
            g.drawImage(this.tileImage.getAsBufferedImage(this.getWidth(), this.getHeight()), 0, 0, this.getWidth(),
                    this.getHeight(), null);
            this.setOpaque(true);
            // this.repaint();

        }
    }

    @Override
    public JButton getComponent() {
        return this;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void addMeeple(final Meeple meeple, final TileSection section) {
        this.meeple = Optional.of(meeple);
        this.section = section;
    }

    private boolean containsMeeple() {
        return meeple.isPresent();
    }

    @Override
    public void lock() {
        this.locked = true;
    }
}
