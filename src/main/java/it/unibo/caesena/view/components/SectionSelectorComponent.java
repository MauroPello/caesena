package it.unibo.caesena.view.components;

import it.unibo.caesena.model.tile.TileSection;

public interface SectionSelectorComponent<X> {

    TileSection getSelectedSection();

    Boolean isSectionSelected();

    void draw();

    void reset();

    X getComponent();
}