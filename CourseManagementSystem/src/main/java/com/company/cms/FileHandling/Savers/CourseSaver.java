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
    private FileHandler fileHandler = new FileHandler();

    /**
     * @param course The individual course to be saved to the file
     */
    public void saveCourse(Course course) {
        String line = course.getName() + ", ";

        for (CourseModule courseModule: course.getCourseModules()) {
            if (courseModule != null) {
                line += courseModule.getCourseModuleCode() + "  ";
            }
        }

        line += ", " + course.isAvailable() + "\n";
        fileHandler.appendFile(Filename.COURSES, line);
    }

    /**
     * @param allCourses All the courses in the program to be saved
     */
    public void saveAllCourses(ArrayList<Course> allCourses) {
        fileHandler.clearFile(Filename.COURSES);
        for (Course course: allCourses) {
            String line = course.getName() + ", ";

            for (CourseModule courseModule: course.getCourseModules()) {
                if (courseModule != null) {
                    line += courseModule.getCourseModuleCode() + "  ";
                }
            }

            line += ", " + course.isAvailable() + "\n";
            fileHandler.appendFile(Filename.COURSES, line);
        }
    }
}
