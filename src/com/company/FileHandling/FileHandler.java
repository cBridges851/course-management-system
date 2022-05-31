package com.company.FileHandling;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Interacts with the files that are used to keep the program's data persistent
 */
public class FileHandler {
    private final HashMap<Filename, String> filenames = new HashMap<>();

    public FileHandler() {
        filenames.put(
                Filename.ASSIGNMENTS,
                "./src/com/company/FileHandling/Files/Study/assignments.json"
        );
        filenames.put(
                Filename.COURSEMODULES,
                "./src/com/company/FileHandling/Files/Study/course-modules.json"
        );
        filenames.put(
                Filename.COURSES,
                "./src/com/company/FileHandling/Files/Study/courses.json"
        );
        filenames.put(
                Filename.COURSEADMINISTRATORS,
                "./src/com/company/FileHandling/Files/Users/course-administrators.json"
        );
        filenames.put(
                Filename.INSTRUCTORS,
                "./src/com/company/FileHandling/Files/Users/instructors.json"
        );
        filenames.put(
                Filename.STUDENTS,
                "./src/com/company/FileHandling/Files/Users/students.json"
        );
    }

    /**
     * A method that writes to a pre-defined file
     * @param filename The file to write to
     * @param contents What to save to the file
     */
    public void writeFile(Filename filename, String contents) {
        try {
            FileWriter fileWriter = new FileWriter(this.filenames.get(filename));
            fileWriter.write(contents);
            fileWriter.close();
        } catch(FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
        } catch (Exception exception) {
            System.out.println("Unable to append to file: " + exception);
        }
    }

    /**
     * A method that writes to a custom file
     * @param customFilename The file to write to
     * @param contents What to save to the file
     */
    public void writeFile(String customFilename, String contents) {
        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(customFilename);
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
