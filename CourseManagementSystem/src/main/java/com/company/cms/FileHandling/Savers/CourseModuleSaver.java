package com.company.cms.FileHandling.Savers;

import com.company.cms.FileHandling.FileHandler;
import com.company.cms.FileHandling.Filename;
import com.company.cms.Models.Study.CourseModule;

import java.util.ArrayList;

/**
 * A class that handles the saving of the modules that are on the courses.
 */
public class CourseModuleSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Saves all the course modules there are in the system.
     * @param courseModules The list of course modules to be saved.
     */
    public void saveAllCourseModules(ArrayList<CourseModule> courseModules) {
        fileHandler.clearFile(Filename.COURSEMODULES);

        for (CourseModule courseModule: courseModules) {
            StringBuilder line = new StringBuilder(courseModule.getCourseModuleCode().trim() + ", " +
                    courseModule.getName().trim() + ", " +
                    courseModule.getLevel() + ", " +
                    courseModule.getInstructorName().trim() + ", " +
                    courseModule.getIsMandatory() + ", ");

            for (int i = 0; i < courseModule.getAssignments().size(); i++) {
                if (courseModule.getAssignments().get(i) != null && i != courseModule.getAssignments().size() - 1) {
                    line.append(courseModule.getAssignments().get(i).getAssignmentId().trim()).append("  ");
                } else if (i == courseModule.getAssignments().size() - 1) {
                    line.append(courseModule.getAssignments().get(i).getAssignmentId().trim());
                }
            }

            line.append(", ");

            for (String studentName: courseModule.getStudentNames()) {
                if (studentName != null) {
                    line.append(studentName.trim()).append("  ");
                }
            }
            line.append("\n");
            fileHandler.appendFile(Filename.COURSEMODULES, line.toString());
        }
    }
}
