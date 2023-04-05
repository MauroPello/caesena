package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.components.common.JPanelWithBackgroundImage;
import it.unibo.caesena.view.scene.GameScene;

/**
 * {@inheritDoc}
 *
 * Implements the interface {@link SideBarBasicComponent} using a
 * {@link it.unibo.caesena.view.components.common.JPanelWithBackgroundImage}.
 */
public class SideBarBasicComponentImpl implements SideBarBasicComponent<JPanel> {
    private final Controller controller;
    private final GameScene gameScene;
    private final JButton zoomInButton;
    private final JButton zoomOutButton;
    private final JButton upRowButton;
    private final JButton downRowButton;
    private final JButton leftRowButton;
    private final JButton rightRowButton;
    private final JButton placeTileButton;
    private final JButton toggleBoardButton;
    private final JButton discardTileButton;
    private final JButton endTurnButton;
    private final JPanelWithBackgroundImage mainPanel;

    /**
     * Class constructor.
     *
     * @param gameScene the parent GameScene
     */
    public SideBarBasicComponentImpl(final GameScene gameScene) {
        this.mainPanel = new JPanelWithBackgroundImage(ResourceUtil.getBufferedImage("background_Sidebar.jpg", List.of()));
        this.mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.mainPanel.setLayout(new GridBagLayout());

        zoomInButton = new JButton("Zoom +");
        zoomOutButton = new JButton("Zoom -");
        upRowButton = new JButton();
        downRowButton = new JButton();
        leftRowButton = new JButton();
        rightRowButton = new JButton();
        placeTileButton = new JButton(LocaleHelper.getPlaceTileText());
        toggleBoardButton = new JButton(LocaleHelper.getPlaceMeepleText());
        discardTileButton = new JButton(LocaleHelper.getDiscardText());
        endTurnButton = new JButton(LocaleHelper.getEndTurnText());

        zoomInButton.setFont(GUI.MEDIUM_BOLD_FONT);
        zoomOutButton.setFont(GUI.MEDIUM_BOLD_FONT);
        placeTileButton.setFont(GUI.MEDIUM_BOLD_FONT);
        toggleBoardButton.setFont(GUI.MEDIUM_BOLD_FONT);
        discardTileButton.setFont(GUI.MEDIUM_BOLD_FONT);
        endTurnButton.setFont(GUI.MEDIUM_BOLD_FONT);

        final int iconSize = (int) GUI.SCREEN_WIDTH / 80;
        Image img = ResourceUtil.getBufferedImage("up.png", List.of());
        Icon icon = new ImageIcon(img.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        upRowButton.setIcon(icon);

        img = ResourceUtil.getBufferedImage("down.png", List.of());
        icon = new ImageIcon(img.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        downRowButton.setIcon(icon);

        img = ResourceUtil.getBufferedImage("left.png", List.of());
        icon = new ImageIcon(img.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        leftRowButton.setIcon(icon);

        img = ResourceUtil.getBufferedImage("right.png", List.of());
        icon = new ImageIcon(img.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        rightRowButton.setIcon(icon);

        this.gameScene = gameScene;
        this.controller = gameScene.getUserInterface().getController();

        final JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        final JPanel zoomPanel = new JPanel();
        zoomPanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING,
            GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));

        final JPanel arrowsPanel = new JPanel();
        arrowsPanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING,
            GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));

        final JPanel centerArrowsPanel = new JPanel();
        final JPanel actionsPanel = new JPanel();
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING,
            GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));

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
        toggleBoardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionsPanel.add(toggleBoardButton);
        endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionsPanel.add(endTurnButton);

        innerPanel.add(zoomPanel);
        innerPanel.add(arrowsPanel);
        innerPanel.add(actionsPanel);

        toggleBoardButton.setVisible(false);
        endTurnButton.setVisible(false);
        innerPanel.setVisible(true);

        this.mainPanel.add(innerPanel);

        zoomInButton.addActionListener(zoomInEventListener());
        zoomOutButton.addActionListener(zoomOutEventListener());
        upRowButton.addActionListener(moveUpEventListener());
        leftRowButton.addActionListener(moveLeftEventListener());
        downRowButton.addActionListener(moveDownEventListener());
        rightRowButton.addActionListener(moveRightEventListener());
        placeTileButton.addActionListener(placeTileEventListener());
        toggleBoardButton.addActionListener(placeMeepleEventListener());
        endTurnButton.addActionListener(endTurnEventListener());
        discardTileButton.addActionListener(discardTileEventListener());
        this.mainPanel.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

    /**
     * allows to switch from the board to the place meeple scene.
     * @return ActionListener that allows to switch from the board to the place meeple scene
     */
    private ActionListener placeMeepleEventListener() {
        return (e) -> {
            this.gameScene.toggleBoard();
            if (this.gameScene.isShowingBoard()) {
                this.toggleBoardButton.setText(LocaleHelper.getPlaceMeepleText());
            } else {
                this.toggleBoardButton.setText(LocaleHelper.getShowBoardText());
            }
        };
    }

    /**
     * allows to place the current tile.
     * @return ActionListener that allows to place the current tile
     */
    private ActionListener placeTileEventListener() {
        return (e) -> {
            if (this.gameScene.placeTile()) {
                placeTileButton.setVisible(false);
                toggleBoardButton.setVisible(true);
                endTurnButton.setVisible(true);
                discardTileButton.setVisible(false);
            }
        };
    }

    /**
     * allows to end the turn of the current player.
     * @return ActionListener that allows to end the turn of the current player
     */
    private ActionListener endTurnEventListener() {
        return (e) -> {
            placeTileButton.setVisible(true);
            toggleBoardButton.setVisible(false);
            toggleBoardButton.setEnabled(true);
            endTurnButton.setVisible(false);
            discardTileButton.setVisible(true);
            discardTileButton.setEnabled(true);
            this.gameScene.endTurn();
        };
    }

    /**
     * allows to zoom in the BoardComponent.
     * @return ActionListener that allows to zoom in the BoardComponent
     */
    private ActionListener zoomInEventListener() {
        return (e) -> {
            this.gameScene.zoomIn();
            if (!this.gameScene.canZoomIn()) {
                ((JButton) e.getSource()).setEnabled(false);
            }
            this.zoomOutButton.setEnabled(true);
        };
    }

    /**
    * allows to zoom out the BoardComponent.
    * @return ActionListener that allows to zoom out the BoardComponent
    */
    private ActionListener zoomOutEventListener() {
        return (e) -> {
            this.gameScene.zoomOut();
            if (!this.gameScene.canZoomOut()) {
                ((JButton) e.getSource()).setEnabled(false);
            }
            this.zoomInButton.setEnabled(true);
        };
    }

    /**
     * get the direction of every button, updating every
     * move button checking for each if it is possible to move
     * the board in that direction.
     */
    private void updateMoveButtons() {
        for (final var direction : Direction.values()) {
            if (this.gameScene.canMove(direction)) {
                getButton(direction).setEnabled(true);
            } else {
                getButton(direction).setEnabled(false);
            }
        }
    }

    /**
     * get the correct the button by the direction.
     * @param direction
     * @return the JButton of the desired direction
     */
    private JButton getButton(final Direction direction) {
        return switch (direction) {
            case DOWN -> downRowButton;
            case LEFT -> leftRowButton;
            case RIGHT -> rightRowButton;
            case UP -> upRowButton;
            default -> throw new IllegalStateException("Direction wasn't set");
        };
    }

    /**
     * allows to move up in the BoardComponent.
     * @return ActionListener that allows to move up the BoardComponent
     */
    private ActionListener moveUpEventListener() {
        return (e) -> {
            this.gameScene.move(Direction.UP);
            this.updateMoveButtons();
        };
    }

    /**
     * allows to move left the BoardComponent.
     * @return ActionListener that allows to move left the BoardComponent
     */
    private ActionListener moveLeftEventListener() {
        return (e) -> {
            this.gameScene.move(Direction.LEFT);
            this.updateMoveButtons();
        };
    }

    /**
     * allows to move down the BoardComponent.
     * @return ActionListener that allows to move down the BoardComponent
     */
    private ActionListener moveDownEventListener() {
        return (e) -> {
            this.gameScene.move(Direction.DOWN);
            this.updateMoveButtons();
        };
    }

    /**
     * allows to move right the BoardComponent.
     * @return ActionListener that allows to move right the BoardComponent
     */
    private ActionListener moveRightEventListener() {
        return (e) -> {
            this.gameScene.move(Direction.RIGHT);
            this.updateMoveButtons();
        };
    }

    /**
     * allows to discard the current tile.
     * @return ActionListener that allows discard the current tile
     */
    private ActionListener discardTileEventListener() {
        return (e) -> {
            if (!controller.discardCurrentTile()) {
                ((JButton) e.getSource()).setEnabled(false);
            }
        };
    }

    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    @Override
    public void setVisible(final boolean visible) {
        this.mainPanel.setVisible(visible);
    }
}
