package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Users.Student;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A class that handles the saving of instructors.
 */
public class StudentSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Saves all the instructors in the system.
     * @param students all the instructors to save.
     */
    public void saveAllStudents(ArrayList<Student> students) {
        Gson gson = new Gson();
        String serialisedInstructors = gson.toJson(students);
        fileHandler.writeFile(Filename.STUDENTS, serialisedInstructors);
        System.out.println("Students Updated!");
    }
}
