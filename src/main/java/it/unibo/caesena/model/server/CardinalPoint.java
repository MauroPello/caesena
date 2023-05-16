package it.unibo.caesena.model.server;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "CardinalPoints")
@Table(name = "CardinalPoints")
@Access(AccessType.FIELD)
public class CardinalPoint {
    @Id
    private final String point;

    public CardinalPoint(String point) {
        this.point = point;
    }

    public String getPoint() {
        return point;
    }
}
