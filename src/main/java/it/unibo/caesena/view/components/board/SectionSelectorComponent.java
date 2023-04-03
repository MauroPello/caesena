package it.unibo.caesena.view.components.board;

import java.util.Optional;

import it.unibo.caesena.model.tile.TileSection;

public interface SectionSelectorComponent<X> {

    Optional<TileSection> getSelectedSection();

    Boolean isSectionSelected();

    void draw();

    void reset();

    X getComponent();
}
