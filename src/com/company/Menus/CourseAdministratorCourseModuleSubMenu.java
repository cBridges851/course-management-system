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

/**
 * Menu that handles tasks specifically about course modules.
 */
public class CourseAdministratorCourseModuleSubMenu implements IMenu {
    private final CourseAdministrator courseAdministrator;
    private final Scanner scanner;
    private final ArrayList<Course> courses;
    private ArrayList<CourseModule> courseModules;

    public CourseAdministratorCourseModuleSubMenu(Scanner scanner, CourseAdministrator courseAdministrator, ArrayList<Course> courses) {
        this.scanner = scanner;
        this.courseAdministrator = courseAdministrator;
        this.courses = courses;
    }

    /**
     * Displays options in regards to modules.
     */
    public void run() {
        System.out.println("""
                What would you like to do?\s
                (1) Add a course module to a course\s
                (2) Remove course module from a course\s
                (3) Rename a course module\s
                (4) Add instructor to course module\s
                (5) Remove instructor from course module\s
                (6) Go back to main menu""");
        String action = scanner.nextLine();
        this.courseModules = courseAdministrator.getAllCourseModules();

        if (Objects.equals(action, "1")) {
            this.addCourseModuleToCourse();
        } else if (Objects.equals(action, "2")) {
            this.removeCourseModuleFromCourse();
        } else if (Objects.equals(action, "3")) {
            this.renameCourseModule();
        } else if (Objects.equals(action, "4")) {
            this.addInstructorToCourseModule();
        } else if (Objects.equals(action, "5")) {
            this.removeInstructorFromCourseModule();
        } else if(Objects.equals(action, "6")) {
            new CourseAdministratorMenu(this.courseAdministrator, this.scanner).run();
        } else {
            this.run();
        }
    }

    /**
     * Adds a new course module to an existing course.
     */
    private void addCourseModuleToCourse() {
        System.out.print("Enter the number of the course to add a course module to: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 < this.courses.size() && Integer.parseInt(action) - 1 >= 0) {
                Course courseToAddTo = this.courses.get(Integer.parseInt(action) - 1);

                System.out.print("Are you sure you want to add a course module to "
                        + courseToAddTo.getName()
                        + "? (Y/N) ");
                action = scanner.nextLine();

                if (action.toLowerCase(Locale.ROOT).equals("y")) {
                    boolean isValidCourseModuleCode = false;
                    String courseModuleCode = "";

                    while(!isValidCourseModuleCode) {
                        System.out.print("Enter the course module's module code: ");
                        courseModuleCode = scanner.nextLine();
                        CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCode);

                        if (courseModule == null) {
                            isValidCourseModuleCode = true;
                        } else {
                            System.out.println("Course module code taken");
                        }
                    }

                    System.out.print("Enter the course module's name: ");
                    String courseModuleName = scanner.nextLine();
                    System.out.print("Enter the course module's level: ");
                    String moduleLevel = scanner.nextLine();

                    if (!StringUtils.isNumeric(moduleLevel)) {
                        System.out.println("Invalid level");
                        this.addCourseModuleToCourse();
                        return;
                    }

                    int courseModuleLevelInt = Integer.parseInt(moduleLevel);

                    ArrayList<Instructor> instructors = new InstructorLoader().loadAllInstructors();

                    boolean isValidInstructor = false;
                    HashSet<String> instructorNames = new HashSet<>();

                    while (!isValidInstructor) {
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
                            this.addCourseModuleToCourse();
                            return;
                        }

                        String instructorName;

                        if (Integer.parseInt(instructorNumber) - 1 < instructors.size()
                                && Integer.parseInt(instructorNumber) - 1 >= 0) {
                            instructorName = instructors.get(Integer.parseInt(instructorNumber) - 1).getUsername();

                            String[] instructorsCourseModules = instructors.get(Integer.parseInt(instructorNumber) - 1).getCourseModules();
                            ArrayList<String> instructorsCourseModulesArrayList = new ArrayList<>(Arrays.asList(instructorsCourseModules));
                            instructorsCourseModulesArrayList.removeAll(Collections.singleton(null));

                            if (instructorsCourseModulesArrayList.size() < 4) {
                                instructorNames.add(instructorName);
                                isValidInstructor = true;
                            } else {
                                System.out.println("Instructors cannot be assigned to more than 4 modules");
                            }
                        } else if (Integer.parseInt(instructorNumber) == instructors.size() + 1) {
                            isValidInstructor = true;
                        } else {
                            System.out.println("Instructor number does not exist");
                        }
                    }

                    System.out.print("Is this module mandatory? (Y/N) ");
                    boolean isMandatory = scanner.nextLine().toLowerCase(Locale.ROOT).equals("y");

                    courseAdministrator.addNewCourseModuleToCourse(
                            courseToAddTo,
                            courseModuleCode,
                            courseModuleName,
                            courseModuleLevelInt,
                            instructorNames,
                            isMandatory,
                            new HashSet<>(),
                            new HashSet<>()
                    );
                }

            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.run();
    }

    /**
     * Removes a course module from a course.
     */
    private void removeCourseModuleFromCourse() {
        System.out.print("Enter the number of the course to remove a course module from: ");
        String action = scanner.nextLine();

        if (StringUtils.isNumeric(action)) {
            if (Integer.parseInt(action) - 1 < this.courses.size() && Integer.parseInt(action) - 1 >= 0) {
                Course courseToRemoveModuleFrom = this.courses.get(Integer.parseInt(action) - 1);

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
                            courseModule.getInstructorNames(),
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
                            courseAdministrator.removeCourseModuleFromCourse(this.courses, courseToRemoveModuleFrom, courseModuleToRemove);
                        }
                    } else {
                        System.out.println("Course number does not exist");
                    }
                } else {
                    System.out.println("Invalid input");
                }
            }

            this.run();
        }
    }

    /**
     * Renames a course module (which could be in one or multiple courses)
     */
    private void renameCourseModule() {
        this.displayCourseModuleTable(this.courseModules);

        System.out.print("Enter the number of the course module to rename: ");
        String courseModuleNumber = scanner.nextLine();

        if (StringUtils.isNumeric(courseModuleNumber)) {
            if (Integer.parseInt(courseModuleNumber) - 1 < this.courseModules.size() && Integer.parseInt(courseModuleNumber) - 1 >= 0) {
                CourseModule courseModuleToRename = this.courseModules.get(Integer.parseInt(courseModuleNumber) - 1);

                System.out.print("Are you sure you want to rename " + courseModuleToRename.getName() + "? (Y/N) ");
                String action = scanner.nextLine();

                if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                    System.out.print("What would you like to rename " + courseModuleToRename.getName() + " to? ");
                    String newName = scanner.nextLine();
                    this.courseAdministrator.renameCourseModule(courseModuleToRename, newName);
                }
            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.run();
    }

    /**
     * Adds an instructor onto a selected course module
     */
    private void addInstructorToCourseModule() {
        this.displayCourseModuleTable(this.courseModules);

        System.out.print("Enter the number of the course module to add an instructor to: ");
        String courseModuleNumber = scanner.nextLine();

        if (StringUtils.isNumeric(courseModuleNumber)) {
            if (Integer.parseInt(courseModuleNumber) - 1 < this.courseModules.size() && Integer.parseInt(courseModuleNumber) - 1 >= 0) {
                CourseModule courseModuleToAddInstructor = this.courseModules.get(Integer.parseInt(courseModuleNumber) - 1);

                System.out.print("Are you sure you want to add an instructor to " + courseModuleToAddInstructor.getName() + "? (Y/N) ");
                String action = scanner.nextLine();

                if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                    ArrayList<Instructor> instructors = new InstructorLoader().loadAllInstructors();

                    for (int i = 0; i < instructors.size(); i++) {
                        System.out.println(
                                i + 1
                                + " " + instructors.get(i).getFirstName()
                                + " " + instructors.get(i).getMiddleName()
                                + " " + instructors.get(i).getLastName());
                    }

                    System.out.print("Enter the number of the instructor to assign to this course module: ");
                    String instructorNumber = scanner.nextLine();

                    if (StringUtils.isNumeric(instructorNumber)) {
                        if (Integer.parseInt(instructorNumber) - 1 < instructors.size() && Integer.parseInt(instructorNumber) - 1 >= 0) {
                            Instructor instructor = instructors.get(Integer.parseInt(instructorNumber) - 1);
                            System.out.print("Assign "
                                    + instructor.getFirstName()
                                    + " " + instructor.getMiddleName()
                                    + " " + instructor.getLastName()
                                    + " to " + courseModuleToAddInstructor.getName() + "? (Y/N)");

                            action = scanner.nextLine();

                            if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                                this.courseAdministrator.assignInstructorToCourseModule(
                                        courseModuleToAddInstructor,
                                        instructor);
                            }
                        } else {
                            System.out.println("Instructor number does not exist");
                        }
                    } else {
                        System.out.println("Invalid input");
                    }
                }
            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.run();
    }

    /**
     * Removes an instructor from a selected course module.
     */
    private void removeInstructorFromCourseModule() {
        this.displayCourseModuleTable(this.courseModules);

        System.out.print("Enter the number of the course module to remove an instructor from: ");
        String courseModuleNumber = scanner.nextLine();

        if (StringUtils.isNumeric(courseModuleNumber)) {
            if (Integer.parseInt(courseModuleNumber) - 1 < this.courseModules.size() && Integer.parseInt(courseModuleNumber) - 1 >= 0) {
                CourseModule courseModuleToRemoveInstructor = this.courseModules.get(Integer.parseInt(courseModuleNumber) - 1);

                System.out.print("Are you sure you want to remove an instructor from " + courseModuleToRemoveInstructor.getName() + "? (Y/N) ");
                String action = scanner.nextLine();

                if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                    HashSet<String> allInstructorNamesOnCourseModule = courseModuleToRemoveInstructor.getInstructorNames();
                    ArrayList<Instructor> instructors = new ArrayList<>();

                    for (String instructorOnCourseModule: allInstructorNamesOnCourseModule) {
                        Instructor instructor = new InstructorLoader().loadInstructor(instructorOnCourseModule);
                        instructors.add(instructor);
                    }

                    if (instructors.size() == 0) {
                        System.out.println("There are no instructors on this course module");
                        run();
                        return;
                    }

                    for (int i = 0; i < instructors.size(); i++) {
                        System.out.println(
                                i + 1
                                + " " + instructors.get(i).getFirstName()
                                + " " + instructors.get(i).getMiddleName()
                                + " " + instructors.get(i).getLastName()
                        );
                    }

                    System.out.print("Enter the number of the instructor to remove: ");
                    String instructorNumber = scanner.nextLine();

                    if (StringUtils.isNumeric(instructorNumber)) {
                        if (Integer.parseInt(instructorNumber) - 1 < instructors.size()
                                && Integer.parseInt(instructorNumber) - 1 >= 0) {
                            Instructor instructorToRemove = instructors.get(Integer.parseInt(instructorNumber) - 1);
                            System.out.print(
                                    "Are you sure you want to remove "
                                            + instructorToRemove.getFirstName()
                                            + " " + instructorToRemove.getMiddleName()
                                            + " " + instructorToRemove.getLastName()
                                            + " from " + courseModuleToRemoveInstructor.getName() + "? (Y/N)"
                            );

                            action = scanner.nextLine();

                            if (Objects.equals(action.toLowerCase(Locale.ROOT), "y")) {
                                courseAdministrator.removeInstructorFromCourseModule(
                                        courseModuleToRemoveInstructor,
                                        instructorToRemove);
                            }
                        } else {
                            System.out.println("Instructor number does not exist");
                        }
                    } else {
                        System.out.println("Invalid input");
                    }
                }
            } else {
                System.out.println("Course module number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.run();
    }

    /**
     * Displays all the course modules in the system in a table
     * @param allCourseModules the course modules to display in the table
     */
    private void displayCourseModuleTable(ArrayList<CourseModule> allCourseModules) {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(null, null, null, null, null, null, null, "All Course Modules");
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
        
        for (int i = 0; i < allCourseModules.size(); i++) {
            asciiTable.addRule();
            asciiTable.addRow(
                    i + 1,
                    allCourseModules.get(i).getCourseModuleCode(),
                    allCourseModules.get(i).getName(),
                    allCourseModules.get(i).getLevel(),
                    allCourseModules.get(i).getInstructorNames(),
                    allCourseModules.get(i).getIsMandatory() ? "Mandatory" : "Optional",
                    allCourseModules.get(i).getAssignmentIds(),
                    allCourseModules.get(i).getStudentNames()
            );
        }

        asciiTable.addRule();
        System.out.println(asciiTable.render());
    }
}