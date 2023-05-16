package it.unibo.caesena.model.meeple;

import java.util.ArrayList;
import java.util.List;

public class MeepleType {

    public static List<MeepleType> values = new ArrayList<>();

    private final String name;
    private final int strength;
    // private final Expansion expansion;

    /**
     * Class constructor.
     *
     * @param name of the meeple type
     * @param strength of the meeple type
     */
    public MeepleType(final String name, final int strength) {
        this.strength = strength;
        this.name = name;
        MeepleType.values.add(this);
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

    public static MeepleType getFromName(final String name) {
        return values.stream().filter(s -> s.getName().equals(name)).findFirst().get();
    }
}
