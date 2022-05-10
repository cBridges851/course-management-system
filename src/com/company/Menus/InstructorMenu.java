package com.company.Menus;

import com.company.FileHandling.Loaders.CourseModuleLoader;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.Models.Study.CourseModule;
import com.company.Models.Users.Instructor;
import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles the interactions for instructors.
 */
public class InstructorMenu {
    private Instructor instructor;
    private final Scanner scanner;

    public InstructorMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * The initial method that is called when an instructor is logged in,
     * which directs them to what they can do
     */
    public void runInstructorMenu() {
        System.out.println("Logging in as instructor");

        this.instructor = new InstructorLoader().loadAllInstructors().get(0);
        String[] courseModuleCodes = instructor.getCourseModules();
        ArrayList<CourseModule> courseModules = new ArrayList<>();

        for (String courseModuleCode: courseModuleCodes) {
            courseModules.add(new CourseModuleLoader().loadCourseModule(courseModuleCode));
        }

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(null, null, null, null, null, null, "My Course Modules");
        asciiTable.addRule();
        asciiTable.addRow(
                "Number",
                "Course Module Code",
                "Name",
                "Level",
                "Mandatory or Optional",
                "Assignment IDs",
                "Enrolled Students");
        asciiTable.addRule();

        for (int i = 0; i < courseModules.size(); i++) {
            asciiTable.addRow(
                i + 1,
                courseModules.get(i).getCourseModuleCode(),
                courseModules.get(i).getName(),
                courseModules.get(i).getLevel(),
                courseModules.get(i).getIsMandatory() ? "Mandatory" : "Optional",
                courseModules.get(i).getAssignmentIds(),
                courseModules.get(i).getStudentNames()
            );
            asciiTable.addRule();
        }

        System.out.println(asciiTable.render());
    }
}
