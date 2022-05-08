package com.company.Menus;

import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.Instructor;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class CourseAdministratorCourseModuleSubMenu {
    private CourseAdministrator courseAdministrator;
    private final Scanner scanner;
    private final ArrayList<Course> courses;

    public CourseAdministratorCourseModuleSubMenu(Scanner scanner, CourseAdministrator courseAdministrator, ArrayList<Course> courses) {
        this.scanner = scanner;
        this.courses = courses;
    }

    /**
     * Displays options in regards to modules.
     * @param courses the list of courses to update.
     */
    private void runCourseModuleSubMenu(ArrayList<Course> courses) {
        System.out.println("""
                What would you like to do?\s
                (1) Add a course module to a course\s
                (2) Remove course module from a course\s
                (3) Go back to main menu""");
        String action = scanner.nextLine();

        if (Objects.equals(action, "1")) {
            this.addCourseModuleToCourse(courses);
        } else if (Objects.equals(action, "2")) {
            this.removeCourseModuleFromCourse(courses);
        } else if (Objects.equals(action, "3")) {
            new CourseAdministratorMenu(this.scanner).runCourseAdministratorMenu();
        }
    }

    /**
     * Adds a new course module to an existing course.
     * @param courses the list of courses to update.
     */
    private void addCourseModuleToCourse(ArrayList<Course> courses) {
        System.out.print("Enter the number of the course to add a course module to: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 >= 0) {
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

                    if (Integer.parseInt(instructorNumber) - 1 < courses.size() - 1 && Integer.parseInt(instructorNumber) - 1 >= 0) {
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

    /**
     * Removes a course module from a course.
     * @param courses the lost of courses to update.
     */
    private void removeCourseModuleFromCourse(ArrayList<Course> courses) {
        System.out.print("Enter the number of the course to remove a course module from: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 < courses.size() && Integer.parseInt(action) - 1 >= 0) {
                Course courseToRemoveModuleFrom = courses.get(Integer.parseInt(action) - 1);

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
                        "Course Modules in " + courseToRemoveModuleFrom.getName());
                asciiTable.addRule();
                asciiTable.addRow(
                        "Number",
                        "Course Module Code",
                        "Name",
                        "Level",
                        "Instructor",
                        "Mandatory or Optional",
                        "Assignment Ids",
                        "Students");

                ArrayList<String> courseModuleCodes = new ArrayList<>(courseToRemoveModuleFrom.getCourseModuleCodes());

                for (int i = 0; i < courseToRemoveModuleFrom.getCourseModuleCodes().size(); i++) {
                    CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCodes.get(i));

                    asciiTable.addRule();
                    asciiTable.addRow(
                            i + 1,
                            courseModule.getCourseModuleCode(),
                            courseModule.getName(),
                            courseModule.getLevel(),
                            courseModule.getInstructorName(),
                            courseModule.getIsMandatory() ? "Mandatory" : "Optional",
                            courseModule.getAssignmentIds(),
                            courseModule.getStudentNames()
                    );
                }

                asciiTable.addRule();
                System.out.println(asciiTable.render());

                System.out.print("Enter the number of the course module to remove: ");
                String courseModuleNumber = scanner.nextLine();

                if (StringUtils.isNumeric(courseModuleNumber)) {
                    if (Integer.parseInt(courseModuleNumber) - 1 < courseModuleCodes.size() &&
                            Integer.parseInt(courseModuleNumber) - 1 >= 0) {
                        CourseModule courseModuleToRemove =
                                new CourseModuleLoader()
                                        .loadCourseModule(courseModuleCodes.get(Integer.parseInt(courseModuleNumber) - 1));
                        System.out.print(
                                "Are you sure you want to remove "
                                        + courseModuleToRemove.getName()
                                        + " from "
                                        + courseToRemoveModuleFrom.getName()
                                        + "? (Y/N)");
                        action = scanner.nextLine();

                        if (action.toLowerCase(Locale.ROOT).equals("y")) {
                            courseAdministrator.removeCourseModuleFromCourse(courses, courseToRemoveModuleFrom, courseModuleToRemove);
                        }
                    } else {
                        System.out.println("Course number does not exist");
                    }
                } else {
                    System.out.println("Invalid input");
                }
            }

            this.runCourseModuleSubMenu(courses);
        }
    }
}
