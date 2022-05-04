package com.company.Models.Study;

import static java.util.UUID.randomUUID;

/**
 * Model that represents the assignments, which students complete to demonstrate their understanding and application of
 * the skills they have learnt in the module.
 */
public class Assignment {
    private final String assignmentId;
    private String assignmentName;
    private int totalPossibleMarks;

    public Assignment(String assignmentId, String assignmentName, int totalPossibleMarks) {
        this.assignmentId = assignmentId == null ? randomUUID().toString() : assignmentId;
        this.assignmentName = assignmentName;
        this.totalPossibleMarks = totalPossibleMarks;
    }

    /**
     * @return the ID that represents the assignment.
     */
    public String getAssignmentId() {
        return this.assignmentId;
    }

    /**
     * @return the name of the assignment.
     */
    public String getAssignmentName() {
        return this.assignmentName;
    }

    /**
     * @return the maximum number of marks that the student can achieve on the assignment.
     */
    public int getTotalPossibleMarks() {
        return this.totalPossibleMarks;
    }
}