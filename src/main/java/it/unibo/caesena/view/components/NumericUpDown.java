package it.unibo.caesena.view.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class NumericUpDown extends JSpinner {
    
    private final SpinnerNumberModel model;
    private final DefaultEditor editor;

    public NumericUpDown(final int start, final int min, final int max, final int step) {
        super();

        model = new SpinnerNumberModel(start, min, max, step);
        this.setModel(model);

        editor = new DefaultEditor(this);
        getTextField().setEnabled(false);
        getTextField().setDisabledTextColor(Color.BLACK);
        this.setEditor(editor);
    }

    public int getNumberAsInt() {
        return model.getNumber().intValue();
    }

    public JFormattedTextField getTextField() {
        return editor.getTextField();
    }

    public void setSize(final Dimension d) {
        this.setPreferredSize(d);
        this.setMinimumSize(d);
    }

}
