-- [QUERY DA INSERIRE] OBIETTIVO: selezionare le regioni in ordine di punteggio medio più alto dei giocatori in partite concluse dentro quella regione
SELECT R.regionID, AVG(P.score) AS AveragePlayerScore FROM ((Regions R INNER JOIN Servers S on R.regionID = S.region_regionID) INNER JOIN Games G ON S.serverID = G.server_serverID) INNER JOIN PlayersInGame P ON G.gameID = P.game_gameID
    WHERE G.concluded = TRUE GROUP BY R.regionID ORDER BY AveragePlayerScore DESC;

-- [QUERY DA INSERIRE] OBIETTIVO: selezionare per ogni giocatore con quanti altri giocatori diversi ha giocato
SELECT P.player_name, (SELECT COUNT(DISTINCT P2.player_name) AS EnemiesCount FROM PlayersInGame P2 INNER JOIN Games G2 on P2.game_gameID = G2.gameID
    WHERE G2.gameID IN (SELECT DISTINCT P3.game_gameID FROM PlayersInGame P3 WHERE P3.player_name = P.player_name) AND P2.player_name <> P.player_name) AS EnemiesCount
    FROM PlayersInGame P INNER JOIN Games G on P.game_gameID = G.gameID
    GROUP BY P.player_name ORDER BY EnemiesCount DESC;

-- [SUBQUERY SCRITTA PER CHIAREZZA] OBIETTIVO: selezionare il numero di giocatori diversi che hanno giocato con 'prova'
SELECT COUNT(DISTINCT P2.player_name) AS EnemiesCount FROM PlayersInGame P2 INNER JOIN Games G2 on P2.game_gameID = G2.gameID
    WHERE G2.gameID IN (SELECT DISTINCT P3.game_gameID FROM PlayersInGame P3 WHERE P3.player_name = 'prova') AND P2.player_name <> 'prova';

-- [SUBQUERY SCRITTA PER CHIAREZZA] OBIETTIVO: selezionare gli ID delle partite in cui ha giocato 'prova'
SELECT DISTINCT P3.game_gameID FROM PlayersInGame P3 WHERE P3.player_name = 'prova';

-- [QUERY DA INSERIRE] OBIETTIVO: selezionare l'espansione non Basic più giocata in ogni regione
SELECT SQ.regionID, SQ.expansions_name, MAX(SQ.GamesCount) AS MaxGamesCount FROM (
        SELECT R.regionID, GE.expansions_name, COUNT(GE.expansions_name) AS GamesCount FROM ((Regions R INNER JOIN Servers S on R.regionID = S.region_regionID) INNER JOIN Games G ON G.server_serverID = S.serverID) INNER JOIN Games_Expansions GE ON GE.Games_gameID = G.gameID
        WHERE GE.expansions_name <> 'Basic' GROUP BY R.regionID, GE.expansions_name) AS SQ
    GROUP BY SQ.regionID ORDER BY SQ.regionID ASC;
