package com.example.cms.Models.Study;

/**
 * Model that represents the number of marks someone has achieved on an assignment.
 */
public class AssignmentResult {
    private Assignment assignment;
    private int mark;

    public AssignmentResult(Assignment assignment, int mark) {
        this.assignment = assignment;
        this.mark = mark;
    }

    /**
     * @return the assignment that the student completed.
     */
    public Assignment getAssignment() {
        return this.assignment;
    }

    /**
     * @return the mark that was achieved by the student.
     */
    public int getMark() {
        return this.mark;
    }
}
