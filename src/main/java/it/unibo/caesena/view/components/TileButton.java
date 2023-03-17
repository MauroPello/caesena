package it.unibo.caesena.view.components;

import java.awt.event.*;
import java.awt.Image;
import java.util.Optional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;

public class TileButton extends JButton {
    // private static final String SEP = File.separator;
    // private static final String ROOT = "it" + SEP + "unibo" + SEP + "caesena" + SEP + "images" + SEP + "tiles" + SEP;
    // private static final URL DEFAULT_IMAGE_PATH = ClassLoader.getSystemResource(ROOT + "TILE_BACK.png");
    private final Pair<Integer, Integer> position;
    private Optional<Tile> containedTile;
    private Optional<Meeple> placedMeeple;
    private boolean locked = false;


    public TileButton(int x, int y, ActionListener onSelection) {
        super();
        this.containedTile = Optional.empty();
        this.placedMeeple = Optional.empty();
        this.position = new Pair<Integer,Integer>(x, y);
        this.addActionListener(onSelection);
        this.setContentAreaFilled(false);
        this.setFocusable(false);
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

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void addTile(Tile tile) {
        this.containedTile = Optional.of(tile);
    }

    public void lockTile() {
        if (containedTile.isPresent()) {
            locked = true;
        } else {
            throw new IllegalStateException("Can't lock tile since it's not present");
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public Tile getConteinedTile() {
        return containedTile.orElseThrow(() -> new IllegalStateException("tried to get contained tile but there was none"));
    }

    public void addMeeple() {
        //TODO implementa
    }

    public void removeTile() {
        this.containedTile = Optional.empty();
    }

    public void removeMeeple() {
        //TODO implementa
    }

    public boolean containsTile() {
        return this.containedTile.isPresent();
    }

    public boolean containsMeeple(){
        return this.placedMeeple.isPresent();
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
