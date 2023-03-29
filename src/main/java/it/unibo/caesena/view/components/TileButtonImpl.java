package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JButton;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.view.GameView;

public class TileButtonImpl extends JButton implements TileButton<JButton> {
    private final GameView gameView;
    private Optional<Tile> containedTile;
    private Optional<Meeple> meeple;
    private TileSection section;
    private Color currentPlayerColor;

    public TileButtonImpl(ActionListener onClickActionListener, GameView gameView) {
        super();
        this.gameView = gameView;
        this.containedTile = Optional.empty();
        this.addActionListener(onClickActionListener);
        this.setContentAreaFilled(false);
        this.setFocusable(false);
    }

    @Override
    public void addTile(Tile tile) {
        this.containedTile = Optional.of(tile);
        this.meeple = Optional.empty();
        Controller controller = this.gameView.getUserInterface().getController();
        Player currentPlayer = controller.getCurrentPlayer();
        this.currentPlayerColor = gameView.getUserInterface().getPlayerColor(currentPlayer);
    }

    @Override
    public Tile getContainedTile() {
        return containedTile.orElseThrow(() -> new IllegalStateException("tried to get contained tile but there was none"));
    }

    @Override
    public void removeTile() {
        this.containedTile = Optional.empty();
    }

    @Override
    public boolean containsTile() {
        return this.containedTile.isPresent();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (this.containsTile()) {
            TileImage tileImage = new TileImage(getContainedTile(), currentPlayerColor);
            if (this.containsMeeple())  {
                tileImage.addMeeple(this.meeple.get(), this.section);
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

    @Override
    public void addMeeple(Meeple meeple, TileSection section) {
        this.meeple = Optional.of(meeple);
        this.section = section;
    }

    private boolean containsMeeple() {
        if (meeple.isPresent() && !meeple.get().isPlaced()) {
            meeple = Optional.empty();
        }
        return meeple.isPresent();
    }
}
