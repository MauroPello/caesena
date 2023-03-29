package it.unibo.caesena.view.components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.GameView;

import it.unibo.caesena.model.meeple.*;

public class RemainingMeeplesComponentImpl extends JPanel implements RemainingMeeplesComponent<JPanel>{
    
    GUI userInterface;

    JPanel allMeeplesPanel;
    List<Meeple> meeples;

    public RemainingMeeplesComponentImpl(final GameView gameView) {
        super();
        userInterface = gameView.getUserInterface();

        meeples = userInterface.getController().getPlayerMeeples(userInterface.getController().getCurrentPlayer());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel meepleLabel = new JLabel("Remaining Meeples: " + meeples.stream().filter(m -> !m.isPlaced()).count());
        meepleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(meepleLabel);

        this.allMeeplesPanel = new JPanel();
        allMeeplesPanel.setLayout(new GridLayout(1, meeples.size()));

        update();

        this.add(allMeeplesPanel);
        allMeeplesPanel.setVisible(true);
        this.setVisible(true);
    }

    @Override
    public void update() {
        allMeeplesPanel.removeAll();
        allMeeplesPanel.validate();
        allMeeplesPanel.repaint();
        
        for (Meeple meeple : meeples) {
            JPanel meeplePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics graphics) {
                    super.paintComponent(graphics);
                    Color color = userInterface.getPlayerColor(meeple.getOwner());
                    if (meeple.isPlaced()) {
                        color = Color.GRAY;
                    }
                    MeepleImage image = new MeepleImage(color);
                    image.resize(getWidth(), getHeight());
                    graphics.drawImage(image.getAsBufferedImage(), 0, 0, getWidth(), getHeight(), null);
                }
            };
            meeplePanel.setOpaque(false);
            allMeeplesPanel.add(meeplePanel);
        }
    }

    @Override
    public JPanel getComponent() {
        return this;
    }
}
