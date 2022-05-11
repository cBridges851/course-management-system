package com.company.Menus;

import com.company.FileHandling.Loaders.CourseLoader;
import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.Models.Study.Course;
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
        this.student = new StudentLoader().loadAllStudents().get(0);
        System.out.println(("My Course: "
                + (!Objects.equals(this.student.getCourseName(), "") ? this.student.getCourseName() : "Not Enrolled")));

        System.out.println("""
                What would you like to do?\s
                (1) Enrol on a course""");
        String action = scanner.nextLine();

        if (Objects.equals(action, "1")) {
            this.registerForCourse();
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
                String courseName = allCourses.get(Integer.parseInt(courseNumber) - 1).getName();
                System.out.print("Are you sure you want to enrol onto "
                        + courseName
                        + "? (Y/N)");
                String confirmation = scanner.nextLine();

                if (Objects.equals(confirmation.toLowerCase(Locale.ROOT), "y")) {
                    student.registerForCourse(courseName);
                }
            } else {
                System.out.println("Course number does not exist");
            }
        } else {
            System.out.println("Invalid input");
        }

        this.runStudentMenu();
    }
}
