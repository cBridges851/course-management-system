package com.company.Models.Users;

import com.company.FileHandling.Loaders.AssignmentLoader;
import com.company.FileHandling.Loaders.CourseLoader;
import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.FileHandling.Savers.CourseModuleSaver;
import com.company.FileHandling.Savers.CourseSaver;
import com.company.FileHandling.Savers.InstructorSaver;
import com.company.FileHandling.Savers.StudentSaver;
import com.company.Models.Study.Assignment;
import com.company.Models.Study.Course;
import com.company.Models.Study.CourseModule;
import com.company.Models.Study.CourseModuleResult;
import de.vandermeer.asciitable.AsciiTable;

import java.util.*;

/**
 * Model that represents the course administrator, which is a type of user who manages the courses.
 */
public class CourseAdministrator extends User {

    public CourseAdministrator(String username, String password, String firstName, String middleName, String lastName, Calendar dateOfBirth) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
    }

    /**
     * Adds a new course to the list of courses.
     * @param courseName The name of the new course.
     */
    public void addNewCourse(String courseName) {
        Course newCourse = new Course(courseName, new HashSet<>(), true);
        new CourseSaver().saveCourse(newCourse);
    }

    /**
     * Adds a course module to the course, and the persistent data is updated.
     * @param course the course to add the new module to
     * @param courseModuleCode the identifier of the new module
     * @param name the name of the new module
     * @param level the level of the new module
     * @param instructorNames the name of the instructor teaching the module
     * @param isMandatory indicator of whether the module is optional
     * @param assignmentIds the assignments that have to be completed in the module
     * @param studentNames the students enrolled in the module
     */
    public void addNewCourseModuleToCourse(Course course, String courseModuleCode, String name, int level,
                                   HashSet<String> instructorNames, boolean isMandatory, HashSet<String> assignmentIds,
                                   HashSet<String> studentNames) {
        CourseModule courseModule = new CourseModule(
                courseModuleCode,
                name,
                level,
                instructorNames,
                isMandatory,
                assignmentIds,
                studentNames
        );
        course.addCourseModule(courseModule.getCourseModuleCode());
        new CourseSaver().saveCourse(course);
        new CourseModuleSaver().saveCourseModule(courseModule);
        ArrayList<Instructor> allInstructors = new InstructorLoader().loadAllInstructors();
        ArrayList<Instructor> instructorsToUpdate = new ArrayList<>();
        ArrayList<String> instructorNamesAsArray = new ArrayList<>(instructorNames);

        for (String relevantInstructor: instructorNamesAsArray) {
            for (Instructor instructor: allInstructors) {
                if (instructor.getUsername().equals(relevantInstructor)) {
                    instructorsToUpdate.add(instructor);
                }
            }
        }

        for (Instructor instructorToUpdate: instructorsToUpdate) {
            assignInstructorToCourseModule(courseModule, instructorToUpdate);
        }
    }

    /**
     * A method that retrieves all the courses that are in this class and its file.
     * @return all the courses, no matter whether they are available or cancelled.
     */
    public ArrayList<Course> getAllCourses() {
        return new CourseLoader().loadAllCourses();
    }

    public ArrayList<CourseModule> getAllCourseModules() {
        return new CourseModuleLoader().loadAllCourseModules();
    }

    /**
     * Makes a course unavailable, but not permanently removed
     * @param course the course to be cancelled.
     */
    public void cancelCourse(Course course) {
        course.setIsAvailable(false);
        new CourseSaver().saveCourse(course);
    }

    /**
     * Make a course available
     * @param course the course to reopen.
     */
    public void reopenCourse(Course course) {
        course.setIsAvailable(true);
        new CourseSaver().saveCourse(course);
    }

    /**
     * Permanently removes a course from the list of courses in this class and its file.
     * @param courseToDelete the course to be deleted.
     */
    public void deleteCourse(Course courseToDelete) {
        new CourseSaver().deleteCourseAndSave(courseToDelete);
        HashSet<String> courseModuleCodes = courseToDelete.getCourseModuleCodes();

        for (String courseModuleCode: courseModuleCodes) {
            CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleCode);
            this.removeCourseModuleFromSystem(new CourseLoader().loadAllCourses(), courseModule);
        }
    }

    /**
     * Allows the course to have its name changed
     * @param course the course that needs to be renamed.
     * @param newName the name that the course will be changed to.
     */
    public void renameCourse(Course course, String newName) {
        course.setName(newName);
        new CourseSaver().saveCourse(course);
    }

    /**
     * Allows a course module inside a course to be renamed.
     * @param courseModule the course module that needs to be renamed.
     * @param newName the name that the course module will be changed to.
     */
    public void renameCourseModule(CourseModule courseModule, String newName) {
        courseModule.setName(newName);
        new CourseModuleSaver().saveCourseModule(courseModule);
    }

    /**
     * Generate a results slip for a specific student
     * @param student the student that the results slip will be about, which indicates the grades on each module.
     * @return a string that represents the results slip
     */
    public String createResultsSlip(Student student) {
        StringBuilder resultsSlip = new StringBuilder();
        resultsSlip
                .append("Name: ")
                .append(student.getFirstName())
                .append(" ");
        if (student.getMiddleName() != null) {
            resultsSlip
                .append(student.getMiddleName())
                .append(" ");
        }

        Course course = new CourseLoader().loadCourse(student.getCourseId());

        resultsSlip.append(student.getLastName())
                .append("     ")
                .append("Course: ")
                .append(course.getName())
                .append("     ")
                .append("Level: ")
                .append(student.getLevel())
                .append("     ")
                .append("Year: ")
                .append(student.getYear())
                .append("\n\n");

        AsciiTable currentLevelTable = new AsciiTable();
        currentLevelTable.addRule();
        currentLevelTable.addRow(null, "Results for Level " + student.getLevel());
        currentLevelTable.addRule();
        ArrayList<CourseModuleResult> courseModulesForLevel = new ArrayList<>();
        CourseModuleResult[] currentCourseModuleResults = student.getCurrentCourseModules();
        ArrayList<CourseModuleResult> currentCourseModuleResultsAsArray = new ArrayList<>(Arrays.asList(currentCourseModuleResults));
        currentCourseModuleResultsAsArray.removeAll(Collections.singleton(null));

        for (CourseModuleResult currentCourseModuleResult: currentCourseModuleResultsAsArray) {
            CourseModule currentCourseModule =
                    new CourseModuleLoader().loadCourseModule(currentCourseModuleResult.getCourseModuleCode());
            if (currentCourseModule.getLevel() == student.getLevel()) {
                courseModulesForLevel.add(currentCourseModuleResult);
            }
        }

        ArrayList<CourseModuleResult> completedCourseModuleResults = student.getCompletedCourseModules();

        for (CourseModuleResult completedCourseModuleResult: completedCourseModuleResults) {
            CourseModule completedCourseModule =
                    new CourseModuleLoader().loadCourseModule(completedCourseModuleResult.getCourseModuleCode());

            if (completedCourseModule.getLevel() == student.getLevel()) {
                courseModulesForLevel.add(completedCourseModuleResult);
            }
        }

        if (courseModulesForLevel.size() != 0) {
            for (CourseModuleResult courseModuleResult: courseModulesForLevel) {
                CourseModule courseModule = new CourseModuleLoader().loadCourseModule(courseModuleResult.getCourseModuleCode());
                currentLevelTable.addRow(null, courseModule.getName() + " (" + courseModuleResult.getCourseModuleCode() + ")");
                currentLevelTable.addRule();

                for (String assignmentId: courseModuleResult.getAssignmentResults().keySet()) {
                    Assignment assignment = new AssignmentLoader().loadAssignment(assignmentId);

                    currentLevelTable.addRow(
                            assignment.getAssignmentName(),
                            courseModuleResult.getAssignmentResults().get(assignmentId) + "/" + assignment.getTotalPossibleMarks()
                    );
                    currentLevelTable.addRule();
                }

                currentLevelTable.addRow(
                        "",
                        "Total: "
                                + courseModuleResult.getTotalMark()
                                + "/"
                                + courseModule.getTotalAvailableMarks()
                );
                currentLevelTable.addRule();

                String passOrFail =
                        ((double) courseModuleResult.getTotalMark() / (double) courseModule.getTotalAvailableMarks() * 100) > 40 ? "Pass" : "Fail";
                String status = completedCourseModuleResults.contains(courseModuleResult) ? passOrFail : "In Progress";
                currentLevelTable.addRow(
                        "",
                        "Pass/Fail/In Progress: "
                                + status
                );
                currentLevelTable.addRule();
            }

            resultsSlip.append(currentLevelTable.render());
        } else {
            resultsSlip.append("No courses in progress or completed");
        }

        boolean canProgressToNextLevel = student.canProgressToNextLevel();
        resultsSlip.append("\nCan progress to next level? ")
                .append(canProgressToNextLevel ? "Yes" : "No");
        return resultsSlip.toString();
    }

    /**
     * Assigns an instructor to a course module
     * @param courseModule the course module that will have the instructor assigned to it
     * @param instructor the instructor that will be added to the course module
     */
    public void assignInstructorToCourseModule(CourseModule courseModule,
                                               Instructor instructor) {
        boolean canBeAssigned = instructor.addCourseModule(courseModule.getCourseModuleCode());

        if (canBeAssigned) {
            courseModule.addInstructorName(instructor.getUsername());

            new InstructorSaver().saveInstructor(instructor);
            new CourseModuleSaver().saveCourseModule(courseModule);
        }
    }

    /**
     * Removes an instructor from a course module.
     * @param courseModule the course module that will have the instructor removed from it.
     */
    public void removeInstructorFromCourseModule(CourseModule courseModule,
                                                 Instructor instructor) {
        courseModule.removeInstructorName(instructor.getUsername());
        instructor.removeCourseModule(courseModule.getCourseModuleCode());

        new InstructorSaver().saveInstructor(instructor);
        new CourseModuleSaver().saveCourseModule(courseModule);
    }

    /**
     * Removes course modules from courses
     * @param course the course to remove the module from
     * @param courseModule the course module that needs to be removed
     */
    public void removeCourseModuleFromCourse(ArrayList<Course> courses, Course course, CourseModule courseModule) {
        course.removeCourseModule(courseModule.getCourseModuleCode());
        new CourseSaver().saveCourse(course);
        this.removeCourseModuleFromSystem(courses, courseModule);
    }

    /**
     * Removes a course module permanently from the system
     * @param courses the courses to check through to see if the course module is in another course
     * @param courseModule the course module to remove
     */
    private void removeCourseModuleFromSystem(ArrayList<Course> courses, CourseModule courseModule) {
        boolean isInAnotherCourse = false;

        for (Course courseItem : courses) {
            if (courseItem.getCourseModuleCodes().contains(courseModule.getCourseModuleCode())) {
                isInAnotherCourse = true;
                break;
            }
        }

        if (!isInAnotherCourse) {
            new CourseModuleSaver().removeCourseModuleAndSave(courseModule);
            ArrayList<Instructor> allInstructors = new InstructorLoader().loadAllInstructors();

            for (Instructor instructor: allInstructors) {
                for (String courseModuleCode : instructor.getCourseModules()) {
                    if (Objects.equals(courseModuleCode, courseModule.getCourseModuleCode())) {
                        instructor.removeCourseModule(courseModuleCode);
                        new InstructorSaver().saveInstructor(instructor);
                    }
                }
            }
        }
    }

    /**
     * Promotes a student to their next level.
     * @param student the student to promote.
     */
    public void promoteStudent(Student student) {
        int newLevel = student.getLevel();
        newLevel++;
        student.setLevel(newLevel);
        new StudentSaver().saveStudent(student);
    }
}
