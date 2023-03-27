package it.unibo.caesena.controller;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.model.tile.TileType;

public class ConfigurationLoader {
    
    private final List<Tile> tiles = new ArrayList<>();
    private static final String SEP = File.separator;
    private static final String CONFIG_FOLDER_PATH = "it" + SEP + "unibo" + SEP + "caesena" + SEP;

    public List<Tile> read (String fileName) {
        
        try {
            Object fileJson = new JSONParser().parse(new InputStreamReader(ClassLoader.getSystemResourceAsStream(CONFIG_FOLDER_PATH + fileName)));
            JSONObject jsonObject =  (JSONObject) fileJson;
            JSONArray array = (JSONArray) jsonObject.get("Tiles");
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                for (var key : object.keySet()) {
                    for (int j = 0; j < Integer.parseInt(object.get(key).toString()); j++) {
                        tiles.add(TileType.valueOf(key.toString()).createTile(new TileFactoryWithBuilder()));
                    }
                }
            }

            Collections.shuffle(tiles);
            
            for (int i = 0; i < tiles.size(); i++) {
                if (tiles.get(i).getTileType().equals(TileType.valueOf(jsonObject.get("Starting Tile").toString()))) {
                    Tile firstTile = tiles.get(i);
                    Tile currentTile = tiles.get(0);
                    tiles.set(i, currentTile);
                    tiles.set(0, firstTile);
                }
            }

            return this.tiles;
        } catch (Exception e) {
            throw new IllegalStateException("Error reading tiles from file, maybe it's missing");
        }
    }


}
