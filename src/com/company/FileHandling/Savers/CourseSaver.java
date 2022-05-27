package com.company.FileHandling.Savers;


import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.FileHandling.Loaders.CourseLoader;
import com.company.Models.Study.Course;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that handles the saving of courses
 */
public class CourseSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * A method which saves all the courses to a file
     * @param courses All the courses in the program to be saved
     */
    public void saveAllCourses(ArrayList<Course> courses) {
        Gson gson = new Gson();
        String serialisedCourses = gson.toJson(courses);
        fileHandler.writeFile(Filename.COURSES, serialisedCourses);
        System.out.println("Courses Updated!");
    }

    /**
     * Saves a specific course to the list of courses.
     * @param course the course to save.
     */
    public void saveCourse(Course course) {
        ArrayList<Course> courses = new CourseLoader().loadAllCourses();
        boolean alreadyExists = false;

        for (int i = 0; i < courses.size(); i++) {
            if (Objects.equals(courses.get(i).getCourseId(), course.getCourseId())) {
                courses.set(i, course);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) {
            courses.add(course);
        }

        this.saveAllCourses(courses);
    }

    /**
     * Deletes a course from the list of courses in the system and saves the list.
     * @param course the course to delete.
     */
    public void deleteCourseAndSave(Course course) {
        ArrayList<Course> courses = new CourseLoader().loadAllCourses();
        courses.removeIf(courseItem -> Objects.equals(courseItem.getCourseId(), course.getCourseId()));
        this.saveAllCourses(courses);
    }
}
