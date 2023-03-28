package it.unibo.caesena.view.components;

import java.awt.Color;

import it.unibo.caesena.utils.Pair;

public interface PlayerInput<X> {

    Pair<String, Color> getPlayerData();

    X getComponent();

}
