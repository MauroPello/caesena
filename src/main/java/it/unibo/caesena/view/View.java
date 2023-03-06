package it.unibo.caesena.view;

import javax.swing.JPanel;

public class View extends JPanel {

    private final UserInterface userInterface;

    public View(final UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    protected UserInterface getUserInterface() {        
        return userInterface;
    }

}
