package it.unibo.caesena.view;

import javax.swing.*;
import java.awt.*;
import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.BoardComponent;
import it.unibo.caesena.view.components.BoardComponentImpl;
import it.unibo.caesena.view.components.FooterComponent;
import it.unibo.caesena.view.components.FooterComponentImpl;

import java.awt.event.*;

public class GameView extends View {
    private final Controller controller;
    private BoardComponent<JPanel> board;
    private FooterComponent<JPanel> footer;

    public GameView(GUI userInterface) {
        super(userInterface);
        this.controller = getUserInterface().getController();
    }

    public void start() {
        this.controller.startGame();
        this.board = new BoardComponentImpl(controller);
        this.setLayout(new BorderLayout());
        this.footer = new FooterComponentImpl(controller);
        this.add(board.getComponent(), BorderLayout.CENTER);
        this.add(footer.getComponent(), BorderLayout.SOUTH);
        this.add(getMapControls(), BorderLayout.EAST);
    }

    //TODO implementare component getMapControls
    private Component getMapControls() {
        JPanel OuterPanel = new JPanel();
        JPanel innerPanel = new JPanel();

        OuterPanel.setBackground(java.awt.Color.BLACK);
        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 1;

        JButton zoomInButton = new JButton("ZOOM +");
        JButton zoomOutButton = new JButton("ZOOM -");
        JButton moveUpButton = new JButton("UP");
        JButton moveLeftButton = new JButton("LEFT");
        JButton moveRightButton = new JButton("RIGHT");
        JButton moveDownButton = new JButton("DOWN");
        JButton placeTile = new JButton("PLACETILE");
        JButton placeMeeple = new JButton("PLACEMEEPLE");
        JButton endTurnButton = new JButton("ENDTURN");

        innerPanel.add(zoomInButton, constraints);
        constraints.gridy ++;
        innerPanel.add(zoomOutButton, constraints);
        constraints.gridy ++;
        innerPanel.add(moveUpButton, constraints);
        constraints.gridy ++;
        constraints.gridx --;
        innerPanel.add(moveLeftButton, constraints);
        constraints.gridx ++;
        constraints.gridx ++;
        innerPanel.add(moveRightButton, constraints);
        constraints.gridx --;
        constraints.gridy ++;
        innerPanel.add(moveDownButton, constraints);
        constraints.gridy ++;
        innerPanel.add(placeTile, constraints);
        constraints.gridy ++;
        innerPanel.add(placeMeeple, constraints);
        constraints.gridy ++;
        innerPanel.add(endTurnButton, constraints);
        constraints.gridy ++;

        zoomInButton.addActionListener(zoomInEventListener());
        zoomOutButton.addActionListener(zoomOutEventListener());
        moveUpButton.addActionListener(moveUpEventListener());
        moveLeftButton.addActionListener(moveLeftEventListener());
        moveDownButton.addActionListener(moveDownEventListener());
        moveRightButton.addActionListener(moveRightEventListener());
        placeTile.addActionListener(placeTileEventListener());
        placeMeeple.addActionListener(placeMeepleEventListener());
        endTurnButton.addActionListener(endTurnEventListener());

        OuterPanel.add(innerPanel);
        return OuterPanel;
    }

    private ActionListener placeMeepleEventListener() {
        return (e) -> {
            Tile tile = board.getCurrentlySelectedTileButton().getConteinedTile();
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
            this.controller.endTurn();
            footer.updateCurrentTile();
            board.endTurn();
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
}
