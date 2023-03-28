package it.unibo.caesena.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.BoardComponent;
import it.unibo.caesena.view.components.BoardComponentImpl;
import it.unibo.caesena.view.components.FooterComponent;
import it.unibo.caesena.view.components.FooterComponentImpl;
import it.unibo.caesena.view.components.SideBarComponent;
import it.unibo.caesena.view.components.SideBarComponentImpl;

public class GameView extends JPanel implements View<JPanel> {

    private final GUI userInterface;
    private final BoardComponent<JPanel> board;
    private final FooterComponent<JPanel> footer;
    private final SideBarComponent<JPanel> sidebar;

    public GameView(final GUI userInterface) {
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

    public final void updateHUD() {
        this.footer.updateFooter();
        this.sidebar.update();
    }

    public final void placeMeeple() {
        board.toggleBoardContent();
        updateHUD();
    }

    public final boolean placeTile() {
        final Pair<Integer, Integer> position = board.getCurrentlySelectedTileButton().getPosition();
        if (this.userInterface.getController().placeCurrentTile(position)) {
            this.board.lockTile();
            updateHUD();
            return true;
        }
        return false;
    }

    public final void endTurn() {
        this.board.endTurn();
        this.userInterface.getController().endTurn();
        if (this.userInterface.getController().isGameOver()) {
            userInterface.showGameOverView();
        } else {
            updateHUD();
        }
    }

    public final void zoomIn() {
        this.board.zoomIn();
    }

    public final void zoomOut() {
        this.board.zoomOut();
    }

    public final void move(final Direction direction) {
        this.board.move(direction);
    }

    public final boolean canMove(final Direction direction) {
        return this.board.canMove(direction);
    }

    public final void removePlacedTile() {
        this.board.removePlacedTile();
    }

    public final void updateComponents() {
        this.board.updateComponents();
    }

    @Override
    public final JPanel getComponent() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final GUI getUserInterface() {
        return this.userInterface;
    }
}
