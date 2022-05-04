package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.CourseModule;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A class that handles the saving of the modules that are on the courses.
 */
public class CourseModuleSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Saves all the course modules there are in the system.
     * @param courseModules The list of course modules to be saved.
     */
    public void saveAllCourseModules(ArrayList<CourseModule> courseModules) {
        Gson gson = new Gson();
        String serialisedCourseModules = gson.toJson(courseModules);
        new FileHandler().writeFile(Filename.COURSEMODULES, serialisedCourseModules);
    }
}
