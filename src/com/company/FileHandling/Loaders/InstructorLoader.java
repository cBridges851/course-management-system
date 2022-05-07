package com.company.FileHandling.Loaders;

import com.company.FileHandling.FileHandler;
import com.company.FileHandling.Filename;
import com.company.Models.Users.Instructor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InstructorLoader {
    private final FileHandler fileHandler = new FileHandler();

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
}
