package it.unibo.caesena.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import it.unibo.caesena.utils.Pair;

public class Statistic implements Iterable<Pair<String,String>>{
    private final String title;
    private final List<Pair<String, String>> valuesMap;
    private Pair<String, String> header;
    private int index;

    public Statistic(String title) {
        this.index = 0;
        this.title = title;
        this.valuesMap = new ArrayList<>();
        this.header = null;
    }

    public void addHeader(String nameColumn, String valueColum) {
        this.header = new Pair<String, String>(nameColumn, valueColum);
    }

    public void addRow(String name, String value) {
        valuesMap.add(new Pair<String,String>(name, value));
    }

    public String getTitle() {
        return title;
    }

    public Optional<Pair<String, String>> getHeader(){
        return header == null ? Optional.empty() : Optional.of(header);
    }

    @Override
    public Iterator<Pair<String, String>> iterator() {
        return new Iterator<Pair<String,String>>() {
            @Override
            public boolean hasNext() {
                return index < valuesMap.size();
            }

            @Override
            public Pair<String, String> next() {
                return valuesMap.get(index++);
            }
        };
    }

}
