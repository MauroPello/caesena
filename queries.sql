-- CardinalPoints
INSERT INTO caesena.CardinalPoints (point)
VALUES ('WEST');

INSERT INTO caesena.CardinalPoints (point)
VALUES ('EAST');

INSERT INTO caesena.CardinalPoints (point)
VALUES ('NORTH');

INSERT INTO caesena.CardinalPoints (point)
VALUES ('SOUTH');

INSERT INTO caesena.CardinalPoints (point)
VALUES ('CENTRAL');

-- Continents
INSERT INTO caesena.Continents (continentName)
VALUES ('Europe');

INSERT INTO caesena.Continents (continentName)
VALUES ('Asia');

INSERT INTO caesena.Continents (continentName)
VALUES ('North America');

INSERT INTO caesena.Continents (continentName)
VALUES ('Oceania');

INSERT INTO caesena.Continents (continentName)
VALUES ('South America');

-- Regions
INSERT INTO caesena.Regions (cardinalPoint_point, continent_continentName)
VALUES ('NORTH', 'Europe');

INSERT INTO caesena.Regions (cardinalPoint_point, continent_continentName)
VALUES ('WEST', 'Asia');

INSERT INTO caesena.Regions (cardinalPoint_point, continent_continentName)
VALUES ('EAST', 'North America');

INSERT INTO caesena.Regions (cardinalPoint_point, continent_continentName)
VALUES ('Central', 'Oceania');

-- Servers
INSERT INTO caesena.Servers (active, maxGames, region_regionID)
VALUES (true, 512, 2);

INSERT INTO caesena.Servers (active, maxGames, region_regionID)
VALUES (false, 4, 2);

INSERT INTO caesena.Servers (active, maxGames, region_regionID)
VALUES (true, 8, 1);

INSERT INTO caesena.Servers (active, maxGames, region_regionID)
VALUES (true, 12, 3);

INSERT INTO caesena.Servers (active, maxGames, region_regionID)
VALUES (false, 1, 4);

-- Expansions
INSERT INTO caesena.Expansions (name)
VALUES ('Basic');

INSERT INTO caesena.Expansions (name)
VALUES ('Big meeples');

INSERT INTO caesena.Expansions (name)
VALUES ('Pennant cities');

INSERT INTO caesena.Expansions (name)
VALUES ('Only city tile');

-- TileSectionTypes
INSERT INTO caesena.TileSectionTypes (name)
VALUES ('LEFT_UP');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('LEFT_CENTER');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('LEFT_DOWN');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('UP_LEFT');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('UP_CENTER');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('UP_RIGHT');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('RIGHT_UP');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('RIGHT_CENTER');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('RIGHT_DOWN');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('DOWN_LEFT');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('DOWN_CENTER');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('DOWN_RIGHT');

INSERT INTO caesena.TileSectionTypes (name)
VALUES ('CENTER');

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'RIGHT_CENTER',
    t.previous_name = 'UP_RIGHT'
WHERE t.name LIKE 'RIGHT#_UP' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'RIGHT_UP',
    t.previous_name = 'UP_CENTER'
WHERE t.name LIKE 'UP#_RIGHT' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'LEFT_UP',
    t.previous_name = 'LEFT_DOWN'
WHERE t.name LIKE 'LEFT#_CENTER' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'LEFT_DOWN',
    t.previous_name = 'DOWN_CENTER'
WHERE t.name LIKE 'DOWN#_LEFT' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'DOWN_CENTER',
    t.previous_name = 'RIGHT_DOWN'
WHERE t.name LIKE 'DOWN#_RIGHT' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'DOWN_RIGHT',
    t.previous_name = 'RIGHT_CENTER'
WHERE t.name LIKE 'RIGHT#_DOWN' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'UP_CENTER',
    t.previous_name = 'LEFT_UP'
WHERE t.name LIKE 'UP#_LEFT' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'UP_LEFT',
    t.previous_name = 'LEFT_CENTER'
WHERE t.name LIKE 'LEFT#_UP' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'DOWN_LEFT',
    t.previous_name = 'DOWN_RIGHT'
WHERE t.name LIKE 'DOWN#_CENTER' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'UP_RIGHT',
    t.previous_name = 'UP_LEFT'
WHERE t.name LIKE 'UP#_CENTER' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'RIGHT_DOWN',
    t.previous_name = 'RIGHT_UP'
WHERE t.name LIKE 'RIGHT#_CENTER' ESCAPE '#';

UPDATE caesena.TileSectionTypes t
SET t.next_name     = 'LEFT_CENTER',
    t.previous_name = 'DOWN_LEFT'
WHERE t.name LIKE 'LEFT#_DOWN' ESCAPE '#';

-- TileTypes
INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_EDGE');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_LARGE');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (1, 'Basic', 'CITY_LARGE_ROAD');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (2, 'Only city tile', 'CITY');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (2, 'Basic', 'CITY_SIDE_DOUBLE_NEXT');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_SIDE_DOUBLE');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_SIDE_JUNCTION');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (5, 'Basic', 'CITY_SIDE');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (4, 'Basic', 'CITY_SIDE_ROAD');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_SIDE_TURN_LEFT');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_SIDE_TURN_RIGHT');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (1, 'Basic', 'CITY_TUBE');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (4, 'Basic', 'MONASTERY');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (2, 'Basic', 'MONASTERY_ROAD');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (1, 'Basic', 'ROAD_JUNCTION_LARGE');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (4, 'Basic', 'ROAD_JUNCTION_SMALL');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (8, 'Basic', 'ROAD_STRAIGHT');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (9, 'Basic', 'ROAD_TURN');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (2, 'Pennant cities', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (2, 'Pennant cities', 'CITY_EDGE_ROAD_PENNANT');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (1, 'Pennant cities', 'CITY_LARGE_PENNANT');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (2, 'Pennant cities', 'CITY_LARGE_ROAD_PENNANT');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (1, 'Pennant cities', 'CITY_PENNANT');

INSERT INTO caesena.TileTypes (quantity, expansion_name, name)
VALUES (2, 'Pennant cities', 'CITY_TUBE_PENNANT');

-- MeepleTypes
INSERT INTO caesena.MeepleTypes (quantity, strength, expansion_name, name)
VALUES (8, 1, 'Basic', 'Normal');

INSERT INTO caesena.MeepleTypes (quantity, strength, expansion_name, name)
VALUES (1, 2, 'Big meeples', 'Big');

-- GameSetTypes
INSERT INTO caesena.GameSetTypes (endGameRatio, startingPoints, expansion_name, name)
VALUES (1, 1, 'Basic', 'MONASTERY');

INSERT INTO caesena.GameSetTypes (endGameRatio, startingPoints, expansion_name, name)
VALUES (1, 1, 'Basic', 'ROAD');

INSERT INTO caesena.GameSetTypes (endGameRatio, startingPoints, expansion_name, name)
VALUES (1, 0, 'Basic', 'FIELD');

INSERT INTO caesena.GameSetTypes (endGameRatio, startingPoints, expansion_name, name)
VALUES (1, 0, 'Basic', 'JUNCTION');

INSERT INTO caesena.GameSetTypes (endGameRatio, startingPoints, expansion_name, name)
VALUES (2, 2, 'Basic', 'CITY');

-- Colors
INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (0, 0, 255, 'ff0000', 'Red');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (0, 255, 0, '00ff00', 'Green');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (255, 0, 0, '0000ff', 'Blue');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (255, 255, 255, 'FFFFFF', 'White');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (0, 0, 0, '000000', 'Black');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (128, 10, 94, '5E0A80', 'Purple');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (2, 231, 243, 'F3E702', 'Yellow');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (121, 115, 124, '7C7379', 'Grey');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (31, 51, 95, '5F331F', 'Brown');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (203, 192, 255, 'FFC0CB', 'Pink');

INSERT INTO caesena.Colors (blue, green, red, hex, name)
VALUES (0, 102, 255, 'FF6600', 'Orange');

-- TileTypeConfigurations
INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, true, false, 'FIELD', 'CENTER', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'DOWN_CENTER', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'DOWN_LEFT', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'DOWN_RIGHT', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'LEFT_CENTER', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'LEFT_DOWN', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'LEFT_UP', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'RIGHT_CENTER', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'RIGHT_DOWN', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'RIGHT_UP', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'UP_CENTER', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'UP_LEFT', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'UP_RIGHT', 'CITY_EDGE');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, true, false, 'FIELD', 'CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'DOWN_CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'DOWN_LEFT', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'DOWN_RIGHT', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'LEFT_CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'LEFT_DOWN', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'FIELD', 'LEFT_UP', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'RIGHT_CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'RIGHT_DOWN', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'RIGHT_UP', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'UP_CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'CITY', 'UP_LEFT', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, true, 'CITY', 'UP_RIGHT', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, true, false, 'ROAD', 'CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'ROAD', 'DOWN_CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'FIELD', 'DOWN_LEFT', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (2, false, false, 'FIELD', 'DOWN_RIGHT', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (0, false, false, 'ROAD', 'LEFT_CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (1, false, false, 'FIELD', 'LEFT_DOWN', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (2, false, false, 'FIELD', 'LEFT_UP', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (3, false, false, 'CITY', 'RIGHT_CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (3, false, false, 'CITY', 'RIGHT_DOWN', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (3, false, false, 'CITY', 'RIGHT_UP', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (3, false, false, 'CITY', 'UP_CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (3, false, false, 'CITY', 'UP_LEFT', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, gameSetType_name, tileSectionType_name, tileType_name)
VALUES (3, false, false, 'CITY', 'UP_RIGHT', 'CITY_EDGE_ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, true, false, 'CITY_EDGE_ROAD_PENNANT', 'CENTER', 'ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_EDGE_ROAD_PENNANT', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_EDGE_ROAD_PENNANT', 'LEFT_CENTER', 'ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_EDGE_ROAD_PENNANT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_EDGE_ROAD_PENNANT', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_EDGE_ROAD_PENNANT', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_EDGE_ROAD_PENNANT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (3, false, true, 'CITY_EDGE_ROAD_PENNANT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, true, false, 'CITY_LARGE', 'CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_LARGE', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_LARGE', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE', 'LEFT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_LARGE', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, true, false, 'CITY_LARGE_PENNANT', 'CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_LARGE_PENNANT', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_LARGE_PENNANT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'LEFT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_LARGE_PENNANT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, true, 'CITY_LARGE_PENNANT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, true, false, 'CITY_LARGE_ROAD', 'CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_LARGE_ROAD', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_LARGE_ROAD', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'LEFT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (3, false, false, 'CITY_LARGE_ROAD', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, true, false, 'CITY_LARGE_ROAD_PENNANT', 'CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_LARGE_ROAD_PENNANT', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_LARGE_ROAD_PENNANT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'LEFT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (3, false, false, 'CITY_LARGE_ROAD_PENNANT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, true, 'CITY_LARGE_ROAD_PENNANT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, true, false, 'CITY', 'CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'LEFT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'DOWN_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'DOWN_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY', 'DOWN_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, true, false, 'CITY_PENNANT', 'CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'LEFT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, true, 'CITY_PENNANT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'DOWN_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'DOWN_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_PENNANT', 'DOWN_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, true, false, 'CITY_SIDE_DOUBLE_NEXT', 'CENTER', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'LEFT_UP', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'RIGHT_CENTER', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, true, false, 'CITY_SIDE_DOUBLE', 'CENTER', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE', 'DOWN_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE', 'DOWN_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'LEFT_CENTER', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE', 'DOWN_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'RIGHT_CENTER', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (0, true, false, 'CITY_SIDE_JUNCTION', 'CENTER', 'JUNCTION');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (6, false, false, 'CITY_SIDE_JUNCTION', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (7, false, false, 'CITY_SIDE_JUNCTION', 'UP_LEFT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (6, false, false, 'CITY_SIDE_JUNCTION', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (7, false, false, 'CITY_SIDE_JUNCTION', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (4, false, false, 'CITY_SIDE_JUNCTION', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (4, false, false, 'CITY_SIDE_JUNCTION', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (7, false, false, 'CITY_SIDE_JUNCTION', 'UP_CENTER', 'CITY');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (5, false, false, 'CITY_SIDE_JUNCTION', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (5, false, false, 'CITY_SIDE_JUNCTION', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (3, false, false, 'CITY_SIDE_JUNCTION', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (1, false, false, 'CITY_SIDE_JUNCTION', 'LEFT_CENTER', 'ROAD');

INSERT INTO caesena.TileTypeConfigurations (id, closed, pennant, tileType_name, tileSectionType_name, gameSetType_name)
VALUES (2, false, false, 'CITY_SIDE_JUNCTION', 'RIGHT_CENTER', 'ROAD');

