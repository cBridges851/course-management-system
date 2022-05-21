package com.company.Models.Users;

import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.Models.Study.CourseModule;
import com.company.Models.Study.CourseModuleResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Model that represents the student, which is a type of user who partakes in the modules in courses.
 */
public class Student extends User {
    private final int year;
    private final int level;
    private String courseName;
    private final ArrayList<CourseModuleResult> completedCourseModules;
    private CourseModuleResult[] currentCourseModules;

    public Student(String username, String password, String firstName, String middleName, String lastName,
                   Calendar dateOfBirth, int year, int level, String courseName, ArrayList<CourseModuleResult> completedCourseModules,
                   CourseModuleResult[] currentCourseModules) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
        this.year = year;
        this.level = level;
        this.courseName = courseName;
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
    public String getCourseName() {
        return this.courseName;
    }

    /**
     * @return the course modules that the student has passed and completed.
     */
    public ArrayList<CourseModuleResult> getCompletedCourseModules() {
        return this.completedCourseModules;
    }

    /**
     * @return the course modules that the student is currently studying.
     */
    public CourseModuleResult[] getCurrentCourseModules() {
        return this.currentCourseModules;
    }

    /**
     * Allows the student to register for a course
     * @param courseName the name of the course the student would like to enrol on
     */
    public void registerForCourse(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Allows the student to enrol for a course module
     * @param courseModuleCode the course module code of the course module the student wants to enrol on
     */
    public void enrolForCourseModule(String courseModuleCode) {
        for (int i = 0; i < 4; i++) {
            if (currentCourseModules[i] == null) {
                CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCode);
                HashSet<String> assignmentIds = courseModule.getAssignmentIds();
                HashMap<String, Integer> defaultAssignmentResults = new HashMap<>();

                for (String assignmentId: assignmentIds) {
                    defaultAssignmentResults.put(assignmentId, 0);
                }

                CourseModuleResult courseModuleResult = new CourseModuleResult(
                        courseModuleCode,
                        defaultAssignmentResults,
                        0);
                currentCourseModules[i] = courseModuleResult;
                return;
            }
        }

        System.out.println("Students can only have 4 course modules per semester");
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
