package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *  Retrieves and converts courses from the courses.csv file
 */
public class CourseLoader {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Loads all courses
     * @return all the courses saved in the courses.csv file
     */
    public ArrayList<Course> loadAllCourses() {
        String deserialisedCoursesList = this.fileHandler.loadFile(Filename.COURSES);
        Gson gson = new Gson();
        Type coursesListType = new TypeToken<ArrayList<Course>>(){}.getType();
        ArrayList<Course> courses = gson.fromJson(deserialisedCoursesList, coursesListType);

        if (courses == null) {
            return new ArrayList<>();
        }

        return courses;
    }

    /**
     * Just loads the courses that are available, so can be enrolled on.
     * @return the courses that are available.
     */
    public ArrayList<Course> loadAllAvailableCourses() {
        ArrayList<Course> allCourses = this.loadAllCourses();
        ArrayList<Course> allAvailableCourses = new ArrayList<>();

        for (Course course: allCourses) {
            if (course.getIsAvailable()) {
                allAvailableCourses.add(course);
            }
        }

        return allAvailableCourses;
    }
}
