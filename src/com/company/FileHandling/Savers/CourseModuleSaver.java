package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.Models.Study.CourseModule;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

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
        fileHandler.writeFile(Filename.COURSEMODULES, serialisedCourseModules);
        System.out.println("Course Modules Updated!");
    }

    /**
     * Saves a specific course module to the list of course modules.
     * @param courseModule the course module to save.
     */
    public void saveCourseModule(CourseModule courseModule) {
        ArrayList<CourseModule> allCourseModules = new CourseModuleLoader().loadAllCourseModules();
        boolean alreadyExists = false;

        for (int i = 0; i < allCourseModules.size(); i++) {
            if (Objects.equals(allCourseModules.get(i).getCourseModuleCode(), courseModule.getCourseModuleCode())) {
                allCourseModules.set(i, courseModule);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) {
            allCourseModules.add(courseModule);
        }

        this.saveAllCourseModules(allCourseModules);
    }

    /**
     * Removes a course module from the list of course modules in the system and saves
     * @param courseModule the course module to remove
     */
    public void removeCourseModuleAndSave(CourseModule courseModule) {
        ArrayList<CourseModule> allCourseModules = new CourseModuleLoader().loadAllCourseModules();
        allCourseModules.removeIf(courseModuleItem -> courseModuleItem.getCourseModuleCode().equals(courseModule.getCourseModuleCode()));
        this.saveAllCourseModules(allCourseModules);
    }
}
