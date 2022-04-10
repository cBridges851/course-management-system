package com.example.cms.Models.Users;

import java.util.ArrayList;
import java.util.Calendar;

public class Instructor extends User {
    private CourseModules[] courseModules = new CourseModules[4];

    public Instructor(String username, String password, String firstName, String middleName, String lastName,
                      Calendar dateOfBirth, CourseModules[] modules) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
        this.courseModules = modules;
    }

    public CourseModules[] getCourseModules() {
        return courseModules;
    }

    public void addMark(Student student, Assignment assignment, int mark) throws Exception {
        throw new Exception("Not implemented yet");
    }

    public ArrayList<Student> getStudentsOnModule(int index) throws Exception {
        throw new Exception("Not implemented yet");
    }
}