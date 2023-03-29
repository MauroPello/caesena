package it.unibo.caesena.view.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.view.GameView;

public class SideBarComponentImpl extends JPanel implements SideBarComponent<JPanel>{

    JButton zoomInButton = new JButton("Zoom +");
    JButton zoomOutButton = new JButton("Zoom -");

    private JPanel arrowsPanel = new JPanel();
    private JPanel centerArrowPanel = new JPanel();

    JButton upRowButton = new JButton("UP");
    JButton downRowButton = new JButton("DOWN");
    JButton leftRowButton = new JButton("LEFT");
    JButton rightRowButton = new JButton("RIGHT");

    JButton placeTileButton = new JButton("PLACE TILE");
    JButton placeMeepleButton = new JButton("PLACE MEEPLE");
    JButton endTurnButton = new JButton("ENDTURN");
    JButton discardTileButton = new JButton("DISCARD");

    Controller controller;
    GameView gameView;
    LeaderBoardComponent<JPanel> leaderBoard;

    public SideBarComponentImpl(final GameView gameView) {
        super();
        JPanel innerPanel = new JPanel();
        this.controller = gameView.getUserInterface().getController();
        this.gameView = gameView;
        this.leaderBoard = new LeaderBoardComponentImpl(controller);

        this.setLayout(new GridBagLayout());
        this.setBackground(java.awt.Color.BLACK);
        this.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        arrowsPanel.setLayout(new BoxLayout(arrowsPanel, BoxLayout.Y_AXIS));
        centerArrowPanel.setLayout(new BorderLayout());

        innerPanel.setOpaque(false);
        arrowsPanel.setOpaque(false);
        centerArrowPanel.setOpaque(false);

        arrowsPanel.add(upRowButton);
        upRowButton.setAlignmentX(CENTER_ALIGNMENT);

        centerArrowPanel.add(leftRowButton, BorderLayout.WEST);
        centerArrowPanel.add(rightRowButton, BorderLayout.EAST);
        arrowsPanel.add(centerArrowPanel);

        arrowsPanel.add(downRowButton);
        downRowButton.setAlignmentX(CENTER_ALIGNMENT);

        innerPanel.add(zoomInButton);
        zoomInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(zoomOutButton);
        zoomOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(arrowsPanel);
        arrowsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(placeTileButton);
        placeTileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(discardTileButton);
        discardTileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(placeMeepleButton);
        placeMeepleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(endTurnButton);
        endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    private ActionListener zoomInEventListener() {
        return (e) -> this.gameView.zoomIn();
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
