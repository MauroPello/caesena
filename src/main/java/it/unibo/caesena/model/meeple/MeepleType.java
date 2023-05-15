package it.unibo.caesena.model.meeple;

public class MeepleType {

    private final String name;
    private final int strength;

    /**
     * Class constructor.
     *
     * @param name of the meeple type
     * @param strength of the meeple type
     */
    public MeepleType(final String name, final int strength) {
        this.strength = strength;
        this.name = name;
    }

    /**
     *
     * @return name of the meeple.
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return strength of a the meeple.
     */
    public int getStrength() {
        return this.strength;
    }

}
