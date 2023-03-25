package it.unibo.caesena.view;

import javax.swing.*;
import java.awt.*;
import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.BoardComponent;
import it.unibo.caesena.view.components.BoardComponentImpl;
import it.unibo.caesena.view.components.FooterComponent;
import it.unibo.caesena.view.components.FooterComponentImpl;
import it.unibo.caesena.view.components.SideBarComponent;
import it.unibo.caesena.view.components.SideBarComponentImpl;

public class GameView extends JPanel implements View<JPanel> {
    private final Controller controller;
    private BoardComponent<JPanel> board;
    private FooterComponent<JPanel> footer;
    private SideBarComponent<JPanel> sidebar;
    GUI userInterface;

    public GameView(GUI userInterface) {
        super();
        this.controller = userInterface.getController();
        this.userInterface = userInterface;

        this.controller.startGame();
        this.board = new BoardComponentImpl(userInterface);
        this.setLayout(new BorderLayout());
        this.footer = new FooterComponentImpl(userInterface);
        this.sidebar = new SideBarComponentImpl(controller, this);
        this.add(sidebar.getComponent(), BorderLayout.EAST);
        this.add(board.getComponent(), BorderLayout.CENTER);
        this.add(footer.getComponent(), BorderLayout.SOUTH);
    }

    public void updateHUD() {
        this.footer.updateFooter();
        this.sidebar.update();
    }

    public void placeMeeple() {
        board.placeMeeple(board.getCurrentlySelectedTileButton().getContainedTile());
        updateHUD();
    }

    public void placeTile() {
        Pair<Integer, Integer> position = board.getCurrentlySelectedTileButton().getPosition();
        if (this.controller.isValidPositionForCurrentTile(position)) {
            this.controller.placeCurrentTile(position);
            this.board.lockTile();
        } else {
            throw new IllegalStateException("Tried to place tile in a not valid position");
        }
        updateHUD();
    }

    public void endTurn() {
        this.board.endTurn();
        this.controller.endTurn();
        updateHUD();
    }

    public void zoomIn() {
        this.board.zoomIn();
    }

    public void zoomOut() {
        this.board.zoomOut();
    }

    public void moveUp() {
        this.board.moveUp();
    }

    public void moveLeft() {
        this.board.moveLeft();
    }

    public void moveDown() {
        this.board.moveDown();
    }

    public void moveRight() {
        this.board.moveRight();
    }

    @Override
    public JPanel getComponent() {
        return this;
    }
}
