package com.example.cms.Models.Study;

import com.example.cms.Models.Users.Instructor;

import java.util.ArrayList;

/**
 * Model that represents a module that could be on a course. For example, 5CS019 - Object-Oriented Programming
 */
public class CourseModule {
    private String courseModuleCode;
    private String name;
    private int level;
    private Instructor instructor;
    private boolean isMandatory;
    private ArrayList<Assignment> assignments;
    private ArrayList<StudentResult> studentResults;

    public CourseModule(String courseModuleCode, String name, int level, Instructor instructor, boolean isMandatory,
                        ArrayList<Assignment> assignments, ArrayList<StudentResult> studentResults) {
        this.courseModuleCode = courseModuleCode;
        this.name = name;
        this.level = level;
        this.instructor = instructor;
        this.isMandatory = isMandatory;
        this.assignments = assignments;
        this.studentResults = studentResults;
    }

    /**
     * @return the code that identifies the module.
     */
    public String getCourseModuleCode() {
        return this.courseModuleCode;
    }

    /**
     * @return the name of the course module.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the level of the course module. E.g. 4, 5 or 6.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * @return the instructor who is teaching the module.
     */
    public Instructor getInstructor() {
        return this.instructor;
    }

    /**
     * @return whether or not students must do this module. True indicates that it must be completed,
     * false indicates that it is optional.
     */
    public boolean getIsMandatory() {
        return this.isMandatory;
    }

    /**
     * @return the assignments that must be completed as part of the module.
     */
    public ArrayList<Assignment> getAssignments() {
        return this.assignments;
    }

    /**
     * @return The results that students have got on the module.
     */
    public ArrayList<StudentResult> getStudentResults() {
        return this.studentResults;
    }
}
