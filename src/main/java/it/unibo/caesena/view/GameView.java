package it.unibo.caesena.view;

import javax.swing.*;
import java.awt.*;
import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Color;
import it.unibo.caesena.view.components.BoardComponent;
import it.unibo.caesena.view.components.BoardComponentImpl;
import java.awt.event.*;

public class GameView extends View {
    private final Controller controller;
    private final BoardComponent<JPanel> board;

    public GameView(GUI userInterface) {
        super(userInterface);
        this.controller = getUserInterface().getController();
        this.gameSetupDebug();
        this.controller.startGame();
        Tile firstTile = controller.getCurrentTile();
        this.board = new BoardComponentImpl(controller, firstTile);


        this.setLayout(new BorderLayout());
        this.add(board.getComponent(), BorderLayout.CENTER);
        this.add(getTableTop(), BorderLayout.SOUTH);
        this.add(getMapControls(), BorderLayout.EAST);
   }

    private void gameSetupDebug() {
        Color color1 = Color.createColor("FF0000", "Red");
        Color color2 = Color.createColor("00FF00", "Green");
        this.controller.addPlayer("Giocatore1", color1);
        this.controller.addPlayer("Giocatore2", color2);
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

        OuterPanel.add(innerPanel);
        return OuterPanel;
    }

    //TODO implementare component getTableTop
    private Component getTableTop() {
        return new JButton("South");
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
