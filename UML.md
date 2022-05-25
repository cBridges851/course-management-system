//www.plantuml.com/plantuml/png/hHbFR-AuzzvFuLj1elUVgWeDFzfEaGgj29n6Ut39AnEDOxEsGxSQxNVVk0wnJLwSXDsSoFl_Ftxccz9KwY9dLzTapgXIe8W4HdN6zqGBedIHVbwHyjaPB9cBGYeWlqkGVJZDuPOaMfOCDN3tHRKKQS50tPAPbFJpELFwRkuXfgIcpzHifxDZAUXD20QKVs7-ll8LEqvVVVu5c8lK6O5PvbGnE0Ax9HdNJZjNMXOxBUGASoPJIyfJMg8_VVliGvNAzZm7hWC_PpLuMfEQK9O0eoEWjUgcJPUT4F8RKRX8dQaD9Eg95fgoLr4IlJ5OKlbBkK04ccsOrw0APdHZQRqDCe9OOBbB3KzK_LpIG-NdX2ouXZt8QMYMCQTXpJNPAf3gwpTzKrhIdQvCD0ZFj6lpRZ9ECaKAdlrPWCjvON1OG0vKgGyXqsQPl6TIUK7qKNcMfWnm7ADjN2dLyFB-_qpgdsNeA0EUKacQeKzibNYkVGALBZ9X3PEptApmRimF33IaywwsT5cVEkv2Ik3wZBV9yUD_VzGyTtTHsTCfeMawnymQZJqXHv6b9Qs4VIa8vAEGbdHa7VHyHhY0Iy4iYvCUC2xhsO4gpSKHvatdHqs70dc-h6fQbE9sbBzAiPUWr4Qiu2_zR79elCK7tIpDCvwPYjV2rRcppmHm1HzLFCwZFQqiKXE2XmgNvPpOY6x95b2zJmWUMeSm3JWnDMlBTt95kXypEXAeHsGugOqDLO3z6JrfSFaZKrsarn_TuqYLcyhUWvunPjyLKiwc57R0MkDB3JCMOhH8nG7uVo8g1TEB0xE-4VU63JVD93mYpvL9Qorn-70LYqjtRObF74IJU_LnKQqMOt8dWUggwrN2iiFeDA-h4a5ijUMtg4jt8xhjTidhPxeJulJuxL9oEuG7RnryZlfHYdoegLTaq1FnRAYaxa0CbJWiRhud9d836ogIv9wfkh2sqAXSdZT4uENfC-OkBizWTnxzxwzFXvzyrUc0zSC_y9n0zWiGH7Sqdg1d0fgc_-vmSXcpdM5kkHXrVRd3hS9-mtCgV-4d2NEGFv-AqTdbBF4qHRKOSALXfkfZv02but39UA8yBIbUGIfpqEGx-480BtBoc37phV5tpbcIBBwlbW-hJLB3vY_RTVAmVBdVFZ-SGztx-Y7PFZUuPlVBnMgHRDQppSlQ8HUh4hIT1x1aixqtUfjbQ-oqVg37nG-PQJ0qezgXT_l3FxfnRHRPSOpzH5ieBVB70IAOe2bkG9YDvv8Ef4BtO8iB_4DtxahTZ3473CzY3bxHDll1FpRsEwTRyt3h47BFJ0IhsfU3Q1WiIKEsSx-3V7Qa6QDl330-2wk8msNCpoH-RzDXjoFqpV71gcRqeOQoro2D3lkeROxCiym1mnWvU4Nh09LbzON46D__kogzel5igI218JMPfQhUeYPS9_JOrdi6XOuMLIBEEWrjIl-kh5y_TQb4koRUScI7y7IKRRSPZbRrsaDQDVRKfTeFbD4YxvBkbtJNIHhJv2r6KLKUNSS7HJhAzcQ9wlAxezb2H5Mui0-Mm0iLBywWGeetmu6P80kEK8xminDw1JDmr-YrBEFgMtVKuRi7ofDcwi3eP-2b-TBq6WOsR94Ge_I3hrzS6-6NBLsCU6Ixk2w-aBdqvWLZt0C7UNQzWJJTayYXhTgPs6Vyn4Jm8HJfb91erBvWg8seEhZllErUWouLTCpWmubH2pxyMghtZZDSA3Ol8ndmAFfTnRVTlBzm7PaXBdB83RjgfKN_o0XgrFulT7TpS_8r129p8YHm5MZ5-MlLpOq83k0uWVkCetYZ2MLtw7RcDHo4rD-1WpNz6_2qoDa_

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
        courseName: String
        completedCourseModules: ArrayList<CourseModuleResult>
        currentCourseModules: CourseModuleResult[8]
        <<ArrayList<CourseModuleResult>>> addCompletedCourseModule()
        <<void>> registerForCourse(String courseName)
        <<void>> enrolForCourseModule(String courseModuleCode)
        <<void>> removeCurrentCourseModule(CourseModuleResult courseModule)
        <<boolean>> canProgressToNextLevel()
    }

    class CourseAdministrator {
        <<void>> addNewCourse(ArrayList<Course> courses, String courseName)
        <<void>> addNewCourseModuleToCourse(ArrayList<Course> courses, Course course, String courseModuleCode, String name, int level,
                                   HashSet<String> instructorNames, boolean isMandatory, HashSet<String> assignmentIds,
                                   HashSet<String> studentNames)
        <<ArrayList<Course>>> getAllCourses()
        <<void>> cancelCourse(ArrayList<Course> allCourses, Course course)
        <<void>> reopenCourse(ArrayList<Course> allCourses, Course course)
        <<void>> deleteCourse(ArrayList<Course> courses, Course courseToDelete)
        <<void>> renameCourse(ArrayList<Course> courseModules, Course course, String newName)
        <<void>> renameCourseModule(ArrayList<CourseModule> courseModules, CourseModule courseModule, String newName)
        <<String>> createResultsSlip(Student student)
        <<void>> assignInstructorToCourseModule(ArrayList<CourseModule> allCourseModules, CourseModule courseModule, ArrayList<Instructor> instructors, Instructor instructor)
        <<void>> removeInstructorFromModule(ArrayList<CourseModule> allCourseModules,
                                               CourseModule courseModule,
                                               ArrayList<Instructor> instructors,
                                               Instructor instructor)
        <<void>> removeCourseModuleFromCourse(ArrayList<Course> courses, Course course, CourseModule courseModule)
        <<void>> removeInstructorFromCourseModule(ArrayList<CourseModule> allCourseModules, 
                                                     CourseModule courseModule, 
                                                     ArrayList<Instructor> allInstructors,
                                                     Instructor instructor)
    }

    class Instructor {
        courseModules: String[]
        <<String[]>> getCourseModules(),
        <<boolean>> addCourseModule(String courseModuleCode)
        <<void>> removeCourseModule(String courseModuleCode)
        <<void>> createAssignment(String courseModuleCode, String assignmentName, int mark)
        <<void>> addMark(Student student, CourseModule courseModule, Assignment assignment, int mark)
        <<void>> markStudentAsCompleted(Student student, CourseModule courseModule)
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
