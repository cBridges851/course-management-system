package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

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
        ArrayList<Course> courses = null;

        try {
            courses = gson.fromJson(deserialisedCoursesList, coursesListType);
        } catch (Exception exception) {
            System.out.println("Invalid JSON");
        }

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

    /**
     * Loads a specific course
     * @param courseId the id of the course to load
     * @return the course the user requested, or null if it is not found
     */
    public Course loadCourse(String courseId) {
        ArrayList<Course> allCourses = this.loadAllCourses();

        for (Course course: allCourses) {
            if (Objects.equals(course.getCourseId(), courseId)) {
                return course;
            }
        }

        return null;
    }
}
