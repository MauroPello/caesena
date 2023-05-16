package it.unibo.caesena.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.player.PlayerInGame;
import it.unibo.caesena.model.server.Server;
import it.unibo.caesena.model.tile.MutableTile;
import it.unibo.caesena.model.tile.TileSectionType;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;

public class Game {

    private static final Map<Direction, Pair<List<TileSectionType>, List<TileSectionType>>> NEIGHBOUR_TILES_CHECK = new HashMap<>(Map.of(
            Direction.UP,
            new Pair<>(List.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")),
                List.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT"))),
            Direction.DOWN,
            new Pair<>(List.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")),
                List.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT"))),
            Direction.LEFT,
            new Pair<>(List.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN")),
                List.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"))),
            Direction.RIGHT,
            new Pair<>(List.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN")),
                List.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN")))));
    public static final int POINTS_CLOSED_CITY_NEARBY_FIELD = 3;
    public static final int POINTS_TILE_NEARBY_MONASTERY = 1;
    public static final int POINTS_CLOSED_MONASTERY = 9;
    public static final int MEEPLES_PER_PLAYER = 8;

    private List<PlayerInGame> playersInGame;
    private List<Expansion> expansions;
    private List<MutableTile> tiles;
    private Server server;
    private int gameID;

    private boolean concluded;

    public Game() {
    }

    public Game(final List<Player> players, final List<Color> colors) {
        // TODO crea playersingame
    }

    public MutableTile getCurrentTile() {
        return null;
    }

    public PlayerInGame getCurrentPlayer() {
        return null;
    }

    public List<PlayerInGame> getPlayersInGame() {
        return playersInGame;
    }

    public List<MutableTile> getTiles() {
        return this.tiles;
    }

    public boolean isOver() {
        return this.concluded;
    }

    public List<MutableMeeple> getMeeples() {
        return null;
    }

    public void drawNewTile() {

    }
}
