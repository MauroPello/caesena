package it.unibo.caesena.view.components;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JPanel;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.GameView;

import it.unibo.caesena.model.meeple.*;

public class RemainingMeeplesComponentImpl extends JPanel implements RemainingMeeplesComponent<JPanel>{

    
    GUI userInterface;

    JPanel innerPanel;
    List<Meeple> meeples = List.of();
    int meepleSize = 30;
    //TODO mettere a posto il meeplesize

    public RemainingMeeplesComponentImpl(final GameView gameView) {
        super();
        this.innerPanel = new JPanel();
        userInterface = gameView.getUserInterface();

        this.setBackground(java.awt.Color.RED);
        this.setPreferredSize(new Dimension(300,100));
        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets.right = 10;
        constraints.gridy = 0;
        constraints.gridx = 1;

        update();

        this.add(innerPanel, BorderLayout.CENTER);
        innerPanel.setVisible(true);
        this.setVisible(true);
    }

    @Override
    public void update() {
        innerPanel.removeAll();
        innerPanel.validate();
        innerPanel.repaint();
        for (Meeple meeple : userInterface.getController().getNotPlacedPlayerMeeples(userInterface.getController().getCurrentPlayer())) {
            JPanel ciao = new JPanel(){
                @Override
                protected void paintComponent(Graphics graphics) {
                    super.paintComponent(graphics);
                    MeepleImage prova = new MeepleImage(userInterface.getPlayerColor(meeple.getOwner()));
                    meepleSize = 30;
                    prova.resize(meepleSize, meepleSize);
                    graphics.drawImage(prova.getAsBufferedImage(), 0, 0, meepleSize, meepleSize, null);
                }
            };

            ciao.setMaximumSize(new Dimension(meepleSize, meepleSize));
            ciao.setMinimumSize(new Dimension(meepleSize, meepleSize));
            ciao.setPreferredSize(new Dimension(meepleSize, meepleSize));
            innerPanel.add(ciao);
        }
        innerPanel.setBackground(Color.white);
        innerPanel.setPreferredSize(new Dimension(280, 90));
    }

    @Override
    public JPanel getComponent() {
        return this;
    }
}
