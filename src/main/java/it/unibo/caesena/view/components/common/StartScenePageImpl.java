package it.unibo.caesena.view.components.common;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.UpdatableComponent;
import it.unibo.caesena.view.scene.StartScene;

public class StartScenePageImpl<Y extends UpdatableComponent<JPanel>> implements StartScenePage<JPanel, Y> {

    private Y contentPanel;
    private final JPanel mainPanel;

    public StartScenePageImpl(final StartScene startScene, final Y component) {
        this.contentPanel = component;
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setOpaque(false);

        this.mainPanel.add(this.contentPanel.getComponent());

        final JButton backButton = new JButton(LocaleHelper.getBackText());
        backButton.setFont(GUI.MEDIUM_BOLD_FONT);
        backButton.addActionListener((e) -> {
            startScene.backToButtonsMenu();
        });
        final JPanel backPanel = new JPanel();
        backPanel.setOpaque(false);
        backPanel.add(backButton);
        backPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, GUI.DEFAULT_PADDING * 4, 0));
        this.mainPanel.add(backPanel);
        this.mainPanel.setVisible(false);
    }

    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        if (!isVisible()) {
            contentPanel.update();
        }

        this.mainPanel.setVisible(visible);
        this.contentPanel.setVisible(visible);
    }

    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

    @Override
    public Y getContentComponent() {
        return this.contentPanel;
    }

}
