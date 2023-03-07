package it.unibo.caesena.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {//TODO controllare se ha da fare argomenti reflection hai capito

    public static String capitalize(String string) {
        char[] charArray = string.toLowerCase().toCharArray();
        charArray[0] = String.valueOf(charArray[0]).toUpperCase().charAt(0);
        return String.valueOf(charArray);
    }

    public static class ToStringBuilder {
        private final static String NULL_STRING = "null";
        private final static String EMPTY_STRING = "";
        private final static String SPACE_STRING = " ";
        private final List<Pair<String, String>> elements;

        public ToStringBuilder() {
            this.elements = new ArrayList<>();
        }

        public ToStringBuilder add(final String field, final Object value) {
            String actualValue = NULL_STRING;
            if (value != null) {
                actualValue = value.toString();
            }
            Pair<String, String> definitivePair = new Pair<>(StringUtil.capitalize(field), actualValue);
            elements.add(definitivePair);
            return this;
        }

        private String getNameFromMethod(Method method) {
            String result = ToStringBuilder.EMPTY_STRING;
            String name = method.getName().replace("get", ToStringBuilder.EMPTY_STRING);
            List<String> words = getWordsFromUppercaseChar(name);
            for (String word : words) {
                if (!word.isEmpty()) {
                    result += result.isEmpty() ? word : ToStringBuilder.SPACE_STRING + word;
                }
            }
            return result;
        }

        private List<String> getWordsFromUppercaseChar(String name) {
            List<String> result = new ArrayList<>();
            String currentString = ToStringBuilder.EMPTY_STRING;
            char[] charArray = name.toCharArray();
            for (char c : charArray) {
                String currentCharAsString = String.valueOf(c);
                if (!(result.isEmpty() && currentString.isEmpty()) && isUppercase(currentCharAsString)) {
                    result.add(result.isEmpty() ? currentString : currentString.toLowerCase());
                    currentString = ToStringBuilder.EMPTY_STRING;
                }
                currentString += currentCharAsString;
            }
            result.add(currentString);
            return result;
        }

        private boolean isUppercase(String currentCharAsString) {
            return currentCharAsString == currentCharAsString.toUpperCase();
        }

        private boolean isGetter(Method method) {
            return method.getName().contains("get") && !method.getName().equals("getClass");
        }

        public ToStringBuilder addFromObjectGetters(Object obj)
        {
            var methods = obj.getClass().getMethods();
            for (Method method : methods) {
                if (isGetter(method) && hasArguments(method)) {
                    String name = getNameFromMethod(method);
                    String value = NULL_STRING;
                    try {
                        var getterResult = method.invoke(obj);
                        if (getterResult != null) {
                            value = getterResult.toString();
                        }
                    } catch (Exception e) { }
                    this.add(name, value);
                }
            }
            return this;
        }

        private boolean hasArguments(Method method) {
            return method.getParameterCount() == 0;
        }

        public String build()
        {
            elements.sort((p1, p2) -> p1.getX().compareTo(p2.getX()));
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            for (int i = 0; i < elements.size() - 1; i++) {
                Pair<String, String> pair = elements.get(i);
                stringBuilder.append(pair.getX() + ": " + pair.getY() + ", ");
            }
            Pair<String, String> pair = elements.get(elements.size() - 1);
            stringBuilder.append(pair.getX() + ": " + pair.getY() + "]");

            return stringBuilder.toString();
        }

    }


}

