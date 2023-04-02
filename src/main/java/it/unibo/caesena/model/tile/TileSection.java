package it.unibo.caesena.model.tile;

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

    public static int getSectionsPerSide() {
        return 3;
    }

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

    public static TileSection rotateClockwise(final TileSection section) {
        return shiftAroundBorders(section, getSectionsPerSide());
    }

    public static TileSection next(final TileSection section) {
        return shiftAroundBorders(section, 1);
    }

    public static TileSection previous(final TileSection section) {
        return shiftAroundBorders(section, -1);
    }
}
