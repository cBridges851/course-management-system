package com.company.cms.FileHandling.Loaders;

import com.company.cms.FileHandling.FileHandler;
import com.company.cms.FileHandling.Filename;
import com.company.cms.Models.Study.Assignment;

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
            ArrayList<String> allAssignmentsFromFile = this.fileHandler.loadFile(Filename.ASSIGNMENTS);

            for (String assignment: allAssignmentsFromFile) {
                String[] parts = assignment.split(",");

                if (parts[0].equals(assignmentId)) {
                    return new Assignment(parts[0], parts[1], Integer.parseInt(parts[2].trim()));
                }
            }

        } catch (Exception exception) {
            System.out.println("Unable to load assignment: " + exception);
        }

        System.out.println("Assignment " + assignmentId + " not found");
        return null;
    }
}
