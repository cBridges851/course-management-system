package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.Assignment;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Retrieves and converts assignments from the assignments.csv file
 */
public class AssignmentLoader {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Loads a specific assignment
     * @param assignmentId The id of the assignment to retrieve
     * @return the assignment
     */
    public Assignment loadAssignment(String assignmentId) {
        try {
            String allAssignmentsFromFile = this.fileHandler.loadFile(Filename.ASSIGNMENTS);

            Gson gson = new Gson();


        } catch (Exception exception) {
            System.out.println("Unable to load assignment: " + exception);
        }

        System.out.println("Assignment " + assignmentId + " not found");
        return null;
    }
}
