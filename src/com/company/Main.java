package com.company;

import com.company.FileHandling.Loaders.AssignmentLoader;
import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Loaders.CourseLoader;
import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Savers.AssignmentSaver;
import com.company.FileHandling.Savers.CourseAdministratorSaver;
import com.company.FileHandling.Savers.CourseModuleSaver;
import com.company.FileHandling.Savers.CourseSaver;
import com.company.Models.Study.Assignment;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.User;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the Course Management System!");

        HashSet<String> courseModules = new HashSet<>();
        courseModules.add("4CI018");
        courseModules.add("4CS001");

        ArrayList<Course> courses = new CourseLoader().loadAllCourses();
        Course course = new Course("BSc Computer Science", courseModules, true);
        courses.add(course);

        new CourseSaver().saveAllCourses(courses);
    }
}
