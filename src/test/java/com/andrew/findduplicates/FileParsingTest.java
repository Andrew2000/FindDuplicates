package com.andrew.findduplicates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;


class FileParsingTest {

    @Test
    void canParseSimpleSampleFileIntoBeans() {
        Stream<Contact> normalBeans = ResourceStreamLoader.parseResource("samples/normal.csv");
        Assertions.assertTrue(normalBeans.count() > 0, "Could not read normal contacts");
    }

    @Test
    void canParseAdvancedSampleFileIntoBeans() {
        Stream<Contact> advancedBeans = ResourceStreamLoader.parseResource("samples/advanced.csv");
        Assertions.assertTrue(advancedBeans.count() > 0, "Could not read advanced contacts");
    }
}


