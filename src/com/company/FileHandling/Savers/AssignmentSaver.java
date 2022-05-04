package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.Assignment;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AssignmentSaver {
    private final FileHandler fileHandler = new FileHandler();

    public void saveAllAssignments(ArrayList<Assignment> assignments) {
        Gson gson = new Gson();
        String serialisedAssignments = gson.toJson(assignments);
        fileHandler.writeFile(Filename.ASSIGNMENTS, serialisedAssignments);
        System.out.println("Assignments Updated!");
    }
}