package it.unibo.caesena.view;

import javax.swing.JPanel;

public class View extends JPanel {

    private final GUI userInterface;

    public View(final GUI userInterface) {
        this.userInterface = userInterface;
    }

    protected GUI getUserInterface() {        
        return userInterface;
    }

}
