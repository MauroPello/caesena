package it.unibo.caesena.view.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
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
            Image image;
            double angle = 90 * containedTile.get().getRotationCount();
            image = rotateImageIcon(new ImageIcon(containedTile.get().getImageResourcesPath()), angle);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }

    // https://coderanch.com/t/467131/java/Rotating-ImageIcon
    static private BufferedImage rotateImageIcon(ImageIcon picture, double angle) {
        int w = picture.getIconWidth();
        int h = picture.getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;  // other options, see api
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g2 = image.createGraphics();
        double x = (h - w)/2.0;
        double y = (w - h)/2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(angle), w/2.0, h/2.0);
        g2.drawImage(picture.getImage(), at, null);
        g2.dispose();
        picture = new ImageIcon(image);

        return image;
    }
}
