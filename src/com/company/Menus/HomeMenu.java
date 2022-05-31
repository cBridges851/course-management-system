package com.company.Menus;

import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.FileHandling.Savers.CourseAdministratorSaver;
import com.company.FileHandling.Savers.InstructorSaver;
import com.company.FileHandling.Savers.StudentSaver;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.Instructor;
import com.company.Models.Users.Student;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Scanner;

/**
 * The menu that all users will see when opening the application
 */
public class HomeMenu {
    private final Scanner scanner = new Scanner(System.in);
    /**
     * Method that allows the user to enter the program.
     */
    public void runHomeMenu() {
        System.out.println("Welcome to the Course Management System!");

        System.out.println("""
                What would you like to do?\s
                (1) Create an account
                (2) Login""");
        String action = this.scanner.nextLine();
        if (Objects.equals(action, "1")) {
            this.createAccount();
        } else if (Objects.equals(action, "2")) {
            this.login();
        }
    }

    private void createAccount() {
        boolean isValidUsername = false;
        String username = "";

        while (!isValidUsername) {
            System.out.print("Username: ");
            username = this.scanner.nextLine();
            Student student = new StudentLoader().loadStudent(username);
            Instructor instructor = new InstructorLoader().loadInstructor(username);
            CourseAdministrator courseAdministrator = new CourseAdministratorLoader().loadCourseAdministrator(username);

            if (student == null && instructor == null && courseAdministrator == null) {
                isValidUsername = true;
            } else {
                System.out.println("Username already taken");
            }
        }

        System.out.print("Password: ");
        String password = this.scanner.nextLine();
        System.out.print("First Name: ");
        String firstName = this.scanner.nextLine();
        System.out.print("Middle Name (leave blank if you don't have one): ");
        String middleName = this.scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = this.scanner.nextLine();
        String userType = "";
        boolean isValidUserType = false;

        while(!isValidUserType) {
            System.out.println("""
                    Enter User Type:\s
                    (1) Student\s
                    (2) Instructor\s
                    (3) Course Administrator""");
            userType = this.scanner.nextLine();
            if (Objects.equals(userType, "1")
                    || Objects.equals(userType,"2")
                    || Objects.equals(userType, "3")) {
                isValidUserType = true;
            }
        }

        if (Objects.equals(userType, "1")) {
            int year = -1;
            while (year == -1) {
                System.out.print("Enter your year: ");
                String yearAsString = scanner.nextLine();

                if (StringUtils.isNumeric(yearAsString)) {
                    year = Integer.parseInt(yearAsString);
                }
            }

            int level = 0;

            while (level < 4 || level > 6) {
                System.out.print("Enter your level: ");
                String levelAsString = scanner.nextLine();

                if (StringUtils.isNumeric(levelAsString)) {
                    level = Integer.parseInt(levelAsString);
                }
            }

            this.createStudent(username, password, firstName, middleName, lastName, year, level);
        } else if (Objects.equals(userType, "2")) {
            this.createInstructor(username, password, firstName, middleName, lastName);
        } else {
            this.createCourseAdministrator(username, password, firstName, middleName, lastName);
        }

        this.runHomeMenu();
    }

    private void createStudent(String username, String password, String firstName, String middleName, String lastName, int year, int level) {
        new StudentSaver().saveStudent(new Student(username, password, firstName, middleName, lastName, year, level));
    }

    private void createInstructor(String username, String password, String firstName, String middleName, String lastName) {
        new InstructorSaver().saveInstructor(new Instructor(username, password, firstName, middleName, lastName));
    }

    private void createCourseAdministrator(String username, String password, String firstName, String middleName, String lastName) {
        new CourseAdministratorSaver().saveCourseAdministrator(new CourseAdministrator(username, password, firstName, middleName, lastName));
    }

    private void login() {
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
                this.runHomeMenu();
                return;
            }
        }

        switch (userType) {
            case "1" -> this.studentLogin();
            case "2" -> this.instructorLogin();
            case "3" -> this.courseAdministratorLogin();
        }
    }

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

        new StudentMenu(student, this.scanner).runStudentMenu();
    }

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

        new InstructorMenu(instructor, this.scanner).runInstructorMenu();
    }

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

        new CourseAdministratorMenu(courseAdministrator, this.scanner).runCourseAdministratorMenu();
    }
}
