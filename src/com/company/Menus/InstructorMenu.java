package com.company.Menus;

import com.company.FileHandling.Loaders.AssignmentLoader;
import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.Models.Study.Assignment;
import com.company.Models.Study.CourseModule;
import com.company.Models.Study.CourseModuleResult;
import com.company.Models.Users.Instructor;
import com.company.Models.Users.Student;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Class that handles the interactions for instructors.
 */
public class InstructorMenu {
    private Instructor instructor;
    private final Scanner scanner;

    public InstructorMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * The initial method that is called when an instructor is logged in,
     * which directs them to what they can do
     */
    public void runInstructorMenu() {
        System.out.println("Logging in as instructor");

        this.instructor = new InstructorLoader().loadAllInstructors().get(0);
        String[] courseModuleCodes = instructor.getCourseModules();
        ArrayList<CourseModule> courseModules = new ArrayList<>();

        for (String courseModuleCode: courseModuleCodes) {
            if (courseModuleCode != null) {
                courseModules.add(new CourseModuleLoader().loadCourseModule(courseModuleCode));
            }
        }

        if (courseModules.size() > 0) {
            AsciiTable asciiTable = new AsciiTable();
            asciiTable.addRule();
            asciiTable.addRow(null, null, null, null, null, null, "My Course Modules");
            asciiTable.addRule();
            asciiTable.addRow(
                    "Number",
                    "Course Module Code",
                    "Name",
                    "Level",
                    "Mandatory or Optional",
                    "Assignments",
                    "Enrolled Students");
            asciiTable.addRule();

            for (int i = 0; i < courseModules.size(); i++) {
                StringBuilder assignmentNames = new StringBuilder();

                for (String assignmentId: courseModules.get(i).getAssignmentIds()) {
                    Assignment currentAssignment = new AssignmentLoader().loadAssignment(assignmentId);
                    assignmentNames.append(currentAssignment.getAssignmentName());
                }

                asciiTable.addRow(
                        i + 1,
                        courseModules.get(i).getCourseModuleCode(),
                        courseModules.get(i).getName(),
                        courseModules.get(i).getLevel(),
                        courseModules.get(i).getIsMandatory() ? "Mandatory" : "Optional",
                        assignmentNames,
                        courseModules.get(i).getStudentNames()
                );
                asciiTable.addRule();
            }

            System.out.println(asciiTable.render());

            System.out.println("""
                    What would you like to do?\s
                    (1) Add marks to student\s
                    (2) Create an assignment\s
                    (3) View assignments in a course module\s
                    (4) Mark a student as completed the course module""");
            String action = scanner.nextLine();

            if (Objects.equals(action, "1")) {
                this.addMarksToStudent(courseModules);
            } else if (Objects.equals(action, "2")) {
                this.createAssignment(courseModules);
            } else if (Objects.equals(action, "3")) {
                this.viewAssignmentsInCourseModule(courseModules);
            } else if (Objects.equals(action, "4")) {
                this.markStudentAsCompletedModule(courseModules);
            }

            this.runInstructorMenu();
        } else {
            System.out.println("You have not been assigned any course modules");
        }
    }

    /**
     * Allows the instructor to view the students on a selected course module that they are teaching
     * @param courseModules all the course modules the instructor has
     */
    private void addMarksToStudent(ArrayList<CourseModule> courseModules) {
        System.out.print("Please enter the number of the course module you would like to view: ");
        String courseModuleNumber = scanner.nextLine();

        if (StringUtils.isNumeric(courseModuleNumber)) {
            if (Integer.parseInt(courseModuleNumber) - 1 < courseModules.size()
                    && Integer.parseInt(courseModuleNumber) - 1 >= 0) {
                CourseModule selectedCourseModule = courseModules.get(Integer.parseInt(courseModuleNumber) - 1);

                HashSet<String> studentNames = selectedCourseModule.getStudentNames();

                if (studentNames.size() == 0) {
                    System.out.println("There are no students enrolled on this course module.");
                    this.runInstructorMenu();
                    return;
                }

                AsciiTable asciiTable = new AsciiTable();
                asciiTable.addRule();
                asciiTable.addRow(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "Students in " + selectedCourseModule.getName());
                asciiTable.addRule();
                asciiTable.addRow(
                        "Number",
                        "Username",
                        "First Name",
                        "Middle Name",
                        "Last Name",
                        "Year",
                        "Level",
                        "Course",
                        "Completed Course Modules",
                        "Current Course Modules"
                );
                asciiTable.addRule();

                for (int i = 0; i < studentNames.size(); i++) {
                    Student currentStudent = new StudentLoader().loadStudent(new ArrayList<>(studentNames).get(i));

                    if (currentStudent == null) {
                        System.out.println("Student not found");
                        break;
                    }

                    StringBuilder currentCourseModules = new StringBuilder();
                    for (CourseModuleResult courseModule: currentStudent.getCurrentCourseModules()) {
                        if (courseModule != null) {
                            currentCourseModules.append(courseModule.getCourseModuleCode());
                        }
                    }

                    StringBuilder completedCourseModules = new StringBuilder();
                    for (CourseModuleResult courseModuleResult: currentStudent.getCompletedCourseModules()) {
                        completedCourseModules.append(courseModuleResult.getCourseModuleCode());
                    }

                    asciiTable.addRow(
                            i + 1,
                            currentStudent.getUsername(),
                            currentStudent.getFirstName(),
                            currentStudent.getMiddleName(),
                            currentStudent.getLastName(),
                            currentStudent.getYear(),
                            currentStudent.getLevel(),
                            currentStudent.getCourseName(),
                            completedCourseModules,
                            currentCourseModules
                    );
                    asciiTable.addRule();
                }


                System.out.println(asciiTable.render());
                System.out.print("Enter the number of the student you wish to add marks to: ");
                String studentNumber = scanner.nextLine();

                if (StringUtils.isNumeric(studentNumber)) {
                    if (Integer.parseInt(studentNumber) - 1 < studentNames.size()
                            && Integer.parseInt(studentNumber) - 1 >= 0) {
                        Student selectedStudent = new StudentLoader().loadStudent(new ArrayList<>(studentNames)
                                .get(Integer.parseInt(studentNumber) - 1));
                        System.out.print("Are you sure you want to add marks for "
                                + selectedStudent.getFirstName() + " " + selectedStudent.getLastName() + "? (Y/N) "
                        );

                        String action = scanner.nextLine();

                        if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                            CourseModuleResult[] currentCourseModules = selectedStudent.getCurrentCourseModules();
                            AsciiTable assignmentsTable = new AsciiTable();

                            for (CourseModuleResult courseModuleResult : currentCourseModules) {
                                if (Objects.equals(courseModuleResult.getCourseModuleCode(),
                                        selectedCourseModule.getCourseModuleCode())) {
                                    LinkedHashMap<String, Integer> assignmentResults = courseModuleResult.getAssignmentResults();

                                    assignmentsTable.addRule();
                                    assignmentsTable.addRow(
                                            null,
                                            null,
                                            selectedStudent.getFirstName()
                                                    + "'s Assignment Results in "
                                                    + selectedCourseModule.getName());
                                    assignmentsTable.addRule();
                                    assignmentsTable.addRow("Number", "Assignment Name", "Mark");
                                    assignmentsTable.addRule();

                                    Set<String> assignmentIds = assignmentResults.keySet();
                                    int index = 1;

                                    for (String assignmentId : assignmentIds) {
                                        Assignment currentAssignment = new AssignmentLoader().loadAssignment(assignmentId);

                                        assignmentsTable.addRow(
                                                index,
                                                currentAssignment.getAssignmentName(),
                                                assignmentResults.get(assignmentId)
                                                        + "/" + currentAssignment.getTotalPossibleMarks());
                                        assignmentsTable.addRule();
                                        index++;
                                    }

                                    System.out.println(assignmentsTable.render());

                                    System.out.print("Enter the number of the assignment you'd like to add marks to: ");
                                    String assignmentNumber = scanner.nextLine();

                                    if (StringUtils.isNumeric(assignmentNumber)) {
                                        if (Integer.parseInt(assignmentNumber) - 1 < studentNames.size()
                                                && Integer.parseInt(assignmentNumber) - 1 >= 0) {
                                            ArrayList<String> assignmentIdsArrayList = new ArrayList<>(assignmentIds);
                                            Assignment assignment = new AssignmentLoader().loadAssignment(
                                                    assignmentIdsArrayList.get(Integer.parseInt(assignmentNumber) - 1)
                                            );

                                            System.out.print("Are you sure you want to add marks to "
                                                    + assignment.getAssignmentName()
                                                    + "? (Y/N) ");

                                            action = scanner.nextLine();

                                            if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                                                System.out.print("Enter the mark: ");
                                                String mark = scanner.nextLine();

                                                if (StringUtils.isNumeric(mark)) {
                                                    if (!(Integer.parseInt(mark) > assignment.getTotalPossibleMarks())
                                                            && !(Integer.parseInt(mark) < 0)) {
                                                        this.instructor.addMark(
                                                                selectedStudent,
                                                                selectedCourseModule,
                                                                assignment,
                                                                Integer.parseInt(mark));

                                                        System.out.println("Marks added!");

                                                        System.out.print("Has "
                                                                + selectedStudent.getFirstName()
                                                                + " completed the course module? (Y/N)");

                                                        action = scanner.nextLine();

                                                        if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                                                           this.instructor.markStudentAsCompleted(selectedStudent, selectedCourseModule);
                                                        }
                                                    } else {
                                                        System.out.println("Marks entered are out of range");
                                                    }
                                                } else {
                                                    System.out.println("Invalid input");
                                                }
                                            }
                                        } else {
                                            System.out.println("Assignment number not found");
                                        }
                                    } else {
                                        System.out.println("Invalid input");
                                    }

                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Student number not found");
                    }
                } else {
                    System.out.println("Invalid input");
                }
            } else {
                System.out.println("Course module number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runInstructorMenu();
    }

    /**
     * Allows the instructor to create an assignment
     * @param courseModules the instructor's course modules that they can add an assignment to
     */
    private void createAssignment(ArrayList<CourseModule> courseModules) {
        System.out.print("Please enter the number of the course module you would like to add an assignment to: ");
        String courseModuleNumber = scanner.nextLine();

        if (StringUtils.isNumeric(courseModuleNumber)) {
            if (Integer.parseInt(courseModuleNumber) - 1 < courseModules.size()
                    && Integer.parseInt(courseModuleNumber) - 1 >= 0) {
                System.out.print("Are you sure you want to add an assignment to "
                        + courseModules.get(Integer.parseInt(courseModuleNumber) - 1).getName() + "? (Y/N)");
                String action = scanner.nextLine();

                if (Objects.equals(action, "y")) {
                    System.out.print("Enter the assignment name: ");
                    String assignmentName = scanner.nextLine();
                    String totalPossibleMarks = null;

                    while (!StringUtils.isNumeric(totalPossibleMarks)) {
                        System.out.print("Enter the total number of marks on this assignment: ");
                        totalPossibleMarks = scanner.nextLine();
                    }

                    int total = Integer.parseInt(totalPossibleMarks);
                    this.instructor.createAssignment(
                            courseModules.get(Integer.parseInt(courseModuleNumber) - 1).getCourseModuleCode(),
                            assignmentName,
                            total
                    );
                }
            } else {
                System.out.println("Course module does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runInstructorMenu();
    }

    /**
     * See all the assignments that are in a course module
     * @param courseModules all the course modules the instructor teaches
     */
    private void viewAssignmentsInCourseModule(ArrayList<CourseModule> courseModules) {
        System.out.print("Please enter the number of the course module you would like to view: ");
        String courseModuleNumber = scanner.nextLine();

        if (StringUtils.isNumeric(courseModuleNumber)) {
            if (Integer.parseInt(courseModuleNumber) - 1 < courseModules.size()
                    && Integer.parseInt(courseModuleNumber) - 1 >= 0) {
                CourseModule selectedCourseModule = courseModules.get(Integer.parseInt(courseModuleNumber) - 1);
                HashSet<String> assignmentIds = selectedCourseModule.getAssignmentIds();

                if (assignmentIds.size() == 0) {
                    System.out.println("There are no assignments on this course module.");
                    this.runInstructorMenu();
                    return;
                }

                AsciiTable asciiTable = new AsciiTable();

                asciiTable.addRule();
                asciiTable.addRow(null, "Assignments in " + selectedCourseModule.getName());
                asciiTable.addRule();
                asciiTable.addRow("Name", "Total Marks");
                asciiTable.addRule();

                for (String assignmentId: assignmentIds) {
                    Assignment assignment = new AssignmentLoader().loadAssignment(assignmentId);
                    asciiTable.addRow(assignment.getAssignmentName(), assignment.getTotalPossibleMarks());
                    asciiTable.addRule();
                }

                System.out.println(asciiTable.render());
                System.out.print("Press a key to continue: ");
                scanner.nextLine();
            } else {
                System.out.println("Course module number not found");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runInstructorMenu();
    }

    private void markStudentAsCompletedModule(ArrayList<CourseModule> courseModules) {
        System.out.print("Please enter the number of the course module: ");
        String courseModuleNumber = scanner.nextLine();

        if (StringUtils.isNumeric(courseModuleNumber)) {
            if (Integer.parseInt(courseModuleNumber) - 1 < courseModules.size()
                    && Integer.parseInt(courseModuleNumber) - 1 >= 0) {
                CourseModule selectedCourseModule = courseModules.get(Integer.parseInt(courseModuleNumber) - 1);

                HashSet<String> studentNames = selectedCourseModule.getStudentNames();

                if (studentNames.size() == 0) {
                    System.out.println("There are no students enrolled on this course module.");
                    this.runInstructorMenu();
                    return;
                }

                AsciiTable asciiTable = new AsciiTable();
                asciiTable.addRule();
                asciiTable.addRow(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "Students in " + selectedCourseModule.getName());
                asciiTable.addRule();
                asciiTable.addRow(
                        "Number",
                        "Username",
                        "First Name",
                        "Middle Name",
                        "Last Name",
                        "Year",
                        "Level",
                        "Course",
                        "Completed Course Modules",
                        "Current Course Modules"
                );
                asciiTable.addRule();

                for (int i = 0; i < studentNames.size(); i++) {
                    Student currentStudent = new StudentLoader().loadStudent(new ArrayList<>(studentNames).get(i));

                    if (currentStudent == null) {
                        System.out.println("Student not found");
                        break;
                    }

                    StringBuilder currentCourseModules = new StringBuilder();
                    for (CourseModuleResult courseModuleResult: currentStudent.getCurrentCourseModules()) {
                        if (courseModuleResult != null) {
                            currentCourseModules.append(courseModuleResult.getCourseModuleCode());
                        }
                    }

                    StringBuilder completedCourseModules = new StringBuilder();
                    for (CourseModuleResult courseModuleResult: currentStudent.getCompletedCourseModules()) {
                        completedCourseModules.append(courseModuleResult.getCourseModuleCode());
                    }

                    asciiTable.addRow(
                            i + 1,
                            currentStudent.getUsername(),
                            currentStudent.getFirstName(),
                            currentStudent.getMiddleName(),
                            currentStudent.getLastName(),
                            currentStudent.getYear(),
                            currentStudent.getLevel(),
                            currentStudent.getCourseName(),
                            completedCourseModules,
                            currentCourseModules
                    );
                    asciiTable.addRule();
                }


                System.out.println(asciiTable.render());
                System.out.print("Enter the number of the student you wish to mark as completed for this course module: ");
                String studentNumber = scanner.nextLine();

                if (StringUtils.isNumeric(studentNumber)) {
                    if (Integer.parseInt(studentNumber) - 1 < studentNames.size()
                            && Integer.parseInt(studentNumber) - 1 >= 0) {
                        Student selectedStudent = new StudentLoader().loadStudent(new ArrayList<>(studentNames)
                                .get(Integer.parseInt(studentNumber) - 1));
                        System.out.print("Are you sure you want to mark "
                                + selectedStudent.getFirstName()
                                + " "
                                + selectedStudent.getLastName()
                                + " as completed for this course module? (Y/N) "
                        );

                        String action = scanner.nextLine();

                        if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                            this.instructor.markStudentAsCompleted(selectedStudent, selectedCourseModule);
                        }
                    }
                }
            }
        }
    }
}
