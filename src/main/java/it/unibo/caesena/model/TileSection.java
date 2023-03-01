package it.unibo.caesena.model;

public enum TileSection {
    UpLeft,
    UpCenter,
    UpRight,
    RightUp,
    RightCenter,
    RightDown,
    DownRight,
    DownCenter,
    DownLeft,
    LeftDown,
    LeftCenter,
    LeftUp,
    Center;

    private static int getSectionsInSide() {
        return 3;
    }

    public static TileSection rotateClockwise(TileSection section) {
        int index = (section.ordinal() + getSectionsInSide()) % values().length;
        if (values()[index].equals(TileSection.Center)) {
            index = (index + 1) % values().length;
        }
        
        return values()[index];
    }
}
