package com.company.cms;

import com.company.cms.FileHandling.FileHandler;
import com.company.cms.FileHandling.Filename;
import com.company.cms.FileHandling.Loaders.CourseLoader;
import com.company.cms.FileHandling.Savers.CourseSaver;
import com.company.cms.Models.Study.Assignment;
import com.company.cms.Models.Study.Course;
import com.company.cms.Models.Study.CourseModule;
import com.company.cms.Models.Users.CourseAdministrator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CMSApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CMSApplication.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Course Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2022, Calendar.APRIL, 15);
//        System.out.println(calendar.getTime());
          Calendar calendar = new GregorianCalendar(2022, 3, 15);
//        System.out.println(calendar.getTime());
//        FileHandler fileHandler = new FileHandler();
//        System.out.println(fileHandler.loadFile(Filename.STUDENTS).get(0));
        //new FileHandler().appendFile(Filename.COURSES, "test");
        Assignment assignment = new Assignment("id", "assignment name", 100);
        ArrayList<Assignment> assignments = new ArrayList<>();
        assignments.add(assignment);
        CourseModule courseModule = new CourseModule("code", "module name", 5, "bob", false, null, null);
        ArrayList<CourseModule> courseModules = new ArrayList<>();
        courseModules.add(courseModule);
        Course course = new Course("Test Course", courseModules, true);
        new CourseAdministrator(null, null, null, null, null, null, new ArrayList<Course>()).addNewCourse(course);
    }
}