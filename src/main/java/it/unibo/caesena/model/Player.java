package it.unibo.caesena.model;

import it.unibo.caesena.utils.Color;

public interface Player {

    String getName();

    Color getColor();

    int getScore();

    void setScore(int newScore);

    void addScore(int amount);
}
