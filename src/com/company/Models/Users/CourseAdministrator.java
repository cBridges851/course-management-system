package com.company.Models.Users;

import com.company.FileHandling.Loaders.CourseLoader;
import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.FileHandling.Savers.CourseModuleSaver;
import com.company.FileHandling.Savers.CourseSaver;
import com.company.FileHandling.Savers.InstructorSaver;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;

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
     * @param instructorNames the name of the instructor teaching the module
     * @param isMandatory indicator of whether or not the module is optional
     * @param assignmentIds the assignments that have to be completed in the module
     * @param studentNames the students enrolled in the module
     */
    public void addNewCourseModuleToCourse(ArrayList<Course> courses, Course course, String courseModuleCode, String name, int level,
                                   HashSet<String> instructorNames, boolean isMandatory, HashSet<String> assignmentIds,
                                   HashSet<String> studentNames) {
        CourseModule courseModule = new CourseModule(
                courseModuleCode,
                name,
                level,
                instructorNames,
                isMandatory,
                assignmentIds,
                studentNames
        );
        course.addCourseModule(courseModule.getCourseModuleCode());
        new CourseSaver().saveAllCourses(courses);
        ArrayList<CourseModule> allCourseModules = new CourseModuleLoader().loadAllCourseModules();
        allCourseModules.add(courseModule);
        new CourseModuleSaver().saveAllCourseModules(allCourseModules);
        ArrayList<Instructor> allInstructors = new InstructorLoader().loadAllInstructors();
        ArrayList<Instructor> instructorsToUpdate = new ArrayList<Instructor>();
        ArrayList<String> instructorNamesAsArray = new ArrayList<String>(instructorNames);

        for (String relevantInstructor: instructorNamesAsArray) {
            for (Instructor instructor: allInstructors) {
                if (instructor.getUsername().equals(relevantInstructor)) {
                    instructorsToUpdate.add(instructor);
                }
            }
        }

        for (Instructor instructorToUpdate: instructorsToUpdate) {
            assignInstructorToCourseModule(allCourseModules, courseModule, allInstructors, instructorToUpdate);
        }
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
    public void renameCourseModule(ArrayList<CourseModule> courseModules, CourseModule courseModule, String newName) {
        courseModule.setName(newName);
        new CourseModuleSaver().saveAllCourseModules(courseModules);
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
     */
    public void assignInstructorToCourseModule(ArrayList<CourseModule> allCourseModules,
                                               CourseModule courseModule,
                                               ArrayList<Instructor> instructors,
                                               Instructor instructor) {
        boolean canBeAssigned = instructor.addCourseModule(courseModule.getCourseModuleCode());

        if (canBeAssigned) {
            courseModule.addInstructorName(instructor.getUsername());

            new InstructorSaver().saveAllInstructors(instructors);
            new CourseModuleSaver().saveAllCourseModules(allCourseModules);
        }
    }

    /**
     * Removes course modules from courses
     * @param course the course to remove the module from
     * @param courseModule the course module that needs to be removed
     */
    public void removeCourseModuleFromCourse(ArrayList<Course> courses, Course course, CourseModule courseModule) {
        course.removeCourseModule(courseModule.getCourseModuleCode());
        new CourseSaver().saveAllCourses(courses);

        boolean isInAnotherCourse = false;

        for (Course courseItem : courses) {
            if (courseItem.getCourseModuleCodes().contains(courseModule.getCourseModuleCode())) {
                isInAnotherCourse = true;
                break;
            }
        }

        if (!isInAnotherCourse) {
            ArrayList<CourseModule> allCourseModules = new CourseModuleLoader().loadAllCourseModules();

            allCourseModules.removeIf(courseModuleItem -> courseModuleItem.getCourseModuleCode().equals(courseModule.getCourseModuleCode()));
            new CourseModuleSaver().saveAllCourseModules(allCourseModules);
        }
    }

    /**
     * @param courseModule the course module that will have the instructor removed from it.
     */
    public void removeInstructorFromCourseModule(ArrayList<CourseModule> allCourseModules,
                                                 CourseModule courseModule,
                                                 ArrayList<Instructor> allInstructors,
                                                 Instructor instructor) {
        courseModule.removeInstructorName(instructor.getUsername());
        instructor.removeCourseModule(courseModule.getCourseModuleCode());

        for (int i = 0; i < allInstructors.size(); i++) {
            if (Objects.equals(allInstructors.get(i).getUsername(), instructor.getUsername())) {
                allInstructors.set(i, instructor);
            }
        }

        new InstructorSaver().saveAllInstructors(allInstructors);
        new CourseModuleSaver().saveAllCourseModules(allCourseModules);
    }
}
