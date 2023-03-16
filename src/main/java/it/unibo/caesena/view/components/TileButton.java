package it.unibo.caesena.view.components;

import java.awt.event.*;
import java.awt.Image;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;

public class TileButton extends JButton {
    private static final String SEP = File.separator;
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "caesena" + SEP + "images" + SEP + "tiles" + SEP;
    private static final URL DEFAULT_IMAGE_PATH = ClassLoader.getSystemResource(ROOT + "TILE_BACK.png");
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
        this.addComponentListener(this.OnResizeOrShown());
        this.setContentAreaFilled(false);
        this.setFocusable(false);
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void addTile(Tile tile) {
        this.containedTile = Optional.of(tile);
        redraw();
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

    private void redraw() {
        String imagePath = "";

        if (containedTile.isPresent()) {
            imagePath = containedTile.get().getImageResourcesPath();
        } else {
            try {
                imagePath = Paths.get(DEFAULT_IMAGE_PATH.toURI()).toString();
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        double angle = this.containedTile.isPresent() ? 90 * containedTile.get().getRotationCount() : 0;
        ImageIcon icon = rotateImageIcon(new ImageIcon(imagePath), angle);

        if (this.getHeight()!=0 && this.getWidth()!=0 ) {
            this.setIcon(resizeIcon(icon, this.getHeight(), this.getWidth()));
        } else {
            this.setIcon(icon);
        }
    }

    public Tile getConteinedTile() {
        return containedTile.orElseThrow(() -> new IllegalStateException("tried to get contained tile but there was none"));
    }

    public void addMeeple() {
        //TODO implementa
    }

    public void removeTile() {
        this.containedTile = Optional.empty();
        redraw();
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

    // https://stackoverflow.com/questions/36957450/fit-size-of-an-imageicon-to-a-jbutton
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // https://coderanch.com/t/467131/java/Rotating-ImageIcon
    static private ImageIcon rotateImageIcon(ImageIcon picture, double angle) {
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

        return picture;
    }

    private ComponentListener OnResizeOrShown() {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                TileButton tileButton = (TileButton)e.getSource();
                tileButton.redraw();
            }
            @Override
            public void componentShown(ComponentEvent e) {
                TileButton tileButton = (TileButton)e.getSource();
                tileButton.redraw();
            }
        };
    }
}
