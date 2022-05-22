package com.company.Models.Study;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * A class that represents the results achieved by a student on a module
 */
public class CourseModuleResult {
    private final String courseModuleCode;
    private final LinkedHashMap<String, Integer> assignmentResults;

    public CourseModuleResult(String courseModule, LinkedHashMap<String, Integer> assignmentResults) {
        this.courseModuleCode = courseModule;
        this.assignmentResults = assignmentResults;
    }

    /**
     * @return the course module that the results were achieved on.
     */
    public String getCourseModuleCode() {
        return courseModuleCode;
    }

    /**
     * @return all the assignment results on a module.
     */
    public LinkedHashMap<String, Integer> getAssignmentResults() {
        return assignmentResults;
    }

    /**
     * Gets all the marks the student as achieved in the course module.
     * @return the number of marks that the student achieved across all assignments on the course module.
     */
    public int getTotalMark() {
        Set<String> assignmentIds = this.assignmentResults.keySet();
        int total = 0;

        for (String assignmentId: assignmentIds) {
            total += this.assignmentResults.get(assignmentId);
        }

        return total;
    }

    /**
     * Adds the results from an assignment for a course module
     * @param assignmentId the identifier of the assignment that was completed
     * @param mark the number of marks achieved on the assignment
     */
    public void addAssignmentResults(String assignmentId, int mark) {
        this.assignmentResults.put(assignmentId, mark);
    }
}
