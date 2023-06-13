-- cardinal_points
INSERT INTO caesena.cardinal_points (name)
VALUES ('WEST');

INSERT INTO caesena.cardinal_points (name)
VALUES ('EAST');

INSERT INTO caesena.cardinal_points (name)
VALUES ('NORTH');

INSERT INTO caesena.cardinal_points (name)
VALUES ('SOUTH');

INSERT INTO caesena.cardinal_points (name)
VALUES ('CENTRAL');

-- continents
INSERT INTO caesena.continents (name)
VALUES ('Europe');

INSERT INTO caesena.continents (name)
VALUES ('Asia');

INSERT INTO caesena.continents (name)
VALUES ('America');

INSERT INTO caesena.continents (name)
VALUES ('Oceania');

-- regions
INSERT INTO caesena.regions (cardinal_point_name, continent_name)
VALUES ('NORTH', 'Europe');

INSERT INTO caesena.regions (cardinal_point_name, continent_name)
VALUES ('WEST', 'Asia');

INSERT INTO caesena.regions (cardinal_point_name, continent_name)
VALUES ('EAST', 'America');

INSERT INTO caesena.regions (cardinal_point_name, continent_name)
VALUES ('WEST', 'America');

INSERT INTO caesena.regions (cardinal_point_name, continent_name)
VALUES ('CENTRAL', 'Oceania');

INSERT INTO caesena.regions (cardinal_point_name, continent_name)
VALUES ('CENTRAL', 'Europe');

-- servers
INSERT INTO caesena.servers (active, max_games, region_id)
VALUES (true, 512, 2);

INSERT INTO caesena.servers (active, max_games, region_id)
VALUES (false, 4, 2);

INSERT INTO caesena.servers (active, max_games, region_id)
VALUES (true, 8, 1);

INSERT INTO caesena.servers (active, max_games, region_id)
VALUES (true, 12, 3);

INSERT INTO caesena.servers (active, max_games, region_id)
VALUES (false, 1, 4);

-- expansions
INSERT INTO caesena.expansions (name)
VALUES ('Basic');

INSERT INTO caesena.expansions (name)
VALUES ('Big meeples');

INSERT INTO caesena.expansions (name)
VALUES ('Pennant cities');

INSERT INTO caesena.expansions (name)
VALUES ('Only city tile');

-- tile_section_types
INSERT INTO caesena.tile_section_types (name)
VALUES ('LEFT_UP');

INSERT INTO caesena.tile_section_types (name)
VALUES ('LEFT_CENTER');

INSERT INTO caesena.tile_section_types (name)
VALUES ('LEFT_DOWN');

INSERT INTO caesena.tile_section_types (name)
VALUES ('UP_LEFT');

INSERT INTO caesena.tile_section_types (name)
VALUES ('UP_CENTER');

INSERT INTO caesena.tile_section_types (name)
VALUES ('UP_RIGHT');

INSERT INTO caesena.tile_section_types (name)
VALUES ('RIGHT_UP');

INSERT INTO caesena.tile_section_types (name)
VALUES ('RIGHT_CENTER');

INSERT INTO caesena.tile_section_types (name)
VALUES ('RIGHT_DOWN');

INSERT INTO caesena.tile_section_types (name)
VALUES ('DOWN_LEFT');

INSERT INTO caesena.tile_section_types (name)
VALUES ('DOWN_CENTER');

INSERT INTO caesena.tile_section_types (name)
VALUES ('DOWN_RIGHT');

INSERT INTO caesena.tile_section_types (name)
VALUES ('CENTER');

UPDATE caesena.tile_section_types t
SET t.next_name     = 'RIGHT_CENTER',
    t.previous_name = 'UP_RIGHT'
WHERE t.name LIKE 'RIGHT#_UP' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'RIGHT_UP',
    t.previous_name = 'UP_CENTER'
WHERE t.name LIKE 'UP#_RIGHT' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'LEFT_UP',
    t.previous_name = 'LEFT_DOWN'
WHERE t.name LIKE 'LEFT#_CENTER' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'LEFT_DOWN',
    t.previous_name = 'DOWN_CENTER'
WHERE t.name LIKE 'DOWN#_LEFT' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'DOWN_CENTER',
    t.previous_name = 'RIGHT_DOWN'
WHERE t.name LIKE 'DOWN#_RIGHT' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'DOWN_RIGHT',
    t.previous_name = 'RIGHT_CENTER'
WHERE t.name LIKE 'RIGHT#_DOWN' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'UP_CENTER',
    t.previous_name = 'LEFT_UP'
WHERE t.name LIKE 'UP#_LEFT' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'UP_LEFT',
    t.previous_name = 'LEFT_CENTER'
WHERE t.name LIKE 'LEFT#_UP' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'DOWN_LEFT',
    t.previous_name = 'DOWN_RIGHT'
WHERE t.name LIKE 'DOWN#_CENTER' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'UP_RIGHT',
    t.previous_name = 'UP_LEFT'
WHERE t.name LIKE 'UP#_CENTER' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'RIGHT_DOWN',
    t.previous_name = 'RIGHT_UP'
WHERE t.name LIKE 'RIGHT#_CENTER' ESCAPE '#';

UPDATE caesena.tile_section_types t
SET t.next_name     = 'LEFT_CENTER',
    t.previous_name = 'DOWN_LEFT'
WHERE t.name LIKE 'LEFT#_DOWN' ESCAPE '#';

-- tile_types
INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_EDGE');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_LARGE');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (1, 'Basic', 'CITY_LARGE_ROAD');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (2, 'Only city tile', 'CITY');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (2, 'Basic', 'CITY_SIDE_DOUBLE_NEXT');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_SIDE_DOUBLE');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_SIDE_JUNCTION');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (5, 'Basic', 'CITY_SIDE');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (4, 'Basic', 'CITY_SIDE_ROAD');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_SIDE_TURN_LEFT');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (3, 'Basic', 'CITY_SIDE_TURN_RIGHT');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (1, 'Basic', 'CITY_TUBE');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (4, 'Basic', 'MONASTERY');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (2, 'Basic', 'MONASTERY_ROAD');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (1, 'Basic', 'ROAD_JUNCTION_LARGE');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (4, 'Basic', 'ROAD_JUNCTION_SMALL');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (8, 'Basic', 'ROAD_STRAIGHT');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (9, 'Basic', 'ROAD_TURN');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (2, 'Pennant cities', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (2, 'Pennant cities', 'CITY_EDGE_ROAD_PENNANT');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (1, 'Pennant cities', 'CITY_LARGE_PENNANT');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (2, 'Pennant cities', 'CITY_LARGE_ROAD_PENNANT');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (1, 'Pennant cities', 'CITY_PENNANT');

INSERT INTO caesena.tile_types (quantity, expansion_name, name)
VALUES (2, 'Pennant cities', 'CITY_TUBE_PENNANT');

-- meeple_types
INSERT INTO caesena.meeple_types (quantity, strength, expansion_name, name)
VALUES (8, 1, 'Basic', 'Normal');

INSERT INTO caesena.meeple_types (quantity, strength, expansion_name, name)
VALUES (1, 2, 'Big meeples', 'Big');

-- gameset_types
INSERT INTO caesena.gameset_types (endgame_ratio, starting_points, expansion_name, name)
VALUES (1, 1, 'Basic', 'MONASTERY');

INSERT INTO caesena.gameset_types (endgame_ratio, starting_points, expansion_name, name)
VALUES (1, 1, 'Basic', 'ROAD');

INSERT INTO caesena.gameset_types (endgame_ratio, starting_points, expansion_name, name)
VALUES (1, 0, 'Basic', 'FIELD');

INSERT INTO caesena.gameset_types (endgame_ratio, starting_points, expansion_name, name)
VALUES (1, 0, 'Basic', 'JUNCTION');

INSERT INTO caesena.gameset_types (endgame_ratio, starting_points, expansion_name, name)
VALUES (2, 2, 'Basic', 'CITY');

-- colors
INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (0, 0, 255, 'ff0000', 'Red');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (0, 255, 0, '00ff00', 'Green');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (255, 0, 0, '0000ff', 'Blue');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (255, 255, 255, 'FFFFFF', 'White');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (0, 0, 0, '000000', 'Black');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (128, 10, 94, '5E0A80', 'Purple');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (2, 231, 243, 'F3E702', 'Yellow');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (121, 115, 124, '7C7379', 'Grey');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (31, 51, 95, '5F331F', 'Brown');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (203, 192, 255, 'FFC0CB', 'Pink');

INSERT INTO caesena.colors (blue, green, red, hex, name)
VALUES (0, 102, 255, 'FF6600', 'Orange');

-- tile_type_configurations
INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, true, false, 'FIELD', 'CENTER', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'DOWN_CENTER', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'DOWN_LEFT', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'DOWN_RIGHT', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'LEFT_CENTER', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'LEFT_DOWN', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'LEFT_UP', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'RIGHT_CENTER', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'RIGHT_DOWN', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'RIGHT_UP', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'UP_CENTER', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'UP_LEFT', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'UP_RIGHT', 'CITY_EDGE');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, true, false, 'FIELD', 'CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'DOWN_CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'DOWN_LEFT', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'DOWN_RIGHT', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'LEFT_CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'LEFT_DOWN', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'FIELD', 'LEFT_UP', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'RIGHT_CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'RIGHT_DOWN', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'RIGHT_UP', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'UP_CENTER', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'CITY', 'UP_LEFT', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, true, 'CITY', 'UP_RIGHT', 'CITY_EDGE_PENNANT');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, true, false, 'ROAD', 'CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'ROAD', 'DOWN_CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'FIELD', 'DOWN_LEFT', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (2, false, false, 'FIELD', 'DOWN_RIGHT', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (0, false, false, 'ROAD', 'LEFT_CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (1, false, false, 'FIELD', 'LEFT_DOWN', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (2, false, false, 'FIELD', 'LEFT_UP', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (3, false, false, 'CITY', 'RIGHT_CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (3, false, false, 'CITY', 'RIGHT_DOWN', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (3, false, false, 'CITY', 'RIGHT_UP', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (3, false, false, 'CITY', 'UP_CENTER', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (3, false, false, 'CITY', 'UP_LEFT', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, gameset_type_name, tile_section_type_name, tile_type_name)
VALUES (3, false, false, 'CITY', 'UP_RIGHT', 'CITY_EDGE_ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'CITY_EDGE_ROAD_PENNANT', 'CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_EDGE_ROAD_PENNANT', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_EDGE_ROAD_PENNANT', 'LEFT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_EDGE_ROAD_PENNANT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_EDGE_ROAD_PENNANT', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_EDGE_ROAD_PENNANT', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_EDGE_ROAD_PENNANT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, true, 'CITY_EDGE_ROAD_PENNANT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_EDGE_ROAD_PENNANT', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'CITY_LARGE', 'CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_LARGE', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_LARGE', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE', 'LEFT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_LARGE', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'CITY_LARGE_PENNANT', 'CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_LARGE_PENNANT', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_LARGE_PENNANT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'LEFT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_LARGE_PENNANT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, true, 'CITY_LARGE_PENNANT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_PENNANT', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'CITY_LARGE_ROAD', 'CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_LARGE_ROAD', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_LARGE_ROAD', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'LEFT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_LARGE_ROAD', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'CITY_LARGE_ROAD_PENNANT', 'CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_LARGE_ROAD_PENNANT', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_LARGE_ROAD_PENNANT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'LEFT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_LARGE_ROAD_PENNANT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, true, 'CITY_LARGE_ROAD_PENNANT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_LARGE_ROAD_PENNANT', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'CITY', 'CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'LEFT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'DOWN_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'DOWN_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY', 'DOWN_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'CITY_PENNANT', 'CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'LEFT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, true, 'CITY_PENNANT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'DOWN_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'DOWN_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_PENNANT', 'DOWN_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, true, false, 'CITY_SIDE_DOUBLE_NEXT', 'CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'LEFT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE_NEXT', 'RIGHT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, true, false, 'CITY_SIDE_DOUBLE', 'CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE', 'DOWN_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE', 'DOWN_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_DOUBLE', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'LEFT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_DOUBLE', 'DOWN_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_DOUBLE', 'RIGHT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'CITY_SIDE_JUNCTION', 'CENTER', 'JUNCTION');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (6, false, false, 'CITY_SIDE_JUNCTION', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (7, false, false, 'CITY_SIDE_JUNCTION', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (6, false, false, 'CITY_SIDE_JUNCTION', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (7, false, false, 'CITY_SIDE_JUNCTION', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (4, false, false, 'CITY_SIDE_JUNCTION', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (4, false, false, 'CITY_SIDE_JUNCTION', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (7, false, false, 'CITY_SIDE_JUNCTION', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (5, false, false, 'CITY_SIDE_JUNCTION', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (5, false, false, 'CITY_SIDE_JUNCTION', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_JUNCTION', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_JUNCTION', 'LEFT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_JUNCTION', 'RIGHT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'CITY_SIDE', 'CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE', 'LEFT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE', 'RIGHT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, true, false, 'CITY_SIDE_ROAD', 'CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_ROAD', 'LEFT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_ROAD', 'RIGHT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_ROAD', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_ROAD', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_ROAD', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_ROAD', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_ROAD', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_ROAD', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_ROAD', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_ROAD', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_ROAD', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_ROAD', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_LEFT', 'CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_TURN_LEFT', 'LEFT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_LEFT', 'RIGHT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_TURN_LEFT', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_TURN_LEFT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_TURN_LEFT', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_LEFT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_LEFT', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_LEFT', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_LEFT', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_TURN_LEFT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_TURN_LEFT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_TURN_LEFT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_RIGHT', 'CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_RIGHT', 'LEFT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_TURN_RIGHT', 'RIGHT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_SIDE_TURN_RIGHT', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_RIGHT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_RIGHT', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_TURN_RIGHT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_SIDE_TURN_RIGHT', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_RIGHT', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'CITY_SIDE_TURN_RIGHT', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_TURN_RIGHT', 'UP_LEFT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_TURN_RIGHT', 'UP_RIGHT', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_SIDE_TURN_RIGHT', 'UP_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_TUBE', 'UP_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_TUBE', 'UP_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_TUBE', 'UP_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_TUBE', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_TUBE', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, true, false, 'CITY_TUBE', 'CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE', 'LEFT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_TUBE', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_TUBE_PENNANT', 'UP_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_TUBE_PENNANT', 'UP_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'CITY_TUBE_PENNANT', 'UP_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_TUBE_PENNANT', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, true, 'CITY_TUBE_PENNANT', 'RIGHT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_TUBE_PENNANT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE_PENNANT', 'RIGHT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, true, false, 'CITY_TUBE_PENNANT', 'CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE_PENNANT', 'LEFT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE_PENNANT', 'RIGHT_UP', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'CITY_TUBE_PENNANT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE_PENNANT', 'LEFT_DOWN', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'CITY_TUBE_PENNANT', 'LEFT_CENTER', 'CITY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'UP_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'UP_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'UP_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'DOWN_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'RIGHT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'MONASTERY', 'CENTER', 'MONASTERY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY', 'LEFT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'UP_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'UP_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'UP_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'MONASTERY_ROAD', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'RIGHT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'MONASTERY_ROAD', 'CENTER', 'MONASTERY');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'MONASTERY_ROAD', 'LEFT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (7, false, false, 'ROAD_JUNCTION_LARGE', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (7, false, false, 'ROAD_JUNCTION_LARGE', 'UP_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (6, false, false, 'ROAD_JUNCTION_LARGE', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (6, false, false, 'ROAD_JUNCTION_LARGE', 'UP_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (5, false, false, 'ROAD_JUNCTION_LARGE', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (5, false, false, 'ROAD_JUNCTION_LARGE', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_JUNCTION_LARGE', 'UP_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (8, false, false, 'ROAD_JUNCTION_LARGE', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (8, false, false, 'ROAD_JUNCTION_LARGE', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'ROAD_JUNCTION_LARGE', 'LEFT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'ROAD_JUNCTION_LARGE', 'RIGHT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'ROAD_JUNCTION_LARGE', 'CENTER', 'JUNCTION');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (4, false, false, 'ROAD_JUNCTION_LARGE', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'ROAD_JUNCTION_SMALL', 'CENTER', 'JUNCTION');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_JUNCTION_SMALL', 'UP_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'ROAD_JUNCTION_SMALL', 'LEFT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (3, false, false, 'ROAD_JUNCTION_SMALL', 'RIGHT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (4, false, false, 'ROAD_JUNCTION_SMALL', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (5, false, false, 'ROAD_JUNCTION_SMALL', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (5, false, false, 'ROAD_JUNCTION_SMALL', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_JUNCTION_SMALL', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_JUNCTION_SMALL', 'UP_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_JUNCTION_SMALL', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_JUNCTION_SMALL', 'UP_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (6, false, false, 'ROAD_JUNCTION_SMALL', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (6, false, false, 'ROAD_JUNCTION_SMALL', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'ROAD_STRAIGHT', 'CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_STRAIGHT', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_STRAIGHT', 'UP_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'ROAD_STRAIGHT', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'ROAD_STRAIGHT', 'UP_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'ROAD_STRAIGHT', 'UP_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_STRAIGHT', 'LEFT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'ROAD_STRAIGHT', 'RIGHT_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'ROAD_STRAIGHT', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_STRAIGHT', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_STRAIGHT', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'ROAD_STRAIGHT', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'ROAD_STRAIGHT', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, true, false, 'ROAD_TURN', 'CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_TURN', 'UP_CENTER', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'ROAD_TURN', 'DOWN_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_TURN', 'LEFT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_TURN', 'UP_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'ROAD_TURN', 'DOWN_LEFT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (2, false, false, 'ROAD_TURN', 'LEFT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (0, false, false, 'ROAD_TURN', 'LEFT_CENTER', 'ROAD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_TURN', 'RIGHT_UP', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_TURN', 'UP_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_TURN', 'DOWN_RIGHT', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_TURN', 'RIGHT_DOWN', 'FIELD');

INSERT INTO caesena.tile_type_configurations (id, closed, pennant, tile_type_name, tile_section_type_name, gameset_type_name)
VALUES (1, false, false, 'ROAD_TURN', 'RIGHT_CENTER', 'FIELD');

