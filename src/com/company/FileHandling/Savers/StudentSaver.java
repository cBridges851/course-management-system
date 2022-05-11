package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Users.Student;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StudentSaver {
    private final FileHandler fileHandler = new FileHandler();

    public void saveAllStudents(ArrayList<Student> students) {
        Gson gson = new Gson();
        String serialisedInstructors = gson.toJson(students);
        fileHandler.writeFile(Filename.STUDENTS, serialisedInstructors);
        System.out.println("Students Updated!");
    }
}
