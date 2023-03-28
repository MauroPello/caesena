package it.unibo.caesena.model.gameset;

public interface GameSetFactory {

    GameSet createCitySet();

    GameSet createMonasterySet();

    GameSet createRoadSet();

    GameSet createFieldSet();

    GameSet createJunctionSet();

    GameSet createJoinedSet(GameSet gs1, GameSet gs2);

}
