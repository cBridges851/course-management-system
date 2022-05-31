package com.company.Menus;

import com.company.Menus.Accounts.AccountCreator;
import com.company.Menus.Accounts.LoginHandler;

import java.util.Objects;
import java.util.Scanner;

/**
 * The menu that all users will see when opening the application
 */
public class HomeMenu implements IMenu {
    private final Scanner scanner = new Scanner(System.in);
    /**
     * Method that allows the user to enter the program.
     */
    public void run() {
        System.out.println("Welcome to the Course Management System!");

        System.out.println("""
                What would you like to do?\s
                (1) Create an account
                (2) Login""");
        String action = this.scanner.nextLine();
        if (Objects.equals(action, "1")) {
            new AccountCreator(this.scanner).createAccount();
        } else if (Objects.equals(action, "2")) {
            new LoginHandler(this.scanner).login();
        }
    }
}
