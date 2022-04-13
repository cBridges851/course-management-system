package com.example.cms.Models.Study;

import com.example.cms.Models.Users.Student;

import java.util.ArrayList;

public class StudentResult {
    private Student student;
    private CourseModule courseModule;
    private ArrayList<AssignmentResult> assignmentResults;
    private int totalMark;

    public StudentResult(Student student, CourseModule courseModule, ArrayList<AssignmentResult> assignmentResults, int totalMark) {
        this.student = student;
        this.courseModule = courseModule;
        this.assignmentResults = assignmentResults;
        this.totalMark = totalMark;
    }

    /**
     * @return the student who achieved the results.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @return the course module that the results were achieved on.
     */
    public CourseModule getCourseModule() {
        return courseModule;
    }

    /**
     * @return all the assignment results on a module.
     */
    public ArrayList<AssignmentResult> getAssignmentResults() {
        return assignmentResults;
    }

    /**
     * @return the number of marks that the student achieved across all assignments on the module.
     */
    public int getTotalMark() {
        return totalMark;
    }
}
