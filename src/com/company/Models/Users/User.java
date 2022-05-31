package com.company.Models.Users;

import java.util.Calendar;

/**
 * The class that all types of users inherit from.
 */
public abstract class User {
    private final String username;
    private final String password;
    private final String firstName;
    private final String middleName;
    private final String lastName;

    public User(String username, String password, String firstName, String middleName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    /**
     * @return the username of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return the password the user has.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return the user's first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return the user's middle name.
     */
    public String getMiddleName() {
        return this.middleName;
    }

    /**
     * @return the user's last name.
     */
    public String getLastName() {
        return this.lastName;
    }
}
