package com.company.Models.Users;

import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.FileHandling.Savers.AssignmentSaver;
import com.company.FileHandling.Savers.CourseModuleSaver;
import com.company.FileHandling.Savers.StudentSaver;
import com.company.Models.Study.Assignment;
import com.company.Models.Study.CourseModule;
import com.company.Models.Study.CourseModuleResult;

import java.util.Calendar;
import java.util.Objects;

/**
 * Model that represents the instructor, which is a type of user who teach module content to students.
 */
public class Instructor extends User {
    private final String[] courseModuleCodes;

    public Instructor(String username, String password, String firstName, String middleName, String lastName) {
        super(username, password, firstName, middleName, lastName);
        this.courseModuleCodes = new String[] {null, null, null, null};
    }

    public Instructor(String username, String password, String firstName, String middleName, String lastName,
                      String[] courseModuleCodes) {
        super(username, password, firstName, middleName, lastName);
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

    /**
     * Creates an assignment that will be added onto a course module.
     * @param courseModuleCode the identifier of the course module that the assignment will go onto.
     * @param assignmentName the name of the assignment.
     * @param totalPossibleMarks the maximum number of marks that could be achieved on the assignment.
     */
    public void createAssignment(String courseModuleCode, String assignmentName, int totalPossibleMarks) {
        Assignment assignment = new Assignment(assignmentName, totalPossibleMarks);
        new AssignmentSaver().saveAssignment(assignment);

        CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCode);
        courseModule.addAssignmentId(assignment.getAssignmentId());
        new CourseModuleSaver().saveCourseModule(courseModule);
        this.updateExistingStudents(courseModule, assignment);
    }

    /**
     * New assignments are added to students who are already on the course module, so results can be added to them later.
     * @param courseModule the course module the assignment is on.
     * @param assignment the new assignment.
     */
    private void updateExistingStudents(CourseModule courseModule, Assignment assignment) {
        for (String studentUsername: courseModule.getStudentNames()) {
            Student student = new StudentLoader().loadStudent(studentUsername);
            CourseModuleResult[] courseModuleResults = student.getCurrentCourseModules();

            for (CourseModuleResult courseModuleResult: courseModuleResults) {
                if (courseModuleResult != null) {
                    if (Objects.equals(courseModuleResult.getCourseModuleCode(), courseModule.getCourseModuleCode())) {
                        if (courseModuleResult.getAssignmentResults().get(assignment.getAssignmentId()) != null) {
                            courseModuleResult.getAssignmentResults().remove(assignment.getAssignmentId());
                        }
                        courseModuleResult.addAssignmentResults(assignment.getAssignmentId(), 0);
                        new StudentSaver().saveStudent(student);
                    }
                }
            }
        }
    }

    /**
     * Adds results a student has achieved to an assignment
     * @param student the student that has achieved the mark.
     * @param assignment the work the student has done.
     * @param mark the number of marks the student has been achieved.
     */
    public void addMark(Student student, CourseModule courseModule, Assignment assignment, int mark) {
        CourseModuleResult[] studentsCurrentCourseModules = student.getCurrentCourseModules();

        for (CourseModuleResult currentCourseModule: studentsCurrentCourseModules) {
            if (currentCourseModule != null) {
                if (Objects.equals(currentCourseModule.getCourseModuleCode(), courseModule.getCourseModuleCode())) {
                    currentCourseModule.addAssignmentResults(assignment.getAssignmentId(), mark);
                    new StudentSaver().saveStudent(student);
                    break;
                }
            }
        }
    }

    /**
     * Marks the student as completed for the course module, thus removing them from the enrolled students and moves it
     * to the completed list
     * @param student the student to mark as completed
     * @param courseModule the course module that the student has completed
     */
    public void markStudentAsCompleted(Student student, CourseModule courseModule) {
        CourseModuleResult[] studentsCurrentCourseModules = student.getCurrentCourseModules();

        for (CourseModuleResult currentCourseModule: studentsCurrentCourseModules) {
            if (currentCourseModule != null) {
                if (Objects.equals(currentCourseModule.getCourseModuleCode(), courseModule.getCourseModuleCode())) {
                    student.addCompletedCourseModule(currentCourseModule);
                    student.removeCurrentCourseModule(currentCourseModule);
                    courseModule.removeStudentName(student.getUsername());
                    new StudentSaver().saveStudent(student);
                    new CourseModuleSaver().saveCourseModule(courseModule);
                    break;
                }
            }
        }
    }

    /**
     * Changes the name of an assignment
     * @param assignment the assignment to update
     * @param newName the name to change it to
     */
    public void updateAssignmentName(Assignment assignment, String newName) {
        assignment.setAssignmentName(newName);
        new AssignmentSaver().saveAssignment(assignment);
    }

    /**
     * Changes the number of marks that can be achieved on an assignment.
     * @param courseModule the course module that the assignment belongs to.
     * @param assignment the assignment to update.
     * @param marks the new number of marks that can be achieved.
     */
    public void updateAssignmentMarks(CourseModule courseModule, Assignment assignment, int marks) {
        assignment.setTotalPossibleMarks(marks);
        new AssignmentSaver().saveAssignment(assignment);
        this.updateExistingStudents(courseModule, assignment);
    }
}
