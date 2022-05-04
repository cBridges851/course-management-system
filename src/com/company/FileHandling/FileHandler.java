package com.company.FileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Interacts with the files that are used to keep the program's data persistent
 */
public class FileHandler {
    private final HashMap<Filename, String> filenames = new HashMap<>();

    public FileHandler() {
        filenames.put(Filename.ASSIGNMENTS, "./src/main/java/com/company/cms/FileHandling/Files/Study/assignments.csv");
        filenames.put(Filename.COURSEMODULES, "./src/main/java/com/company/cms/FileHandling/Files/Study/course-modules.csv");
        filenames.put(Filename.COURSES, "./src/main/java/com/company/cms/FileHandling/Files/Study/courses.csv");
        filenames.put(Filename.RESULTS, "./src/main/java/com/company/cms/FileHandling/Files/Study/results.csv");
        filenames.put(Filename.COURSEADMINISTRATORS, "./src/main/java/com/company/cms/FileHandling/Files/Users/course-administrators.csv");
        filenames.put(Filename.INSTRUCTORS, "./src/main/java/com/company/cms/FileHandling/Files/Users/instructors.csv");
        filenames.put(Filename.STUDENTS, "./src/main/java/com/company/cms/FileHandling/Files/Users/students.csv");
    }

    /**
     * A method that adds a line to the end of a file
     * @param filename The file to append to
     * @param contents What to save to the file
     */
    public void writeFile(Filename filename, String contents) {
        try {
            FileWriter fileWriter = new FileWriter(this.filenames.get(filename), true);
            fileWriter.write(contents);
            fileWriter.close();
        } catch(FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
        } catch (Exception exception) {
            System.out.println("Unable to append to file: " + exception);
        }
    }

    /**
     * A method which loads the contents of the file
     * @param filename The file that data needs to be retrieved from.
     * @return all the lines that are in the file.
     */
    public String loadFile(Filename filename) {
        try {
            return Files.readString(Path.of(this.filenames.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
