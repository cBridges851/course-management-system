package com.example.cms.Models.Users;

import java.util.Calendar;

public class Instructor extends User {
    private Modules[] modules;

    public Instructor(String username, String password, String firstName, String middleName, String lastName,
                      Calendar dateOfBirth, Modules[] modules) {
        super(username, password, firstName, middleName, lastName, dateOfBirth);
        this.modules = modules;
    }

    public Modules[] getModules() {
        return modules;
    }
}
