package com.company.cms.FileHandling;

import com.company.cms.Models.Study.Assignment;
import com.company.cms.Models.Study.Course;
import com.company.cms.Models.Study.CourseModule;

import java.util.ArrayList;

public class CourseLoader {
    private FileHandler fileHandler = new FileHandler();

    public ArrayList<Course> loadAllCourses() {
        try {
            ArrayList<String> allCoursesFromFileArray = this.fileHandler.loadFile(Filename.COURSES);
            ArrayList<Course> allCourses = new ArrayList<>();

            for (String course: allCoursesFromFileArray) {
                String[] parts = course.split(",");

                String[] moduleCodes = parts[1].split(" ");
                ArrayList<CourseModule> courseModules = new ArrayList<>();

                for (String moduleCode: moduleCodes) {
                    courseModules.add(this.loadCourseModule(moduleCode));
                }

                allCourses.add(new Course(parts[0], courseModules, Boolean.parseBoolean(parts[2])));
            }

            return allCourses;
        } catch (Exception exception) {
            System.out.println("Unable to load all courses: " + exception);
        }

        return null;
    }

    private CourseModule loadCourseModule(String courseModuleCode) {
        try {
            ArrayList<String> allModulesFromFile = this.fileHandler.loadFile(Filename.COURSEMODULES);
            for (String courseModule: allModulesFromFile) {
                String[] parts = courseModule.split(",");

                if (parts[0].equals(courseModuleCode)) {
                    String[] assignmentIds = parts[5].split(" ");
                    ArrayList<Assignment> assignments = new ArrayList<>();

                    for (String assignmentId: assignmentIds) {
                        assignments.add(loadAssignment(assignmentId));
                    }

                    ArrayList<String> students = new ArrayList<>();

                    for (String studentName: parts[6].split(" ")) {
                        students.add(studentName);
                    }

                    return new CourseModule(parts[0], parts[1], Integer.parseInt(parts[2].trim()), parts[3],
                            Boolean.parseBoolean(parts[4].trim()), assignments, students);
                }
            }
        } catch (Exception exception) {
            System.out.println("Unable to load course module: " + exception);
        }

        return null;
    }

    private Assignment loadAssignment(String assignmentId) throws Exception {
        try {
            ArrayList<String> allAssignmentsFromFile = this.fileHandler.loadFile(Filename.ASSIGNMENTS);

            for (String assignment: allAssignmentsFromFile) {
                String[] parts = assignment.split(",");

                if (parts[0].equals(assignmentId)) {
                    return new Assignment(parts[0], parts[1], Integer.parseInt(parts[3].trim()));
                }
            }

        } catch (Exception exception) {
            System.out.println("Unable to load assignment: " + exception);
        }
        return null;
    }
}
