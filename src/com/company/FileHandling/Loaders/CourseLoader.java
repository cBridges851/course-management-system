package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *  Retrieves and converts courses from the courses.csv file
 */
public class CourseLoader {
    private final FileHandler fileHandler = new FileHandler();
    private final CourseModuleLoader courseModuleLoader = new CourseModuleLoader();

    /**
     * Loads all courses
     * @return all the courses saved in the courses.csv file
     */
    public ArrayList<Course> loadAllCourses() {
        String deserialisedCoursesList = this.fileHandler.loadFile(Filename.COURSES);
        Gson gson = new Gson();
        Type coursesListType = new TypeToken<ArrayList<Course>>(){}.getType();
        return gson.fromJson(deserialisedCoursesList, coursesListType);
    }
}
