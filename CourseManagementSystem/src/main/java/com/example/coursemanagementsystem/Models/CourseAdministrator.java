package com.example.coursemanagementsystem.Models;

import java.util.ArrayList;
import java.util.Calendar;

public class CourseAdministrator extends User {
    private ArrayList<Course> courses;

    public CourseAdministrator(String username, String password, String firstName, String middleName, String lastName, Calendar dateOfBirth,
                               ArrayList<Course> courses) {
        super(username, password, firstName, middleName, lastName,dateOfBirth);
        this.courses = courses;
    }

    public ArrayList<Course> getAllCourses() {
        return this.courses;
    }
}
