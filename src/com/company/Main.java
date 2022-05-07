package com.company;

import com.company.FileHandling.Loaders.AssignmentLoader;
import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.FileHandling.Loaders.CourseLoader;
import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Savers.AssignmentSaver;
import com.company.FileHandling.Savers.CourseAdministratorSaver;
import com.company.FileHandling.Savers.CourseModuleSaver;
import com.company.Models.Study.Assignment;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
import com.company.Models.Users.CourseAdministrator;
import com.company.Models.Users.User;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the Course Management System!");

        HashSet<String> assignmentIds = new HashSet<String>();
        assignmentIds.add("9c0ae8d1-a2e7-4314-879b-26c25dc74530");

        HashSet<String> studentNames = new HashSet<String>();
        studentNames.add("a.student");

        ArrayList<CourseModule> courseModules = new CourseModuleLoader().loadAllCourseModules();
        CourseModule courseModule = new CourseModule(
                "4CS001",
                "Introductory Programming and Problem Solving",
                4,
                "t.cher",
                true,
                assignmentIds,
                studentNames);

        courseModules.add(courseModule);
        new CourseModuleSaver().saveAllCourseModules(courseModules);
    }
}
