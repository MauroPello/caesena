package it.unibo.caesena.model.tile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;

/**
 * Class representing the different types of Tiles that can be created.
 */
public class TileType {

    private final String name;

    public TileType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Creates a new tile from the name of the enum on which it's called.
     * It uses {@link java.lang.reflect} to call the proper method inside the
     * factory to create the new tile.
     *
     * @param tileFactory used to create the new Tile
     * @return a pair containing the new tile with the same type as the enum on
     *         which it's called and a map containing a set of tileSections for
     *         every gameSet belonging the newly created tile
     */
    @SuppressWarnings("unchecked")
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createTile(final TileFactory tileFactory) {
        final StringBuilder methodNameBuilder = new StringBuilder();
        methodNameBuilder.append("create");
        final String[] words = this.name.split("_");
        for (final String word : words) {
            methodNameBuilder.append(StringUtil.capitalize(word));
        }

        final String methodName = methodNameBuilder.toString();

        try {
            final Method method = TileFactory.class.getMethod(methodName);
            return (Pair<MutableTile, Map<GameSet, Set<TileSection>>>) method.invoke(tileFactory, this);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new IllegalStateException("Error using reflection, devs fault", e);
        }
    }
}
