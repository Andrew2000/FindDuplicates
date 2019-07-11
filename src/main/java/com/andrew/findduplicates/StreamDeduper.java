package com.andrew.findduplicates;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDeduper<T> {
    private final List<FindDuplicatesColumn<T>> FindDuplicatesColumns;
    private final int thresholdForFuzzyMatch = 6;

    public StreamDeduper(List<FindDuplicatesColumn<T>> FindDuplicatesColumns) {
        this.FindDuplicatesColumns = FindDuplicatesColumns;
    }

    public FindDuplicatesResults<T> dedupe(Stream<T> ContactStream) {
        LinkedList<T> totalSet = new LinkedList<>();
        FindDuplicatesColumnMap<T>[] invertedIndexes = FindDuplicatesColumns.stream().map(FindDuplicatesColumnMap<T>::new).toArray(FindDuplicatesColumnMap[]::new);
        ContactStream.forEach(t -> {
            totalSet.add(t);
            for (FindDuplicatesColumnMap<T> index : invertedIndexes) {
                index.index(t);
            }
        });


        HashSet<T> identifiedDupes = new HashSet<>();
        HashMap<T, Set<T>> deduped = new HashMap<>();
        Set<T> unique = new HashSet<>();

        for (T item : totalSet) {
            if(identifiedDupes.contains(item)){
                continue;
            }

            Set<T> dupes = findDuplicates(item, invertedIndexes);
            if (dupes.size() > 0) {
                identifiedDupes.addAll(dupes);
                deduped.put(item, dupes);
            } else {
                unique.add(item);
            }

        }
        return new FindDuplicatesResults<>(deduped, unique);
    }

    private Set<T> findDuplicates(T item, FindDuplicatesColumnMap<T>[] invertedIndexes) {
        List<Set<T>> collect = Arrays.stream(invertedIndexes).map(tFindDuplicatesColumnMap -> tFindDuplicatesColumnMap.duplicatedRows(item, thresholdForFuzzyMatch)).collect(Collectors.toList());
        return collect.stream().reduce((ts, ts2) -> ts.stream().filter(ts2::contains).collect(Collectors.toSet())).orElse(new HashSet<>());
    }
}
