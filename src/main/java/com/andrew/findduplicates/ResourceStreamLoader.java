package com.andrew.findduplicates;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ResourceStreamLoader {
    private ResourceStreamLoader() {}

    public static Stream<Contact> parseResource(String resourcePath) {
        InputStream csvSampleAsStream = Contact.class.getResourceAsStream(resourcePath);
        Iterator<Contact> iterator = new CsvToBeanBuilder<Contact>(new InputStreamReader(csvSampleAsStream, StandardCharsets.UTF_8)).withType(Contact.class).build().iterator();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false).onClose(() -> {
            try {
                csvSampleAsStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error closing stream", e);
            }
        });
    }
}
