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
import it.unibo.caesena.view.components.SideBarComponent;
import it.unibo.caesena.view.components.SideBarComponentImpl;

import java.awt.event.*;

public class GameView extends View {
    private final Controller controller;
    private BoardComponent<JPanel> board;
    private FooterComponent<JPanel> footer;
    SideBarComponent<JPanel> sidebar;

    public GameView(GUI userInterface) {
        super(userInterface);
        this.controller = getUserInterface().getController();
    }

    public void start() {
        this.controller.startGame();
        this.board = new BoardComponentImpl(controller);
        this.setLayout(new BorderLayout());
        this.footer = new FooterComponentImpl(controller);
        this.sidebar = new SideBarComponentImpl(controller, board, footer);
        this.add(sidebar.getComponent(), BorderLayout.EAST);
        this.add(board.getComponent(), BorderLayout.CENTER);
        this.add(footer.getComponent(), BorderLayout.SOUTH);
    }
}
