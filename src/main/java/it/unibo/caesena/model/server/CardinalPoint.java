package it.unibo.caesena.model.server;

import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "CardinalPoints")
@Table(name = "CardinalPoints")
@Access(AccessType.FIELD)
public class CardinalPoint {

    @Id
    private String point;

    @OneToMany(mappedBy = "cardinalPoint")
    private List<Region> regions;

    public CardinalPoint() {}

    public String getPoint() {
        return point;
    }
}
