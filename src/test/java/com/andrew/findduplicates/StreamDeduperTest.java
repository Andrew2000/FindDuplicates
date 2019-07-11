package com.andrew.findduplicates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamDeduperTest {
    @Test
    void streamDeduperTest() {
        List<FindDuplicatesColumn<Contact>> dedupeFields = Arrays.asList(
                FindDuplicatesColumn.forClassField(Contact.class, Contact::getFirst_name),
                FindDuplicatesColumn.forClassField(Contact.class, Contact::getLast_name),
                FindDuplicatesColumn.forClassField(Contact.class, Contact::getEmail)
        );
        StreamDeduper<Contact> deduper = new StreamDeduper<>(dedupeFields);
        Contact Contact1 = new Contact();
        Contact1.setId(1);
        Contact1.setFirst_name("Nicholas");
        Contact1.setLast_name("Goodwin");
        Contact1.setEmail("nick@goodwin.com");
        Contact Contact2 = new Contact();
        Contact2.setId(2);
        Contact2.setFirst_name("Nicholsa");
        Contact2.setLast_name("Goodwin");
        Contact2.setEmail("nick@goodwin.com");
        Contact Contact3 = new Contact();
        Contact3.setId(3);
        Contact3.setFirst_name("Bill");
        Contact3.setLast_name("Goodwin");
        Contact3.setEmail("nick@goodwin.com");
        FindDuplicatesResults<Contact> dupes = deduper.dedupe(Stream.of(Contact1, Contact2, Contact3));
        Assertions.assertEquals(dupes.getDuplicates().size(), 1, "one duplicate");
        Assertions.assertEquals(dupes.getUnique().size(), 1, "one unique");
    }
}
