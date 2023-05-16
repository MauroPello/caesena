package it.unibo.caesena.model.tile;

import java.util.ArrayList;
import java.util.List;

public class TileSectionType {

    public static List<TileSectionType> values = new ArrayList<>();
    private final String name;

    public TileSectionType(final String name) {
        this.name = name;
        // TODO add in order
        TileSectionType.values.add(this);
    }

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
    private static TileSectionType shiftAroundBorders(final TileSectionType section, final int offset) {
        TileSectionType centerSection = getFromName("CENTER");
        if (section.equals(centerSection)) {
            return section;
        }

        int index = Math.floorMod(values.indexOf(section) + offset, values.size());
        if (offset > 0 && (values.get(index).equals(centerSection)
                || values.indexOf(section) < values.indexOf(centerSection)
                && values.indexOf(section) + offset >= values.indexOf(centerSection))) {
            index = Math.floorMod(index + 1, values.size());
        }
        if (offset < 0 && (values.get(index).equals(centerSection)
                || values.indexOf(section) > values.indexOf(centerSection)
                && values.indexOf(section) + offset <= values.indexOf(centerSection))) {
            index = Math.floorMod(index - 1, values.size());
        }

        return values.get(index);
    }

    /**
     * Rotates clockwise the provided section.
     * For example UP_LEFT becomes RIGHT_UP.
     *
     * @param section the section to be rotated
     * @return the provided section rotated
     */
    public static TileSectionType rotateClockwise(final TileSectionType section) {
        return shiftAroundBorders(section, getSectionsPerSide());
    }

    /**
     * Shifts the provided section clockwise around the borders by 1.
     *
     * @param section the section to be shifted
     * @return the provided section shifted
     */
    public static TileSectionType next(final TileSectionType section) {
        return shiftAroundBorders(section, 1);
    }

    /**
     * Shifts the provided section counter clockwise around the borders by 1.
     *
     * @param section the section to be shifted
     * @return the provided section shifted
     */
    public static TileSectionType previous(final TileSectionType section) {
        return shiftAroundBorders(section, -1);
    }

    public static TileSectionType getFromName(final String name) {
        return values.stream().filter(s -> s.getName().equals(name)).findFirst().get();
    }
}