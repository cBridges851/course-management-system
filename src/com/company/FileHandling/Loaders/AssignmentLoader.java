package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.Assignment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Retrieves and converts assignments from persistent data storage
 */
public class AssignmentLoader {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Loads all the assignments that are in the system
     * @return all the assignments in the system
     */
    public ArrayList<Assignment> loadAllAssignments() {
        String deserialisedAssignmentList = this.fileHandler.loadFile(Filename.ASSIGNMENTS);
        Gson gson = new Gson();
        Type assignmentListType = new TypeToken<ArrayList<Assignment>>(){}.getType();
        ArrayList<Assignment> assignments = null;

        try {
            assignments = gson.fromJson(deserialisedAssignmentList, assignmentListType);
        } catch (Exception exception) {
            System.out.println("Invalid JSON");
        }

        if (assignments == null) {
            return new ArrayList<>();
        }

        return assignments;
    }

    /**
     * Loads a specific assignment
     * @param assignmentId The id of the assignment to retrieve
     * @return the assignment
     */
    public Assignment loadAssignment(String assignmentId) {
        ArrayList<Assignment> allAssignments = this.loadAllAssignments();

        for (Assignment assignment: allAssignments) {
            if (assignment.getAssignmentId().equals(assignmentId)) {
                return assignment;
            }
        }

        System.out.println("Assignment " + assignmentId + " not found");
        return null;
    }
}
