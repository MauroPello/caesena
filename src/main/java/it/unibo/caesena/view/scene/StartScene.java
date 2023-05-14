package it.unibo.caesena.view.scene;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;
import it.unibo.caesena.view.UserInterface;
import it.unibo.caesena.view.components.common.ModalPanel;
import it.unibo.caesena.view.components.common.StartScenePage;
import it.unibo.caesena.view.components.common.StartScenePageImpl;
import it.unibo.caesena.view.components.game.GameListPanel;
import it.unibo.caesena.view.components.game.GameListPanelImpl;
import it.unibo.caesena.view.components.game.NewGamePanel;
import it.unibo.caesena.view.components.game.NewGamePanelImpl;
import it.unibo.caesena.view.components.StatisticsPanel;
import it.unibo.caesena.view.components.StatisticsPanelImpl;
import it.unibo.caesena.view.components.common.JPanelWithBackgroundImage;

/**
 * A class defining the start menu for the game.
 */
public class StartScene implements Scene<JPanel> {

    private static final float GAME_IMAGE_RATIO = 0.6f;

    private final StartScenePage<JPanel, StatisticsPanel<JPanel>> statisticsPage;
    private final StartScenePage<JPanel, GameListPanel<JPanel>> gameListPage;
    private final StartScenePage<JPanel, NewGamePanel<JPanel>> newGamePage;
    private final UserInterface userInterface;
    private final JPanel mainPanel;
    private final JPanel buttonsPanel;

    /**
     * Public constructor that sets up the components and places them.
     *
     * @param userInterface the interface in which this scene is displayed
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "This component will always need access to the UserInterface "
        + "he's placed in as it uses its methods and needs to send and retrieve information from it")
    public StartScene(final UserInterface userInterface) {
        this.userInterface = userInterface;

        this.mainPanel = new JPanelWithBackgroundImage(
                ResourceUtil.getBufferedImage("background_StartScene.png", List.of()), true);
        this.mainPanel.setLayout(new GridBagLayout());
        this.mainPanel.setOpaque(false);

        final JPanel modal = new ModalPanel(ResourceUtil.getBufferedImage("background_Modal.png", List.of()), false);
        modal.setLayout(new BoxLayout(modal, BoxLayout.Y_AXIS));
        modal.setOpaque(false);

        final JPanel imagePanel = new JPanel() {
            private final BufferedImage image = ResourceUtil.getBufferedImage("caesena.png", List.of());

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getPreferredSize() {
                final Dimension d = this.getParent().getSize();
                final double height = (image.getHeight() * d.getWidth()) / image.getWidth();
                return new Dimension((int) Math.round(d.width * GAME_IMAGE_RATIO),
                        (int) Math.round(height * GAME_IMAGE_RATIO));
            }

            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),
                    0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        imagePanel.setOpaque(false);
        final var imagePanelWithPadding = new JPanel();
        imagePanelWithPadding.setOpaque(false);
        imagePanelWithPadding.add(imagePanel);
        imagePanelWithPadding.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING * 4, 0, 0, 0));
        modal.add(imagePanelWithPadding);

        this.mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                imagePanel.revalidate();
                imagePanel.repaint();
            }
        });

        this.newGamePage = new StartScenePageImpl<NewGamePanel<JPanel>>(this, new NewGamePanelImpl(this.userInterface));
        modal.add(this.newGamePage.getComponent());

        this.gameListPage = new StartScenePageImpl<GameListPanel<JPanel>>(this, new GameListPanelImpl(this.userInterface));
        modal.add(this.gameListPage.getComponent());

        this.statisticsPage = new StartScenePageImpl<StatisticsPanel<JPanel>>(this, new StatisticsPanelImpl(this.userInterface));
        modal.add(this.statisticsPage.getComponent());

        this.buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(GUI.DEFAULT_PADDING, 0, GUI.DEFAULT_PADDING, 0);

        final JButton newGameButton = new JButton(LocaleHelper.getNewGameText());
        newGameButton.setFont(GUI.MEDIUM_BOLD_FONT);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener((e) -> {
            this.newGamePage.setVisible(true);
            this.buttonsPanel.setVisible(false);
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(newGameButton, gridBagConstraints);

        final JButton gameListButton = new JButton(LocaleHelper.getGameListText());
        gameListButton.setFont(GUI.MEDIUM_BOLD_FONT);
        gameListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameListButton.addActionListener((e) -> {
            this.gameListPage.setVisible(true);
            this.buttonsPanel.setVisible(false);
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        buttonsPanel.add(gameListButton, gridBagConstraints);

        final JButton statisticsButton = new JButton(LocaleHelper.getStatisticsText());
        statisticsButton.setFont(GUI.MEDIUM_BOLD_FONT);
        statisticsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statisticsButton.addActionListener((e) -> {
            this.statisticsPage.setVisible(true);
            this.buttonsPanel.setVisible(false);
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        buttonsPanel.add(statisticsButton, gridBagConstraints);

        modal.add(buttonsPanel);

        this.mainPanel.add(modal);
        this.mainPanel.setVisible(false);
        backToButtonsMenu();
    }

    /**
     * {@inheritDoc}
     *
     * When set to visible, if there is no change listener for the NumericUpDown it
     * creates a new one and adds it.
     */
    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            update();
        }

        this.mainPanel.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a JFrame which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
    public final JPanel getComponent() {
        return this.mainPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Needed to allow access to the Controller for lower-level "
        + "dynamic components")
    public final UserInterface getUserInterface() {
        return this.userInterface;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.newGamePage.getContentComponent().update();
        this.gameListPage.getContentComponent().update();
        this.statisticsPage.getContentComponent().update();

        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    public void backToButtonsMenu() {
        this.newGamePage.setVisible(false);
        this.gameListPage.setVisible(false);
        this.statisticsPage.setVisible(false);

        this.buttonsPanel.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }
}
