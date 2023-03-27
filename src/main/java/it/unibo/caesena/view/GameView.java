package it.unibo.caesena.view;

import javax.swing.*;
import java.awt.*;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.BoardComponent;
import it.unibo.caesena.view.components.BoardComponentImpl;
import it.unibo.caesena.view.components.FooterComponent;
import it.unibo.caesena.view.components.FooterComponentImpl;
import it.unibo.caesena.view.components.SideBarComponent;
import it.unibo.caesena.view.components.SideBarComponentImpl;

public class GameView extends JPanel implements View<JPanel> {

    private final GUI userInterface;
    private BoardComponent<JPanel> board;
    private FooterComponent<JPanel> footer;
    private SideBarComponent<JPanel> sidebar;

    public GameView(GUI userInterface) {
        super();
        this.userInterface = userInterface;

        this.userInterface.getController().startGame();
        this.board = new BoardComponentImpl(this);
        this.setLayout(new BorderLayout());
        this.footer = new FooterComponentImpl(this);
        this.sidebar = new SideBarComponentImpl(this);
        this.add(sidebar.getComponent(), BorderLayout.EAST);
        this.add(board.getComponent(), BorderLayout.CENTER);
        this.add(footer.getComponent(), BorderLayout.SOUTH);
    }

    public void updateHUD() {
        this.footer.updateFooter();
        this.sidebar.update();
    }

    public void placeMeeple() {
        board.toggleBoardContent();
        updateHUD();
    }

    public boolean placeTile() {
        Pair<Integer, Integer> position = board.getCurrentlySelectedTileButton().getPosition();
        if (this.userInterface.getController().placeCurrentTile(position)) {
            this.board.lockTile();
            updateHUD();
            return true;
        }
        return false;
    }

    public void endTurn() {
        this.board.endTurn();
        this.userInterface.getController().endTurn();
        if (this.userInterface.getController().isGameOver()) {
            userInterface.showGameOverView();
        } else {
            updateHUD();
        }
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

    public void removePlacedTile() {
        this.board.removePlacedTile();
    }

    public void updateComponents() {
        this.board.updateComponents();
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    public GUI getUserInterface() {
        return this.userInterface;
    }
}
