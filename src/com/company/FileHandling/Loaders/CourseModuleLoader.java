package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.CourseModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *  Retrieves and converts course modules from the course-modules.csv file
 */
public class CourseModuleLoader {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Retrieves all the course modules there are in the system
     * @return all the course modules, or null if they were unable to be retrieved
     */
    public ArrayList<CourseModule> loadAllCourseModules() {
        String deserialisedCourseAdministrators = this.fileHandler.loadFile(Filename.COURSEMODULES);
        Gson gson = new Gson();
        Type courseModuleListType = new TypeToken<ArrayList<CourseModule>>(){}.getType();
        ArrayList<CourseModule> courseModules = null;

        try {
            courseModules = gson.fromJson(deserialisedCourseAdministrators, courseModuleListType);
        } catch (Exception exception) {
            System.out.println("Invalid JSON");
        }

        if (courseModules == null) {
            return new ArrayList<>();
        }

        return courseModules;
    }

    /**
     * Loads a specific course module
     * @param courseModuleCode The (unique) course module code of the course module to retrieve
     * @return the course module
     */
    public CourseModule loadCourseModule(String courseModuleCode) {
        ArrayList<CourseModule> allCourseModules = this.loadAllCourseModules();

        for (CourseModule courseModule: allCourseModules) {
            if (courseModule.getCourseModuleCode().equals(courseModuleCode)) {
                return courseModule;
            }
        }

        return null;
    }
}
