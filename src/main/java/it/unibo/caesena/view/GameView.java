package it.unibo.caesena.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.components.FooterComponent;
import it.unibo.caesena.view.components.FooterComponentImpl;
import it.unibo.caesena.view.components.MainComponent;
import it.unibo.caesena.view.components.MainComponentImpl;
import it.unibo.caesena.view.components.SideBarComponent;
import it.unibo.caesena.view.components.SideBarComponentImpl;
import it.unibo.caesena.view.components.TileButton;
import it.unibo.caesena.view.components.TileImage;

public class GameView extends JPanel implements View<JPanel> {

    private final GUI userInterface;
    private MainComponent<JPanel> mainComponent;
    private FooterComponent<JPanel> footer;
    private SideBarComponent<JPanel> sidebar;

    public GameView(final GUI userInterface) {
        super();
        this.userInterface = userInterface;

        this.userInterface.getController().startGame();
        this.mainComponent = new MainComponentImpl(this);
        this.setLayout(new BorderLayout());
        this.footer = new FooterComponentImpl(this);
        this.sidebar = new SideBarComponentImpl(this);
        this.add(sidebar.getComponent(), BorderLayout.EAST);
        this.add(mainComponent.getComponent(), BorderLayout.CENTER);
        this.add(footer.getComponent(), BorderLayout.SOUTH);
    }

    public final void updateHUD() {
        this.footer.updateFooter();
        this.sidebar.update();
    }

    public TileImage getCurrentTileImage() {
        return this.footer.getCurrentTileImage();
    }

    public void placeMeeple() {
        mainComponent.toggleComponents();
        updateHUD();
    }

    public boolean placeTile() {
        TileButton<JButton> placeTileButton = mainComponent.getBoard().getPlacedTileButton();
        Pair<Integer, Integer> position = mainComponent.getBoard().getTileButtonPosition(placeTileButton);
        if (this.userInterface.getController().placeCurrentTile(position)) {
            updateHUD();
            return true;
        }
        return false;
    }

    public void endTurn() {
        this.mainComponent.endTurn();
        this.userInterface.getController().endTurn();
        if (this.userInterface.getController().isGameOver()) {
            userInterface.showGameOverView();
        } else {
            updateHUD();
        }
    }

    public void zoomIn() {
        this.mainComponent.getBoard().zoomIn();
    }

    public void zoomOut() {
        this.mainComponent.getBoard().zoomOut();
    }

    public void move(Direction direction) {
        this.mainComponent.getBoard().move(direction);
    }

    public boolean canZoomIn() {
        return this.mainComponent.getBoard().canZoomIn();
    }

    public boolean canZoomOut() {
        return this.mainComponent.getBoard().canZoomOut();
    }
    
    public boolean canMove(Direction direction) {
        return this.mainComponent.getBoard().canMove(direction);
    }

    public void removePlacedTile() {
        this.mainComponent.getBoard().removePlacedTile();
    }

    public void updateComponents() {
        this.mainComponent.getBoard().draw();
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
