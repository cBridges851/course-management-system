package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Users.CourseAdministrator;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CourseAdministratorSaver {
    private final FileHandler fileHandler = new FileHandler();

    public void saveAllCourseAdminstrators(ArrayList<CourseAdministrator> courseAdministrators) {
        Gson gson = new Gson();
        String serialisedCourseAdministrators = gson.toJson(courseAdministrators);
        fileHandler.writeFile(Filename.COURSEADMINISTRATORS, serialisedCourseAdministrators);
        System.out.println("Course Administrators Updated!");
    }
}
