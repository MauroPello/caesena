-- [QUERY DA INSERIRE] OBIETTIVO: selezionare le regioni in ordine di punteggio medio più alto dei giocatori in partite concluse dentro quella regione
SELECT R.id, AVG(P.score) AS AveragePlayerScore FROM ((regions R INNER JOIN servers S on R.id = S.region_id) INNER JOIN games G ON S.id = G.server_id) INNER JOIN players_in_game P ON G.id = P.game_id
    WHERE G.concluded = TRUE GROUP BY R.id ORDER BY AveragePlayerScore DESC;

-- [QUERY DA INSERIRE] OBIETTIVO: selezionare per ogni giocatore con quanti altri giocatori diversi ha giocato
SELECT P.player_name, (SELECT COUNT(DISTINCT P2.player_name) AS EnemiesCount FROM players_in_game P2 INNER JOIN games G2 on P2.game_id = G2.id
    WHERE G2.id IN (SELECT DISTINCT P3.game_id FROM players_in_game P3 WHERE P3.player_name = P.player_name) AND P2.player_name <> P.player_name) AS EnemiesCount
    FROM players_in_game P INNER JOIN games G on P.game_id = G.id
    GROUP BY P.player_name ORDER BY EnemiesCount DESC;

-- [SUBQUERY SCRITTA PER CHIAREZZA] OBIETTIVO: selezionare il numero di giocatori diversi che hanno giocato con 'prova'
SELECT COUNT(DISTINCT P2.player_name) AS EnemiesCount FROM players_in_game P2 INNER JOIN games G2 on P2.game_id = G2.id
    WHERE G2.id IN (SELECT DISTINCT P3.game_id FROM players_in_game P3 WHERE P3.player_name = 'prova') AND P2.player_name <> 'prova';

-- [SUBQUERY SCRITTA PER CHIAREZZA] OBIETTIVO: selezionare gli id delle partite in cui ha giocato 'prova'
SELECT DISTINCT P3.game_id FROM players_in_game P3 WHERE P3.player_name = 'prova';

-- [QUERY DA INSERIRE] OBIETTIVO: selezionare l'espansione non Basic più giocata in ogni regione
SELECT SQ.id, SQ.expansion_name, MAX(SQ.GamesCount) AS MaxGamesCount FROM (
        SELECT R.id, GE.expansion_name, COUNT(GE.expansion_name) AS GamesCount FROM ((regions R INNER JOIN servers S on R.id = S.region_id) INNER JOIN games G ON G.server_id = S.id) INNER JOIN games_expansions GE ON GE.game_id = G.id
        WHERE GE.expansion_name <> 'Basic' GROUP BY R.id, GE.expansion_name) AS SQ
    GROUP BY SQ.id ORDER BY SQ.id ASC;


-- [QUERY DA INSERIRE] OBIETTIVO: "probabilità statistica" di vincere a seconda del colore selezionato
SELECT c.name, (SUM(CASE WHEN pig.Score = max_scores.max_score THEN 1 ELSE 0 END) / COUNT(*)) AS WinProbability
FROM colors c
JOIN players_in_game pig ON c.hex = pig.color_hex
JOIN (
    SELECT game_id, MAX(Score) AS max_score
    FROM players_in_game
    GROUP BY game_id
) max_scores ON pig.game_id = max_scores.game_id
JOIN games g ON pig.game_id = g.id
WHERE g.concluded = true
GROUP BY c.hex, c.name;


-- [QUERY DA INSERIRE] OBIETTIVO: selezionare i server attivi che non hanno raggiunto il limite di partite
SELECT * FROM servers AS s WHERE active=true AND max_games > (SELECT COUNT(*) FROM games AS g WHERE g.server_ID=s.ID AND g.concluded = FALSE)
