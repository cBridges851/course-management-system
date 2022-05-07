package com.company.Menus;

import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.Models.Study.Course;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.Instructor;
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
                    (4) Delete a course\s
                    (5) Rename a course
                    (6) Manage course modules""");
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
            this.runCourseModuleSubMenu(courses);
        }
    }

    /**
     * Displays options in regards to modules.
     * @param courses the list of courses to update.
     */
    private void runCourseModuleSubMenu(ArrayList<Course> courses) {
        System.out.println("""
                What would you like to do?\s
                (1) Add a course module to a course\s
                (2) Go back to main menu""");
        String action = scanner.nextLine();

        if (Objects.equals(action, "1")) {
            this.addCourseModuleToCourse(courses);
        } else if (Objects.equals(action, "2")) {
            this.runCourseAdministratorMenu();
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
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 > 1) {
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
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 > 1) {
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
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 > 1) {
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
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 > 1) {
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
     * Adds a new course module to an existing course.
     * @param courses the list of courses to update.
     */
    private void addCourseModuleToCourse(ArrayList<Course> courses) {
        System.out.print("Enter the number of the course to add a course module to: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 > -1) {
                Course courseToAddTo = courses.get(Integer.parseInt(action) - 1);

                System.out.println("Are you sure you want to add a module to " + courseToAddTo.getName() + "? (Y/N)");
                action = scanner.nextLine();

                if (action.toLowerCase(Locale.ROOT).equals("y")) {
                    System.out.print("Enter the course module's module code: ");
                    String courseModuleCode = scanner.nextLine();
                    System.out.print("Enter the course module's name: ");
                    String courseModuleName = scanner.nextLine();
                    System.out.print("Enter the course module's level: ");
                    String moduleLevel = scanner.nextLine();

                    if (!StringUtils.isNumeric(moduleLevel)) {
                        System.out.println("Invalid level");
                        this.addCourseModuleToCourse(courses);
                        return;
                    }

                    int courseModuleLevelInt = Integer.parseInt(moduleLevel);

                    ArrayList<Instructor> instructors = new InstructorLoader().loadAllInstructors();

                    for (int i = 0; i < instructors.size(); i++) {
                        System.out.println((i + 1) + " "
                                + instructors.get(i).getFirstName() + " "
                                + instructors.get(i).getMiddleName() + " "
                                + instructors.get(i).getLastName());
                    }
                    System.out.println((instructors.size() + 1) + " Skip");
                    System.out.print("Enter the number of the instructor for this course module, or press "
                            + (instructors.size() + 1) + " to skip: ");

                    String instructorNumber = scanner.nextLine();

                    if (!StringUtils.isNumeric(instructorNumber)) {
                        System.out.println("Invalid input");
                        this.runCourseModuleSubMenu(courses);
                        return;
                    }

                    String instructorName;

                    if (Integer.parseInt(instructorNumber) - 1 < courses.size() - 1 && Integer.parseInt(instructorNumber) - 1 > -1) {
                        System.out.println(Integer.parseInt(instructorNumber) - 1);
                        instructorName = instructors.get(Integer.parseInt(instructorNumber) - 1).getUsername();
                    } else {
                        instructorName = "";
                    }

                    System.out.print("Is this module mandatory? (Y/N) ");
                    boolean isMandatory = scanner.nextLine().toLowerCase(Locale.ROOT).equals("y");

                    courseAdministrator.addNewCourseModuleToCourse(
                            courses,
                            courseToAddTo,
                            courseModuleCode,
                            courseModuleName,
                            courseModuleLevelInt,
                            instructorName,
                            isMandatory,
                            new HashSet<>(),
                            new HashSet<>());
                }

            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runCourseModuleSubMenu(courses);
    }
}
