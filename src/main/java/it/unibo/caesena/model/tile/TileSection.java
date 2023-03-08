package it.unibo.caesena.model.tile;

public enum TileSection {
    UpLeft,     //8->DownLeft
    UpCenter,   //6->DownCenter
    UpRight,    //4->DownRight
    RightUp,    //8->LeftUp
    RightCenter,//6->LeftCenter
    RightDown,  //4->LeftDown

    DownRight,  //4->UpRight
    DownCenter, //6->UpCenter
    DownLeft,   //8->UpLeft
    LeftDown,   //4->RightDown
    LeftCenter, //6->RightCenter
    LeftUp,     //8->RightUp
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

    private static TileSection shiftTileSection(final TileSection section, final int offset) {
        if(section == TileSection.Center) {
            return TileSection.Center;
        }
        int index = Math.floorMod(section.ordinal() + offset, values().length);
        if (offset > 0 && section.ordinal() < TileSection.Center.ordinal() &&
            section.ordinal() + offset >= TileSection.Center.ordinal()) {
            index = (index + 1) % values().length;
        }
        if (offset < 0 && section.ordinal() > TileSection.Center.ordinal() &&
            section.ordinal() + offset <= TileSection.Center.ordinal()) {
            index = (index + 1) % values().length;
        }
        
        return values()[index];
    }

    public static TileSection getOpposite(final TileSection section) {
        if(section == TileSection.Center) {
            return TileSection.Center;
        }

        if(section.ordinal()<=TileSection.RightDown.ordinal()) {
            switch(section.ordinal()%3) {
                case 0:
                shiftTileSection(section, 8);
                    break;
                case 1:
                shiftTileSection(section, 6);
                    break;
                case 2:
                shiftTileSection(section, 4);
                    break;
            }
        } else {
            switch(section.ordinal()%3) {
                case 0:
                shiftTileSection(section, -4);
                    break;
                case 1:
                shiftTileSection(section, -6);
                    break;
                case 2:
                shiftTileSection(section, -8);
                    break;
            }
        }

        return section;
    }

    public static TileSection rotateClockwise(final TileSection section) {
        return shiftTileSection(section, getSectionsInSide());
    }

    public static TileSection next(final TileSection section) {
        return shiftTileSection(section, 1);
    }

    public static TileSection previous(final TileSection section) {
        return shiftTileSection(section, -1);
    }
}
