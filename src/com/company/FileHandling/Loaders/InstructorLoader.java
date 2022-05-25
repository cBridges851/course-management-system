package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Users.Instructor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Retrieves and converts instructors from persistent data storage
 */
public class InstructorLoader {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Loads all the instructors
     * @return all the instructors
     */
    public ArrayList<Instructor> loadAllInstructors() {
        String deserialisedInstructorList = this.fileHandler.loadFile(Filename.INSTRUCTORS);
        Gson gson = new Gson();
        Type instructorListType = new TypeToken<ArrayList<Instructor>>(){}.getType();
        ArrayList<Instructor> instructors = gson.fromJson(deserialisedInstructorList, instructorListType);

        if (instructors == null) {
            return new ArrayList<>();
        }

        return instructors;
    }

    /**
     * Loads a specific instructor
     * @param username the username (identifier) of an instructor.
     * @return all the instructor data for that instructor.
     */
    public Instructor loadInstructor(String username) {
        ArrayList<Instructor> allInstructors = this.loadAllInstructors();

        for (Instructor instructor: allInstructors) {
            if (instructor.getUsername().equals(username)) {
                return instructor;
            }
        }

        return null;
    }
}
