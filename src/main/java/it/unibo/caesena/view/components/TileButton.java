package it.unibo.caesena.view.components;

import java.awt.event.*;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.utils.Pair;

public class TileButton extends JButton {
    private static final String SEP = File.separator;
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "caesena" + SEP + "images" + SEP + "tiles" + SEP;
    private static final URL DEFAULT_IMAGE_PATH = ClassLoader.getSystemResource(ROOT + "tile-back.png");
    private final Pair<Integer, Integer> position;
    private final BoardComponentImpl parent;
    private ImageIcon icon;
    private boolean hasTile;
    private boolean hasMeeple;

    public TileButton(int x, int y, BoardComponentImpl parent) {
        super();
        this.icon = new ImageIcon(DEFAULT_IMAGE_PATH);
        this.hasTile = false;
        this.hasMeeple = false;
        this.position = new Pair<Integer,Integer>(x, y);
        this.parent = parent;
        this.addActionListener(this.OnSelection());
        this.setContentAreaFilled(false);
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void setActualTile(String imagePath) {
        setImage(imagePath);
        hasTile = true;
    }

    public void setMeeple() {
        hasMeeple = true;
    }

    private void setImage(String imagePath) {
        URL url = ClassLoader.getSystemResource(imagePath);
        this.icon = new ImageIcon(url);
    }

    public boolean containsTile(){
        return this.hasTile;
    }

    public boolean containsMeeple(){
        return this.hasMeeple;
    }

    //Courtesy of StackOverflow
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public void resize() {
        this.setIcon(resizeIcon(icon, this.getHeight(), this.getWidth()));
    }

    private ActionListener OnSelection() {
        return (e) -> {
            String imagePath = this.parent.getCurrentTileImageResourcePath();
            setImage(imagePath);
            this.resize();
            repaint();
        };
    }
}
