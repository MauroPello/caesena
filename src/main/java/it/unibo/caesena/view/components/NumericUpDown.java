package it.unibo.caesena.view.components;

public interface NumericUpDown<X> {

    void setFont(String family, int size);

    int getValueAsInt();

    X getComponent();

}
