package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.Models.Study.Course;
import com.company.Models.Users.CourseAdministrator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that handles the saving of course administrators
 */
public class CourseAdministratorSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Saves all the course administrators to a file.
     * @param courseAdministrators all the course administrators to save.
     */
    public void saveAllCourseAdminstrators(ArrayList<CourseAdministrator> courseAdministrators) {
        Gson gson = new Gson();
        String serialisedCourseAdministrators = gson.toJson(courseAdministrators);
        fileHandler.writeFile(Filename.COURSEADMINISTRATORS, serialisedCourseAdministrators);
        System.out.println("Course Administrators Updated!");
    }

    /**
     * Saves a specific course administrator to the list of course administrators.
     * @param courseAdministrator the course administrator to save.
     */
    public void saveCourseAdministrator(CourseAdministrator courseAdministrator) {
        ArrayList<CourseAdministrator> courseAdministrators = new CourseAdministratorLoader().loadAllCourseAdministrators();
        boolean alreadyExists = false;

        for (int i = 0; i < courseAdministrators.size(); i++) {
            if (Objects.equals(courseAdministrators.get(i).getUsername(), courseAdministrator.getUsername())) {
                courseAdministrators.set(i, courseAdministrator);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) {
            courseAdministrators.add(courseAdministrator);
        }

        this.saveAllCourseAdminstrators(courseAdministrators);
    }
}
