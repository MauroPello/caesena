package it.unibo.caesena.model.gameset;

public interface GameSetFactory {
    
    public GameSet createCitySet();

    public GameSet createMonasterySet();

    public GameSet createRoadSet();

    public GameSet createFieldSet();

}
