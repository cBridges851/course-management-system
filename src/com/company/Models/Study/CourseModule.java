package com.company.Models.Study;

import java.util.HashSet;

/**
 * Model that represents a module that could be on a course. For example, 5CS019 - Object-Oriented Programming
 */
public class CourseModule {
    private String courseModuleCode;
    private String name;
    private int level;
    private HashSet<String> instructorNames;
    private boolean isMandatory;
    private HashSet<String> assignmentIds;
    private HashSet<String> studentNames;

    public CourseModule(String courseModuleCode, String name, int level, HashSet<String> instructorNames, boolean isMandatory,
                        HashSet<String> assignmentIds, HashSet<String> studentNames) {
        this.courseModuleCode = courseModuleCode;
        this.name = name;
        this.level = level;
        this.instructorNames = instructorNames;
        this.isMandatory = isMandatory;
        this.assignmentIds = assignmentIds;
        this.studentNames = studentNames;
    }

    /**
     * Gets the code that identifies the module.
     * @return the code that identifies the module.
     */
    public String getCourseModuleCode() {
        return this.courseModuleCode;
    }

    /**
     * Gets the name of the course module.
     * @return the name of the course module.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Changes the name of the course module.
     * @param name the new name for the course module.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the level of the course module
     * @return the level of the course module. E.g. 4, 5 or 6.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Gets the name of the instructor who is teaching the course module
     * @return the instructor who is teaching the course module.
     */
    public HashSet<String> getInstructorNames() {
        return this.instructorNames;
    }

    /**
     * Adds an instructor to the module
     * @param instructorName the name of the instructor to add to the course module.
     */
    public void addInstructorName(String instructorName) {
        this.instructorNames.add(instructorName);
    }

    /**
     * Gets whether or not students must undertake this module.
     * @return whether or not students must do this module. True indicates that it must be completed,
     * false indicates that it is optional.
     */
    public boolean getIsMandatory() {
        return this.isMandatory;
    }

    /**
     * A method which gets the assignments that have to be completed as part of the module.
     * @return the assignments that must be completed as part of the module.
     */
    public HashSet<String> getAssignmentIds() {
        return this.assignmentIds;
    }

    /**
     * A method which gets the names of the students who are undertaking the module.
     * @return the names of the students who are undertaking the module.
     */
    public HashSet<String> getStudentNames() {
        return this.studentNames;
    }
}
