package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Users.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Retrieves and converts students from persistent data storage.
 */
public class StudentLoader {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Loads all the students.
     * @return all the students.
     */
    public ArrayList<Student> loadAllStudents() {
        String deserialisedStudentList = this.fileHandler.loadFile(Filename.STUDENTS);
        Gson gson = new Gson();
        Type studentListType = new TypeToken<ArrayList<Student>>(){}.getType();
        ArrayList<Student> students = gson.fromJson(deserialisedStudentList, studentListType);

        if (students == null) {
            return new ArrayList<>();
        }

        return students;
    }

    /**
     * Loads a specific student.
     * @param username the username (identifier) of a student.
     * @return all the student data for that student.
     */
    public Student loadStudent(String username) {
        ArrayList<Student> allStudents = this.loadAllStudents();

        for (Student student: allStudents) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }

        return null;
    }
}
