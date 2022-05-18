package com.company.Menus;

import com.company.FileHandling.Loaders.*;
import com.company.FileHandling.Savers.CourseModuleSaver;
import com.company.FileHandling.Savers.StudentSaver;
import com.company.Models.Study.Assignment;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
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
        ArrayList<String> currentCourseModules = new ArrayList<>(Arrays.asList(this.student.getCurrentCourseModules()));
        currentCourseModules.removeAll(Collections.singleton(null));
        System.out.println(("My Course: "
                + (!Objects.equals(this.student.getCourseName(), "") &&
                (!Objects.equals(this.student.getCourseName(), null))
                ? this.student.getCourseName() : "Not Enrolled")));

        if (Objects.equals(this.student.getCourseName(), "") || Objects.equals(this.student.getCourseName(), null)) {
            System.out.println("""
                    What would you like to do?\s
                    (1) Enrol on a course""");

            String action = scanner.nextLine();

            if (Objects.equals(action, "1")) {
                this.registerForCourse(students);
            }
        } else if (currentCourseModules.size() < 4) {
            System.out.println("""
                    What would you like to do?\s
                    (1) Enrol on a course module\s
                    (2) View current course modules""");

            String action = scanner.nextLine();

            if (Objects.equals(action, "1")) {
                this.enrolOntoCourseModule(students);
            } else if (Objects.equals(action, "2")) {
                this.viewCurrentCourseModules(students);
            }
        } else {
            System.out.println("""
                    What would you like to do?
                    (1) View current course modules""");
            String action = scanner.nextLine();

            if (Objects.equals(action, "1")) {
                this.viewCurrentCourseModules(students);
            }
        }
    }

    /**
     * Allows the student to register onto a course.
     * @param students all the students in the system, ready for saving
     */
    private void registerForCourse(ArrayList<Student> students) {
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
                String courseName = allCourses.get(Integer.parseInt(courseNumber) - 1).getName();
                System.out.print("Are you sure you want to enrol onto "
                        + courseName
                        + "? (Y/N)");
                String confirmation = scanner.nextLine();

                if (Objects.equals(confirmation.toLowerCase(Locale.ROOT), "y")) {
                    student.registerForCourse(courseName);
                    new StudentSaver().saveAllStudents(students);
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
        Course course = new CourseLoader().loadCourse(this.student.getCourseName());
        HashSet<String> courseModulesCodesInCourse = course.getCourseModuleCodes();
        ArrayList<CourseModule> availableCourseModules = new ArrayList<>();
        ArrayList<String> currentCourseModules = new ArrayList<>(Arrays.asList(this.student.getCurrentCourseModules()));
        currentCourseModules.removeAll(Collections.singleton(null));
        ArrayList<String> completedCourseModules = this.student.getCompletedCourseModules();

        for (String courseModuleCode: courseModulesCodesInCourse) {
            CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCode);

            if (!completedCourseModules.contains(courseModuleCode)
                    && !currentCourseModules.contains(courseModuleCode)
                    && courseModule.getLevel() == this.student.getLevel()) {
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

                    for (String courseModuleCode: currentCourseModules) {
                        CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCode);

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
                    new StudentSaver().saveAllStudents(students);
                    selectedCourseModule.addStudentName(this.student.getUsername());
                    ArrayList<CourseModule> allCourseModules = new CourseModuleLoader().loadAllCourseModules();

                    for (CourseModule allCourseModule : allCourseModules) {
                        if (Objects.equals(allCourseModule.getCourseModuleCode(),
                                selectedCourseModule.getCourseModuleCode())) {
                            allCourseModule.addStudentName(this.student.getUsername());
                            new CourseModuleSaver().saveAllCourseModules(allCourseModules);
                            this.runStudentMenu();
                            return;
                        }
                    }
                }
            } else {
                System.out.println("Course module does not exist");
            }

        } else {
            System.out.println("Invalid input");
        }

        this.runStudentMenu();
    }

    private void viewCurrentCourseModules(ArrayList<Student> students) {
        ArrayList<String> currentCourseModuleCodes = new ArrayList<>(Arrays.asList(this.student.getCurrentCourseModules()));
        currentCourseModuleCodes.removeAll(Collections.singleton(null));
        ArrayList<CourseModule> currentCourseModules = new ArrayList<>();

        for (String currentCourseModule: currentCourseModuleCodes) {
            currentCourseModules.add(new CourseModuleLoader().loadCourseModule(currentCourseModule));
        }

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Course Module Code", "Name", "Level", "Mandatory or Optional", "Instructors", "Assignments");
        asciiTable.addRule();

        for (CourseModule currentCourseModule: currentCourseModules) {
            StringBuilder instructorNames = new StringBuilder();
            StringBuilder assignmentNames = new StringBuilder();

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
                Assignment assignment = new AssignmentLoader().loadAssignment(assignmentId);
                assignmentNames.append(assignment.getAssignmentName()).append("\n");
            }

            asciiTable.addRow(
                    currentCourseModule.getCourseModuleCode(),
                    currentCourseModule.getName(),
                    currentCourseModule.getLevel(),
                    currentCourseModule.getIsMandatory() ? "Mandatory" : "Optional",
                    instructorNames,
                    assignmentNames
            );

            asciiTable.addRule();
        }

        System.out.println(asciiTable.render());
        System.out.print("Press a key to continue: ");
        scanner.nextLine();
        this.runStudentMenu();
    }
}
