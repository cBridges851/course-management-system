package com.company.cms.Models.Study;

import java.util.ArrayList;

/**
 * Model that represents the courses at the college or university, such as BSc Computer Science.
 */
public class Course {
    private String name;
    private ArrayList<CourseModule> courseModules;
    private boolean isAvailable;

    public Course(String name, ArrayList<CourseModule> courseModules, boolean isAvailable) {
        this.name = name;
        this.courseModules = courseModules;
        this.isAvailable = isAvailable;
    }

    /**
     * @return the name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * @return the modules that are on the course
     */
    public ArrayList<CourseModule> getCourseModules() {
        return courseModules;
    }

    /**
     * @return whether the course is running. True will mean that it is available, false will mean that it has been
     * cancelled, so it is unavailable.
     */
    public boolean isAvailable() {
        return isAvailable;
    }
}
