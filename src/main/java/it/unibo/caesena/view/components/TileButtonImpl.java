package it.unibo.caesena.view.components;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JButton;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.TileSection;

public class TileButtonImpl extends JButton implements TileButton<JButton> {
    private static final long serialVersionUID = 3246088701705856082L;
    private Optional<Meeple> meeple;
    private Optional<TileImage> tileImage;
    private boolean locked;

    public TileButtonImpl(final ActionListener onClickActionListener) {
        super();
        this.locked = false;
        this.meeple = Optional.empty();
        this.tileImage = Optional.empty();
        this.addActionListener(onClickActionListener);
        this.setContentAreaFilled(false);
        this.setFocusable(false);
    }

    @Override
    public void addTile(final TileImage tileImage) {
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
            g.drawImage(this.tileImage.get().getAsBufferedImage(this.getWidth(), this.getHeight()), 0, 0, this.getWidth(),
                    this.getHeight(), null);
            //this.setOpaque(true);
            //this.repaint();
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
        this.tileImage.get().addMeeple(this.meeple.get(), section);
        this.repaint();
    }


    @Override
    public void lock() {
        this.locked = true;
    }

	@Override
    public void unsetMeeple() {
		this.meeple = Optional.empty();
        this.tileImage.get().removeMeeple();
        this.repaint();
	}

	@Override
    public Optional<Meeple> getMeeple() {
		return this.meeple;
	}

}
