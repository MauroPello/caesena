package it.unibo.caesena.view.components;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;

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

    Controller controller;
    BoardComponent<JPanel> board;
    FooterComponent<JPanel> footer;
    LeaderBoardComponent<JPanel> leaderBoard;

    public SideBarComponentImpl(final Controller controller, final BoardComponent<JPanel> board, final FooterComponent<JPanel> footer, final LeaderBoardComponent<JPanel> leaderBoard) {
        super();
        JPanel innerPanel = new JPanel();
        this.board = board;
        this.footer = footer;
        this.controller = controller;
        this.leaderBoard = leaderBoard;

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
    }

    @Override
    public JPanel getComponent() {
       return this;
    }

    private ActionListener placeMeepleEventListener() {
        return (e) -> {
            Tile tile = board.getCurrentlySelectedTileButton().getContainedTile();
            board.placeMeeple(tile);
        };
    }

    private ActionListener placeTileEventListener() {
        return (e) -> {
            Pair<Integer, Integer> position = board.getCurrentlySelectedTileButton().getPosition();
            if (this.controller.isValidPositionForCurrentTile(position)) {
                this.controller.placeCurrentTile(position);
                this.board.lockTile();
            } else {
                throw new IllegalStateException("Tried to place tile in a not valid position");
            }
        };
    }

    private ActionListener endTurnEventListener() {
        return (e) -> {
            this.board.endTurn();
            this.controller.endTurn();
            this.footer.updateFooter();
            this.leaderBoard.updateLeaderBoard();
        };
    }

    public ActionListener zoomInEventListener() {
        return (e) -> this.board.zoomIn();
    }

    public ActionListener zoomOutEventListener() {
        return (e) -> this.board.zoomOut();
    }

    public ActionListener moveUpEventListener() {
        return (e) -> this.board.moveUp();
    }

    public ActionListener moveLeftEventListener() {
        return (e) -> this.board.moveLeft();
    }

    public ActionListener moveDownEventListener() {
        return (e) -> this.board.moveDown();
    }

    public ActionListener moveRightEventListener() {
        return (e) -> this.board.moveRight();
    }

    /*
     * pulsante zoom
     * pulsante dezoom
     * 
     * pulsante conferma
     * pulsante fine turno
     */
}
