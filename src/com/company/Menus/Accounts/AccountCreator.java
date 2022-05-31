package com.company.Menus.Accounts;

import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.FileHandling.Savers.CourseAdministratorSaver;
import com.company.FileHandling.Savers.InstructorSaver;
import com.company.FileHandling.Savers.StudentSaver;
import com.company.Menus.HomeMenu;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.Instructor;
import com.company.Models.Users.Student;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Scanner;

public class AccountCreator {
    private final Scanner scanner;

    public AccountCreator(Scanner scanner) {
        this.scanner = scanner;
    }

    public void createAccount() {
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

        new HomeMenu().runHomeMenu();
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
}
