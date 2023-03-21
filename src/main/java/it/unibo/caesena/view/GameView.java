package it.unibo.caesena.view;

import javax.swing.*;
import java.awt.*;
import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.view.components.BoardComponent;
import it.unibo.caesena.view.components.BoardComponentImpl;
import it.unibo.caesena.view.components.FooterComponent;
import it.unibo.caesena.view.components.FooterComponentImpl;
import it.unibo.caesena.view.components.LeaderBoardComponent;
import it.unibo.caesena.view.components.LeaderBoardComponentImpl;
import it.unibo.caesena.view.components.SideBarComponent;
import it.unibo.caesena.view.components.SideBarComponentImpl;

public class GameView extends View {
    private final Controller controller;
    private BoardComponent<JPanel> board;
    private FooterComponent<JPanel> footer;
    SideBarComponent<JPanel> sidebar;
    LeaderBoardComponent<JPanel> leaderBoard;

    public GameView(GUI userInterface) {
        super(userInterface);
        this.controller = getUserInterface().getController();
    }

    public void start() {
        this.controller.startGame();
        this.board = new BoardComponentImpl(controller);
        this.setLayout(new BorderLayout());
        this.footer = new FooterComponentImpl(controller);
        this.leaderBoard = new LeaderBoardComponentImpl(controller);
        this.sidebar = new SideBarComponentImpl(controller, board, footer, leaderBoard);
        this.add(sidebar.getComponent(), BorderLayout.EAST);
        this.add(board.getComponent(), BorderLayout.CENTER);
        this.add(footer.getComponent(), BorderLayout.SOUTH);
        sidebar.getComponent().add(this.leaderBoard.getComponent());
    }
}
