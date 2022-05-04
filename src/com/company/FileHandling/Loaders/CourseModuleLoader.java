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
    public ArrayList<CourseModule> loadAllCourseModules() throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * Loads a specific course module
     * @param courseModuleCode The (unique) course module code of the course module to retrieve
     * @return the course module
     */
    public CourseModule loadCourseModule(String courseModuleCode) throws Exception {
        throw new Exception("Not implemented yet!");
    }
}
