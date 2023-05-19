package it.unibo.caesena.model.tile;

import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "TileSectionTypes")
@Table(name = "TileSectionTypes")
@Access(AccessType.FIELD)
public class TileSectionType {

    @Id
    private String name;

    @OneToMany(mappedBy = "type")
    private List<TileSection> tileSections;

    @OneToOne
    private TileSectionType next;

    @OneToOne
    private TileSectionType previous;

    public TileSectionType() {}

    public String getName() {
        return this.name;
    }

    /**
     * Gets the number of different TileSections contained in the side of a tile.
     *
     * @return the number of different TileSections contained in the side of a tile
     */
    public static int getSectionsPerSide() {
        return 3;
    }

    /**
     * Shifts the provided section around the borders by as many times as the
     * offset. If the offset is positive than the section will be shifted clockwise,
     * otherwise it will be shifted counter clockwise. Shifting around the borders
     * means shifting ignoring the center section of the tile.
     *
     * @param section the section to be shifted
     * @param offset how many times should the section be shifted and in which direction
     * @return the tile section shifted by the provided offset
     */
    private TileSectionType shiftAroundBorders(final int offset) {
        if (offset == 0 || (offset > 0 && next == null) || (offset < 0 && previous == null)) {
            return this;
        }
        return offset > 0 ? next.shiftAroundBorders(offset - 1) : previous.shiftAroundBorders(offset + 1);
    }

    /**
     * Rotates clockwise the provided section.
     * For example UP_LEFT becomes RIGHT_UP.
     *
     * @param section the section to be rotated
     * @return the provided section rotated
     */
    public TileSectionType rotateClockwise() {
        return shiftAroundBorders(getSectionsPerSide());
    }

    /**
     * Shifts the provided section clockwise around the borders by 1.
     *
     * @param section the section to be shifted
     * @return the provided section shifted
     */
    public TileSectionType getNext() {
        return this.next;
    }

    /**
     * Shifts the provided section counter clockwise around the borders by 1.
     *
     * @param section the section to be shifted
     * @return the provided section shifted
     */
    public TileSectionType getPrevious() {
        return this.previous;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TileSectionType other = (TileSectionType) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}