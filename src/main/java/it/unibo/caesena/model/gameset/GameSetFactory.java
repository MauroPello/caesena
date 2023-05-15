package it.unibo.caesena.model.gameset;

/**
 * This class allows you to create new gamesets city, monastery, road, field, junction
 * and allows join between two gamesets.
 */
public interface GameSetFactory {
    /**
     * Checks if two GameSet objects, gs1 and gs2, have the same type. If they do, the code checks if either gs1 or gs2 is closed.
     * If neither of them is closed, the code calculates the total points for the joined GameSet
     * by adding the points of gs1 and gs2.
     * It also gets the lists of meeples from both gs1 and gs2 and combines them into a single list.
     * Then, the code creates a new GameSet object called joinedGameset with
     * the same type as gs1 and the combined list of meeples.
     * It sets the points of joinedGameset to the calculated total points.
     *
     * @param gs1 first GameSet
     * @param gs2 second GameSet
     * @return a joined GameSet, combined with the first and second gameset
     */
    GameSet createJoinedSet(GameSet gs1, GameSet gs2);
}
