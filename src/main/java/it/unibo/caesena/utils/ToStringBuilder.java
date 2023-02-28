package it.unibo.caesena.utils;

import java.util.ArrayList;
import java.util.List;

public class ToStringBuilder {
    private final List<Pair<String, String>> elements;

    public ToStringBuilder() {
        this.elements = new ArrayList<>();
    }

    public ToStringBuilder add(String field, String value) {
        elements.add(new Pair<String,String>(field, value));
        return this;
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
