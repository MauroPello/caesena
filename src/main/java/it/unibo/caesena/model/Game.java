package it.unibo.caesena.model;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

import it.unibo.caesena.model.gameset.GameSetImpl;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.server.Server;
import it.unibo.caesena.model.tile.MutableTile;
import it.unibo.caesena.model.tile.TileImpl;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.model.tile.TileSectionType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Games")
@Table(name = "Games")
@Access(AccessType.FIELD)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameID;

    @OneToMany(mappedBy = "game", cascade=CascadeType.ALL)
    private List<PlayerInGameImpl> playersInGame;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Expansion> expansions;

    @OneToMany(mappedBy = "game",cascade=CascadeType.ALL)
    private List<TileImpl> tiles;

    @ManyToOne
    private Server server;

    private boolean concluded;

    public Game() {}

    public Game(final Session session, final Server server) {
        this.concluded = false;
        this.server = server;
        this.tiles = new ArrayList<>();
    }

    public void setPlayers(final List<PlayerInGameImpl> playersInGame) {
        this.playersInGame = playersInGame;
    }

    public List<GameSetImpl> getAllGameSets() {
        // TODO [PELLO] con query
        return tiles.stream().flatMap(t -> t.getSections().stream()).map(TileSection::getGameSet).toList();
    }

    public void addTile(final TileImpl tile) {
        this.tiles.add(tile);
    }

    public boolean placeMeeple(final MutableMeeple meeple, final MutableTile tile, final TileSectionType tileSection) {
        // TODO [PELLO]
        // final GameSet gameSet = getGameSetInSectionType(tile, tileSection);
        // if (meeple.isPlaced() || !gameSet.isMeepleFree()) {
        //     return false;
        // }

        // gameSet.addMeeple(meeple);
        // meeple.place();
        return true;
    }

    public PlayerInGameImpl getCurrentPlayer() {
        return playersInGame.stream().filter(PlayerInGameImpl::isCurrent).findFirst().get();
    }

    public List<PlayerInGameImpl> getPlayersInGame() {
        return playersInGame;
    }

    public List<TileImpl> getTiles() {
        return this.tiles;
    }

    public boolean isOver() {
        return this.concluded;
    }

    public List<MeepleImpl> getMeeples() {
        return playersInGame.stream().flatMap(p -> p.getMeeples().stream()).toList();
    }

    public void end() {
        // TODO [PELLO]
    }

    public List<Expansion> getExpansions() {
        return expansions;
    }

    public Server getServer() {
        return server;
    }

    public int getGameID() {
        return gameID;
    }

    public boolean isConcluded() {
        return concluded;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + gameID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (gameID != other.gameID)
            return false;
        return true;
    }

}
