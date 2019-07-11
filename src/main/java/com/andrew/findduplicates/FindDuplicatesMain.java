package com.andrew.findduplicates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = FindDuplicatesMain.class)
public class FindDuplicatesMain {
    public static void main(String[] args) {
        SpringApplication.run(FindDuplicatesMain.class, args);
    }
}
