package com.example.cms.FileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHandler {
    public void saveFile(Filename filename) {

    }

    public void loadFile(Filename filename) {
        if (filename == Filename.COURSE) {
            File file = new File("./src/main/java/com/example/cms/FileHandling/Files/courses.csv");
            System.out.println(file.getAbsoluteFile());
            try {
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String currentLine = scanner.nextLine();

                    String[] parts = currentLine.split(",");

                    for (String part: parts) {
                        System.out.println(part);
                    }
                }

                scanner.close();
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("File not found");
            }
        }
    }
}
