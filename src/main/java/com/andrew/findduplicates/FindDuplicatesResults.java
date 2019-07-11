package com.andrew.findduplicates;

import java.util.HashMap;
import java.util.Set;

public class FindDuplicatesResults<T> {
    private final HashMap<T, Set<T>> deduped;
    private final Set<T> unique;

    public FindDuplicatesResults(HashMap<T, Set<T>> deduped, Set<T> unique) {
        this.deduped = deduped;
        this.unique = unique;
    }

    public HashMap<T, Set<T>> getDuplicates() {
        return deduped;
    }

    public Set<T> getUnique() {
        return unique;
    }
}
