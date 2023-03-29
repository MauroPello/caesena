package it.unibo.caesena.model.tile;

public enum TileSection {
    UP_LEFT, // 8->DOWN_LEFT
    UP_CENTER, // 6->DOWN_CENTER
    UP_RIGHT, // 4->DOWN_RIGHT
    RIGHT_UP, // 8->LEFT_UP
    RIGHT_CENTER, // 6->LEFT_CENTER
    RIGHT_DOWN, // 4->LEFT_DOWN

    DOWN_RIGHT, // 4->UP_RIGHT
    DOWN_CENTER, // 6->UP_CENTER
    DOWN_LEFT, // 8->UP_LEFT
    LEFT_DOWN, // 4->RIGHT_DOWN
    LEFT_CENTER, // 6->RIGHT_CENTER
    LEFT_UP, // 8->RIGHT_UP
    CENTER;

    private static final int OFFSET_1 = 8;
    private static final int OFFSET_2 = 6;
    private static final int OFFSET_3 = 4;
    private static final int OFFSET_4 = -8;
    private static final int OFFSET_5 = -6;
    private static final int OFFSET_6 = -4;

    private static int getSectionsInSide() {
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

    public static TileSection getOpposite(final TileSection section) {
        if (section == TileSection.CENTER) {
            return TileSection.CENTER;
        }

        if (section.ordinal() <= TileSection.RIGHT_DOWN.ordinal()) {
            switch (section.ordinal() % 3) {
                case 0:
                    return shiftAroundBorders(section, OFFSET_1);
                case 1:
                    return shiftAroundBorders(section, OFFSET_2);
                case 2:
                    return shiftAroundBorders(section, OFFSET_3);
                default:
            }
        } else {
            switch (section.ordinal() % 3) {
                case 0:
                    return shiftAroundBorders(section, OFFSET_6);
                case 1:
                    return shiftAroundBorders(section, OFFSET_5);
                case 2:
                    return shiftAroundBorders(section, OFFSET_4);
                default: 
            }
        }

        return section;
    }

    public static TileSection rotateClockwise(final TileSection section) {
        return shiftAroundBorders(section, getSectionsInSide());
    }

    public static TileSection next(final TileSection section) {
        return shiftAroundBorders(section, 1);
    }

    public static TileSection previous(final TileSection section) {
        return shiftAroundBorders(section, -1);
    }
}
