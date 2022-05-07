package com.company;


import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Loaders.CourseLoader;
import com.company.Models.Study.Course;
import com.company.Models.Users.CourseAdministrator;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the Course Management System!");

        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Who are you?\s
                (1) Course Administrator\s
                (2) Instructor\s
                (3) Student""");
        String action = scanner.nextLine();

        if (Objects.equals(action, "1")) {
            System.out.println("Logging in as course administrator");

            CourseAdministrator courseAdministrator = new CourseAdministratorLoader().loadAllCourseAdmistrators().get(0);

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
                    (2) Reopen a course""");
            action = scanner.nextLine();

            if (Objects.equals(action, "1")) {
                System.out.print("Enter the number of the course to cancel: ");
                action = scanner.nextLine();

                if (StringUtils.isNumeric(action)) {
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
                    System.out.println("Invalid input");
                }
            } else if(Objects.equals(action, "2")) {
                System.out.print("Enter the number of the course to reopen: ");
                action = scanner.nextLine();

                if (StringUtils.isNumeric(action)) {
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
                    System.out.println("Invalid input");
                }
            }
        } else if (Objects.equals(action, "2")) {
            System.out.println("Logging in as instructor");
        } else if (Objects.equals(action, "")) {
            System.out.println("Logging in as student");
        } else {
            System.out.println("User type not recognised");
        }
    }
}
