package it.unibo.caesena.view;

public interface View<X> {

    public boolean isVisible();

    public void setVisible(final boolean visible);

    X getComponent();

}
