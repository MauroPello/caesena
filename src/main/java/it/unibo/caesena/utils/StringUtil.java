package it.unibo.caesena.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This class ontains methods that are meant to help developers
 * managing strings.
 */
public final class StringUtil {

    /**
     * Private constructor, used for restricting object creation.
     */
    private StringUtil() {

    }

    /**
     * Capitalize the passed string.
     * As an example the string "hELlo" would become "Hello".
     *
     * @param string to be capitalized
     * @return the capitalized string
     */
    public static String capitalize(final String string) {
        final char[] charArray = string.toLowerCase(Locale.getDefault()).toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        return String.valueOf(charArray);
    }

    /**
     * This class is able to create a string rappresenting any
     * object, suitable to be used in any class' toString method.
     * This not only helps to standardize all toString methods in the project, but
     * it also can, using Reflection API, automatically create the toString string
     * using any object public getter methods.
     *
     * It uses the Builder design pattern, this is because its also possible to
     * manually add any field, this is because a developer might want the have in
     * the toString string field that don't have a public getter method.
     *
     * As an example if we want to build a toString string for a classe
     * <code>"classExample"</code> that has two public getters:
     * <ul>
     * <li><code>getSomething</code>, that returns the string "hello"
     * <li><code>getSomethingElse</code>, that returns the string "world"
     * </ul>
     * it also has the private field <code>privateField</code> that contains the
     * integer "10".
     * The code below:
     * <code> new StringUtil.ToStringBuilder()
     *          .addFromObjectGetters(this)
     *          .add("Private field", privateField)
     *          .build();
     * </code>
     * will return a string containing
     * <code>"[Something: hello, Something Else: world, Private field: 10]"</code>.
     *
     * Keep in mind that for semplicity we will use the word <code>block</code> to
     * mean information given to the builer with which to build the toString string.
     * In the example all of this are what we would call <code>block</code>
     * <ul>
     *  <li> Something: hello
     *  <li> Something else: world
     *  <li> Private field: 10
     * </ul>
     *
     *
     * Beware that a method is considered as a getter if its name starts with "get"
     * and
     * is not the getClass method.
     * Beware that the getter need to not be needing parameters, in which case it
     * will be ignored.
     */
    public static class ToStringBuilder {
        private static final String NULL_STRING = "null";
        private static final String EMPTY_STRING = "";
        private static final String SPACE_STRING = " ";
        private final List<Pair<String, String>> elements;

        /**
         * Class constructor.
         */
        public ToStringBuilder() {
            this.elements = new ArrayList<>();
        }

        /**
         * This method is used to add a block that would not be picked up by the
         * addFromObjectGetters method.
         * Beware that this method needs a <code>field</code> which will be used as an
         * identified of the objects <code>value</code> which is the object to be added,
         * it will use the toString method of <code>value</code>.
         *
         * @see <code> addFromObjectGetters </code>
         * @param fieldName the "name" of the field to add
         * @param fieldValue object of which it gets the toString
         * @return a new builder that once built will give the added field as a string
         */
        public final ToStringBuilder add(final String fieldName, final Object fieldValue) {
            String actualValue = NULL_STRING;
            if (fieldValue != null) {
                actualValue = fieldValue.toString();
            }

            elements.add(new Pair<>(StringUtil.capitalize(fieldName), actualValue));
            return this;
        }

        /**
         * It gets the name for a getter method.
         * All it does is remove the "get" and add spaced beetwen the words.
         *
         * @param method of which we want to extract the name
         * @return a String containing the name extrated from the given method
         */
        private String getNameFromMethod(final Method method) {
            final String name = method.getName().replace("get", ToStringBuilder.EMPTY_STRING);
            return splitCamelCase(name).stream().filter(w -> !w.isEmpty())
                    .collect(Collectors.joining(ToStringBuilder.SPACE_STRING));
        }

        /**
         * Splits a given string written in camelCase into separate words.
         * As an example "exampleMethodName" wuold become "example method name".
         * @param name written in camelCase to be split into words
         * @return list of the splitted words
         */
        private List<String> splitCamelCase(final String name) {
            final List<String> result = new ArrayList<>();
            StringBuilder currentString = new StringBuilder();
            final char[] charArray = name.toCharArray();
            for (final char c : charArray) {
                if (!(result.isEmpty() && currentString.isEmpty()) && Character.isUpperCase(c)) {
                    result.add(result.isEmpty() ? currentString.toString()
                            : currentString.toString().toLowerCase(Locale.getDefault()));
                    currentString = new StringBuilder();
                }
                currentString.append(c);
            }
            result.add(currentString.toString());
            return result;
        }

        /**
         * Checks if the given method is a getter.
         * A method is considered as a getter if its name starts with "get" and
         * is not the getClass method.
         * @param method to be verified as getter
         * @return true if the given method is a getter, false otherwise
         */
        private boolean isGetter(final Method method) {
            return !"getClass".equals(method.getName()) && method.getName().contains("get");
        }

        /**
         * It automatically gets the public getters to be used as blocks of the toString.
         *
         * @param obj of which to get the getters as blocks of the toString
         * @return a new builder
         */
        public final ToStringBuilder addFromObjectGetters(final Object obj) {
            for (final Method method : obj.getClass().getMethods()) {
                if (isGetter(method) && !hasArguments(method)) {
                    final String name = getNameFromMethod(method);
                    try {
                        this.add(name, method.invoke(obj).toString());
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        this.add(name, NULL_STRING);
                    }
                }
            }
            return this;
        }

        /**
         * It checks whether or not the given method needs arguments.
         *
         * @param method to be check if needs arguments
         * @return true if the given method needs arguments, false otherwise
         */
        private boolean hasArguments(final Method method) {
            return method.getParameterCount() != 0;
        }

        /**
         * Builds the toString.
         *
         * @return the string built using the blocks added beforehand
         * @see "StringUtil Javadoc"
         */
        public final String build() {
            elements.sort((p1, p2) -> p1.getX().compareTo(p2.getX()));

            return "[" + elements.stream()
                    .map(p -> p.getX() + ": " + p.getY())
                    .collect(Collectors.joining(", ")) + "]";
        }

    }

}
