package it.unibo.caesena.model.gameset;

public interface GameSetFactory {
    
    public GameSet createCitySet();

    public GameSet createMonasterySet();

    public GameSet createRoadSet();

    public GameSet createFieldSet();

    public GameSet createJunctionSet();

    public GameSet createJoinedSet(GameSet gs1, GameSet gs2);

}
