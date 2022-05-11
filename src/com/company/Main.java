package com.company;


import com.company.FileHandling.Loaders.CourseLoader;
import com.company.FileHandling.Savers.StudentSaver;
import com.company.Menus.HomeMenu;
import com.company.Models.Users.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        new HomeMenu().login();
    }
}
