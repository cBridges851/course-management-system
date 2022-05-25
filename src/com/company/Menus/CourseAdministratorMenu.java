package com.company.Menus;

import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.FileHandling.Savers.ResultsSlipSaver;
import com.company.Models.Study.Course;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.Student;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class that handles the interactions for the course administrator.
 */
public class CourseAdministratorMenu {
    private CourseAdministrator courseAdministrator;
    private final Scanner scanner;

    public CourseAdministratorMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * The initial method that is called when a course administrator is logged in,
     * which directs them to what they can do
     */
    public void runCourseAdministratorMenu() {
        System.out.println("Logging in as course administrator");

        this.courseAdministrator = new CourseAdministratorLoader().loadAllCourseAdministrators().get(0);

        ArrayList<Course> courses = courseAdministrator.getAllCourses();

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(null, null, null, "Courses");
        asciiTable.addRule();

        for (int i = 0; i < courses.size(); i++) {
            String availability = courses.get(i).getIsAvailable() ? "Available" : "Unavailable";
            asciiTable.addRow(i + 1, courses.get(i).getName(), courses.get(i).getCourseModuleCodes(), availability);
            asciiTable.addRule();
        }

        System.out.println(asciiTable.render());
        System.out.println("""
                    What would you like to do?\s
                    (1) Cancel a course\s
                    (2) Reopen a course\s
                    (3) Add a course\s
                    (4) Delete a course\s
                    (5) Rename a course
                    (6) Manage course modules\s
                    (7) Generate results slip""");
        String action = scanner.nextLine();

        if (Objects.equals(action, "1")) {
            this.cancelCourse(courses);
        } else if(Objects.equals(action, "2")) {
            this.reopenCourse(courses);
        } else if (Objects.equals(action, "3")) {
            this.addCourse(courses);
        } else if (Objects.equals(action, "4")) {
            this.deleteCourse(courses);
        } else if (Objects.equals(action, "5")) {
            this.renameCourse(courses);
        } else if(Objects.equals(action, "6")) {
            new CourseAdministratorCourseModuleSubMenu(this.scanner, this.courseAdministrator, courses)
                    .runCourseModuleSubMenu();
        } else if (Objects.equals(action, "7")) {
            this.generateResultsSlip();
        }
    }

    /**
     * Retrieves the course the course administrator wants to cancel, validates it, and proceeds to cancel it.
     * @param courses the list of courses to update
     */
    private void cancelCourse(ArrayList<Course> courses) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the course to cancel: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 >= 0) {
                Course courseToCancel = courses.get(Integer.parseInt(action) - 1);

                if (!courseToCancel.getIsAvailable()) {
                    System.out.println("This course is already cancelled!");
                } else {
                    System.out.print("Are you sure you want to cancel " + courseToCancel.getName() + "? (Y/N) ");
                    action = scanner.nextLine();

                    if (action.toLowerCase(Locale.ROOT).equals("y")) {
                        System.out.println("Cancelling course...");
                        courseAdministrator.cancelCourse(courses, courseToCancel);
                    }
                }
            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runCourseAdministratorMenu();
    }

    /**
     * Retrieves the course the course administrator wants to reopen, validates it, and proceeds to cancel it.
     * @param courses the list of courses to update
     */
    private void reopenCourse(ArrayList<Course> courses) {
        System.out.print("Enter the number of the course to reopen: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 >= 0) {
                Course courseToReopen = courses.get(Integer.parseInt(action) - 1);

                if (courseToReopen.getIsAvailable()) {
                    System.out.println("This course is already available");
                } else {
                    System.out.println("Are you sure you want to reopen " + courseToReopen.getName() + "? (Y/N)");
                    action = scanner.nextLine();

                    if (action.toLowerCase(Locale.ROOT).equals("y")) {
                        System.out.println("Reopening course...");
                        courseAdministrator.reopenCourse(courses, courseToReopen);
                    }
                }
            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runCourseAdministratorMenu();
    }

    /**
     * Adds a new course to the system.
     * @param courses the list of courses to update.
     */
    private void addCourse(ArrayList<Course> courses) {
        System.out.print("Enter the course name: ");
        String courseName = scanner.nextLine();

        courseAdministrator.addNewCourse(courses, courseName);
        this.runCourseAdministratorMenu();
    }

    /**
     * Permanently deletes a course from a system.
     * @param courses the list of courses to update.
     */
    private void deleteCourse(ArrayList<Course> courses) {
        System.out.print("Enter the number of the course to delete: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 >= 0) {
                Course courseToDelete = courses.get(Integer.parseInt(action) - 1);

                System.out.println("Are you sure you want to PERMANENTLY delete " + courseToDelete.getName() + "? (Y/N)");
                action = scanner.nextLine();

                if (action.toLowerCase(Locale.ROOT).equals("y")) {
                    System.out.println("Deleting course...");
                    courseAdministrator.deleteCourse(courses, courseToDelete);
                }
            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runCourseAdministratorMenu();
    }

    /**
     * Renames an existing course in the system.
     * @param courses the list of courses to update.
     */
    private void renameCourse(ArrayList<Course> courses) {
        System.out.print("Enter the number of the course to rename: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 >= 0) {
                Course courseToRename = courses.get(Integer.parseInt(action) - 1);

                System.out.println("Are you sure you want to rename " + courseToRename.getName() + "? (Y/N)");
                action = scanner.nextLine();

                if (action.toLowerCase(Locale.ROOT).equals("y")) {
                    System.out.print("Enter the course's new name: ");
                    String newName = scanner.nextLine();
                    courseAdministrator.renameCourse(courses, courseToRename, newName);
                }
            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runCourseAdministratorMenu();
    }

    /**
     * Creates a results slip for a specified student and outputs it, with the option to save it to a file.
     */
    private void generateResultsSlip() {
        ArrayList<Student> allStudents = new StudentLoader().loadAllStudents();
        AsciiTable studentTable = new AsciiTable();
        studentTable.addRule();
        studentTable.addRow(null, "All Students");
        studentTable.addRule();
        studentTable.addRow("Number", "Name");
        studentTable.addRule();

        for (int i = 0; i < allStudents.size(); i++) {
            studentTable.addRow(
                    i + 1,
                    allStudents.get(i).getFirstName() + " " + allStudents.get(i).getLastName() + " (" + allStudents.get(i).getUsername() + ")");
            studentTable.addRule();
        }

        System.out.println(studentTable.render());
        System.out.print("Enter the number of the student you wish to generate a results slip for: ");
        String studentNumber = scanner.nextLine();

        if (StringUtils.isNumeric(studentNumber)) {
            if (Integer.parseInt(studentNumber) - 1 < allStudents.size() && Integer.parseInt(studentNumber) - 1 >= 0) {
                Student selectedStudent = allStudents.get(Integer.parseInt(studentNumber) - 1);
                System.out.print("Generate a results slip for "
                        + selectedStudent.getFirstName()
                        + " "
                        + selectedStudent.getLastName()
                        + " ("
                        + selectedStudent.getUsername()
                        + ")? (Y/N) ");
                String action = scanner.nextLine();

                if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                    String resultsSlip = this.courseAdministrator.createResultsSlip(selectedStudent);
                    System.out.println(resultsSlip);
                    System.out.print("Would you like to save this results slip to a file? (Y/N) ");
                    action = scanner.nextLine();

                    if (Objects.equals(action, "y")) {
                        new ResultsSlipSaver().saveResultsSlip(
                                selectedStudent.getLastName()
                                        + "-"
                                        + selectedStudent.getFirstName()
                                        + "-lvl" + selectedStudent.getLevel()
                                        + ".txt",
                                resultsSlip);
                    }
                }
            } else {
                System.out.println("Student number not found");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runCourseAdministratorMenu();
    }
}
