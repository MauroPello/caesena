package it.unibo.caesena.view.components;

import java.awt.Color;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.LocaleHelper;

public class PlayerInputImpl extends JPanel implements PlayerInput<JPanel> {

    private final static int TEXT_FIELD_COLUMNS = 4;

    private final PlayerImageImpl playerColorPanel;
    private final JColorChooser playerColorChooser;
    private final JDialog playerColorDialog;
    private final JButton playerColorButton;
    private final JTextField playerName;

    private Color playerColor;

    public PlayerInputImpl() {
        super();

        this.add(new JLabel(LocaleHelper.getNameText()));
        this.playerName = new JTextField();
        this.playerName.setColumns(TEXT_FIELD_COLUMNS);
        this.add(playerName);

        this.add(new JLabel(LocaleHelper.getColorText()));

        this.playerColorPanel = new PlayerImageImpl(50, 50);
        this.playerColorPanel.addPropertyChangeListener("background", (p) -> {
            this.playerColor = (Color) p.getNewValue();
        });
        this.playerColorPanel.setColor(getBackground());
        this.add(this.playerColorPanel);

        this.playerColorChooser = new JColorChooser();
        this.playerColorChooser.setPreviewPanel(new JPanel());
        this.playerColorChooser.setLocale(Locale.ITALIAN);
        for (final var chooserPanel : playerColorChooser.getChooserPanels()) {
            if (!chooserPanel.getDisplayName().equals(LocaleHelper.getSwatchesColorPanelName())
                    && !chooserPanel.getDisplayName().equals("RGB")) {
                this.playerColorChooser.removeChooserPanel(chooserPanel);
            }
        }

        this.playerColorDialog = JColorChooser.createDialog(this, LocaleHelper.getPickColorDialogTitle(), true,
                this.playerColorChooser,
                (e) -> this.playerColorPanel.setColor(this.playerColorChooser.getColor()),
                (e) -> this.playerColorPanel.setColor(getBackground()));

        this.playerColorButton = new JButton(LocaleHelper.getPickColorText());
        this.playerColorButton.addActionListener((e) -> this.playerColorDialog.setVisible(true));
        this.add(playerColorButton);
    }

    @Override
    public final Pair<String, Color> getPlayerData() {
        return new Pair<>(playerName.getText(), playerColor);
    }

    @Override
    public final JPanel getComponent() {
        return this;
    }

}
