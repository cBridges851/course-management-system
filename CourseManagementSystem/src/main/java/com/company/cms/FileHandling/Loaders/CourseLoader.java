package com.company.cms.FileHandling.Loaders;

import com.company.cms.FileHandling.FileHandler;
import com.company.cms.FileHandling.Filename;
import com.company.cms.Models.Study.Course;
import com.company.cms.Models.Study.CourseModule;

import java.util.ArrayList;

public class CourseLoader {
    private final FileHandler fileHandler = new FileHandler();
    private final CourseModuleLoader courseModuleLoader = new CourseModuleLoader();

    public ArrayList<Course> loadAllCourses() {
        try {
            ArrayList<String> allCoursesFromFileArray = this.fileHandler.loadFile(Filename.COURSES);
            ArrayList<Course> allCourses = new ArrayList<>();

            for (String course: allCoursesFromFileArray) {
                String[] parts = course.split(",");

                String[] moduleCodes = parts[1].split("  ");
                ArrayList<CourseModule> courseModules = new ArrayList<>();

                for (String moduleCode: moduleCodes) {
                    courseModules.add(this.courseModuleLoader.loadCourseModule(moduleCode.trim()));
                }

                allCourses.add(new Course(parts[0], courseModules, Boolean.parseBoolean(parts[2].trim())));
            }

            return allCourses;
        } catch (Exception exception) {
            System.out.println("Unable to load all courses: " + exception);
        }

        System.out.println("No courses returned");
        return null;
    }
}
