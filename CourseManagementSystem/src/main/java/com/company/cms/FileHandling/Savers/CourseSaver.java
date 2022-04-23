package com.company.cms.FileHandling.Savers;


import com.company.cms.FileHandling.FileHandler;
import com.company.cms.FileHandling.Filename;
import com.company.cms.Models.Study.Course;
import com.company.cms.Models.Study.CourseModule;

import java.util.ArrayList;

/**
 * A class that handles the saving of courses
 */
public class CourseSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * A method which saves a course to the file
     * @param course The individual course to be saved to the file
     */
    public void saveCourse(Course course) {
        StringBuilder line = new StringBuilder(course.getName() + ", ");

        for (CourseModule courseModule: course.getCourseModules()) {
            if (courseModule != null) {
                line.append(courseModule.getCourseModuleCode()).append("  ");
            }
        }

        line.append(", ").append(course.isAvailable()).append("\n");
        fileHandler.appendFile(Filename.COURSES, line.toString());
    }

    /**
     * A method which saves all the courses to a file
     * @param allCourses All the courses in the program to be saved
     */
    public void saveAllCourses(ArrayList<Course> allCourses) {
        fileHandler.clearFile(Filename.COURSES);
        for (Course course: allCourses) {
            StringBuilder line = new StringBuilder(course.getName() + ", ");

            for (CourseModule courseModule: course.getCourseModules()) {
                if (courseModule != null) {
                    line.append(courseModule.getCourseModuleCode()).append("  ");
                }
            }

            line.append(", ").append(course.isAvailable()).append("\n");
            fileHandler.appendFile(Filename.COURSES, line.toString());
        }
    }
}
