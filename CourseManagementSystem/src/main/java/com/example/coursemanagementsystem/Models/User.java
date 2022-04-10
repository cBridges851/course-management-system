package com.example.coursemanagementsystem.Models;

import java.util.Calendar;

public abstract class User {
    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private Calendar dateOfBirth;

    public User(String username, String password, String firstName, String middleName, String lastName,
                Calendar dateOfBirth) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
