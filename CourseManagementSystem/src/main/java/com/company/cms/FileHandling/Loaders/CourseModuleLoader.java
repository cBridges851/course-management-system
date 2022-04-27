package com.company.cms.FileHandling.Loaders;

import com.company.cms.FileHandling.FileHandler;
import com.company.cms.FileHandling.Filename;
import com.company.cms.Models.Study.Assignment;
import com.company.cms.Models.Study.CourseModule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 *  Retrieves and converts course modules from the course-modules.csv file
 */
public class CourseModuleLoader {
    private final FileHandler fileHandler = new FileHandler();
    private final AssignmentLoader assignmentLoader = new AssignmentLoader();

    public ArrayList<CourseModule> loadAllCourseModules() {
        try {
            ArrayList<String> allCourseModulesFromFileArray = this.fileHandler.loadFile(Filename.COURSEMODULES);
            ArrayList<CourseModule> allCourseModules = new ArrayList<>();

            for (String courseModule: allCourseModulesFromFileArray) {
                String[] parts = courseModule.split(",");

                String[] assignmentIds = parts[5].split("  ");
                ArrayList<Assignment> assignments = new ArrayList<>();

                for (String assignmentId : assignmentIds) {
                    if (!assignmentId.equals(" ")) {
                        assignments.add(this.assignmentLoader.loadAssignment(assignmentId.trim()));
                    }
                }

                String[] studentNamesFromString = parts[6].split(",");
                HashSet<String> studentNames = new HashSet<>();

                studentNames.addAll(Arrays.asList(studentNamesFromString));


                allCourseModules.add(new CourseModule(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim()), parts[3].trim(),
                        Boolean.parseBoolean(parts[4].trim()), assignments, studentNames));
            }

            return allCourseModules;
        } catch (Exception exception) {
            System.out.println("Unable to load all course modules" + exception);
        }

        System.out.println("No course modules returned");
        return null;
    }

    /**
     * Loads a specific course module
     * @param courseModuleCode The (unique) course module code of the course module to retrieve
     * @return the course module
     */
    public CourseModule loadCourseModule(String courseModuleCode) {
        try {
            ArrayList<String> allModulesFromFile = this.fileHandler.loadFile(Filename.COURSEMODULES);
            for (String courseModule: allModulesFromFile) {
                String[] parts = courseModule.split(",");

                if (parts[0].equals(courseModuleCode)) {
                    String[] assignmentIds = parts[5].split("  ");
                    ArrayList<Assignment> assignments = new ArrayList<>();

                    for (String assignmentId: assignmentIds) {
                        if (!assignmentId.equals(" ")) {
                            assignments.add(this.assignmentLoader.loadAssignment(assignmentId.trim()));
                        }
                    }

                    HashSet<String> students = new HashSet<>();

                    Collections.addAll(students, parts[6].split(" "));

                    return new CourseModule(parts[0], parts[1], Integer.parseInt(parts[2].trim()), parts[3],
                            Boolean.parseBoolean(parts[4].trim()), assignments, students);
                }
            }
        } catch (Exception exception) {
            System.out.println("Unable to load course module: " + exception);
        }

        System.out.println("Course Module " + courseModuleCode + " not found");
        return null;
    }
}
