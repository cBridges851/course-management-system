package com.company.cms.FileHandling.Loaders;

import com.company.cms.FileHandling.FileHandler;
import com.company.cms.FileHandling.Filename;
import com.company.cms.Models.Study.Assignment;
import com.company.cms.Models.Study.CourseModule;

import java.util.ArrayList;
import java.util.Collections;

public class CourseModuleLoader {
    private final FileHandler fileHandler = new FileHandler();
    private final AssignmentLoader assignmentLoader = new AssignmentLoader();

    public CourseModule loadCourseModule(String courseModuleCode) {
        try {
            ArrayList<String> allModulesFromFile = this.fileHandler.loadFile(Filename.COURSEMODULES);
            for (String courseModule: allModulesFromFile) {
                String[] parts = courseModule.split(",");

                if (parts[0].equals(courseModuleCode)) {
                    String[] assignmentIds = parts[5].split(" ");
                    ArrayList<Assignment> assignments = new ArrayList<>();

                    for (String assignmentId: assignmentIds) {
                        assignments.add(this.assignmentLoader.loadAssignment(assignmentId));
                    }

                    ArrayList<String> students = new ArrayList<>();

                    Collections.addAll(students, parts[6].split(" "));

                    return new CourseModule(parts[0], parts[1], Integer.parseInt(parts[2].trim()), parts[3],
                            Boolean.parseBoolean(parts[4].trim()), assignments, students);
                }
            }
        } catch (Exception exception) {
            System.out.println("Unable to load course module: " + exception);
        }

        return null;
    }
}
