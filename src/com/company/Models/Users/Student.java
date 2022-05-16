package com.company.Models.Users;

import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.FileHandling.Savers.StudentSaver;
import com.company.Models.Study.CourseModule;
import com.company.Models.Study.ModuleResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * Model that represents the student, which is a type of user who partakes in the modules in courses.
 */
public class Student extends User {
    private final int year;
    private final int level;
    private String courseName;
    private final ArrayList<String> completedCourseModules;
    private String[] currentCourseModules = new String[4];
    private ArrayList<ModuleResult> moduleResults;

    public Student(String username, String password, String firstName, String middleName, String lastName,
                   Calendar dateOfBirth, int year, int level, String courseName, ArrayList<String> completedCourseModules,
                   String[] currentCourseModules) {
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
    public ArrayList<String> getCompletedCourseModules() {
        return this.completedCourseModules;
    }

    /**
     * @return the course modules that the student is currently studying.
     */
    public String[] getCurrentCourseModules() {
        return this.currentCourseModules;
    }

    public void registerForCourse(String courseName) {
        this.courseName = courseName;
    }

    /**
     *
     */
    public void enrolForCourseModule(String courseModuleCode) {
        for (int i = 0; i < 4; i++) {
            if (currentCourseModules[i] == null) {
                currentCourseModules[i] = courseModuleCode;
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
