package it.unibo.caesena.view.components;

import javax.swing.JPanel;

import it.unibo.caesena.model.tile.Tile;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.event.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

import javax.swing.JButton;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.Pair;

public class OverlayedTileComponent extends JPanel{
    private final Tile currentTile;

    public OverlayedTileComponent(Tile tile, Dimension dimension) {
        super();
        this.currentTile = tile;
        this.setSize(dimension);

        this.addComponentListener(this.OnResizeOrShown());
        redraw();

        this.setVisible(true);
        this.validate();
        this.repaint();
    }


    private void redraw() {
        this.removeAll();
        JLabel thumb = new JLabel();
        double angle = 90 * this.currentTile.getRotationCount();
        ImageIcon icon = rotateImageIcon(new ImageIcon(this.currentTile.getImageResourcesPath()), angle);

        if (this.getHeight()!=0 && this.getWidth()!=0 ) {
            icon = resizeIcon(icon, (this.getHeight()), (this.getWidth()));
        }

        thumb.setIcon(icon);
        this.add(thumb);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }

    // https://stackoverflow.com/questions/36957450/fit-size-of-an-imageicon-to-a-jbutton
    private static ImageIcon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
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
                OverlayedTileComponent tile = (OverlayedTileComponent)e.getSource();
                tile.redraw();
            }
            @Override
            public void componentShown(ComponentEvent e) {
                OverlayedTileComponent tile = (OverlayedTileComponent)e.getSource();
                tile.redraw();
            }
        };
    }
}
