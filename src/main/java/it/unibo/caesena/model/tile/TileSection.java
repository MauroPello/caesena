package it.unibo.caesena.model.tile;

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

    // TODO cercare nome migliore
    // se combacia con un altro pezzo (match)
    private boolean closed = false;

    public void close() {
        this.closed = true;
    }

    public boolean isClosed() {
        return this.closed;
    }

    private static int getSectionsInSide() {
        return 3;
    }

    public static TileSection rotateClockwise(final TileSection section) {
        if(section == TileSection.Center) {
            return TileSection.Center;
        }
        int index = (section.ordinal() + getSectionsInSide()) % values().length;
        if (values()[index].equals(TileSection.Center)) {
            index = (index + 1) % values().length;
        }
        
        return values()[index];
    }
}
