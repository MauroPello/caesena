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

    private static int getSectionsInSide() {
        return 3;
    }

    private static TileSection shiftAroundBorders(final TileSection section, final int offset) {
        if (section == TileSection.Center) {
            return TileSection.Center;
        }

        int index = Math.floorMod(section.ordinal() + offset, values().length);
        if (offset > 0 && (values()[index].equals(TileSection.Center) || 
            (section.ordinal() < TileSection.Center.ordinal() && section.ordinal() + offset >= TileSection.Center.ordinal()))) {
            index = Math.floorMod(index + 1, values().length);
        }
        if (offset < 0 && (values()[index].equals(TileSection.Center) || 
            (section.ordinal() > TileSection.Center.ordinal() && section.ordinal() + offset <= TileSection.Center.ordinal()))) {
            index = Math.floorMod(index - 1, values().length);
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
                    return shiftAroundBorders(section, 8);
                case 1:
                    return shiftAroundBorders(section, 6);
                case 2:
                    return shiftAroundBorders(section, 4);
            }
        } else {
            switch(section.ordinal()%3) {
                case 0:
                    return shiftAroundBorders(section, -4);
                case 1:
                    return shiftAroundBorders(section, -6);
                case 2:
                    return shiftAroundBorders(section, -8);
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
