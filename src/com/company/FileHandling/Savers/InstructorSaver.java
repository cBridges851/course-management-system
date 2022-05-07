package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Users.Instructor;
import com.google.gson.Gson;

import java.util.ArrayList;

public class InstructorSaver {
    private final FileHandler fileHandler = new FileHandler();
    public void saveAllInstructors(ArrayList<Instructor> instructors) {
        Gson gson = new Gson();
        String serialisedInstructors = gson.toJson(instructors);
        fileHandler.writeFile(Filename.INSTRUCTORS, serialisedInstructors);
        System.out.println("Instructors Updated!");
    }
}
