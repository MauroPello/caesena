package it.unibo.caesena.model.meeple;

/**
 * Extension of the {@link it.unibo.caesena.model.meeple.Meeple} interface.
 * It rappresents a Meeple as a mutable object.
 */
public interface MutableMeeple extends Meeple {

    void setPlaced(boolean placed);

}
