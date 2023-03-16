package it.unibo.caesena.view.components;

import java.awt.event.*;
import java.awt.Image;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

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
    private Optional<Tile> containedTile;//TODO considerare la possibilità di avere un implementazione di tile e meeple che contenga solo cose inerenti alla view
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

        ImageIcon icon = new ImageIcon(imagePath);
        if (this.getHeight()!=0 && this.getWidth()!=0 ) {
            this.setIcon(resizeIcon(icon, this.getHeight(), this.getWidth()));
        } else {
            this.setIcon(icon);
        }
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

    //Courtesy of StackOverflow
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
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
