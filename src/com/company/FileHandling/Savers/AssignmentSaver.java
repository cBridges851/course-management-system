package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.FileHandling.Loaders.AssignmentLoader;
import com.company.Models.Study.Assignment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

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

    /**
     * Saves a specific assignment to the list of assignments.
     * @param assignment the assignment to save.
     */
    public void saveAssignment(Assignment assignment) {
        ArrayList<Assignment> assignments = new AssignmentLoader().loadAllAssignments();
        boolean alreadyExists = false;

        for (int i = 0; i < assignments.size(); i++) {
            if (Objects.equals(assignments.get(i).getAssignmentId(), assignment.getAssignmentId())) {
                assignments.set(i, assignment);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) {
            assignments.add(assignment);
        }

        this.saveAllAssignments(assignments);
    }
}
