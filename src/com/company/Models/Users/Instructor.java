package com.company.Models.Users;

import com.company.Models.Study.Assignment;
import com.company.Models.Study.CourseModule;

import java.util.ArrayList;
import java.util.Calendar;

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
     * @return the modules that the instructor teaches on.
     */
    public String[] getCourseModules() {
        return courseModuleCodes;
    }

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
     * @param student the student that has achieved the mark.
     * @param assignment the work the student has done.
     * @param mark the number of marks the student has been achieved.
     * @throws Exception
     */
    public void addMark(Student student, Assignment assignment, int mark) throws Exception {
        throw new Exception("Not implemented yet");
    }

    /**
     * @param index the place in the list of modules the instructor has which has the module the instructor wants to
     *              see the students on it.
     * @return a list of all the students on a particular module.
     * @throws Exception
     */
    public ArrayList<Student> getStudentsOnModule(int index) throws Exception {
        throw new Exception("Not implemented yet");
    }
}
