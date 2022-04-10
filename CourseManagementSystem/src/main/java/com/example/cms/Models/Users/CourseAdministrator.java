package com.example.cms.Models.Users;

import java.util.ArrayList;
import java.util.Calendar;

public class CourseAdministrator extends User {
    private ArrayList<Course> courses;

    public CourseAdministrator(String username, String password, String firstName, String middleName, String lastName, Calendar dateOfBirth,
                               ArrayList<Course> courses) {
        super(username, password, firstName, middleName, lastName,dateOfBirth);
        this.courses = courses;
    }

    public void addNewCourse(Course course) throws Exception {
        throw new Exception("Not implemented yet");
    }

    public void addNewCourseModule(CourseModule courseModule) throws Exception {
        throw new Exception("Not implemented yet");
    }

    public ArrayList<Course> getAllAvailableCourses() throws Exception {
        throw new Exception("Not implemented yet");
    }

    public ArrayList<Course> getAllCourses() {
        return this.courses;
    }

    public void cancelCourse(Course course) throws Exception {
        throw new Exception("Not implemented yet");
    }

    public void reopenCourse(Course course) throws Exception {
        throw new Exception("Not implemented yet");
    }

    public void deleteCourse(Course course) throws Exception {
        throw new Exception("Not implemented yet");
    }

    public void renameCourse(Course course, String newName) throws Exception {
        throw new Exception("Not implemented yet");
    }

    public void renameCourseModule(CourseModule courseModule, String newName) throws Exception {
        throw new Exception("Not implemented yet");
    }

    public String createResultsSlip(Student student) throws Exception {
        throw new Exception("Not implemented yet");
    }
}
