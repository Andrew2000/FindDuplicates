package com.andrew.findduplicates;

import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FindDuplicatesColumnMap<T> {
    private final FindDuplicatesColumn<T> column;
    private final HashMap<String, Set<T>> columnIndex = new HashMap<>();
    private final Metaphone metaphone = new Metaphone();

    public FindDuplicatesColumnMap(FindDuplicatesColumn<T> column) {
        this.column = column;
    }

    public void index(T t) {
        String columnValue = metaphone.metaphone(column.get(t));
        if (columnIndex.containsKey(columnValue)) {
            columnIndex.get(columnValue).add(t);
        } else {
            Set<T> collectionForKey = new HashSet<>();
            collectionForKey.add(t);
            columnIndex.put(columnValue, collectionForKey);
        }
    }

    public Set<T> duplicatedRows(T item, int thresholdForFuzzyMatch) {
        String columnRawValue = column.get(item);
        String columnMetaphoneValue = metaphone.metaphone(columnRawValue);
        Set<T> roughMatches = columnIndex.get(columnMetaphoneValue);
        return roughMatches.stream().filter(roughMatch -> {
            if (roughMatch.equals(item)) {
                return false;
            }

            String roughMatchColumnValue = column.get(roughMatch);
            Integer distance = LevenshteinDistance.getDefaultInstance().apply(columnRawValue, roughMatchColumnValue);
            return distance < thresholdForFuzzyMatch;
        }).collect(Collectors.toSet());
    }
}
