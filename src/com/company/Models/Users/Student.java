package com.company.Models.Users;

import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
import com.company.Models.Study.ModuleResult;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Model that represents the student, which is a type of user who partakes in the modules in courses.
 */
public class Student extends User {
    private int year;
    private int level;
    private Course course;
    private ArrayList<CourseModule> completedCourseModules;
    private CourseModule[] currentCourseModules = new CourseModule[8];
    private ArrayList<ModuleResult> moduleResults;

    public Student(String username, String password, String firstName, String middleName, String lastName,
                   Calendar dateOfBirth, int year, int level, Course course, ArrayList<CourseModule> completedCourseModules,
                   CourseModule[] currentCourseModules) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
        this.year = year;
        this.level = level;
        this.course = course;
        this.completedCourseModules = completedCourseModules;
        this.currentCourseModules = currentCourseModules;
    }

    /**
     * @return the year that the student is in. E.g. 1 would be retrieved for a user in their first year.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * @return the level that the student is studying. E.g. level 4, 5 or 6.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * @return the course that the student is enrolled in.
     */
    public Course getCourse() {
        return this.course;
    }

    /**
     * @return the course modules that the student has passed and completed.
     */
    public ArrayList<CourseModule> getCompletedCourseModules() {
        return this.completedCourseModules;
    }

    /**
     * @return the course modules that the student is currently studying.
     */
    public CourseModule[] getCurrentCourseModules() {
        return this.currentCourseModules;
    }

    /**
     * @throws Exception
     */
    public void registerForCourse() throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @throws Exception
     */
    public void enrolForModule() throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param courseModule the course module to retrieve the instructor from.
     * @return the instructor on the course module.
     * @throws Exception
     */
    public Instructor getInstructorOnCourseModule(CourseModule courseModule) throws Exception {
        throw new Exception("Not implemented yet");
    }
}
