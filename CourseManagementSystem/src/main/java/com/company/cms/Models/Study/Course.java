package com.company.cms.Models.Study;

import java.util.ArrayList;

/**
 * Model that represents the courses at the college or University, such as BSc Computer Science.
 */
public class Course {
    private String name;
    private ArrayList<CourseModule> courseModules;
    private boolean isActive;

    public Course(String name, ArrayList<CourseModule> courseModules, boolean isActive) {
        this.name = name;
        this.courseModules = courseModules;
        this.isActive = isActive;
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
     * @return whether the course is running. True will mean that it is active, false will mean that it has been
     * cancelled, so it is inactive.
     */
    public boolean isActive() {
        return isActive;
    }
}
