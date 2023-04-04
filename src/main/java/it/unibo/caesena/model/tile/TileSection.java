package it.unibo.caesena.model.tile;

/**
 * Enum representing the different possible portions of the sides of a Tile.
 */
public enum TileSection {
    /**
     * TileSection.UP_LEFT means that the section is on the left portion of
     * the top side of the tile.
     */
    UP_LEFT,
    /**
     * TileSection.UP_CENTER means that the section is on the center portion of the
     * top side of the tile.
     */
    UP_CENTER,
    /**
     * TileSection.UP_RIGHT means that the section is on the right portion of the top
     * side of the tile.
     */
    UP_RIGHT,
    /**
     * TileSection.RIGHT_UP means that the section is on the top portion of the
     * right side of the tile.
     */
    RIGHT_UP,
    /**
     * TileSection.RIGHT_CENTER means that the section is on the center portion of
     * the right side of the tile.
     */
    RIGHT_CENTER,
    /**
     * TileSection.RIGHT_DOWN means that the section is on the lower portion of the
     * right side of the tile.
     */
    RIGHT_DOWN,
    /**
     * TileSection.DOWN_RIGHT means that the section is on the right portion of the
     * lower side of the tile.
     */
    DOWN_RIGHT,
    /**
     * TileSection.DOWN_CENTER means that the section is on the center portion of
     * the lower side of the tile.
     */
    DOWN_CENTER,
    /**
     * TileSection.DOWN_LEFT means that the section is on the left portion of the
     * lower side of the tile.
     */
    DOWN_LEFT,
    /**
     * TileSection.LEFT_DOWN means that the section is on the lower portion of the
     * left side of the tile.
     */
    LEFT_DOWN,
    /**
     * TileSection.LEFT_CENTER means that the section is on the center portion of
     * the left side of the tile.
     */
    LEFT_CENTER,
    /**
     * TileSection.LEFT_UP means that the section is on the top portion of the left
     * side of the tile.
     */
    LEFT_UP,
    /**
     * TileSection.CENTER means that the section is on the center of the tile.
     */
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
