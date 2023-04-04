package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.components.common.PanelWithBackgroundImage;
import it.unibo.caesena.view.scene.GameScene;

public class SideBarComponentImpl extends PanelWithBackgroundImage implements SideBarComponent<JPanel> {
    private static final long serialVersionUID = 10997719332807770L;
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

    /**
     * SideBarComponent constructor
     *
     * @param gameScene
     */
    public SideBarComponentImpl(final GameScene gameScene) {
        super(ResourceUtil.getBufferedImage("background_Sidebar.jpg", List.of()));
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

        this.setBackground(Color.CYAN);
        this.setLayout(new GridBagLayout());

        final JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        final JPanel zoomPanel = new JPanel();
        zoomPanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));
        final JPanel arrowsPanel = new JPanel();
        arrowsPanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));
        final JPanel centerArrowsPanel = new JPanel();
        final JPanel actionsPanel = new JPanel();
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));

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

        this.add(innerPanel);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

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
        super.setVisible(false);
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
     * @return ActionListener
     */
    private ActionListener placeTileEventListener() {
        return (e) -> {
            if (this.gameScene.placeTile()) {
                placeTileButton.setVisible(false);
                toggleBoardButton.setVisible(true);
                endTurnButton.setVisible(true);
                discardTileButton.setVisible(false);
                final Player currentPlayer = gameScene.getUserInterface().getController().getCurrentPlayer();
                final Optional<Meeple> remainingMeeple = gameScene.getUserInterface().getController()
                        .getPlayerMeeples(currentPlayer)
                        .stream()
                        .filter(m -> !m.isPlaced())
                        .findAny();
                if (remainingMeeple.isEmpty()) {
                    toggleBoardButton.setEnabled(false);
                }
            }
        };
    }

    /**
     * @return ActionListener
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

    private ActionListener zoomInEventListener() {
        return (e) -> {
            this.gameScene.zoomIn();
            if (!this.gameScene.canZoomIn()) {
                ((JButton) e.getSource()).setEnabled(false);
            }
            this.zoomOutButton.setEnabled(true);
        };
    }

    private ActionListener zoomOutEventListener() {
        return (e) -> {
            this.gameScene.zoomOut();
            if (!this.gameScene.canZoomOut()) {
                ((JButton) e.getSource()).setEnabled(false);
            }
            this.zoomInButton.setEnabled(true);
        };
    }

    private void updateMoveButtons() {
        for (final var direction : Direction.values()) {
            if (this.gameScene.canMove(direction)) {
                getButton(direction).setEnabled(true);
            } else {
                getButton(direction).setEnabled(false);
            }
        }
    }

    private JButton getButton(final Direction direction) {
        return switch (direction) {
            case DOWN -> downRowButton;
            case LEFT -> leftRowButton;
            case RIGHT -> rightRowButton;
            case UP -> upRowButton;
            default -> throw new IllegalStateException("Direction wasn't set");
        };
    }

    private ActionListener moveUpEventListener() {
        return (e) -> {
            this.gameScene.move(Direction.UP);
            this.updateMoveButtons();
        };
    }

    private ActionListener moveLeftEventListener() {
        return (e) -> {
            this.gameScene.move(Direction.LEFT);
            this.updateMoveButtons();
        };
    }

    private ActionListener moveDownEventListener() {
        return (e) -> {
            this.gameScene.move(Direction.DOWN);
            this.updateMoveButtons();
        };
    }

    private ActionListener moveRightEventListener() {
        return (e) -> {
            this.gameScene.move(Direction.RIGHT);
            this.updateMoveButtons();
        };
    }

    private ActionListener discardTileEventListener() {
        return (e) -> {
            if (!controller.discardCurrentTile()) {
                ((JButton) e.getSource()).setEnabled(false);
            }
        };
    }
}
