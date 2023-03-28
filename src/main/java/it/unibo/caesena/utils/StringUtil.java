package it.unibo.caesena.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class StringUtil {

    public static String capitalize(final String string) {
        final char[] charArray = string.toLowerCase().toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        return String.valueOf(charArray);
    }

    public static class ToStringBuilder {
        private static final String NULL_STRING = "null";
        private static final String EMPTY_STRING = "";
        private static final String SPACE_STRING = " ";
        private final List<Pair<String, String>> elements;

        public ToStringBuilder() {
            this.elements = new ArrayList<>();
        }

        public final ToStringBuilder add(final String field, final Object value) {
            String actualValue = NULL_STRING;
            if (value != null) {
                actualValue = value.toString();
            }

            elements.add(new Pair<>(StringUtil.capitalize(field), actualValue));
            return this;
        }

        private String getNameFromMethod(final Method method) {
            final String name = method.getName().replace("get", ToStringBuilder.EMPTY_STRING);
            return splitCamelCase(name).stream().filter(w -> !w.isEmpty())
                    .collect(Collectors.joining(ToStringBuilder.SPACE_STRING));
        }

        private List<String> splitCamelCase(final String name) {
            final List<String> result = new ArrayList<>();
            String currentString = ToStringBuilder.EMPTY_STRING;
            final char[] charArray = name.toCharArray();
            for (final char c : charArray) {
                if (!(result.isEmpty() && currentString.isEmpty()) && Character.isUpperCase(c)) {
                    result.add(result.isEmpty() ? currentString : currentString.toLowerCase());
                    currentString = ToStringBuilder.EMPTY_STRING;
                }
                currentString += c;
            }
            result.add(currentString);
            return result;
        }

        private boolean isGetter(final Method method) {
            return method.getName().contains("get") && !method.getName().equals("getClass");
        }

        public final ToStringBuilder addFromObjectGetters(final Object obj) {
            for (final Method method : obj.getClass().getMethods()) {
                if (isGetter(method) && !hasArguments(method)) {
                    final String name = getNameFromMethod(method);
                    try {
                        this.add(name, method.invoke(obj).toString());
                    } catch (final Exception e) {
                        this.add(name, NULL_STRING);
                    }
                }
            }
            return this;
        }

        private boolean hasArguments(final Method method) {
            return method.getParameterCount() != 0;
        }

        public final String build() {
            elements.sort((p1, p2) -> p1.getX().compareTo(p2.getX()));

            return "[" + elements.stream()
                    .map(p -> p.getX() + ": " + p.getY())
                    .collect(Collectors.joining(", ")) + "]";
        }

    }

}
