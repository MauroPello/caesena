package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.Locale;

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

public final class PlayerInputImpl extends JPanel implements PlayerInput<JPanel> {
    private static final long serialVersionUID = 6860767233870664780L;
    private static final int TEXT_FIELD_COLUMNS = 4;
    private static final int PADDING_SIZE = 10;
    private final PlayerImageImpl playerColorPanel;
    private final JColorChooser playerColorChooser;
    private final JDialog playerColorDialog;
    private final JTextField playerName;
    private Color playerColor;

    public PlayerInputImpl() {
        super();

        final JLabel nameLabel = new JLabel(LocaleHelper.getNameText());
        nameLabel.setFont(GUI.MEDIUM_NORMAL_FONT);
        this.playerName = new JTextField();
        this.playerName.setColumns(TEXT_FIELD_COLUMNS);
        this.playerName.setFont(GUI.MEDIUM_NORMAL_FONT);

        final JLabel colorLabel = new JLabel(LocaleHelper.getColorText());
        colorLabel.setFont(GUI.MEDIUM_NORMAL_FONT);

        this.playerColorPanel = new PlayerImageImpl();
        this.playerColorPanel.setColor(getBackground());

        this.playerColorChooser = new JColorChooser();
        this.playerColorChooser.setPreviewPanel(new JPanel());
        for (final var chooserPanel : playerColorChooser.getChooserPanels()) {
            if (!chooserPanel.getDisplayName().equals(LocaleHelper.getSwatchesColorPanelName())
                    && !"RGB".equals(chooserPanel.getDisplayName())) {
                this.playerColorChooser.removeChooserPanel(chooserPanel);
            }
        }

        this.playerColorDialog = JColorChooser.createDialog(this, LocaleHelper.getPickColorDialogTitle(), true,
                this.playerColorChooser,
                (e) -> updateColor(this.playerColorChooser.getColor()),
                (e) -> updateColor(getBackground()));

        final JButton playerColorButton = new JButton(LocaleHelper.getPickColorText());
        playerColorButton.setFont(GUI.MEDIUM_BOLD_FONT);
        playerColorButton.addActionListener((e) -> this.playerColorDialog.setVisible(true));

        this.setLayout(new GridBagLayout());

        final JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(nameLabel);
        namePanel.add(playerName);

        final JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.X_AXIS));
        colorPanel.add(colorLabel);
        colorPanel.add(playerColorPanel);

        namePanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        this.add(namePanel);
        colorPanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        this.add(colorPanel);
        this.add(playerColorButton);
    }

    @Override
    public void setColorPanelSize(final int size) {
        this.playerColorPanel.forceSize(size);
    }

    private void updateColor(final Color color) {
        this.playerColorPanel.setColor(color);
        this.playerColor = color;
    }

    @Override
    public Pair<String, Color> getPlayerData() {
        return new Pair<>(playerName.getText(), playerColor);
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

}
