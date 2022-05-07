package com.company.FileHandling.Savers;


import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Study.Course;
import com.google.gson.Gson;

import java.util.ArrayList;

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
}
