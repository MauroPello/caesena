package it.unibo.caesena.utils;

import java.util.ArrayList;
import java.util.List;

public class ToStringBuilder {
    private final List<Pair<String, String>> elements;

    public ToStringBuilder() {
        this.elements = new ArrayList<>();
    }

    public ToStringBuilder add(final String field, final Object value) {
        String actualValue = String.valueOf("");
        if (value == null) {
            actualValue = "null";
        } else {
            actualValue = value.toString();
        }
        Pair<String, String> definitivePair = new Pair<String,String>(capitalize(field), actualValue);
        elements.add(definitivePair);
        return this;
    }

    private String capitalize(String string) {
        char[] charArray = string.toLowerCase().toCharArray();
        charArray[0] = String.valueOf(charArray[0]).toUpperCase().charAt(0);
        return String.valueOf(charArray);
    }

    public String build()
    {
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
