package com.company.Menus.Accounts;

import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.Menus.CourseAdministratorMenu;
import com.company.Menus.HomeMenu;
import com.company.Menus.InstructorMenu;
import com.company.Menus.StudentMenu;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.Instructor;
import com.company.Models.Users.Student;

import java.util.Objects;
import java.util.Scanner;

/**
 * A class that is responsible for allowing users to login.
 */
public class LoginHandler {
    private final Scanner scanner;

    public LoginHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * The initial login menu where the user selects their user type.
     */
    public void login() {
        String userType = "";
        boolean isValidUserType = false;

        while(!isValidUserType) {
            System.out.println("""
                    Enter User Type:\s
                    (1) Student\s
                    (2) Instructor\s
                    (3) Course Administrator\s
                    (4) Cancel""");
            userType = this.scanner.nextLine();
            if (Objects.equals(userType, "1")
                    || Objects.equals(userType,"2")
                    || Objects.equals(userType, "3")) {
                isValidUserType = true;
            } else if (Objects.equals(userType, "4")) {
                new HomeMenu().run();
                return;
            }
        }

        switch (userType) {
            case "1" -> this.studentLogin();
            case "2" -> this.instructorLogin();
            case "3" -> this.courseAdministratorLogin();
        }
    }

    /**
     * Allows students to login
     */
    private void studentLogin() {
        System.out.println("Student Login");
        System.out.print("Username: ");
        String username = this.scanner.nextLine();
        System.out.print("Password: ");
        String password = this.scanner.nextLine();

        Student student = new StudentLoader().loadStudent(username);

        if (student == null) {
            System.out.println("Username or password is incorrect");
            this.studentLogin();
            return;
        }

        if (!Objects.equals(student.getPassword(), password)) {
            System.out.println("Username or password is incorrect");
            this.studentLogin();
            return;
        }

        new StudentMenu(student, this.scanner).run();
    }

    /**
     * Allows instructors to login
     */
    private void instructorLogin() {
        System.out.println("Instructor Login");
        System.out.print("Username: ");
        String username = this.scanner.nextLine();
        System.out.print("Password: ");
        String password = this.scanner.nextLine();

        Instructor instructor = new InstructorLoader().loadInstructor(username);

        if (instructor == null) {
            System.out.println("Username or password is incorrect");
            this.instructorLogin();
            return;
        }

        if (!Objects.equals(instructor.getPassword(), password)) {
            System.out.println("Username or password is incorrect");
            this.instructorLogin();
            return;
        }

        new InstructorMenu(instructor, this.scanner).run();
    }

    /**
     * Allows course administrators to login.
     */
    private void courseAdministratorLogin() {
        System.out.println("Course Administrator Login");
        System.out.print("Username: ");
        String username = this.scanner.nextLine();
        System.out.print("Password: ");
        String password = this.scanner.nextLine();

        CourseAdministrator courseAdministrator = new CourseAdministratorLoader().loadCourseAdministrator(username);

        if (courseAdministrator == null) {
            System.out.println("Username or password is incorrect");
            this.courseAdministratorLogin();
            return;
        }

        if (!Objects.equals(courseAdministrator.getPassword(), password)) {
            System.out.println("Username or password is incorrect");
            this.courseAdministratorLogin();
            return;
        }

        new CourseAdministratorMenu(courseAdministrator, this.scanner).run();
    }
}
