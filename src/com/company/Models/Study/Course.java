package com.company.Models.Study;

import java.util.HashSet;

/**
 * Model that represents the courses at the college or university, such as BSc Computer Science.
 */
public class Course {
    private String name;
    private HashSet<String> courseModuleCodes;
    private boolean isAvailable;

    public Course(String name, HashSet<String> courseModuleCodes, boolean isAvailable) {
        this.name = name;
        this.courseModuleCodes = courseModuleCodes;
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
     * Retrieves the modules that are on the course
     * @return the modules that are on the course
     */
    public HashSet<String> getCourseModuleCodes() {
        return courseModuleCodes;
    }

    /**
     * Gets whether the course is running or not.
     * @return whether the course is running. True will mean that it is available, false will mean that it has been
     * cancelled, so it is unavailable.
     */
    public boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * Changes the name of the course
     * @param name the name to change the course's name to
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Adds a course module to the list
     * @param courseModuleCode the course module to add to the course
     */
    public void addCourseModule(String courseModuleCode) {
        this.courseModuleCodes.add(courseModuleCode);
    }


    /**
     * Removes a course module from a course
     * @param courseModuleCode the course module to remove from the course.
     */
    public void removeCourseModule(String courseModuleCode) {
        this.courseModuleCodes.remove(courseModuleCode);
    }

    /**
     * Allows the course to be cancelled and reopened.
     * @param isAvailable whether the course is available or not
     */
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
