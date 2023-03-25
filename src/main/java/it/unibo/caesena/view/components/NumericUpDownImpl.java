package it.unibo.caesena.view.components;

import java.awt.Font;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class NumericUpDownImpl extends JSpinner implements NumericUpDown<JSpinner> {
    
    private final SpinnerNumberModel model;
    private final DefaultEditor editor;

    public NumericUpDownImpl(final int start, final int min, final int max, final int step) {
        super();

        model = new SpinnerNumberModel(start, min, max, step);
        this.setModel(model);

        editor = new DefaultEditor(this);
        editor.getTextField().setEditable(false);
        this.setEditor(editor);
    }

    @Override
    public int getValueAsInt() {
        return model.getNumber().intValue();
    }

    @Override
    public void setFont(String family, int size) {
        final Font font = new Font(family, Font.BOLD, size);
        this.setFont(font);
        this.editor.getTextField().setFont(font);
    }

    @Override
    public JSpinner getComponent() {
        return this;
    }

}
