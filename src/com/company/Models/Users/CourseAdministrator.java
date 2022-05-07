package com.company.Models.Users;

import com.company.FileHandling.Loaders.CourseLoader;
import com.company.FileHandling.Savers.CourseSaver;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
import com.company.Models.Study.Assignment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Model that represents the course administrator, which is a type of user who manages the courses.
 */
public class CourseAdministrator extends User {

    public CourseAdministrator(String username, String password, String firstName, String middleName, String lastName, Calendar dateOfBirth) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
    }

    /**
     * Adds a new course to the list of courses.
     * @param courses The list of courses to update.
     * @param courseName The name of the new course.
     */
    public void addNewCourse(ArrayList<Course> courses, String courseName) {
        Course newCourse = new Course(courseName, new HashSet<>(), true);
        courses.add(newCourse);
        new CourseSaver().saveAllCourses(courses);
    }

    /**
     * Adds a course module to the course, and the persistent data is updated.
     * @param course the course to add the new module to
     * @param courseModuleCode the identifier of the new module
     * @param name the name of the new module
     * @param level the level of the new module
     * @param instructorName the name of the instructor teaching the module
     * @param isMandatory indicator of whether or not the module is optional
     * @param assignments the assignments that have to be completed in the module
     * @param studentNames the students enrolled in the module
     */
    public void addNewCourseModule(Course course, String courseModuleCode, String name, int level,
                                   String instructorName, boolean isMandatory, ArrayList<Assignment> assignments,
                                   HashSet<String> studentNames) {
//        CourseModule courseModule = new CourseModule(courseModuleCode, name, level, instructorName, isMandatory,
//                assignments, studentNames);
//
//        course.addCourseModule(courseModule);
//        new CourseSaver().saveAllCourses(this.courses);
//        ArrayList<CourseModule> allCourseModules = new CourseModuleLoader().loadAllCourseModules();
//
//        for (Course courseItem: this.courses) {
//            for (CourseModule courseModuleItem : courseItem.getCourseModules()) {
//                boolean alreadyInAllCourseModules = false;
//
//                for (CourseModule courseModuleFromAllCourseModules : allCourseModules) {
//                    if (courseModuleFromAllCourseModules.getCourseModuleCode()
//                            .equals(courseModule.getCourseModuleCode())) {
//                        System.out.println("Course module already exists!");
//                        return;
//                    }
//
//                    if (courseModuleItem.getCourseModuleCode().trim()
//                            .equals(courseModuleFromAllCourseModules.getCourseModuleCode().trim())) {
//                        alreadyInAllCourseModules = true;
//                        break;
//                    }
//                }
//
//                if (!alreadyInAllCourseModules) {
//                    allCourseModules.add(courseModuleItem);
//                }
//            }
//        }
//
//        new CourseModuleSaver().saveAllCourseModules(allCourseModules);
    }

    /**
     * A method that retrieves all the courses that are in this class and its file.
     * @return all the courses, no matter whether they are available or cancelled.
     */
    public ArrayList<Course> getAllCourses() {
        return new CourseLoader().loadAllCourses();
    }

    /**
     * Makes a course unavailable, but not permanently removed
     * @param allCourses the list of courses to update.
     * @param course the course to be cancelled.
     */
    public void cancelCourse(ArrayList<Course> allCourses, Course course) {
        course.setIsAvailable(false);
        new CourseSaver().saveAllCourses(allCourses);
    }

    /**
     * Make a course available
     * @param allCourses the list of courses to update
     * @param course the course to reopen.
     */
    public void reopenCourse(ArrayList<Course> allCourses, Course course) {
        course.setIsAvailable(true);
        new CourseSaver().saveAllCourses(allCourses);
    }

    /**
     * Permanently removes a course from the list of courses in this class and its file.
     * @param courseToDelete the course to be deleted.
     */
    public void deleteCourse(ArrayList<Course> courses, Course courseToDelete) {
        courses.remove(courseToDelete);
        new CourseSaver().saveAllCourses(courses);
    }

    /**
     * Allows the course to have its name changed
     * @param course the course that needs to be renamed.
     * @param newName the name that the course will be changed to.
     */
    public void renameCourse(ArrayList<Course> courses, Course course, String newName) {
        course.setName(newName);
        new CourseSaver().saveAllCourses(courses);
    }

    /**
     * Gets all the course modules that are in the system.
     * @return all the course modules in the system.
     */
    public ArrayList<CourseModule> getAllCourseModules() throws Exception {
        throw new Exception("Not implemented yet");
//        this.courseModules = new CourseModuleLoader().loadAllCourseModules();
//        return this.courseModules;
    }

    /**
     * @param courseModule the course module that needs to be renamed.
     * @param newName the name that the course module will be changed to.
     */
    public void renameCourseModule(CourseModule courseModule, String newName) {
//        courseModule.setName(newName);
//        new CourseModuleSaver().saveAllCourseModules(this.courseModules);
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
     * Removes course modules from courses
     * @param course the course to remove the module from
     * @param courseModule the course module that needs to be removed
     */
    public void removeCourseModuleFromCourse(Course course, CourseModule courseModule) {
//        course.removeCourseModule(courseModule);
//        new CourseSaver().saveAllCourses(this.courses);
    }

    /**
     * @param courseModule the course module that will have the instructor removed from it.
     * @throws Exception
     */
    public void removeInstructorFromCourseModule(CourseModule courseModule) throws Exception {
        throw new Exception("Not implemented yet");
    }
}
