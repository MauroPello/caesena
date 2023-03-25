package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.caesena.utils.Pair;

public class PlayerInputImpl extends JPanel implements PlayerInput<JPanel> {
    
    private final static int TEXT_FIELD_COLUMNS = 4;

    private final JColorChooser playerColorChooser;
    private final JDialog playerColorDialog;
    private final JPanel playerColorPanel;
    private final JButton playerColorBtn;
    private final JTextField playerName;
    
    private Color playerColor;

    public PlayerInputImpl() {
        super();
        
        this.add(new JLabel("Name: "));
        this.playerName = new JTextField();
        this.playerName.setColumns(TEXT_FIELD_COLUMNS);
        this.add(playerName);
        
        this.add(new JLabel("Color: "));
        this.playerColorPanel = new JPanel();
        this.playerColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.playerColorPanel.setPreferredSize(new Dimension(50,50));
        this.playerColorPanel.setMinimumSize(new Dimension(50,50));
        this.playerColorPanel.addPropertyChangeListener("background", (p) -> {
            this.playerColor = (Color)p.getNewValue();
        });
        this.playerColorPanel.setBackground(getBackground());
        this.add(this.playerColorPanel);

        this.playerColorChooser = new JColorChooser();
        this.playerColorChooser.setPreviewPanel(new JPanel());
        for (var chooserPanel : playerColorChooser.getChooserPanels()) {
            if (!chooserPanel.getDisplayName().equals("Swatches") && 
                !chooserPanel.getDisplayName().equals("RGB")) {
                this.playerColorChooser.removeChooserPanel(chooserPanel);
            }
        }

        this.playerColorDialog = JColorChooser.createDialog(this, "Pick a Color!", true, this.playerColorChooser, 
            (e) -> this.playerColorPanel.setBackground(this.playerColorChooser.getColor()), 
            (e) -> this.playerColorPanel.setBackground(getBackground()));

        this.playerColorBtn = new JButton("Pick");
        this.playerColorBtn.addActionListener((e) -> this.playerColorDialog.setVisible(true));
        this.add(playerColorBtn);
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
