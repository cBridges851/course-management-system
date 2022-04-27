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
     * Retrieves the name of the course
     * @return the name of the course
     */
    public String getName() {
        return this.name;
    }

    /**
     * Changes the name of the course
     * @param name the name to change the course's name to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the modules that are on the course
     * @return the modules that are on the course
     */
    public ArrayList<CourseModule> getCourseModules() {
        return courseModules;
    }

    /**
     * Adds a course module to the list
     * @param courseModule the course module to add to the course
     */
    public void addCourseModule(CourseModule courseModule) {
        this.courseModules.add(courseModule);
    }
    /**
     * Gets whether the course is running or not.
     * @return whether the course is running. True will mean that it is available, false will mean that it has been
     * cancelled, so it is unavailable.
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Allows the course to be cancelled and reopened.
     * @param isAvailable whether the course is available or not
     */
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
