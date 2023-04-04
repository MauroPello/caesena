package it.unibo.caesena.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileFactory;
import it.unibo.caesena.model.tile.TileType;
import it.unibo.caesena.utils.ResourceUtil;

/**
 * The ConfigurationLoader is able to create all the tiles needed for the game
 * using a JSON file.
 */
public class ConfigurationLoader {
    private final List<Tile> tiles = new ArrayList<>();
    private final String fileName;

    /**
     * Class constructor.
     *
     * @param fileName file with which generate the tiles
     */
    public ConfigurationLoader(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets a list with all the tiles for the game.
     * It does this by reading a JSON file given in the costructor.
     * The function also shuffles the list of tiles and sets one tile as firstTile
     * so that it can be placed on top when starting up.
     *
     * @param factory with which the tiles are created
     * @return the list of newly created tiles
     */
    public final List<Tile> getTiles(final TileFactory factory) {
        try {
            final Object fileJson = new JSONParser()
                    .parse(new InputStreamReader(ResourceUtil.getInputStreamFromFile(fileName, List.of())));
            final JSONObject jsonObject = (JSONObject) fileJson;
            final JSONArray array = (JSONArray) jsonObject.get("Tiles");
            for (int i = 0; i < array.size(); i++) {
                final JSONObject object = (JSONObject) array.get(i);
                for (final var key : object.keySet()) {
                    for (int j = 0; j < Integer.parseInt(object.get(key).toString()); j++) {
                        tiles.add(TileType.valueOf(key.toString()).createTile(factory));
                    }
                }
            }

            Collections.shuffle(tiles);

            for (int i = 0; i < tiles.size(); i++) {
                if (tiles.get(i).getTileType().equals(TileType.valueOf(jsonObject.get("Starting Tile").toString()))) {
                    final Tile firstTile = tiles.get(i);
                    final Tile currentTile = tiles.get(0);
                    tiles.set(i, currentTile);
                    tiles.set(0, firstTile);
                }
            }

            return new ArrayList<>(this.tiles);
        } catch (final IOException | ParseException e) {
            throw new IllegalStateException("Configuration file not found ", e);
        }
    }

}
