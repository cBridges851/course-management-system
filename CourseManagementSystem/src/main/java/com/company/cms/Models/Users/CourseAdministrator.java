package com.company.cms.Models.Users;

import com.company.cms.FileHandling.Loaders.CourseLoader;
import com.company.cms.Models.Study.Course;
import com.company.cms.Models.Study.CourseModule;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Model that represents the course administrator, which is a type of user who manages the courses.
 */
public class CourseAdministrator extends User {
    private ArrayList<Course> courses;

    public CourseAdministrator(String username, String password, String firstName, String middleName, String lastName, Calendar dateOfBirth,
                               ArrayList<Course> courses) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
        this.courses = courses;
    }

    /**
     * @param course The course that needs to be added to the course list.
     * @throws Exception
     */
    public void addNewCourse(Course course) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param courseModule The module to add to a course.
     * @throws Exception
     */
    public void addNewCourseModule(Course course, CourseModule courseModule) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @return all the courses that are available in the university or college.
     */
    public ArrayList<Course> getAllAvailableCourses() {
        ArrayList<Course> allCourses = this.getAllCourses();
        ArrayList<Course> availableCourses = new ArrayList<>();

        for (Course course: allCourses) {
            if (course.isAvailable()) {
                availableCourses.add(course);
            }
        }

        return availableCourses;
    }

    /**
     * @return all the courses that have been cancelled.
     */
    public ArrayList<Course> getAllCancelledCourses() {
        ArrayList<Course> allCourses = this.getAllCourses();
        ArrayList<Course> cancelledCourses = new ArrayList<>();

        for (Course course: allCourses) {
            if (!course.isAvailable()) {
                cancelledCourses.add(course);
            }
        }

        return cancelledCourses;
    }

    /**
     * @return all the courses, no matter whether they are available or cancelled.
     */
    public ArrayList<Course> getAllCourses() {
        this.courses = new CourseLoader().loadAllCourses();
        return this.courses;
    }

    /**
     * @param course the course to be cancelled.
     * @throws Exception
     */
    public void cancelCourse(Course course) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param course the course to reopen.
     * @throws Exception
     */
    public void reopenCourse(Course course) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param course the course to be deleted.
     * @throws Exception
     */
    public void deleteCourse(Course course) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param course the course that needs to be renamed.
     * @param newName the name that the course will be changed to.
     * @throws Exception
     */
    public void renameCourse(Course course, String newName) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param courseModule the course module that needs to be renamed.
     * @param newName the name that the course module will be changed to.
     * @throws Exception
     */
    public void renameCourseModule(CourseModule courseModule, String newName) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param student the student that the results slip will be about, which indicates the grades on each module.
     * @return a string that represents the results slip
     * @throws Exception
     */
    public String createResultsSlip(Student student) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param courseModule the course module that will have the instructor assigned to it
     * @param instructor the instructor that will be added to the course module
     * @throws Exception
     */
    public void assignInstructorToCourseModule(CourseModule courseModule, Instructor instructor) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param courseModule the course module that will have the instructor removed from it.
     * @throws Exception
     */
    public void removeInstructorFromCourseModule(CourseModule courseModule) throws Exception {
        throw new Exception("Not implemented yet");
    }
}
