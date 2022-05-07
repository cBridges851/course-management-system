package com.company;


import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Loaders.CourseLoader;
import com.company.Models.Study.Course;
import com.company.Models.Users.CourseAdministrator;
import de.vandermeer.asciitable.AsciiTable;

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
        String userType = scanner.nextLine();

        if (Objects.equals(userType, "1")) {
            System.out.println("Logging in as course administrator");

            CourseAdministrator courseAdministrator = new CourseAdministratorLoader().loadAllCourseAdmistrators().get(0);

            System.out.println("""
                    What would you like to do?\s
                    (1) View all courses""");
            String courseAdministratorAction = scanner.nextLine();

            if (Objects.equals(courseAdministratorAction, "1")) {
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
            }
        } else if (Objects.equals(userType, "2")) {
            System.out.println("Logging in as instructor");
        } else if (Objects.equals(userType, "")) {
            System.out.println("Logging in as student");
        } else {
            System.out.println("User type not recognised");
        }
    }
}
