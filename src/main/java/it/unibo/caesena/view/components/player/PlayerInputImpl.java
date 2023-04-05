package it.unibo.caesena.view.components.player;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.LocaleHelper;

/**
 * A class implementing the PlayerInput interface by using a
 * {@link javax.swing.JPanel}.
 */
public final class PlayerInputImpl implements PlayerInput<JPanel> {
    private static final int TEXT_FIELD_COLUMNS = 5;
    private final PlayerImageImpl playerColorPanel;
    private final JColorChooser playerColorChooser;
    private final JDialog playerColorDialog;
    private final JTextField playerName;
    private final JPanel mainPanel;
    private Color currentColor;

    /**
     * Public constructor that sets up the components and places them.
     */
    public PlayerInputImpl() {
        this.mainPanel = new JPanel();

        final JLabel nameLabel = new JLabel(LocaleHelper.getNameText());
        nameLabel.setFont(GUI.MEDIUM_NORMAL_FONT);
        this.playerName = new JTextField();
        this.playerName.setColumns(TEXT_FIELD_COLUMNS);
        this.playerName.setFont(GUI.MEDIUM_NORMAL_FONT);

        final JLabel colorLabel = new JLabel(LocaleHelper.getColorText());
        colorLabel.setFont(GUI.MEDIUM_NORMAL_FONT);

        this.playerColorPanel = new PlayerImageImpl();
        this.playerColorPanel.setColor(this.mainPanel.getBackground());

        this.playerColorChooser = new JColorChooser();
        this.playerColorChooser.setPreviewPanel(new JPanel());
        for (final var chooserPanel : playerColorChooser.getChooserPanels()) {
            if (!chooserPanel.getDisplayName().equals(LocaleHelper.getSwatchesColorPanelName())
                    && !"RGB".equals(chooserPanel.getDisplayName())) {
                this.playerColorChooser.removeChooserPanel(chooserPanel);
            }
        }

        this.playerColorChooser.setFont(GUI.MEDIUM_BOLD_FONT);
        this.playerColorDialog = JColorChooser.createDialog(this.mainPanel, LocaleHelper.getPickColorDialogTitle(), true,
                this.playerColorChooser,
                (e) -> setCurrentColor(this.playerColorChooser.getColor()),
                (e) -> setCurrentColor(this.mainPanel.getBackground()));
        setFontForAllComponents(playerColorDialog, GUI.SMALL_NORMAL_FONT);
        this.playerColorDialog.pack();

        final JButton playerColorButton = new JButton(LocaleHelper.getPickColorText());
        playerColorButton.setFont(GUI.MEDIUM_BOLD_FONT);
        playerColorButton.addActionListener((e) -> this.playerColorDialog.setVisible(true));

        this.mainPanel.setLayout(new GridBagLayout());

        final JPanel namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(nameLabel);
        namePanel.add(playerName);

        final JPanel colorPanel = new JPanel();
        colorPanel.setOpaque(false);
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.X_AXIS));
        colorPanel.add(colorLabel);
        colorPanel.add(playerColorPanel.getComponent());

        namePanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING,
                GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));
        this.mainPanel.add(namePanel);
        colorPanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING,
                GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));
        this.mainPanel.add(colorPanel);
        this.mainPanel.add(playerColorButton);
    }

    /**
     * Sets the same font for all the components placed in the provided container.
     *
     * @param container of which components fonts should be set
     * @param font      to set
     */
    private void setFontForAllComponents(final Container container, final Font font) {
        for (final var component : container.getComponents()) {
            component.setFont(font);
            if (component instanceof Container) {
                setFontForAllComponents((Container) component, font);
            }
        }
    }

    /**
     * Sets the forced size of the PlayerImageImpl component.
     *
     * @param size to be forced
     */
    public void setColorPanelSize(final int size) {
        this.playerColorPanel.forceSize(size);
    }

    /**
     * Sets the current color selected by the player.
     *
     * @param color to set.
     */
    private void setCurrentColor(final Color color) {
        this.playerColorPanel.setColor(color);
        this.currentColor = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Color> getPlayerData() {
        return new Pair<>(playerName.getText(), currentColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        this.mainPanel.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

}
