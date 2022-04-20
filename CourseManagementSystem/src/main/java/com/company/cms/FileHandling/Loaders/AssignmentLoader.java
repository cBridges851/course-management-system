package com.company.cms.FileHandling.Loaders;

import com.company.cms.FileHandling.FileHandler;
import com.company.cms.FileHandling.Filename;
import com.company.cms.Models.Study.Assignment;

import java.util.ArrayList;

public class AssignmentLoader {
    private FileHandler fileHandler = new FileHandler();

    public Assignment loadAssignment(String assignmentId) throws Exception {
        try {
            ArrayList<String> allAssignmentsFromFile = this.fileHandler.loadFile(Filename.ASSIGNMENTS);

            for (String assignment: allAssignmentsFromFile) {
                String[] parts = assignment.split(",");

                if (parts[0].equals(assignmentId)) {
                    return new Assignment(parts[0], parts[1], Integer.parseInt(parts[3].trim()));
                }
            }

        } catch (Exception exception) {
            System.out.println("Unable to load assignment: " + exception);
        }
        return null;
    }
}
