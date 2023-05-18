package it.unibo.caesena.model.meeple;

import java.util.ArrayList;
import java.util.List;

import it.unibo.caesena.model.Expansion;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "MeepleTypes")
@Table(name = "MeepleTypes")
@Access(AccessType.FIELD)
public class MeepleType {

    @Transient
    public static List<MeepleType> values = new ArrayList<>();

    @Id
    private String name;

    @ManyToOne
    private Expansion expansion;

    @OneToMany(mappedBy = "type")
    private List<MeepleImpl> meeples;

    private int quantity;
    private int strength;

    /**
     * Class constructor.
     *
     * @param name of the meeple type
     * @param strength of the meeple type
     */
    public MeepleType() {}

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
    public int getQuantity() {
        return this.quantity;
    }
}
