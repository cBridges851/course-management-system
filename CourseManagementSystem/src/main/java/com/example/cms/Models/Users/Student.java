package com.example.cms.Models.Users;

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

    public int getYear() {
        return this.year;
    }

    public int getLevel() {
        return this.level;
    }

    public Course getCourse() {
        return this.course;
    }

    public ArrayList<Module> getCompletedModules() {
        return this.completedModules;
    }

    public CourseModule[] getCurrentModules() {
        return this.currentModules;
    }

    public void registerForCourse() throws Exception {
        throw new Exception("Not implemented yet");
    }

    public void enrolForModule() throws Exception {
        throw new Exception("Not implemented yet");
    }
}