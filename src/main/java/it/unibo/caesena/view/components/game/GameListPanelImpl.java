package it.unibo.caesena.view.components.game;

import javax.swing.JPanel;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.view.scene.StartScene;

public class GameListPanelImpl implements GameListPanel<JPanel> {

    private final StartScene startScene;
    private final Controller controller;
    private final JPanel mainPanel;

    public GameListPanelImpl(final StartScene startScene) {
        this.startScene = startScene;
        this.controller = startScene.getUserInterface().getController();
        this.mainPanel = new JPanel();
        this.mainPanel.setOpaque(false);
    }

    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        if (!isVisible()) {
            update();
        }

        this.mainPanel.setVisible(visible);
    }

    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

    @Override
    public void update() {
    }

}
