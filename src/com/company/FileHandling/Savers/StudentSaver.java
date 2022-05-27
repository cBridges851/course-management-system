package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.FileHandling.Loaders.StudentLoader;
import com.company.Models.Users.Student;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that handles the saving of instructors.
 */
public class StudentSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Saves all the instructors in the system.
     * @param students all the instructors to save.
     */
    public void saveAllStudents(ArrayList<Student> students) {
        Gson gson = new Gson();
        String serialisedInstructors = gson.toJson(students);
        fileHandler.writeFile(Filename.STUDENTS, serialisedInstructors);
        System.out.println("Students Updated!");
    }

    /**
     * Saves a specific student to the list of students.
     * @param student the student to save.
     */
    public void saveStudent(Student student) {
        ArrayList<Student> students = new StudentLoader().loadAllStudents();
        boolean alreadyExists = false;

        for (int i = 0; i < students.size(); i++) {
            if (Objects.equals(students.get(i).getUsername(), student.getUsername())) {
                students.set(i, student);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) {
            students.add(student);
        }

        this.saveAllStudents(students);
    }
}
