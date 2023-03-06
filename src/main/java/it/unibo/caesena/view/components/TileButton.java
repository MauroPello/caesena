package it.unibo.caesena.view.components;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.caesena.utils.Pair;

public class TileButton extends JButton {
    private final Pair<Integer, Integer> position;
    private ImageIcon image;

    public TileButton(int x, int y) {
        super();
        this.position = new Pair<Integer,Integer>(x, y);
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void setImage(String imagePath) {
        image = new ImageIcon(imagePath);
        super.setIcon(image);
    }
}
