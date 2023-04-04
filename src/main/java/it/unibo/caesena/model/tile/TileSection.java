package it.unibo.caesena.model.tile;

/**
 * Enum representing the different possible portions of the sides of a Tile.
 */
public enum TileSection {
    UP_LEFT,
    UP_CENTER,
    UP_RIGHT,
    RIGHT_UP,
    RIGHT_CENTER,
    RIGHT_DOWN,
    DOWN_RIGHT,
    DOWN_CENTER,
    DOWN_LEFT,
    LEFT_DOWN,
    LEFT_CENTER,
    LEFT_UP,
    CENTER;

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
    private static TileSection shiftAroundBorders(final TileSection section, final int offset) {
        if (section == TileSection.CENTER) {
            return TileSection.CENTER;
        }

        int index = Math.floorMod(section.ordinal() + offset, values().length);
        if (offset > 0 && (values()[index].equals(TileSection.CENTER)
                || section.ordinal() < TileSection.CENTER.ordinal()
                        && section.ordinal() + offset >= TileSection.CENTER.ordinal())) {
            index = Math.floorMod(index + 1, values().length);
        }
        if (offset < 0 && (values()[index].equals(TileSection.CENTER)
                || section.ordinal() > TileSection.CENTER.ordinal()
                        && section.ordinal() + offset <= TileSection.CENTER.ordinal())) {
            index = Math.floorMod(index - 1, values().length);
        }

        return values()[index];
    }

    /**
     * Rotates clockwise the provided section.
     * For example UP_LEFT becomes RIGHT_UP.
     *
     * @param section the section to be rotated
     * @return the provided section rotated
     */
    public static TileSection rotateClockwise(final TileSection section) {
        return shiftAroundBorders(section, getSectionsPerSide());
    }

    /**
     * Shifts the provided section clockwise around the borders by 1.
     *
     * @param section the section to be shifted
     * @return the provided section shifted
     */
    public static TileSection next(final TileSection section) {
        return shiftAroundBorders(section, 1);
    }

    /**
     * Shifts the provided section counter clockwise around the borders by 1.
     *
     * @param section the section to be shifted
     * @return the provided section shifted
     */
    public static TileSection previous(final TileSection section) {
        return shiftAroundBorders(section, -1);
    }
}
