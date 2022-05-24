package com.company.Models.Users;

import com.company.FileHandling.Loaders.CourseLoader;
import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
import com.company.Models.Study.CourseModuleResult;

import java.util.*;

/**
 * Model that represents the student, which is a type of user who partakes in the modules in courses.
 */
public class Student extends User {
    private final int year;
    private final int level;
    private String courseName;
    private final ArrayList<CourseModuleResult> completedCourseModules;
    private final CourseModuleResult[] currentCourseModules;

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
     * Adds a completed course module to the student's list
     * @param completedCourseModule the results of the completed course module
     */
    public void addCompletedCourseModule(CourseModuleResult completedCourseModule) {
        this.completedCourseModules.add(completedCourseModule);
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
                LinkedHashMap<String, Integer> defaultAssignmentResults = new LinkedHashMap<>();

                for (String assignmentId: assignmentIds) {
                    defaultAssignmentResults.put(assignmentId, 0);
                }

                CourseModuleResult courseModuleResult = new CourseModuleResult(
                        courseModuleCode,
                        defaultAssignmentResults);
                currentCourseModules[i] = courseModuleResult;
                return;
            }
        }

        System.out.println("Students can only have 4 course modules per semester");
    }

    /**
     * Removes a course module the student is currently enrolled on.
     * @param courseModule the course module to remove.
     */
    public void removeCurrentCourseModule(CourseModuleResult courseModule) {
        for (int i = 0; i < 4; i++) {
            if (this.currentCourseModules[i] != null) {
                if (Objects.equals(this.currentCourseModules[i].getCourseModuleCode(), courseModule.getCourseModuleCode())) {
                    this.currentCourseModules[i] = null;
                    ArrayList<Student> allStudents = new StudentLoader().loadAllStudents();

                    for (int j = 0; j < allStudents.size(); j++) {
                        if (Objects.equals(allStudents.get(j).getUsername(), this.getUsername())) {
                            allStudents.set(j, this);
                        }
                    }

                    System.out.println(courseModule.getCourseModuleCode()
                            + " successfully removed from "
                            + this.getFirstName()
                            + "'s current course modules");
                    return;
                }
            }
        }

        System.out.println("Unable to remove "
                + courseModule.getCourseModuleCode()
                + " from "
                + this.getFirstName()
                + "'s current course modules");
    }

    public boolean canProgressToNextLevel() {
        Course course = new CourseLoader().loadCourse(this.courseName);
        int numberOfCourseModulesOnLevel = 0;
        int numberOfPassedCourseModules = 0;

        for (String courseModuleCode: course.getCourseModuleCodes()) {
            CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCode);
            if (courseModule.getLevel() == this.level) {
                numberOfCourseModulesOnLevel++;
            }
        }

        for (CourseModuleResult completedCourseModuleResult: this.completedCourseModules) {
            CourseModule currentCourseModule = new CourseModuleLoader()
                    .loadCourseModule(completedCourseModuleResult.getCourseModuleCode());
            if ((double) completedCourseModuleResult.getTotalMark() / (double) currentCourseModule.getTotalAvailableMarks() * 100 > 40) {
                numberOfPassedCourseModules++;
            }
        }

        return (double) numberOfPassedCourseModules / (double) numberOfCourseModulesOnLevel * 100 >= 50;
    }
}
