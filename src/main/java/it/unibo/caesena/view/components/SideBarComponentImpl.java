package it.unibo.caesena.view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.view.GameView;

public class SideBarComponentImpl extends JPanel implements SideBarComponent<JPanel>{

    JButton zoomInButton = new JButton("Zoom +");
    JButton zoomOutButton = new JButton("Zoom -");

    JButton upRowButton = new JButton("UP");
    JButton downRowButton = new JButton("DOWN");
    JButton leftRowButton = new JButton("LEFT");
    JButton rightRowButton = new JButton("RIGHT");

    JButton placeTileButton = new JButton("PLACE TILE");
    JButton placeMeepleButton = new JButton("PLACE MEEPLE");
    JButton discardTileButton = new JButton("DISCARD");
    JButton endTurnButton = new JButton("ENDTURN");

    Controller controller;
    GameView gameView;
    LeaderBoardComponent<JPanel> leaderBoard;

    public SideBarComponentImpl(final GameView gameView) {
        super();
        
        this.controller = gameView.getUserInterface().getController();
        this.gameView = gameView;
        this.leaderBoard = new LeaderBoardComponentImpl(controller);

        this.setLayout(new GridBagLayout());
        this.setBackground(java.awt.Color.BLACK);
        this.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        JPanel innerPanel = new JPanel();
        JPanel zoomPanel = new JPanel();
        JPanel arrowsPanel = new JPanel();
        JPanel centerArrowPanel = new JPanel();
        JPanel actionsPanel = new JPanel();
        
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        zoomPanel.setLayout(new BoxLayout(zoomPanel, BoxLayout.Y_AXIS));
        arrowsPanel.setLayout(new BoxLayout(arrowsPanel, BoxLayout.Y_AXIS));
        centerArrowPanel.setLayout(new BorderLayout());
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));

        innerPanel.setOpaque(false);
        zoomPanel.setOpaque(false);
        arrowsPanel.setOpaque(false);
        centerArrowPanel.setOpaque(false);
        actionsPanel.setOpaque(false);

        zoomPanel.add(zoomInButton);
        zoomPanel.add(zoomOutButton);
        zoomInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        arrowsPanel.add(upRowButton);
        centerArrowPanel.add(leftRowButton, BorderLayout.WEST);
        centerArrowPanel.add(rightRowButton, BorderLayout.EAST);
        arrowsPanel.add(centerArrowPanel);
        arrowsPanel.add(downRowButton);

        upRowButton.setAlignmentX(CENTER_ALIGNMENT);
        downRowButton.setAlignmentX(CENTER_ALIGNMENT);
        arrowsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        actionsPanel.add(placeTileButton);
        actionsPanel.add(discardTileButton);
        actionsPanel.add(placeMeepleButton);
        actionsPanel.add(endTurnButton);

        placeTileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        discardTileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        placeMeepleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        innerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        innerPanel.add(zoomPanel);
        innerPanel.add(arrowsPanel);
        actionsPanel.setBackground(Color.red);
        innerPanel.add(actionsPanel);
        innerPanel.add(leaderBoard.getComponent());
        leaderBoard.getComponent().setAlignmentX(Component.CENTER_ALIGNMENT);
        
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

    private <Optional>ActionListener zoomInEventListener() {
        return(e) -> this.gameView.zoomIn();
    }

    private ActionListener zoomOutEventListener() {
        return (e) -> this.gameView.zoomOut();
    }

    private ActionListener moveUpEventListener() {
        return (e) -> this.gameView.move(Direction.UP);
    }

    private ActionListener moveLeftEventListener() {
        return (e) -> this.gameView.move(Direction.LEFT);
    }

    private ActionListener moveDownEventListener() {
        return (e) -> this.gameView.move(Direction.DOWN);
    }

    private ActionListener moveRightEventListener() {
        return (e) -> this.gameView.move(Direction.RIGHT);
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
