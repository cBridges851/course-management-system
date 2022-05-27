package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.FileHandling.Loaders.InstructorLoader;
import com.company.Models.Users.Instructor;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that handles the saving of instructors.
 */
public class InstructorSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Saves all the instructors in the system.
     * @param instructors all the instructors to save.
     */
    public void saveAllInstructors(ArrayList<Instructor> instructors) {
        Gson gson = new Gson();
        String serialisedInstructors = gson.toJson(instructors);
        fileHandler.writeFile(Filename.INSTRUCTORS, serialisedInstructors);
        System.out.println("Instructors Updated!");
    }

    /**
     * Saves a specific instructor to the list of instructors.
     * @param instructor the instructor to save.
     */
    public void saveInstructor(Instructor instructor) {
        ArrayList<Instructor> instructors = new InstructorLoader().loadAllInstructors();
        boolean alreadyExists = false;

        for (int i = 0; i < instructors.size(); i++) {
            if (Objects.equals(instructors.get(i).getUsername(), instructor.getUsername())) {
                instructors.set(i, instructor);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) {
            instructors.add(instructor);
        }

        this.saveAllInstructors(instructors);
    }
}
