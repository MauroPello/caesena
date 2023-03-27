package it.unibo.caesena.view.components;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.view.GameView;

public class SideBarComponentImpl extends JPanel implements SideBarComponent<JPanel>{

    JButton zoomInButton = new JButton("Zoom +");
    JButton zoomOutButton = new JButton("Zoom -");

    private JPanel arrowsPanel = new JPanel();
    JButton upRowButton = new JButton("UP");
    JButton downRowButton = new JButton("DOWN");
    JButton leftRowButton = new JButton("LEFT");
    JButton rightRowButton = new JButton("RIGHT");

    //JButton nextStep = new JButton("");
    //JButton EndTurn = new JButton("");
    JButton placeTileButton = new JButton("PLACE TILE");
    JButton placeMeepleButton = new JButton("PLACE MEEPLE");
    JButton endTurnButton = new JButton("ENDTURN");
    JButton discardTileButton = new JButton("DISCARD");

    Controller controller;
    GameView gameView;
    LeaderBoardComponent<JPanel> leaderBoard;

    public SideBarComponentImpl(final Controller controller, final GameView gameView) {
        super();
        JPanel innerPanel = new JPanel();
        this.controller = controller;
        this.gameView = gameView;
        this.leaderBoard = new LeaderBoardComponentImpl(controller);

        this.setBackground(java.awt.Color.BLACK);
        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 1;

        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        arrowsPanel.setLayout(new BorderLayout());

        arrowsPanel.add(upRowButton, BorderLayout.NORTH);
        arrowsPanel.add(leftRowButton, BorderLayout.WEST);
        arrowsPanel.add(rightRowButton, BorderLayout.EAST);
        arrowsPanel.add(downRowButton, BorderLayout.SOUTH);

        innerPanel.add(zoomInButton, constraints);
        constraints.gridy ++;
        innerPanel.add(zoomOutButton, constraints);
        constraints.gridy ++;
        innerPanel.add(arrowsPanel, constraints);
        constraints.gridy ++;
        innerPanel.add(placeTileButton, constraints);
        constraints.gridy ++;
        innerPanel.add(placeMeepleButton, constraints);
        constraints.gridy ++;
        innerPanel.add(endTurnButton, constraints);
        constraints.gridy ++;
        innerPanel.add(leaderBoard.getComponent(), constraints);
        constraints.gridy ++;
        innerPanel.add(discardTileButton, constraints);

        this.add(innerPanel);

        this.setVisible(true);
        innerPanel.setVisible(true);

        zoomInButton.addActionListener(zoomInEventListener());
        zoomOutButton.addActionListener(zoomOutEventListener());
        upRowButton.addActionListener(moveUpEventListener());
        leftRowButton.addActionListener(moveLeftEventListener());
        downRowButton.addActionListener(moveDownEventListener());
        rightRowButton.addActionListener(moveRightEventListener());
        placeTileButton.addActionListener(placeTileEventListener());
        placeMeepleButton.addActionListener(placeMeepleEventListener());
        endTurnButton.addActionListener(endTurnEventListener());
        discardTileButton.addActionListener(discardTileEventListener());

        placeMeepleButton.setVisible(false);
        endTurnButton.setVisible(false);
    }

    
    /** 
     * @return JPanel
     */
    @Override
    public JPanel getComponent() {
       return this;
    }

    
    /** 
     * @return ActionListener
     */
    private ActionListener placeMeepleEventListener() {
        return (e) -> this.gameView.placeMeeple();
    }

    
    /** 
     * @return ActionListener
     */
    private ActionListener placeTileEventListener() {
        return (e) -> {
            if(this.gameView.placeTile()) {
                placeTileButton.setVisible(false);
                placeMeepleButton.setVisible(true);
                endTurnButton.setVisible(true);
                discardTileButton.setVisible(false);
            }
        };
    }

    
    /** 
     * @return ActionListener
     */
    private ActionListener endTurnEventListener() {
        return (e) -> {
            this.gameView.endTurn();
            placeTileButton.setVisible(true);
            placeMeepleButton.setVisible(false);
            endTurnButton.setVisible(false);
            discardTileButton.setVisible(true);
        };
    }

    private ActionListener zoomInEventListener() {
        return (e) -> this.gameView.zoomIn();
    }

    private ActionListener zoomOutEventListener() {
        return (e) -> this.gameView.zoomOut();
    }

    private ActionListener moveUpEventListener() {
        return (e) -> this.gameView.moveUp();
    }

    private ActionListener moveLeftEventListener() {
        return (e) -> this.gameView.moveLeft();
    }

    private ActionListener moveDownEventListener() {
        return (e) -> this.gameView.moveDown();
    }

    private ActionListener moveRightEventListener() {
        return (e) -> this.gameView.moveRight();
    }

    private ActionListener discardTileEventListener() {
        return (e) -> {
            if(controller.discardCurrentTile()) {
                gameView.updateHUD();
            } else {
                ((JButton)e.getSource()).setEnabled(false);
            }
        };
    }

    @Override
    public void update() {
        this.leaderBoard.updateLeaderBoard();
    }
}
