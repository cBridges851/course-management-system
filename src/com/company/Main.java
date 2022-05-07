package com.company;


import com.company.FileHandling.Loaders.CourseAdministratorLoader;
import com.company.Menus.HomeMenu;
import com.company.Models.Study.Course;
import com.company.Models.Users.CourseAdministrator;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        new HomeMenu().login();
    }
}
