package com.company;

import com.company.Models.Study.Course;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the Course Management System!");
        System.out.println("Who are you?\n" +
                "1 Course Administrator\n" +
                "2 Instructor\n" +
                "3 Student");

        Scanner scanner = new Scanner(System.in);
        String userType = scanner.nextLine();

        User user;
        if (Objects.equals(userType, "1")) {
            user = new CourseAdministrator(
                    "c.administrator",
                    "password",
                    "Course",
                    "",
                    "Administrator",
                    new GregorianCalendar(2000, 3, 15));
        }
    }
}
