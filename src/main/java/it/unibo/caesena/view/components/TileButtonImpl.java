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
    private Optional<Meeple> meeple;
    private TileSection section;
    private Optional<TileImage> tileImage;
    private boolean locked;
    private boolean preview;

    public TileButtonImpl(final ActionListener onClickActionListener, final GameView gameView) {
        super();
        this.preview = false;
        this.gameView = gameView;
        this.locked = false;
        this.meeple = Optional.empty();
        this.tileImage = Optional.empty();
        this.addActionListener(onClickActionListener);
        this.setContentAreaFilled(false);
        this.setFocusable(false);
    }

    @Override
    public void addTile() {
        this.preview = false;
        this.tileImage = Optional.of(gameView.getCurrentTileImage());
        this.repaint();
    }

    @Override
    public void addTile(final TileImage tileImage) {
        this.preview = false;
        this.locked = true;
        this.tileImage = Optional.of(tileImage);
        this.repaint();
    }

    @Override
    public void removeTile() {
        this.tileImage = Optional.empty();
        this.repaint();
    }

    @Override
    public boolean containsTile() {
        return this.tileImage.isPresent();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (this.containsTile() && isEnabled()) {
            if (this.containsMeeple()) {
                this.tileImage.get().addMeeple(this.meeple.get(), this.section);
            } else {
                this.tileImage.get().removeMeeple();
            }
            g.drawImage(this.tileImage.get().getAsBufferedImage(this.getWidth(), this.getHeight()), 0, 0, this.getWidth(),
                    this.getHeight(), null);
            this.setOpaque(true);
            this.repaint();
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
    public void setMeeple(final Meeple meeple, final TileSection section) {
        this.meeple = Optional.of(meeple);
        this.section = section;
    }

    private boolean containsMeeple() {
        if (meeple.isPresent() && !meeple.get().isPlaced() && !preview) {
            meeple = Optional.empty();
        }
        return meeple.isPresent();
    }

    @Override
    public void previewMeeple(final Meeple meeple, final TileSection section) {
        this.preview = true;
        setMeeple(meeple, section);
    }

    @Override
    public void lock() {
        this.locked = true;
    }
}
