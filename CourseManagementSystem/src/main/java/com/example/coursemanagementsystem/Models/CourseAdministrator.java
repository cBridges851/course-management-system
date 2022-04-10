package com.example.coursemanagementsystem.Models;

import java.util.ArrayList;

public class CourseAdministrator {
    private ArrayList<Course> courses;

    public CourseAdministrator(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Course> getAllCourses() {
        return this.courses;
    }
}
