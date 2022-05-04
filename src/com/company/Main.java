package com.company;

import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Savers.CourseAdministratorSaver;
import com.company.Models.Study.Course;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.User;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the Course Management System!");
//        System.out.println("Who are you?\n" +
//                "1 Course Administrator\n" +
//                "2 Instructor\n" +
//                "3 Student");
//
//        Scanner scanner = new Scanner(System.in);
//        String userType = scanner.nextLine();

        ArrayList<CourseAdministrator> courseAdministrators = new ArrayList<>();
        CourseAdministrator courseAdministrator = new CourseAdministrator(
                "c.administrator",
                "password",
                "Course",
                "Awesome",
                "Administrator",
                new GregorianCalendar(2000, Calendar.APRIL, 15));

//        courseAdministrators = new CourseAdministratorLoader().loadAllCourseAdmistrators();
        courseAdministrators.add(courseAdministrator);
        new CourseAdministratorSaver().saveAllCourseAdminstrators(courseAdministrators);
//        if (Objects.equals(userType, "1")) {
//        }
    }
}
