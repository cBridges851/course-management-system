package com.company.Menus;

import com.company.FileHandling.Loaders.*;
import com.company.FileHandling.Savers.CourseModuleSaver;
import com.company.FileHandling.Savers.StudentSaver;
import com.company.Models.Study.Assignment;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
import com.company.Models.Study.CourseModuleResult;
import com.company.Models.Users.Instructor;
import com.company.Models.Users.Student;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Class that handles the interactions for the students.
 */
public class StudentMenu {
    private Student student;
    private final Scanner scanner;

    public StudentMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * The initial method that is called when a student is logged in,
     * which directs them to what they can do
     */
    public void runStudentMenu() {
        System.out.println("Logging in as student");
        ArrayList<Student> students = new StudentLoader().loadAllStudents();
        this.student = students.get(0);
        ArrayList<CourseModuleResult> currentCourseModules = new ArrayList<>(Arrays.asList(this.student.getCurrentCourseModules()));
        currentCourseModules.removeAll(Collections.singleton(null));
        Course course = null;

        if (this.student.getCourseId() != null && !Objects.equals(this.student.getCourseId(), "")) {
            course = new CourseLoader().loadCourse(this.student.getCourseId());
        }

        System.out.println("My Course: "
                + (course != null ? course.getName() : "Not Enrolled"));

        if (Objects.equals(this.student.getCourseId(), "") || Objects.equals(this.student.getCourseId(), null)) {
            System.out.println("""
                    What would you like to do?\s
                    (1) Enrol on a course""");

            String action = scanner.nextLine();

            if (Objects.equals(action, "1")) {
                this.registerForCourse();
            } else {
                this.runStudentMenu();
            }
        } else if (currentCourseModules.size() < 4) {
            System.out.println("""
                    What would you like to do?\s
                    (1) Enrol on a course module\s
                    (2) View current course modules\s
                    (3) View completed course modules""");

            String action = scanner.nextLine();

            if (Objects.equals(action, "1")) {
                this.enrolOntoCourseModule(students);
            } else if (Objects.equals(action, "2")) {
                this.viewCurrentCourseModules();
            } else if (Objects.equals(action, "3")) {
                this.viewCompletedCourseModules();
            } else {
                this.runStudentMenu();
            }
        } else {
            System.out.println("""
                    What would you like to do?\s
                    (1) View current course modules\s
                    (2) View completed course modules""");
            String action = scanner.nextLine();

            if (Objects.equals(action, "1")) {
                this.viewCurrentCourseModules();
            } else if (Objects.equals(action, "2")) {
                this.viewCompletedCourseModules();
            } else {
                this.runStudentMenu();
            }
        }
    }

    /**
     * Allows the student to register onto a course.
     */
    private void registerForCourse() {
        ArrayList<Course> allCourses = new CourseLoader().loadAllAvailableCourses();
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Number", "Course Name", "Course Modules");
        asciiTable.addRule();

        for (int i = 0; i < allCourses.size(); i++) {
            Course course = allCourses.get(i);
            HashSet<String> courseModuleCodes = course.getCourseModuleCodes();
            StringBuilder courseModulesAsString = new StringBuilder();

            for (String courseModuleCode: courseModuleCodes) {
                courseModulesAsString.append(new CourseModuleLoader().loadCourseModule(courseModuleCode).getName())
                        .append("\n");
            }
            asciiTable.addRow(i + 1,
                    allCourses.get(i).getName(),
                    courseModulesAsString);
            asciiTable.addRule();
        }

        System.out.println(asciiTable.render());

        System.out.print("Enter the number of the course you would like to enrol in: ");
        String courseNumber = scanner.nextLine();

        if (StringUtils.isNumeric(courseNumber)) {
            if (Integer.parseInt(courseNumber) - 1 < allCourses.size()
                    && Integer.parseInt(courseNumber) - 1 >= 0) {
                Course selectedCourse = allCourses.get(Integer.parseInt(courseNumber) - 1);
                System.out.print("Are you sure you want to enrol onto "
                        + selectedCourse.getName()
                        + "? (Y/N)");
                String confirmation = scanner.nextLine();

                if (Objects.equals(confirmation.toLowerCase(Locale.ROOT), "y")) {
                    student.registerForCourse(selectedCourse.getCourseId());
                    new StudentSaver().saveStudent(student);
                }
            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runStudentMenu();
    }

    /**
     * Allows a student to enrol onto a course module on the course they are enrolled on, unless they already have
     * 4 course modules
     * @param students all the students in the system, ready for saving
     */
    private void enrolOntoCourseModule(ArrayList<Student> students) {
        Course course = new CourseLoader().loadCourse(this.student.getCourseId());
        HashSet<String> courseModulesCodesInCourse = course.getCourseModuleCodes();
        ArrayList<CourseModule> availableCourseModules = new ArrayList<>();
        ArrayList<CourseModuleResult> currentCourseModulesAsArrayList = new ArrayList<>(Arrays.asList(this.student.getCurrentCourseModules()));
        currentCourseModulesAsArrayList.removeAll(Collections.singleton(null));
        ArrayList<CourseModuleResult> completedCourseModules = this.student.getCompletedCourseModules();

        for (String courseModuleCode: courseModulesCodesInCourse) {
            CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCode);

            boolean isAlreadyEnrolledOrCompleted = false;

            for (CourseModuleResult completedCourseModule: completedCourseModules) {
                if (Objects.equals(completedCourseModule.getCourseModuleCode(), courseModuleCode)) {
                    isAlreadyEnrolledOrCompleted = true;
                    break;
                }
            }

            for (CourseModuleResult currentCourseModule: currentCourseModulesAsArrayList) {
                if (Objects.equals(currentCourseModule.getCourseModuleCode(), courseModuleCode)) {
                    isAlreadyEnrolledOrCompleted = true;
                    break;
                }
            }

            if (!isAlreadyEnrolledOrCompleted && courseModule.getLevel() == this.student.getLevel()) {
                availableCourseModules.add(courseModule);
            }
        }

        AsciiTable asciiTable = new AsciiTable();

        asciiTable.addRule();
        asciiTable.addRow(null, null, null, null, null, "Available course modules to enrol on");
        asciiTable.addRule();
        asciiTable.addRow("Number", "Course Module Code", "Name", "Level", "Instructors", "Mandatory or Optional");
        asciiTable.addRule();

        for (int i = 0; i < availableCourseModules.size(); i++) {
            asciiTable.addRow(
                    i + 1,
                    availableCourseModules.get(i).getCourseModuleCode(),
                    availableCourseModules.get(i).getName(),
                    availableCourseModules.get(i).getLevel(),
                    availableCourseModules.get(i).getInstructorNames(),
                    availableCourseModules.get(i).getIsMandatory() ? "Mandatory" : "Optional"
            );
            asciiTable.addRule();
        }

        System.out.println(asciiTable.render());
        System.out.print("Enter the number of the course module you would like to enrol on: ");
        String courseModuleNumber = scanner.nextLine();

        if (StringUtils.isNumeric(courseModuleNumber)) {
            if (Integer.parseInt(courseModuleNumber) - 1 < availableCourseModules.size()
                    && Integer.parseInt(courseModuleNumber) - 1 >= 0) {
                CourseModule selectedCourseModule = availableCourseModules.get(Integer.parseInt(courseModuleNumber) - 1);
                if (this.student.getLevel() == 6) {
                    int numberOfMandatory = 0;
                    int numberOfOptional = 0;

                    for (CourseModuleResult courseModuleCode: currentCourseModulesAsArrayList) {
                        CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCode.getCourseModuleCode());

                        if (courseModule.getIsMandatory()) {
                            numberOfMandatory++;
                        } else {
                            numberOfOptional++;
                        }
                    }

                    if (numberOfMandatory == 2 && selectedCourseModule.getIsMandatory()) {
                        System.out.println("You need to select an optional course module instead");
                        this.enrolOntoCourseModule(students);
                        return;
                    }

                    if (numberOfOptional == 2 && !selectedCourseModule.getIsMandatory()) {
                        System.out.println("You need to select a mandatory course module instead");
                        this.enrolOntoCourseModule(students);
                        return;
                    }
                }

                System.out.print("Are you sure you want to enrol onto " + selectedCourseModule.getName() + "? (Y/N) ");
                String action = scanner.nextLine();

                if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                    this.student.enrolForCourseModule(selectedCourseModule.getCourseModuleCode());
                    new StudentSaver().saveStudent(student);
                    selectedCourseModule.addStudentName(this.student.getUsername());
                    new CourseModuleSaver().saveCourseModule(selectedCourseModule);

                    this.runStudentMenu();
                    return;
                }
            } else {
                System.out.println("Course module does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runStudentMenu();
    }

    /**
     * Allows the user to view the course modules they are currently enrolled in
     */
    private void viewCurrentCourseModules() {
        CourseModuleResult[] currentCourseModuleResults = this.student.getCurrentCourseModules();
        ArrayList<CourseModuleResult> currentCourseModuleResultsAsArray = new ArrayList<>(Arrays.asList(currentCourseModuleResults));
        currentCourseModuleResultsAsArray.removeAll(Collections.singleton(null));
        ArrayList<CourseModule> currentCourseModules = new ArrayList<>();

        for (CourseModuleResult currentCourseModule: currentCourseModuleResultsAsArray) {
            currentCourseModules.add(new CourseModuleLoader().loadCourseModule(currentCourseModule.getCourseModuleCode()));
        }

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(null, null, null, null, null, "My Current Course Modules");
        asciiTable.addRule();
        asciiTable.addRow("Course Module Code", "Name", "Level", "Mandatory or Optional", "Instructors", "Assignments");
        asciiTable.addRule();

        for (CourseModule currentCourseModule: currentCourseModules) {
            StringBuilder instructorNames = new StringBuilder();
            StringBuilder assignmentInfo = new StringBuilder();

            for (String instructorName: currentCourseModule.getInstructorNames()) {
                Instructor instructor = new InstructorLoader().loadInstructor(instructorName);
                instructorNames.append(
                        instructor.getFirstName())
                        .append(" ")
                        .append(instructor.getLastName())
                        .append(" (")
                        .append(instructor.getUsername())
                        .append(")\n");
            }

            for (String assignmentId: currentCourseModule.getAssignmentIds()) {
                int result = 0;

                for (CourseModuleResult courseModuleResult : currentCourseModuleResultsAsArray) {
                    if (courseModuleResult.getAssignmentResults().containsKey(assignmentId)) {
                        result = courseModuleResult.getAssignmentResults().get(assignmentId);
                    }
                }

                Assignment assignment = new AssignmentLoader().loadAssignment(assignmentId);
                assignmentInfo.append(
                        assignment.getAssignmentName())
                        .append(" ")
                        .append(result)
                        .append("/")
                        .append(assignment.getTotalPossibleMarks());
            }

            asciiTable.addRow(
                    currentCourseModule.getCourseModuleCode(),
                    currentCourseModule.getName(),
                    currentCourseModule.getLevel(),
                    currentCourseModule.getIsMandatory() ? "Mandatory" : "Optional",
                    instructorNames,
                    assignmentInfo
            );

            asciiTable.addRule();
        }

        System.out.println(asciiTable.render());
        System.out.print("Press a key to continue: ");
        scanner.nextLine();
        this.runStudentMenu();
    }

    /**
     * Allows the student to see all the course modules they have completed and their results
     */
    private void viewCompletedCourseModules() {
        ArrayList<CourseModuleResult> completedCourseModuleResults = this.student.getCompletedCourseModules();
        ArrayList<CourseModule> completedCourseModules = new ArrayList<>();

        for (CourseModuleResult completedCourseModuleResult: completedCourseModuleResults) {
            completedCourseModules.add(
                    new CourseModuleLoader().loadCourseModule(completedCourseModuleResult.getCourseModuleCode()));
        }

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(null, null, null, null, null, "My Completed Course Modules");
        asciiTable.addRule();
        asciiTable.addRow("Course Module Code", "Name", "Level", "Mandatory or Optional", "Instructors", "Assignments");
        asciiTable.addRule();

        for (CourseModule currentCourseModule: completedCourseModules) {
            StringBuilder instructorNames = new StringBuilder();
            StringBuilder assignmentInfo = new StringBuilder();

            for (String instructorName: currentCourseModule.getInstructorNames()) {
                Instructor instructor = new InstructorLoader().loadInstructor(instructorName);
                instructorNames.append(
                                instructor.getFirstName())
                        .append(" ")
                        .append(instructor.getLastName())
                        .append(" (")
                        .append(instructor.getUsername())
                        .append(")\n");
            }

            for (String assignmentId: currentCourseModule.getAssignmentIds()) {
                int result = 0;

                for (CourseModuleResult courseModuleResult : completedCourseModuleResults) {
                    if (courseModuleResult.getAssignmentResults().containsKey(assignmentId)) {
                        result = courseModuleResult.getAssignmentResults().get(assignmentId);
                    }
                }

                Assignment assignment = new AssignmentLoader().loadAssignment(assignmentId);
                assignmentInfo.append(
                                assignment.getAssignmentName())
                        .append(" ")
                        .append(result)
                        .append("/")
                        .append(assignment.getTotalPossibleMarks());
            }

            asciiTable.addRow(
                    currentCourseModule.getCourseModuleCode(),
                    currentCourseModule.getName(),
                    currentCourseModule.getLevel(),
                    currentCourseModule.getIsMandatory() ? "Mandatory" : "Optional",
                    instructorNames,
                    assignmentInfo
            );

            asciiTable.addRule();
        }

        System.out.println(asciiTable.render());
        System.out.print("Press a key to continue: ");
        scanner.nextLine();
        this.runStudentMenu();
    }
}
