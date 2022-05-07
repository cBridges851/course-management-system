package com.company.Models.Study;

import java.util.HashMap;

/**
 * A class that represents the results achieved by a student on a module
 */
public class ModuleResult {
    private CourseModule courseModule;
    private HashMap<String, Integer> assignmentResults;
    private int totalMark;

    public ModuleResult(CourseModule courseModule, HashMap<String, Integer> assignmentResults, int totalMark) {
        this.courseModule = courseModule;
        this.assignmentResults = assignmentResults;
        this.totalMark = totalMark;
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
    public HashMap<String, Integer> getAssignmentResults() {
        return assignmentResults;
    }

    /**
     * @return the number of marks that the student achieved across all assignments on the module.
     */
    public int getTotalMark() {
        return totalMark;
    }

    /**
     * @param assignmentId the identifier of the assignment that was completed
     * @param mark the number of marks achieved on the assignment
     */
    public void addAssignmentResults(String assignmentId, int mark) {
        this.assignmentResults.put(assignmentId, mark);
    }
}
