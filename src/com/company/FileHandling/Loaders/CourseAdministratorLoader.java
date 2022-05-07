package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Users.CourseAdministrator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CourseAdministratorLoader {
    private FileHandler fileHandler = new FileHandler();

    public ArrayList<CourseAdministrator> loadAllCourseAdmistrators() {
        String deserialisedCourseAdministrators = this.fileHandler.loadFile(Filename.COURSEADMINISTRATORS);
        Gson gson = new Gson();
        Type courseAdminstratorListType = new TypeToken<ArrayList<CourseAdministrator>>(){}.getType();
        ArrayList<CourseAdministrator> courseAdministrators =
                gson.fromJson(deserialisedCourseAdministrators, courseAdminstratorListType);

        if (courseAdministrators == null) {
            return new ArrayList<CourseAdministrator>();
        }

        return courseAdministrators;
    }
}
