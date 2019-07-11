package com.andrew.findduplicates;

import java.util.List;

public class FindDuplicatesBean {
    private final List<String> uniqueRecords;
    private final List<String> possibleDuplicates;

    public FindDuplicatesBean(List<String> uniqueRecords, List<String> possibleDuplicates) {
        this.uniqueRecords = uniqueRecords;
        this.possibleDuplicates = possibleDuplicates;
    }

    public List<String> getuniqueRecords() {
        return uniqueRecords;
    }

    public List<String> getpossibleDuplicates() {
        return possibleDuplicates;
    }
}
