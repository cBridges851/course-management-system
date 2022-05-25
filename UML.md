//www.plantuml.com/plantuml/png/dLXDRoCf4xxdLt2B8oMtz_0gYgpnUfA9fTWU-UEqcWCn5GzQ6hn0EnkDzhzlOt0Nf6dI6LyIgfxwgAAgWFvYBDMshiJ59PaAQWmOea5Go-MUM4MChTdR1Mb-EySbKrLh0-JN1Ga_IIku9Mkh6u6Mk3kXvehL0imjcMXDtvwuiNVJY34cBPwRoSxoOwFeMIa1L9uu_ruaXb4oCb-T25F5q8cSPsXAm174BU7IedLfhAvtLkb5BXXkvbGosh3VKl_SZnh3zx82QPCu9orvt49TAXk2iv5WlRbnDsHKGdvb3CvOwch2p4PYbQNYksf0pmBcLFzbC169PP_c5PXQeErqMs-J7SbuuAKR2u_K_9pJGuZpcioaXJteSUgMSwVZpINP6j3cz3zzDbRJdGqkEaRasgLRknqdt91Qyhzhm3sl7I-Na4EZwLLfrYsJ5wuD9Z5XLPmn0NcUe7rIZ5fOllp1jVtPf8uAa8ngqatzsbT95DeRK8sPIMkOf7lJxaf4hWu2BB3fe9P4kLfhaFQTLBByy_y_MlZTtL5nDXuJ3Vj6AUW7fJruvFy4_www4Y2r4YSuwXx5YwJiOWMpjcyRDNkmk5xAONeo4sR2AYwvAxL6JwVsCvaRTqEYZ2tWjHn_5enUeyWr6PICZbi-9nCX9aVA1NqMu1bcD4niIkKEX03sIR44dKIwEsaSc1ODwW1o89Y1g_71cYMjik3hSzTAU7LTN10UKAj5TPxZagA1MWZ3SYtuONIU0E6yo3ZXXooM-qODTGXbebCl6sQbZe3e1wsgpxSKcaixgJ1J_lSZso7kT7YVcEluWCHpv_eq5wj6e5EmTq53gDQmCksqS89SClZdAZs47gba0iXtqCODDxc34mDaNP47Baub4GKuMQzdtnRp-yLctTAco-rgVJzVVjq-tR-dudfrlzu-TQGcN-UpnMozMKqsonKoPuk6j9qcjFLc-xMrcso5yzF74Uq5vlXLSmiECse3Ul7_O1dlbBGkNrSbyJEsDbPL3vzG8HHbUGVItNXgSA1DzfxaUKbysJjPC3SH0edfo49wm28XX9ZSn8OT9zwzl7SPUEHcXcj6lKQBQV2GZcuClmml7oUU5i1-SKvNkfFPUttPRmIctYFPj88n5Ntn1ApHY3PLh3p0YhuXBF8CYMcEa1wmI0YUjHVMgzxxOT6TW8by2PJKaK0CgB0gkd2vfiU-tdEix6Wn3UDTfsMRCdvB3UkdiicUhYct7DbbP3uesm5kO8cEUYewgSPUYyDiPQoSzPjYaPUrfoMThNwyLMIp5brpYgOYt0S7YaPaVxCKRSNTqMqXOW8lxOCvoDgKYpEfaFhPIUHSqBNC84VvXqACF5_wHhtNjnZjBd1-FUXEcpVVU-CSU8FyI8PnSn3qBOdIdJI3Xe5FMp756NpvbL9eeyABPR0WPdQmLFxDCrmGj_lpWdkGeDF7IkuEygWgwAjseVPSdeL8VWWLEYK51JsvjuBPgD31m-TjgYaypF1y-cr5HmwlgCRCadx_RQtkWHD2d9Zf-IlFvvNcvcLX1n21wIz-_kdxuhEKjZ13C7FJp8UeNcpsOrTIe_wxuTtDpJdMb9YP4oa0Az2hYvzLDpSgkO3d0NYCP_dEKbOSsVt2ApWeRR-11E_w5v2ihiH_

@startuml
' Classes relating to study
    class Course {
        name: String
        courseModules: ArrayList<CourseModule> 
        isActive: boolean
    }

    class CourseModule {
        moduleCode: String
        name: String 
        level: int
        instructorName: String
        isMandatory: boolean
        assignments: ArrayList<Assignment>
        studentNames: ArrayList<String>
    }

    class Assignment {
        assignmentId: String
        assignmentName: String
        totalPossibleMarks: int
    }

    class ModuleResult {
        courseModule: CourseModule
        assignmentResults: HashMap<String, Integer>
        totalMark: int
    }

    ' Users
    abstract class User {
        'Username is unique 
        username: String
        password: String 
        firstName: String 
        middleName: String 
        lastName: String 
        dateOfBirth: Calendar 
    }

    class Student {
        year: int
        level: int 
        course: Course
        completedCourseModules: ArrayList<CourseModule>
        currentCourseModules: CourseModules[8]
        <<void>> registerForCourse(Course course)
        <<void>> enrolForCourseModule(CourseModule courseModule)
        <<Instructor>> getInstructorOnModule(CourseModule courseModule)
    }

    class CourseAdministrator {
        courses: ArrayList<Course>
        <<void>> addNewCourse(Course course)
        <<void>> addNewCourseModule(Course course, CourseModule courseModule)
        <<ArrayList<Course>>> getAllAvailableCourses()
        <<ArrayList<Course>>> getAllCancelledCourses()
        <<ArrayList<Course>>> getAllCourses()
        <<void>> cancelCourse(Course course)
        <<void>> reopenCourse(Course course)
        <<void>> deleteCourse(Course course)
        <<void>> renameCourse(Course course, String newName)
        <<void>> renameCourseModule(CourseModule courseModule, String newName)
        <<String>> createResultsSlip(Student student)
        <<void>> assignInstructorToCourseModule(CourseModule courseModule, Instructor instructor)
        <<void>> removeInstructorFromModule(CourseModule courseModule)
    }

    class Instructor {
        courseModules: CourseModules[4]
        <<void>> addMark(Student student, Assignment assignment, int mark)
        <<Array<Student>>> getStudentsOnModule(int index)
    }

    ' Handle Persistence
    enum Filename {
        ASSIGNMENTS
        COURSEMODULES
        COURSES
        RESULTS
        COURSEADMINISTRATORS
        INSTRUCTORS
        STUDENTS
    }

    class FileHandler {
        <<void>> writeFile(Filename filename, String contents)
        <<void>> writeFile(String customFilename, String contents)
        <<void>> loadFile(Filename filename)
    }

    'Loaders
    class AssignmentLoader {
        <<ArrayList<Assignment>>> loadAllAssignments()
        <<Assignment>> loadAssignment(String assignmentId)
    }

    class CourseAdministratorLoader{
        <<ArrayList<CourseAdministrator>>> loadAllCourseAdministrators()
    }

    class CourseLoader {
        <<ArrayList<Course>>> loadAllCourses()
        <<ArrayList<Course>>> loadAllAvailableCourses()
        <<Course>> loadCourse(String name)
    }

    class CourseModuleLoader {
        <<ArrayList<CourseModule>>> loadAllCourseModules
        CourseModule loadCourseModule(String courseModuleCode)
    }

    class InstructorLoader {
        <<ArrayList<Instructor>>> loadAllInstructors()
        <<Instructor>> loadInstructor(String username)
    }

    class StudentLoader {
        <<ArrayList<Student>>> loadAllStudents()
        <<Student>> loadStudent(String username)
    }

    'Savers
    class AssignmentSaver {
        <<void>> saveAllAssignments(ArrayList<Assignment> assignments)
    }

    class CourseAdministratorSaver {
        <<void>> saveAllCourseAdministrators(ArrayList<CourseAdministrator> courseAdministrators)
    }

    class CourseModuleSaver {
        <<void>> saveAllCourseModules(ArrayList<CourseModule> courseModules)
    }

    class CourseSaver {
        <<void>> saveAllCourses(ArrayList<Course> courses)
    }

    class InstructorSaver {
        <<void>> saveAllInstructors(ArrayList<Instructor> instructors)
    }

    class ResultsSlipSaver {
        <<void>> saveResultsSlip(String customFilename, String resultsSlip)
    }

    class StudentSaver {
        <<void>> saveAllStudents(ArrayList<Student> students)
    }

    'Menus
    class CourseAdministratorCourseModuleSubMenu {
        <<void>> runCourseModuleSubMenu()
        <<void>> addCourseModuleToCourse()
        <<void>> removeCourseModuleFromCourse
        <<void>> renameCourseModule()
        <<void>> addInstructorToCourseModule()
        <<void>> removeInstructorFromCourseModule()
        <<void>> displayCourseModuleTable
    }

    class CourseAdministratorMenu() {
        <<void>> runCourseAdministratorMenu()
        <<void>> cancelCourse(ArrayList<Course> courses)
        <<void>> reopenCourse(ArrayList<Course> courses)
        <<void>> addCourse(ArrayList<Course> courses)
        <<void>> deleteCourse(ArrayList<Course> courses)
        <<void>> renameCourse(ArrayList<Course> courses)
        <<void>> generateResultsSlip()
    }

    class HomeMenu {
        <<void>> login()
    } 

    class InstructorMenu {
        <<void>> runInstructorMenu()
        <<void>> addMarksToStudent(ArrayList<CourseModule> courseModules)
        <<void>> createAssignment(ArrayList<CourseModule> courseModules)
        <<void>> viewAssignmentsInCourseModule(ArrayList<CourseModule> courseModules)
        <<void>> markStudentAsCompletedModule(ArrayList<CourseModule> courseModules)
    }

    class StudentMenu {
        <<void>> runStudentMenu()
        <<void>> registerForCourse()
        <<void>> enrolOntoCourseModule()
        <<void>> viewCurrentCourseModules()
        <<void>> viewCompletedCourseModules()
    }

    User <-- Student
    User <-- CourseAdministrator
    User <-- Instructor

    FileHandler --o CourseLoader
    FileHandler --o CourseSaver
    FileHandler --o UserLoader
    FileHandler --o UserSaver
    FileHandler --o ReportGenerator
@enduml
