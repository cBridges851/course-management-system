package com.company.Menus;

import java.util.Objects;
import java.util.Scanner;

/**
 * The menu that all users will see when opening the application
 */
public class HomeMenu {
    /**
     * Method that allows the user to enter the program.
     */
    public void login() {
        System.out.println("Welcome to the Course Management System!");

        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Who are you?\s
                (1) Course Administrator\s
                (2) Instructor\s
                (3) Student""");
        String action = scanner.nextLine();

        if (Objects.equals(action, "1")) {
            new CourseAdministratorMenu(scanner).runCourseAdministratorMenu();
        } else if (Objects.equals(action, "2")) {
            new InstructorMenu(scanner).runInstructorMenu();
        } else if (Objects.equals(action, "3")) {
            System.out.println("Logging in as student");
        } else {
            System.out.println("User type not recognised");
        }
    }
}
