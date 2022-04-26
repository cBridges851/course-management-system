package com.company.cms.Models.Users;

import com.company.cms.FileHandling.Loaders.CourseLoader;
import com.company.cms.FileHandling.Savers.CourseSaver;
import com.company.cms.Models.Study.Course;
import com.company.cms.Models.Study.CourseModule;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Model that represents the course administrator, which is a type of user who manages the courses.
 */
public class CourseAdministrator extends User {
    private ArrayList<Course> courses;

    public CourseAdministrator(String username, String password, String firstName, String middleName, String lastName, Calendar dateOfBirth) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
        this.getAllCourses();
    }

    /**
     * @param newCourse The course that needs to be added to the course list.
     */
    public void addNewCourse(Course newCourse) {
        for (Course course: this.courses) {
            if (course.getName().equals(newCourse.getName())) {
                System.out.println("Course already exists!");
                return;
            }
        }

        this.courses.add(newCourse);
        new CourseSaver().saveCourse(newCourse);
    }

    /**
     * @param courseModule The module to add to a course.
     * @throws Exception
     */
    public void addNewCourseModule(Course course, CourseModule courseModule) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * Gets all the courses that can be enrolled onto.
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
     * Gets all the courses that are not currently available to be enrolled.
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
     * A method that retrieves all the courses that are in this class and its file.
     * @return all the courses, no matter whether they are available or cancelled.
     */
    public ArrayList<Course> getAllCourses() {
        this.courses = new CourseLoader().loadAllCourses();
        return this.courses;
    }

    /**
     * Makes a course unavailable, but not permanently removed
     * @param course the course to be cancelled.
     */
    public void cancelCourse(Course course) {
        course.setIsAvailable(false);
        new CourseSaver().saveAllCourses(this.courses);
    }

    /**
     * Make a course available
     * @param course the course to reopen.
     */
    public void reopenCourse(Course course) {
        course.setIsAvailable(true);
        new CourseSaver().saveAllCourses(this.courses);
    }

    /**
     * Permanently removes a course from the list of courses in this class and its file.
     * @param courseToDelete the course to be deleted.
     */
    public void deleteCourse(Course courseToDelete) {
        for (Course courseItem: courses) {
            System.out.println(courseItem.getName());
        }

        int index = -1;

        for (int i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getName().equals(courseToDelete.getName())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Course not found!");
            return;
        }

        this.courses.remove(index);
        new CourseSaver().saveAllCourses(this.courses);
    }

    /**
     * Allows the course to have its name changed
     * @param course the course that needs to be renamed.
     * @param newName the name that the course will be changed to.
     */
    public void renameCourse(Course course, String newName) {
        course.setName(newName);
        new CourseSaver().saveAllCourses(this.courses);
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
