package it.unibo.caesena.view.components.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSectionType;
import it.unibo.caesena.view.UserInterface;

/**
 * {@inheritDoc}
 *
 * Implementation of the {@link it.unibo.caesena.view.components.tile.TileButton} interface.
 * It utilizes a JButton from the {@link javax.swing}.
 */
public class TileButtonImpl implements TileButton<JButton> {
    private final MouseAdapter mouseAdapter;
    private final JButton button;
    private Optional<TileImage> tileImage;
    private Optional<MeepleImpl> meeple;
    private boolean locked;
    private final List<TileSectionType> tileSectionTypes;
    private final UserInterface ui;

    /**
     * Class constructor.
     * @param onClickActionListener action listener that specifies what to do in case of a click
     */
    public TileButtonImpl(final ActionListener onClickActionListener, final List<TileSectionType> tileSectionTypes, UserInterface ui) {
        this.ui = ui;
        this.tileSectionTypes = tileSectionTypes;
        this.locked = false;
        this.tileImage = Optional.empty();
        this.meeple = Optional.empty();
        this.button = new JButton() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                if (TileButtonImpl.this.containsTile() && isEnabled()) {
                    g.drawImage(TileButtonImpl.this.tileImage.get().getAsBufferedImage(this.getWidth(), this.getHeight()), 0, 0,
                            this.getWidth(),
                            this.getHeight(), null);
                }
            }

            @Override
            public void setEnabled(final boolean b) {
                if (b) {
                    this.addMouseListener(TileButtonImpl.this.mouseAdapter);
                } else {
                    this.removeMouseListener(TileButtonImpl.this.mouseAdapter);
                }

                super.setEnabled(b);
            }
        };
        this.button.addActionListener(onClickActionListener);
        this.button.setContentAreaFilled(false);
        this.button.setFocusable(false);
        this.button.setBorder(new LineBorder(Color.BLACK));
        this.mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                if (tileImage.isEmpty()) {
                    TileButtonImpl.this.button.setBorder(new LineBorder(Color.RED));
                }
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                TileButtonImpl.this.button.setBorder(new LineBorder(Color.BLACK));
            }
        };
        this.button.addMouseListener(this.mouseAdapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTile(final Tile tile) {
        this.tileImage = Optional.of(new TileImage(tile, tileSectionTypes, ui));
        this.button.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTileImage(final TileImage tileImage) {
        this.tileImage = Optional.of(tileImage);
        this.button.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTile() {
        this.tileImage = Optional.empty();
        this.button.repaint();
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
    public boolean isVisible() {
        return this.button.isVisible();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        this.button.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a JPanel which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
    public JButton getComponent() {
        return this.button;
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
    public void setMeeple(final MeepleImpl meeple) {
        this.meeple = Optional.of(meeple);
        this.tileImage.get().addMeeple(meeple);
        this.button.repaint();
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
        this.button.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MeepleImpl> getMeeple() {
        return this.meeple;
    }

}
