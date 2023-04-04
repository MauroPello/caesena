package it.unibo.caesena.model.gameset;

public interface GameSetFactory {

    /**
     * 
     * @return a GameSet which type is city
     */
    GameSet createCitySet();

    /**
     * 
     * @return a GameSet which type is monastery
     */
    GameSet createMonasterySet();

    /**
     * 
     * @return a GameSet which type is road
     */
    GameSet createRoadSet();

    /**
     * 
     * @return a GameSet which type is field
     */
    GameSet createFieldSet();

    /**
     * 
     * @return a GameSet which type is junction
     */
    GameSet createJunctionSet();

    /**
     * 
     * @param gs1 first GameSet
     * @param gs2 second GameSet
     * @return a joined GameSet, combined with the first & second GameSet's
     */
    GameSet createJoinedSet(GameSet gs1, GameSet gs2);

}
