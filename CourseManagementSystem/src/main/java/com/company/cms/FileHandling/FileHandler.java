package com.company.cms.FileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Interacts with the files that are used to keep the program's data persistent
 */
public class FileHandler {
    public void saveFile(Filename filename) {

    }

    /**
     * @param filename The file that data needs to be retrieved from.
     * @return all the lines that are in the file.
     */
    public ArrayList<String> loadFile(Filename filename) {
        ArrayList<String> allLines = new ArrayList<>();
        File file = null;

        if (filename == Filename.ASSIGNMENTS) {
            file = new File("./src/main/java/com/company/cms/FileHandling/Files/Study/assignments.csv");
        } else if (filename == Filename.COURSEMODULES) {
            file = new File("./src/main/java/com/company/cms/FileHandling/Files/Study/course-modules.csv");
        } else if (filename == Filename.COURSES) {
            file = new File("./src/main/java/com/company/cms/FileHandling/Files/Study/courses.csv");
        } else if (filename == Filename.RESULTS) {
            file = new File("./src/main/java/com/company/cms/FileHandling/Files/Study/results.csv");
        } else if (filename == Filename.COURSEADMINISTRATORS) {
            file = new File("./src/main/java/com/company/cms/FileHandling/Files/Users/course-administrators.csv");
        } else if (filename == Filename.INSTRUCTORS) {
            file = new File("./src/main/java/com/company/cms/FileHandling/Files/Users/instructors.csv");
        } else if (filename == Filename.STUDENTS) {
            file = new File("./src/main/java/com/company/cms/FileHandling/Files/Users/students.csv");
        }
        
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
