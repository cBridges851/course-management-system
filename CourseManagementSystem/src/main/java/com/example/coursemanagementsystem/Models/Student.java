package com.example.coursemanagementsystem.Models;

import java.util.ArrayList;
import java.util.Calendar;

public class Student extends User {
    private int year;
    private int level;
    private Course course;
    private ArrayList<Module> completedModules;
    private CourseModule[] currentModules = new CourseModule[8];

    public Student(String username, String password, String firstName, String middleName, String lastName,
                   Calendar dateOfBirth, int year, int level, Course course, ArrayList<Module> completedModules,
                   CourseModule[] currentModules) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
        this.year = year;
        this.level = level;
        this.course = course;
        this.completedModules = completedModules;
        this.currentModules = currentModules;
    }
}
