package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Users.CourseAdministrator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Retrieves and converts course administrators from persistent data storage
 */
public class CourseAdministratorLoader {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Loads all the course administrators that are in the system
     * @return all the course administrators in the system
     */
    public ArrayList<CourseAdministrator> loadAllCourseAdministrators() {
        String deserialisedCourseAdministrators = this.fileHandler.loadFile(Filename.COURSEADMINISTRATORS);
        Gson gson = new Gson();
        Type courseAdministratorListType = new TypeToken<ArrayList<CourseAdministrator>>(){}.getType();
        ArrayList<CourseAdministrator> courseAdministrators = null;

        try {
            courseAdministrators = gson.fromJson(deserialisedCourseAdministrators, courseAdministratorListType);
        } catch (Exception exception) {
            System.out.println("Invalid JSON");
        }

        return Objects.requireNonNullElseGet(courseAdministrators, ArrayList::new);

    }

    /**
     * Loads a specific course administrator
     * @param username the username (identifier) of a course administrator.
     * @return all the course administrator data for that course administrator.
     */
    public CourseAdministrator loadCourseAdministrator(String username) {
        ArrayList<CourseAdministrator> allCourseAdministrators = this.loadAllCourseAdministrators();

        for (CourseAdministrator courseAdministrator: allCourseAdministrators) {
            if (courseAdministrator.getUsername().equals(username)) {
                return courseAdministrator;
            }
        }

        return null;
    }
}
