package com.company.Menus;

import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.Models.Study.Course;
import com.company.Models.Users.CourseAdministrator;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

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

        this.courseAdministrator = new CourseAdministratorLoader().loadAllCourseAdmistrators().get(0);

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
                    (4) Delete a course""");
        String action = scanner.nextLine();

        if (Objects.equals(action, "1")) {
            this.cancelCourse(courses);
        } else if(Objects.equals(action, "2")) {
            this.reopenCourse(courses);
        } else if (Objects.equals(action, "3")) {
            this.addCourse(courses);
        } else if (Objects.equals(action, "4")) {
            this.deleteCourse(courses);
        }
    }

    /**
     * Retrieves the course the course administrator wants to cancel, validates it, and proceeds to cancel it.
     * @param courses the list of courses to update
     */
    public void cancelCourse(ArrayList<Course> courses) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the course to cancel: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 > courses.size() || Integer.parseInt(action) - 1 < 1) {
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
    public void reopenCourse(ArrayList<Course> courses) {
        System.out.print("Enter the number of the course to reopen: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 > courses.size() || Integer.parseInt(action) - 1 < 1) {
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

    public void addCourse(ArrayList<Course> courses) {
        System.out.print("Enter the course name: ");
        String courseName = scanner.nextLine();

        courseAdministrator.addNewCourse(courses, courseName);
        this.runCourseAdministratorMenu();
    }

    public void deleteCourse(ArrayList<Course> courses) {
        System.out.print("Enter the number of the course to delete: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 > courses.size() || Integer.parseInt(action) - 1 < 1) {
                Course courseToReopen = courses.get(Integer.parseInt(action) - 1);

                System.out.println("Are you sure you want to PERMANENTLY delete " + courseToReopen.getName() + "? (Y/N)");
                action = scanner.nextLine();

                if (action.toLowerCase(Locale.ROOT).equals("y")) {
                    System.out.println("Deleting course...");
                    courseAdministrator.deleteCourse(courses, courseToReopen);
                }
            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runCourseAdministratorMenu();
    }
}
