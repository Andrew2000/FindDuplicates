package com.andrew.findduplicates;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FindDuplicatesService {
    public FindDuplicatesBean runDeduplicationJob(String fileToParse) {
        Stream<Contact> ContactStream = ResourceStreamLoader.parseResource("samples/" + fileToParse + ".csv");
        List<FindDuplicatesColumn<Contact>> dedupeFields = Arrays.asList(
                FindDuplicatesColumn.forClassField(Contact.class, Contact::getFirst_name),
                FindDuplicatesColumn.forClassField(Contact.class, Contact::getLast_name),
                FindDuplicatesColumn.forClassField(Contact.class, Contact::getEmail)
        );
        StreamDeduper<Contact> deduper = new StreamDeduper<>(dedupeFields);
        FindDuplicatesResults<Contact> results = deduper.dedupe(ContactStream);

        List<String> uniqueContactCsvRows = results.getUnique().stream().map(FindDuplicatesService::ContactToString).collect(Collectors.toList());
        List<String> duplicateContactCsvRows = results.getDuplicates().entrySet().stream().flatMap(ContactSetEntry -> {
            return Stream.concat(Stream.of(ContactToString(ContactSetEntry.getKey())),ContactSetEntry.getValue().stream().map(FindDuplicatesService::ContactToString));
        }).collect(Collectors.toList());
        return new FindDuplicatesBean(uniqueContactCsvRows, duplicateContactCsvRows);
    }

    private static String ContactToString(Contact Contact) {
        return Contact.toString();
    }
}
