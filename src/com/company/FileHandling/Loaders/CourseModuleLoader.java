package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.Assignment;
import com.company.Models.Study.CourseModule;

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

    /**
     * Retrieves all the course modules there are in the system
     * @return all the course modules, or null if they were unable to be retrieved
     */
    public ArrayList<CourseModule> loadAllCourseModules() {
        try {
            ArrayList<String> allCourseModulesFromFileArray = this.fileHandler.loadFile(Filename.COURSEMODULES);
            ArrayList<CourseModule> allCourseModules = new ArrayList<>();

            for (String courseModule: allCourseModulesFromFileArray) {
                String[] parts = courseModule.split(",");

                allCourseModules.add(this.loadCourseModule(parts[0]));
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
            ArrayList<String> allCourseModulesFromFile = this.fileHandler.loadFile(Filename.COURSEMODULES);
            for (String courseModule: allCourseModulesFromFile) {
                String[] parts = courseModule.split(",");

                if (parts[0].equals(courseModuleCode)) {
                    String[] assignmentIds = parts[5].split("  ");
                    ArrayList<Assignment> assignments = new ArrayList<>();

                    for (String assignmentId: assignmentIds) {
                        if (!assignmentId.equals(" ")) {
                            assignments.add(this.assignmentLoader.loadAssignment(assignmentId.trim()));
                        }
                    }

                    HashSet<String> studentNames = new HashSet<>();

                    if (parts.length == 7) {
                        Collections.addAll(studentNames, parts[6].split("  "));
                    }

                    return new CourseModule(parts[0], parts[1], Integer.parseInt(parts[2].trim()), parts[3],
                            Boolean.parseBoolean(parts[4].trim()), assignments, studentNames);
                }
            }
        } catch (Exception exception) {
            System.out.println("Unable to load course module: " + exception);
        }

        System.out.println("Course Module " + courseModuleCode + " not found");
        return null;
    }
}
