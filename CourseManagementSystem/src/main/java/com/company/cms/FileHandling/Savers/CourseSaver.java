package com.company.cms.FileHandling.Savers;


import com.company.cms.FileHandling.FileHandler;
import com.company.cms.FileHandling.Filename;
import com.company.cms.Models.Study.Course;
import com.company.cms.Models.Study.CourseModule;

public class CourseSaver {
    private FileHandler fileHandler = new FileHandler();

    public void saveCourse(Course course) {
        String line = course.getName() + ", ";

        for (CourseModule courseModule: course.getCourseModules()) {
            line += courseModule.getName() + "  ";
        }

        line += ", " + course.isAvailable() + "\n";
        fileHandler.appendFile(Filename.COURSES, line);
    }
}
