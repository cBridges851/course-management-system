package com.company.Models.Users;

import com.company.FileHandling.Loaders.AssignmentLoader;
import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Savers.AssignmentSaver;
import com.company.FileHandling.Savers.CourseModuleSaver;
import com.company.Models.Study.Assignment;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * Model that represents the instructor, which is a type of user who teach module content to students.
 */
public class Instructor extends User {
    private final String[] courseModuleCodes;

    public Instructor(String username, String password, String firstName, String middleName, String lastName,
                      Calendar dateOfBirth, String[] courseModuleCodes) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
        this.courseModuleCodes = new String[] {null, null, null, null};

        System.arraycopy(courseModuleCodes, 0, this.courseModuleCodes, 0, courseModuleCodes.length);
    }

    /**
     * Retrieves the course modules the instructor teaches on.
     * @return the course modules that the instructor teaches on.
     */
    public String[] getCourseModules() {
        return courseModuleCodes;
    }

    /**
     * Adds a course module code to the course modules that the instructor teaches on, but they can only have up to 4.
     * @param courseModuleCode the identifier of the course module code to add
     * @return whether the instructor could be assigned to the course module
     */
    public boolean addCourseModule(String courseModuleCode) {
        for (int i = 0; i < 4; i++) {
            if (courseModuleCodes[i] == null) {
                courseModuleCodes[i] = courseModuleCode;
                return true;
            }
        }

        System.out.println("Instructors cannot be assigned to more than 4 modules");
        return false;
    }

    /**
     * Removes a course module from the list of course modules they are assigned to
     * @param courseModuleCode the identifier of the course module to remove
     */
    public void removeCourseModule(String courseModuleCode) {
        for (int i = 0; i < 4; i++) {
            if (Objects.equals(courseModuleCodes[i], courseModuleCode)) {
                courseModuleCodes[i] = null;
            }
        }
    }

    public void createAssignment(String courseModuleCode, String assignmentName, int totalPossibleMarks) {
        ArrayList<Assignment> allAssignments = new AssignmentLoader().loadAllAssignments();
        Assignment assignment = new Assignment(null, assignmentName, totalPossibleMarks);
        allAssignments.add(assignment);
        new AssignmentSaver().saveAllAssignments(allAssignments);

        ArrayList<CourseModule> allCourseModules = new CourseModuleLoader().loadAllCourseModules();
        for (CourseModule courseModule: allCourseModules) {
            if (Objects.equals(courseModule.getCourseModuleCode(), courseModuleCode)) {
                courseModule.addAssignmentId(assignment.getAssignmentId());
                new CourseModuleSaver().saveAllCourseModules(allCourseModules);
            }
        }
    }

    /**
     * @param student the student that has achieved the mark.
     * @param assignment the work the student has done.
     * @param mark the number of marks the student has been achieved.
     * @throws Exception
     */
    public void addMark(Student student, Assignment assignment, int mark) throws Exception {
        throw new Exception("Not implemented yet");
    }
}
