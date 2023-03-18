package it.unibo.caesena.view.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;

public class SectionSelectorComponentImpl extends JPanel implements SectionSelectorComponent {
    private final Tile currentTile;
    private Optional<TileSection> currentSectionSelected = Optional.empty();

    public SectionSelectorComponentImpl(Tile tile, Dimension dimension) {
        super();
        this.currentTile = tile;
        this.setSize(dimension);
        redraw();
        this.setVisible(true);
    }

    @Override
    public TileSection getSelectedSection() {
        return currentSectionSelected.orElseThrow(()->new IllegalStateException("Tried to get section but none was selected"));
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        double angle = 90 * this.currentTile.getRotationCount();
        Image image = rotateImageIcon(new ImageIcon(this.currentTile.getImageResourcesPath()), angle);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    private void redraw() {
        this.removeAll();
        this.drawSections();
        this.validate();
        this.repaint();
    }

    private void drawSections() {

        for (TileSection section : TileSection.values()) {
            JButton button = new SectionButton(section);
            this.add(button);
        }
    }

    private String getLabelFromSection(GameSet gameSet) {
        return gameSet.getType().name();
    }

    private class SectionButton extends JButton {
        private final TileSection section;

        public SectionButton(TileSection section) {
            super();
            this.section = section;
            String buttonLabel = getLabelFromSection(currentTile.getGameSet(section));
            this.setText(buttonLabel);
            this.addActionListener((e) ->
            {
                currentSectionSelected = Optional.of(this.section);
            });
        }
    }
    // https://coderanch.com/t/467131/java/Rotating-ImageIcon
    static private BufferedImage rotateImageIcon(ImageIcon picture, double angle) {
        int w = picture.getIconWidth();
        int h = picture.getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g2 = image.createGraphics();
        double x = (h - w)/2.0;
        double y = (w - h)/2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(angle), w/2.0, h/2.0);
        g2.drawImage(picture.getImage(), at, null);
        BufferedImage overlay = new BufferedImage(h, w, type);

        //TODO imbellisci sto schifo
        String SEP = File.separator;
        String ROOT = "it" + SEP + "unibo" + SEP + "caesena" + SEP + "images" + SEP + "tiles" + SEP;
        try {
            overlay = ImageIO.read(ClassLoader.getSystemResource(ROOT + "tileOverlay.png"));
        } catch (IOException e) {
        }


        g2.drawImage(overlay, at, null);
        g2.dispose();
        picture = new ImageIcon(image);

        return image;
    }
}
