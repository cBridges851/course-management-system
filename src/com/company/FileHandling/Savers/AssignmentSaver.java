package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.Assignment;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A class that handles the saving of assignments.
 */
public class AssignmentSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Saves all the assignments in the system to a file.
     * @param assignments all the assignments to be saved.
     */
    public void saveAllAssignments(ArrayList<Assignment> assignments) {
        Gson gson = new Gson();
        String serialisedAssignments = gson.toJson(assignments);
        fileHandler.writeFile(Filename.ASSIGNMENTS, serialisedAssignments);
        System.out.println("Assignments Updated!");
    }
}
