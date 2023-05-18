package it.unibo.caesena.view.components.player;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.model.Color;
import it.unibo.caesena.view.LocaleHelper;

/**
 * A class implementing the PlayerInput interface by using a
 * {@link javax.swing.JPanel}.
 */
public final class PlayerInputImpl implements PlayerInput<JPanel> {

    private static final int TEXT_FIELD_COLUMNS = 5;
    private final PlayerImage<JPanel> playerImage;
    private final JComboBox<String> playerColorChooser;
    private final List<Color> defaultColors;
    private final JTextField playerName;
    private final JPanel mainPanel;

    /**
     * Public constructor that sets up the components and places them.
     */
    public PlayerInputImpl(final List<Color> defaultColors) {
        this.mainPanel = new JPanel();
        this.defaultColors = defaultColors;

        final JLabel nameLabel = new JLabel(LocaleHelper.getNameText());
        nameLabel.setFont(GUI.MEDIUM_NORMAL_FONT);
        this.playerName = new JTextField();
        this.playerName.setColumns(TEXT_FIELD_COLUMNS);
        this.playerName.setFont(GUI.MEDIUM_NORMAL_FONT);

        final JLabel colorLabel = new JLabel(LocaleHelper.getColorText());
        colorLabel.setFont(GUI.MEDIUM_NORMAL_FONT);

        this.playerImage = new PlayerImageImpl();
        this.playerImage.getComponent().setOpaque(false);

        this.playerColorChooser = new JComboBox<>();
        defaultColors.forEach(c -> playerColorChooser.addItem(c.getName()));
        this.playerColorChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerImage.setColor(defaultColors.get(playerColorChooser.getSelectedIndex()).asSwingColor());
            }
        });
        this.playerColorChooser.setSelectedIndex(0);
        this.playerColorChooser.setFont(GUI.MEDIUM_BOLD_FONT);

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
        colorPanel.add(playerImage.getComponent());

        namePanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING,
                GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));
        this.mainPanel.add(namePanel);
        colorPanel.setBorder(BorderFactory.createEmptyBorder(GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING,
                GUI.DEFAULT_PADDING, GUI.DEFAULT_PADDING));
        this.mainPanel.add(colorPanel);
        this.mainPanel.add(playerColorChooser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerImageSize(final int size) {
        this.playerImage.forceSize(size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Color> getPlayerData() {
        return new Pair<>(playerName.getText(), defaultColors.get(playerColorChooser.getSelectedIndex()));
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
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a bigger JPanel which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
    public JPanel getComponent() {
        return this.mainPanel;
    }

}
