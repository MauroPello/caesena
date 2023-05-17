package it.unibo.caesena.model.meeple;

import java.util.ArrayList;
import java.util.List;

import it.unibo.caesena.model.Expansion;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "MeepleTypes")
@Table(name = "MeepleTypes")
@Access(AccessType.FIELD)
public class MeepleType {

    @Transient
    public static List<MeepleType> values = new ArrayList<>();

    @Id
    private final String name;
    private final int strength;
    @ManyToOne
    @JoinColumn(name = "fk_expansion")
    private final Expansion expansion;
    private final int quantity;

    /**
     * Class constructor.
     *
     * @param name of the meeple type
     * @param strength of the meeple type
     */
    public MeepleType(final String name, final int strength, final Expansion expansion, final int quantity) {
        this.strength = strength;
        this.name = name;
        this.expansion = expansion;
        this.quantity = quantity;
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

    /**
     *
     * @return strength of a the meeple.
     */
    public Expansion getExpansion() {
        return this.expansion;
    }

        /**
     *
     * @return strength of a the meeple.
     */
    public int getQuantity() {
        return this.quantity;
    }

    public static MeepleType getFromName(final String name) {
        return values.stream().filter(s -> s.getName().equals(name)).findFirst().get();
    }
}
