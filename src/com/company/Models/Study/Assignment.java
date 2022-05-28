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

    public Assignment(String assignmentName, int totalPossibleMarks) {
        this.assignmentId = randomUUID().toString();
        this.assignmentName = assignmentName;
        this.totalPossibleMarks = totalPossibleMarks;
    }

    public Assignment(String assignmentId, String assignmentName, int totalPossibleMarks) {
        this.assignmentId = assignmentId == null ? randomUUID().toString() : assignmentId;
        this.assignmentName = assignmentName;
        this.totalPossibleMarks = totalPossibleMarks;
    }

    /**
     * Gets the ID that represents the assignment.
     * @return the ID that represents the assignment.
     */
    public String getAssignmentId() {
        return this.assignmentId;
    }

    /**
     * Gets the name of the assignment.
     * @return the name of the assignment.
     */
    public String getAssignmentName() {
        return this.assignmentName;
    }

    /**
     * Gives the assignment a new name.
     * @param newName the name to change the assignment to
     */
    public void setAssignmentName(String newName) {
        this.assignmentName = newName;
    }

    /**
     * Gets the number of marks that students can achieve on the assignment.
     * @return the maximum number of marks that the student can achieve on the assignment.
     */
    public int getTotalPossibleMarks() {
        return this.totalPossibleMarks;
    }

    /**
     * Changes the number of marks that can be achieved on the assignment.
     * @param marks the number of marks to be achieved.
     */
    public void setTotalPossibleMarks(int marks) {
        this.totalPossibleMarks = marks;
    }
}
