package it.unibo.caesena.view.components.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;

/**
 * {@inheritDoc}
 *
 * Implementation of the {@link it.unibo.caesena.view.components.tile.TileButton} interface.
 * It utilizes a JButton from the {@link javax.swing}.
 */
public class TileButtonImpl extends JButton implements TileButton<JButton> {
    private static final long serialVersionUID = 3246088701705856082L;
    private final MouseAdapter mouseAdapter;
    private Optional<TileImage> tileImage;
    private boolean locked;

    /**
     * Class constructor.
     * @param onClickActionListener action listener that specifies what to do in case of a click
     */
    public TileButtonImpl(final ActionListener onClickActionListener) {
        super();
        this.locked = false;
        this.tileImage = Optional.empty();
        this.addActionListener(onClickActionListener);
        this.setContentAreaFilled(false);
        this.setFocusable(false);
        this.setBorder(new LineBorder(Color.BLACK));
        this.mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                if (tileImage.isEmpty()) {
                    TileButtonImpl.this.setBorder(new LineBorder(Color.RED));
                }
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                TileButtonImpl.this.setBorder(new LineBorder(Color.BLACK));
            }
        };
        this.addMouseListener(this.mouseAdapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnabled(final boolean b) {
        super.setEnabled(b);
        if (b) {
            this.addMouseListener(this.mouseAdapter);
        } else {
            this.removeMouseListener(this.mouseAdapter);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTile(final Tile tile) {
        this.tileImage = Optional.of(new TileImage(tile));
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTileImage(final TileImage tileImage) {
        this.tileImage = Optional.of(tileImage);
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTile() {
        this.tileImage = Optional.empty();
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsTile() {
        return this.tileImage.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (this.containsTile() && isEnabled()) {
            g.drawImage(this.tileImage.get().getAsBufferedImage(this.getWidth(), this.getHeight()), 0, 0,
                    this.getWidth(),
                    this.getHeight(), null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JButton getComponent() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLocked() {
        return locked;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMeeple(final Meeple meeple) {
        this.tileImage.get().addMeeple(meeple);
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lock() {
        this.locked = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsetMeeple() {
        this.tileImage.get().removeMeeple();
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Meeple> getMeeple() {
        return this.tileImage.get().getMeeple();
    }

}
