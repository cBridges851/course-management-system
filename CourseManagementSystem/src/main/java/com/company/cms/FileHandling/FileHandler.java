package com.company.cms.FileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Interacts with the files that are used to keep the program's data persistent
 */
public class FileHandler {
    private HashMap<Filename, String> filenames = new HashMap<Filename, String>();

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
     * A method which removes all the records in a file
     * @param filename The file to clear
     */
    public void clearFile(Filename filename) {
        try {
            FileWriter fileWriter = new FileWriter(this.filenames.get(filename), false);
            fileWriter.write("");
            fileWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
        } catch (Exception exception) {
            System.out.println("Unable to clear file " + exception);
        }
    }

    /**
     * A method that adds a line to the end of a file
     * @param filename The file to append to
     * @param line The line to be added
     */
    public void appendFile(Filename filename, String line) {
        try {
            FileWriter fileWriter = new FileWriter(this.filenames.get(filename), true);
            fileWriter.append(line);
            fileWriter.close();
            System.out.println("Written!");

        } catch (Exception exception) {
            System.out.println("Unable to append to file: " + exception);
        }
    }

    /**
     * A method which loads the contents of the file
     * @param filename The file that data needs to be retrieved from.
     * @return all the lines that are in the file.
     */
    public ArrayList<String> loadFile(Filename filename) {
        ArrayList<String> allLines = new ArrayList<>();
        File file = new File(this.filenames.get(filename));
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                allLines.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
        }
        
        return allLines;
    }
}
