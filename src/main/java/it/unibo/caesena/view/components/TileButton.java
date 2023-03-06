package it.unibo.caesena.view.components;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import it.unibo.caesena.utils.Pair;

public class TileButton extends JButton {
    private final Pair<Integer, Integer> position;
    private ImageIcon image;
    private boolean hasTile;
    private boolean hasMeeple;

    public TileButton(int x, int y) {
        super();
        this.hasTile = false;
        this.hasMeeple = false;
        this.position = new Pair<Integer,Integer>(x, y);
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
        final ImageIcon icon = new ImageIcon(url);
        this.setIcon(icon);
    }

    public boolean containsTile(){
        return this.hasTile;
    }

    public boolean containsMeeple(){
        return this.hasTile;
    }
}
