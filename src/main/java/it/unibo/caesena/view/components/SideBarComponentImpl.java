package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.view.GameView;
import it.unibo.caesena.view.LocaleHelper;

public class SideBarComponentImpl extends JPanel implements SideBarComponent<JPanel>{
    
    private Controller controller;
    private GameView gameView;

    private JButton zoomInButton = new JButton("Zoom +");
    private JButton zoomOutButton = new JButton("Zoom -");

    private JButton upRowButton = new JButton("|");
    private JButton downRowButton = new JButton("|");
    private JButton leftRowButton = new JButton("<-");
    private JButton rightRowButton = new JButton("->");

    private JButton placeTileButton = new JButton(LocaleHelper.getPlaceTileText());//"PLACE TILE"
    private JButton placeMeepleButton = new JButton(LocaleHelper.getPlaceMeepleText());//PLACE MEEPLE
    private JButton discardTileButton = new JButton(LocaleHelper.getDiscardText());//"DISCARD"
    private JButton endTurnButton = new JButton(LocaleHelper.getEndTurnText());//"ENDTURN"

    private LeaderBoardComponent<JPanel> leaderBoard;

    /**
     * SideBarComponent constructor
     * @param gameView
     */
    public SideBarComponentImpl(final GameView gameView) {
        super();

        this.gameView = gameView;
        this.controller = gameView.getUserInterface().getController();

        this.setBackground(Color.CYAN);
        this.setLayout(new GridBagLayout());

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        JPanel zoomPanel = new JPanel();
        JPanel arrowsPanel = new JPanel();
        JPanel centerArrowsPanel = new JPanel();
        JPanel actionsPanel = new JPanel();

        zoomPanel.setLayout(new BoxLayout(zoomPanel, BoxLayout.Y_AXIS));
        arrowsPanel.setLayout(new BoxLayout(arrowsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        centerArrowsPanel.setLayout(new BoxLayout(centerArrowsPanel, BoxLayout.X_AXIS));

        innerPanel.setOpaque(false);
        zoomPanel.setOpaque(false);
        arrowsPanel.setOpaque(false);
        centerArrowsPanel.setOpaque(false);
        actionsPanel.setOpaque(false);

        zoomInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomPanel.add(zoomInButton);
        zoomOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomPanel.add(zoomOutButton);

        upRowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        arrowsPanel.add(upRowButton);

        leftRowButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        centerArrowsPanel.add(leftRowButton);
        leftRowButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerArrowsPanel.add(rightRowButton);

        centerArrowsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        arrowsPanel.add(centerArrowsPanel);
        downRowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        arrowsPanel.add(downRowButton);

        placeTileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionsPanel.add(placeTileButton);
        discardTileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionsPanel.add(discardTileButton);
        placeMeepleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionsPanel.add(placeMeepleButton);
        endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionsPanel.add(endTurnButton);

        this.leaderBoard = new LeaderBoardComponentImpl(gameView.getUserInterface());
        leaderBoard.getComponent().setAlignmentX(Component.CENTER_ALIGNMENT);

        innerPanel.add(zoomPanel);
        innerPanel.add(arrowsPanel);
        innerPanel.add(actionsPanel);
        innerPanel.add(leaderBoard.getComponent());

        placeMeepleButton.setVisible(false);
        endTurnButton.setVisible(false);
        innerPanel.setVisible(true);

        this.add(innerPanel);

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
            if (this.gameView.placeTile()) {
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

    private <Optional> ActionListener zoomInEventListener() {
        return (e) -> {
            this.gameView.zoomIn();
            if (!this.gameView.canZoomIn()) {
                ((JButton) e.getSource()).setEnabled(false);
            }
            this.zoomOutButton.setEnabled(true);
        };
    }

    private ActionListener zoomOutEventListener() {
        return (e) -> {
            this.gameView.zoomOut();
            if (!this.gameView.canZoomOut()) {
                ((JButton) e.getSource()).setEnabled(false);
            }
            this.zoomInButton.setEnabled(true);
        };
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
            if (controller.discardCurrentTile()) {
                gameView.updateHUD();
            } else {
                ((JButton) e.getSource()).setEnabled(false);
            }
        };
    }

    @Override
    public void update() {
        this.leaderBoard.updateLeaderBoard();
    }
}
