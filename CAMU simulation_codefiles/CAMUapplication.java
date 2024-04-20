import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class represents the CAMU application (Course Allocation and Management Utility).
 * It provides functionalities for managing students, faculty, courses, and their interactions.
 */
public class CAMUapplication {

    /**
     * The main method of the application.
     * It initializes the application and displays the main menu.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nWelcome to CAMU - Course Allocation and Management Utility");
        createCoursesFromTextInfo("coursesInfo.txt");
        mainMenu(input);
    }
    //set default major requirement to true and make it false when name contains leadership in the method below
    public static void createCoursesFromTextInfo(String courseInfofileName){
        File courseInfoFile = new File(courseInfofileName);
        try(Scanner readfile = new Scanner(courseInfoFile)) {
            while (readfile.hasNextLine()){
                String line = readfile.nextLine();
                if (line.contains(",")){
                    String[] courseInfo = line.split(",");
                    if (courseInfo.length < 5){
                        System.out.println("Check data records well. Make sure format is right");
                        break;
                    }
                    String courseID = courseInfo[0];
                    String courseName = courseInfo[1];
                    double creditHours = Double.parseDouble(courseInfo[2]);
                    String courseType = courseInfo[3];
                    String department = courseInfo[4];
                    Course course = new Course(courseID,courseName,creditHours,courseType,department);
                    DataManager.storeAcademicEntity(course);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
    }

    /**
     * Displays the main menu and handles user input to navigate through different management options.
     *
     * @param input Scanner object for user input
     */
    public static void mainMenu(Scanner input){
        // Display the main menu
        String menu = "\nMAIN MENU\n\n" +
                "1. Student management\n" +
                "2. Faculty management\n" +
                "3. Course management\n" +
                "4. Exit\n\n" +
                "Please select an option by entering the corresponding number: ";
        System.out.print(menu);

        // Continuously prompt user until valid input is received
        int mainMenuChoice = 0;
        while (true) {
            try{
                mainMenuChoice = input.nextInt();
                if (mainMenuChoice<1 || mainMenuChoice>4){
                    throw new IllegalArgumentException();
                }
                input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e){
                input.nextLine(); // Clear input buffer
                System.out.print("Please enter a valid option: ");
            }
        }

        // Perform actions based on user choice
        switch (mainMenuChoice) {
            case 1:
                studentManagement(input);
                break;
            case 2:
                facultyManagement(input);
                break;
            case 3:
                courseManagement(input);
                break;
            case 4:
                // Exit the application
                System.out.println("\nTHANK YOU FOR USING THE APPLICATION. BYE!\n");
                return;
        }
    }

    /**
     * Handles the management of students by presenting a menu of options and executing the selected action.
     * @param input Scanner object to receive user input
     */
    public static void studentManagement(Scanner input){
        // Display the menu options
        String menu = "\nSTUDENT MANAGEMENT\n\n" +
                "1. Enroll New Student\n" +
                "2. Existing Student Management\n" +
                "3. View All Enrolled Students\n" +
                "4. Main Menu\n" +
                "Please select an option by entering the corresponding number: ";
        System.out.print(menu);

        // Continuously prompt user until valid input is received
        int studentChoice = 0;
        while (true) {
            try {
                studentChoice = input.nextInt();
                if (studentChoice<1 || studentChoice>4){
                    throw new IllegalArgumentException();
                } input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e){
                input.nextLine(); // Consume the newline character
                System.out.print("Please enter a valid option: ");
            }
        }

        // Execute the selected action based on user input
        switch (studentChoice){
            case 1:
                enrollNewStudent(input);
                break;
            case 2:
                existingStudentManagement(input);
                break;
            case 3:
                viewAllStudents(input);
                break;
            case 4:
                mainMenu(input);
                return;
        }
    }

    /**
     * Manages operations related to existing students by allowing the user to input a student ID,
     * displaying information about the student if found, and providing an option to return to the main menu.
     * @param input Scanner object to receive user input
     */
    public static void existingStudentManagement(Scanner input) {
        System.out.println("EXISTING STUDENT MANAGEMENT");
        System.out.println("\nEnter '0' to go to the main menu");

        String studentID;
        Student student;
        // Continuously prompt user until valid input is received or the user chooses to return to the main menu
        while (true) {
            System.out.print("Enter Student ID: ");
            studentID = input.nextLine();
            if (studentID.equals("0")) {
                mainMenu(input);
                return;
            }

            // Check if the student is enrolled
            student = DataManager.retrieveStudent(studentID);
            if (student == null) {
                System.out.println("No student found with this ID.");
                continue;
            } else {
                break;
            }
        }
        // Display methods available for existing student
        printExistingStudent(input, student, studentID);
    }

    /**
     * Displays the menu for managing an existing student, allows various actions such as viewing information,
     * updating information, removing, viewing course enrollment, allocating courses, dropping courses, adding grades,
     * viewing transcript, and returning to the main menu.
     * @param input Scanner object to receive user input
     * @param student The existing student object
     * @param studentID The ID of the existing student
     */
    public static void printExistingStudent(Scanner input, Student student, String studentID) {
        // Display the student management menu options
        String studentMenu = "\nEXISTING STUDENT MANAGEMENT\n\n" +
                "1. View Student Information\n" +
                "2. Update Student Information\n" +
                "3. Remove Student\n" +
                "4. View Course Enrollment\n" +
                "5. Allocate Courses to Student\n" +
                "6. Drop Course\n" +
                "7. Add Student Grades\n" +
                "8. View Student Transcript\n" +
                "9. Back to Previous Menu\n\n" +
                "Please select an option by entering the corresponding number: ";
        System.out.print(studentMenu);

        // Continuously prompt user until valid input is received
        int studentChoice = 0;
        while(true) {
            try {
                studentChoice = input.nextInt();
                if (studentChoice < 1 || studentChoice > 9) {
                    throw new IllegalArgumentException("Please enter a valid option between 1 and 9.");
                }
                input.nextLine(); // Consume the newline character after integer input
                break;
            } catch (Exception e) {
                input.nextLine(); // Consume the newline character in case of error
                System.out.print("Please enter a valid option: ");
            }
        }

        // Perform actions based on user choice
        switch (studentChoice) {
            case 1:
                viewStudentInformation(input, student, studentID);
                break;
            case 2:
                updateStudentInformation(input, student, studentID);
                break;
            case 3:
                removeStudent(input, student, studentID);
                break;
            case 4:
                viewCourseEnrollment(input, student, studentID);
                break;
            case 5:
                allocateCoursesToStudent(input, student, studentID);
                break;
            case 6:
                dropCourse(input, student, studentID);
            case 7:
                addStudentGrades(input, student, studentID);
                break;
            case 8:
                viewStudentTranscript(input, student, studentID);
                break;
            case 9:
                studentManagement(input);
                return;
        }
    }

    /**
     * Manages operations related to faculty by presenting a menu of options and executing the selected action.
     * @param input Scanner object to receive user input
     */
    public static void facultyManagement(Scanner input){
        // Display the menu options for faculty management
        String menu = "\nFACULTY MANAGEMENT\n\n" +
                "1. Add New Faculty\n" +
                "2. Existing Faculty Management\n" +
                "3. View All Faculty\n" +
                "4. Main Menu\n" +
                "Please select an option by entering the corresponding number: ";
        System.out.print(menu);

        // Continuously prompt user until valid input is received
        int choice = 0;
        while (true) {
            try {
                choice = input.nextInt();
                if (choice<1 || choice>4){
                    throw new IllegalArgumentException();
                } input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e){
                input.nextLine(); // Consume the newline character
                System.out.print("Please enter a valid option: ");
            }
        }

        // Execute the selected action based on user input
        switch (choice){
            case 1:
                addNewFaculty(input);
                break;
            case 2:
                existingFacultyManagement(input);
                break;
            case 3:
                viewAllFaculty(input);
                break;
            case 4:
                mainMenu(input);
                return;
        }
    }


    /**
     * Manages operations related to existing faculty by allowing the user to input a faculty ID,
     * displaying information about the faculty if found, and providing an option to return to the main menu.
     * @param input Scanner object to receive user input
     */
    public static void existingFacultyManagement(Scanner input) {
        System.out.println("EXISTING FACULTY MANAGEMENT");
        System.out.println("\nEnter '0' to go to the main menu");

        String facultyID;
        Faculty faculty;
        // Continuously prompt user until valid input is received or the user chooses to return to the main menu
        while (true) {
            System.out.print("Enter Faculty ID: ");
            facultyID = input.nextLine();
            if (facultyID.equals("0")) {
                mainMenu(input);
                return;
            }

            // Check if the faculty is enrolled
            faculty = DataManager.retrieveFaculty(facultyID);
            if (faculty == null) {
                System.out.println("No faculty found with this ID.");
                continue;
            } else {
                break;
            }
        }
        // Display methods available for existing faculty
        printExistingFaculty(input, faculty, facultyID);
    }

    /**
     * Displays the menu for managing an existing faculty, allowing various actions such as viewing information,
     * updating information, removing, viewing assigned courses, assigning courses, and returning to the main menu.
     * @param input Scanner object to receive user input
     * @param faculty The existing faculty object
     * @param facultyID The ID of the existing faculty
     */
    public static void printExistingFaculty(Scanner input, Faculty faculty, String facultyID){
        // Display the faculty management menu options
        String facultyMenu = "\nFACULTY MANAGEMENT\n\n" +
                "1. View Faculty Information\n" +
                "2. Update Faculty Information\n" +
                "3. Remove Faculty\n" +
                "4. View Courses Assigned to Faculty\n" +
                "5. Assign Courses to Faculty\n" +
                "6. Back to Previous Menu\n\n" +
                "Please select an option by entering the corresponding number: ";
        System.out.print(facultyMenu);

        // Continuously prompt user until valid input is received
        int facultyChoice = 0;
        while (true){
            try {
                facultyChoice = input.nextInt();
                if (facultyChoice<1 || facultyChoice>6){
                    throw new IllegalArgumentException();
                } input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e){
                input.nextLine(); // Consume the newline character
                System.out.print("Please enter a valid option: ");
            }
        }

        // Perform actions based on user choice
        switch (facultyChoice) {
            case 1:
                viewFacultyInformation(input, faculty, facultyID);
                break;
            case 2:
                updateFacultyInformation(input, faculty, facultyID);
                break;
            case 3:
                removeFaculty(input, faculty, facultyID);
                break;
            case 4:
                viewCoursesAssignedToFaculty(input, faculty, facultyID);
                break;
            case 5:
                assignCoursesToFaculty(input, faculty, facultyID);
                break;
            case 6:
                facultyManagement(input);
                return;
        }
    }

    /**
     * Manages operations related to courses by presenting a menu of options and executing the selected action.
     * @param input Scanner object to receive user input
     */
    public static void courseManagement(Scanner input){
        // Display the menu options for course management
        String menu = "\nCOURSE MANAGEMENT\n\n" +
                "1. Add New Course\n" +
                "2. Existing Course Management\n" +
                "3. View All Courses\n" +
                "4. Main Menu\n" +
                "Please select an option by entering the corresponding number: ";
        System.out.print(menu);

        // Continuously prompt user until valid input is received
        int choice = 0;
        while (true) {
            try {
                choice = input.nextInt();
                if (choice<1 || choice>4){
                    throw new IllegalArgumentException();
                } input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e){
                input.nextLine(); // Consume the newline character
                System.out.print("Please enter a valid option: ");
            }
        }

        // Execute the selected action based on user input
        switch (choice){
            case 1:
                addNewCourse(input);
                break;
            case 2:
                existingCourseManagement(input);
                break;
            case 3:
                viewAllCourses(input);
                break;
            case 4:
                mainMenu(input);
                return;
        }
    }

    /**
     * Manages operations related to existing courses by allowing the user to input a course ID,
     * displaying information about the course if found, and providing an option to return to the main menu.
     * @param input Scanner object to receive user input
     */
    public static void existingCourseManagement(Scanner input) {
        System.out.println("EXISTING COURSE MANAGEMENT");
        System.out.println("\nEnter '0' to go to the main menu");

        String courseID;
        Course course;
        // Continuously prompt user until valid input is received or the user chooses to return to the main menu
        while (true) {
            System.out.print("Enter Course ID: ");
            courseID = input.nextLine();
            if (courseID.equals("0")) {
                mainMenu(input);
                return;
            }

            // Check if the course is enrolled
            course = DataManager.retrieveCourse(courseID);
            if (course == null) {
                System.out.println("No course found with this ID.");
                continue;
            } else {
                break;
            }
        }
        // Display methods available for existing course
        printExistingCourse(input, course, courseID);
    }

    /**
     * Displays the course management menu options and handles user input.
     *
     * @param input Scanner object for user input
     * @param course Course object representing the current course
     * @param courseID ID of the current course
     */
    public static void printExistingCourse(Scanner input, Course course, String courseID){
        // Display the course management menu options
        String courseMenu = "\nCOURSE MANAGEMENT\n\n" +
                "1. View Course Information\n" +
                "2. Remove Course\n" +
                "3. View Enrolled Students\n" +
                "4. View Faculty assigned to Course\n" +
                "5. Back to Previous Menu\n\n" +
                "Please select an option by entering the corresponding number: ";
        System.out.print(courseMenu);

        // Continuously prompt user until valid input is received
        int courseChoice = 0;
        while (true) {
            try {
                courseChoice = input.nextInt();
                if (courseChoice<1 || courseChoice>5){
                    throw new IllegalArgumentException();
                }
                input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e){
                input.nextLine(); // Consume the newline character
                System.out.print("Please enter a valid option: ");
            }
        }

        // Perform actions based on user choice
        switch (courseChoice) {
            case 1:
                viewCourseInformation(input, course, courseID);
                break;
            case 2:
                removeCourse(input, course, courseID);
                break;
            case 3:
                viewEnrolledStudents(input, course, courseID);
                break;
            case 4:
                viewAssignedFaculty(input, course, courseID);
                break;
            case 5:
                courseManagement(input);
                return;
        }
    }

    //STUDENT MANAGEMENT METHODS
    /**
     * Enrolls a new student into the system by collecting necessary information such as name, ID, age, gender, year group, major and nationality.
     * Allows cancellation of the enrollment process at any point by entering '0'.
     *
     * @param input Scanner object for user input
     */
    public static void enrollNewStudent(Scanner input){
        System.out.println("\nENROLL NEW STUDENT");
        System.out.println("\nAt any point, you can enter '0' to cancel new student enrollment");
        System.out.println("Enter the following details to enroll the new student\n");

        // Prompt user for student name
        System.out.print("Student name: ");
        String studentName = input.nextLine();
        if (studentName.equals("0")) {studentManagement(input); return;} // Check if user wants to cancel

        // Prompt user for student ID
        System.out.print("Student ID: ");
        String studentID = input.nextLine();
        if (studentID.equals("0")) {studentManagement(input); return;} // Check if user wants to cancel

        // Prompt user for student nationality
        System.out.print("Nationality: ");
        String nationality = input.nextLine();
        if (nationality.equals("0")) {studentManagement(input);return;} // Check if user wants to cancel

        // Prompt user for student age and validate input
        int age = 0;
        while (true) {
            System.out.print("Student age: ");
            try {
                age = input.nextInt(); input.nextLine(); // Consume the newline character
                if (age == 0) {studentManagement(input); return;} // Check if user wants to cancel
                break; // Exit the loop if age input is successful
            } catch (Exception e) {
                input.nextLine(); // Clear the input buffer
                System.out.println("\nPlease enter a valid age (eg. 18)");
            }
        }

        // Prompt user for student gender and validate input
        char gender;
        while (true) {
            System.out.print("Student gender (F / M): ");
            String genderInput = input.nextLine().toUpperCase(); // Convert input to uppercase for case-insensitive comparison
            if (genderInput.equals("0")) {studentManagement(input); return;} // Check if user wants to cancel

            // Check if the input is valid (either "F" or "M")
            if (genderInput.length() == 1 && (genderInput.charAt(0) == 'F' || genderInput.charAt(0) == 'M')) {
                gender = genderInput.charAt(0);
                break; // Exit the loop if valid input is provided
            } else {
                System.out.println("\nInvalid input. Please enter either 'F' or 'M'.");
            }
        }

        // Prompt user for student year group and validate input
        int yearGroup = 0;
        System.out.print("\nYEAR GROUPS:\n" +
                "1. Freshman\n" +
                "2. Sophomore\n" +
                "3. Junior\n" +
                "4. Senior\n" +
                "Select the student's year group by entering the corresponding number: ");
        while (true) {
            try {
                yearGroup = input.nextInt(); //input.nextLine(); // Consume the newline character
                if (yearGroup == 0) {
                    input.nextLine(); // Clear the input buffer
                    studentManagement(input); return;} // Check if user wants to cancel
                if (yearGroup < 1 || yearGroup > 4){
                    throw new IllegalArgumentException();
                } input.nextLine(); // Clear the input buffer
                break;
            } catch (Exception e) {
                input.nextLine(); // Clear the input buffer
                System.out.print("Please enter a valid year group number: ");
            }
        }

        // Prompt user for student major and validate input
        int major = 0;
        System.out.print("\nMAJORS:\n" +
                "1. Undecided\n" +
                "2. Business Administration\n" +
                "3. Economics\n" +
                "4. Computer Science\n" +
                "5. Computer Engineering\n" +
                "6. Electrical Engineering\n" +
                "7. Mechanical Engineering\n" +
                "Select the student's major by entering the corresponding number: ");
        while (true) {
            try {
                major = input.nextInt();
                if (major == 0) {
                    input.nextLine(); // Consume the newline character
                    studentManagement(input); return;}// Check if user wants to cancel
                if (major<1 || major >7){
                    throw new IllegalArgumentException();
                } input.nextLine(); // Consume the newline character
                break; // Exit the loop if major selection is successful
            } catch (Exception e){
                input.nextLine(); // Consume the newline character
                System.out.print("Please enter a valid option: ");
            }
        }
        // Perform actions based on user choice
        String selectedMajor="";
        switch (major) {
            case 1:
                selectedMajor = "Undecided";
                break;
            case 2:
                selectedMajor = "Business Administration";
                break;
            case 3:
                selectedMajor = "Economics";
                break;
            case 4:
                selectedMajor = "Computer Science";
                break;
            case 5:
                selectedMajor = "Computer Engineering";
                break;
            case 6:
                selectedMajor = "Electrical Engineering";
                break;
            case 7:
                selectedMajor = "Mechanical Engineering";
                break;
        }
        // Create a new Student object with collected information
        Student student = new Student(studentID, studentName, age, gender, yearGroup,selectedMajor,nationality);

        // Store the student object in the data manager
        DataManager.storeAcademicEntity(student);
        System.out.println("\nSTUDENT ENROLLED SUCCESSFULLY!");
        studentManagement(input); // Return to student management menu
    }

    /**
     * Displays the information of a specific student.
     *
     * @param input Scanner object for user input
     * @param student Student object representing the student whose information is to be displayed
     * @param studentID ID of the student
     */
    public static void viewStudentInformation(Scanner input, Student student, String studentID){
        System.out.println("\nVIEW STUDENT INFORMATION");

        System.out.println(student);
        printExistingStudent(input,student, studentID); return;
    }

    /**
     * Allows updating of student information such as age, gender, major, and nationality.
     * Cancels the update process at any point by entering '0'.
     *
     * @param input Scanner object for user input
     * @param student Student object representing the student whose information is to be updated
     * @param studentID ID of the student
     */
    public static void updateStudentInformation(Scanner input, Student student, String studentID){
        System.out.println("\nUPDATE STUDENT INFORMATION\n");
        System.out.println("\n At any point in the update, enter '0' to cancel and return to the previous menu");


        boolean updating = true;
        while(updating) {
            System.out.print("""
                    
                    1. Update Student Age
                    2. Update Student Gender
                    3. Update Student Major
                    4. Update Student Nationality
                    5. Back to Previous Menu

                    Please select an option by entering the corresponding number:\s""");

            int updateStudent = 0;
            try{
                updateStudent = input.nextInt();
                if (updateStudent < 1 || updateStudent > 5) {
                    throw new IllegalArgumentException();
                } input.nextLine(); // clear input buffer
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number 1-6");
                input.nextLine(); // clear input buffer
                continue;
            }

            switch (updateStudent) {
                case 1:
                    // Update student age
                    System.out.printf("\nCurrent student age: %s\n", student.getAge());
                    System.out.print("Enter Student Age: ");

                    int studentAge = 0;
                    try {
                        studentAge = input.nextInt();
                        input.nextLine(); // clear input buffer
                    } catch (Exception e){
                        System.out.println("Invalid input. Please enter a valid age");
                        input.nextLine(); // clear input buffer
                        continue;
                    }
                    student.setAge(studentAge);
                    System.out.println("\nStudent age updated successfully");
                    break;
                case 2:
                    // Update student gender
                    System.out.printf("\nCurrent student gender: %c\n",student.getGender());
                    System.out.print("Enter Student Gender (F / M): ");

                    char studentGender;
                    try {
                        studentGender = input.next().toUpperCase().charAt(0);
                        if (studentGender != 'F' && studentGender != 'M') {
                            throw new IllegalArgumentException();
                        }input.nextLine(); // Clear the input buffer
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter either 'F' or 'M'.");
                        input.nextLine(); // Clear the input buffer
                        continue;
                    }
                    student.setGender(studentGender);
                    System.out.println("\nStudent gender updated successfully");
                    break;
                case 3:
                    // Update student major
                    System.out.printf("\nCurrent student major: %s\n", student.getMajor());
                    String selectedMajor="";
                    while (true) {
                        System.out.print("\nMAJORS:\n" +
                                "1. Undecided\n" +
                                "2. Business Administration\n" +
                                "3. Economics\n" +
                                "4. Computer Science\n" +
                                "5. Computer Engineering\n" +
                                "6. Electrical Engineering\n" +
                                "7. Mechanical Engineering\n" +
                                "Select the student's major by entering the corresponding number: ");
                        int major =0;
                        try {
                            major = input.nextInt();
                            if (major < 0 || major > 7) {
                                throw new IllegalArgumentException();
                            }input.nextLine(); // Clear the input buffer
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid major number (1-7).");
                            input.nextLine(); // Clear the input buffer
                            continue;
                        }

                        switch (major) {
                            case 0:
                                updateStudentInformation(input, student, studentID);
                                return;
                            case 1:
                                selectedMajor = "Undecided";
                                break;
                            case 2:
                                selectedMajor = "Business Administration";
                                break;
                            case 3:
                                selectedMajor = "Economics";
                                break;
                            case 4:
                                selectedMajor = "Computer Science";
                                break;
                            case 5:
                                selectedMajor = "Computer Engineering";
                                break;
                            case 6:
                                selectedMajor = "Electrical Engineering";
                                break;
                            case 7:
                                selectedMajor = "Mechanical Engineering";
                                break;
                        }
                        student.setMajor(selectedMajor);
                        System.out.printf("\nStudent major updated successfully to %s\n", student.getMajor());
                        break; // Exit the loop if major selection is successful
                    } break;
                case 4:
                    // Update student nationality
                    System.out.printf("\nCurrent student nationality: %s\n", student.getNationality());
                    System.out.print("Enter Student Nationality: ");
                    String studentNationality = input.nextLine();
                    student.setNationality(studentNationality);
                    System.out.println("\nStudent nationality updated successfully");
                    break;
                case 5:
                    // Back to previous menu
                    updating = false;
                    printExistingStudent(input, student, studentID);
                    return;
            } DataManager.storeAcademicEntity(student); // Write update to file
        } printExistingStudent(input, student, studentID);
    }

    /**
     * Removes a student from the system.
     *
     * @param input Scanner object for user input
     * @param student Student object representing the student to be removed
     * @param studentID ID of the student
     */
    public static void removeStudent(Scanner input,Student student, String studentID){
        System.out.println("\nREMOVE STUDENT\n");

        while (true){
            System.out.print("Are you sure you want to remove student (y / n): ");
            String removeStudent = input.nextLine().toLowerCase();
            if (removeStudent.equals("y")){
                DataManager.removeAcademicEntity(student);
                System.out.println("Student removed");
                break;
            } else if (removeStudent.equals("n")){
                System.out.println("Student not removed");
                break;
            } else {
                System.out.println("Invalid input. Enter 'y' or 'n' ");
            }
        }
        printExistingStudent(input, student, studentID); return;

    }

    /**
     * Displays the courses in which a student is enrolled.
     *
     * @param input Scanner object for user input
     * @param student Student object representing the student whose course enrollment is to be viewed
     * @param studentID ID of the student
     */
    public static void viewCourseEnrollment(Scanner input, Student student, String studentID){
        System.out.println("\nVIEW COURSE ENROLLMENT");

        ArrayList<Course> courseList =  student.getRegisteredCourses();
        if (courseList == null || courseList.isEmpty()){
            System.out.println("Student has not been enrolled in any course.");
        }else {
            System.out.println("Courses:");
            System.out.println(".............................");
            for (Course course: courseList){
                System.out.println(course);
            }
        }
        printExistingStudent(input, student, studentID); return;
    }

    /**
     * Allows allocating courses to a student based on their major.
     *
     * @param input Scanner object for user input
     * @param student Student object representing the student to whom courses are to be allocated
     * @param studentID ID of the student
     */
    public static void allocateCoursesToStudent(Scanner input, Student student, String studentID) {
        System.out.println("\nALLOCATE COURSES TO STUDENT");

        String major = student.getMajor();
        HashMap<String, String> courseInfo = DataManager.retrieveCourseInfo(major);
        String chosenCourseID = null;
        String chosenCourseName;

        int optionNumber = 1; // Start numbering options from 1
        System.out.println("COURSES AVAILABLE");
        for (Map.Entry<String, String> entry : courseInfo.entrySet()) {
            System.out.println(optionNumber + ". " + entry.getKey() + " - " + entry.getValue());
            optionNumber++;
        }
        System.out.print("Enter the number corresponding to your choice: ");

        int courseChoice = 0;
        while (true) {
            try {
                courseChoice = input.nextInt();
                if (courseChoice == 0) {input.nextLine(); printExistingStudent(input,student,studentID); return;} // Check if user wants to cancel
                if (courseChoice < 1 || courseChoice > optionNumber-1){
                    throw new IllegalArgumentException();
                } input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e) {
                input.nextLine(); // Clear the input buffer
                System.out.print("Please enter a valid course number between 1 and "+(optionNumber-1) + ": ");
            }
        }

        // Looping through the hash map
        int optionNumber2 = 1; 
        for (Map.Entry<String, String> entry : courseInfo.entrySet()) {
            if (optionNumber2 == courseChoice){
                chosenCourseID = entry.getKey();
                chosenCourseName = entry.getValue();
                break;
            } optionNumber2++;
        }

        // Get the course object and check if student is already enrolled
        Course tempCourse = DataManager.retrieveCourse(chosenCourseID);
        for (Course course : student.getRegisteredCourses()) {
            if (tempCourse.equals(course)) {
                System.out.println("Student is already enrolled in course " + tempCourse.getName());
                printExistingStudent(input,student,studentID);
                return;
            }
        }

        // Register the student for the course and update student records
        student.registerCourse(tempCourse);
        DataManager.storeAcademicEntity(student);
        // Add student to the course enrolled list and update course records
        tempCourse.setEnrolledStudents(student);
        DataManager.storeAcademicEntity(tempCourse);

        System.out.println("Student enrolled in course successfully.");
        printExistingStudent(input,student,studentID);
        return;
    }

    /**
     * Allows a student to drop a course from their enrolled courses.
     *
     * @param input Scanner object for user input
     * @param student Student object representing the student who wants to drop the course
     * @param studentID ID of the student
     */
    public static void dropCourse(Scanner input, Student student, String studentID){
        // List student's registered courses
        ArrayList<Course> courseList = student.getRegisteredCourses();

        int optionNumber = 1;
        for (Course course : courseList) {
            System.out.println(optionNumber + ". " + course.getName());
            optionNumber++;
        }
        int courseChoice;
        while (true) {
            System.out.println("Enter the number corresponding to your choice: ");
            courseChoice = 0;
            try {
                courseChoice = input.nextInt();
                if (courseChoice == 0) {
                    input.nextLine();
                    studentManagement(input);
                    return;
                }
                if (courseChoice < 1 || courseChoice > courseList.size()) {
                    throw new IllegalArgumentException("Invalid course number.");
                }
                input.nextLine(); // Clear buffer after integer input
                break;
            } catch (Exception e) {
                input.nextLine(); // Ensure buffer is cleared after error
                System.out.println("Please enter a valid course number between 1 and " + courseList.size() + ".");
            }
        }
        Course course = courseList.get(courseChoice-1);
        student.dropCourse(course);
        course.removeStudent(student);
        DataManager.storeAcademicEntity(student);
        DataManager.storeAcademicEntity(course);
        printExistingStudent(input,student, studentID); return;
    }

    /**
     * Allows adding grades for courses taken by a student.
     *
     * @param input Scanner object for user input
     * @param student1 Student object representing the student for whom grades are to be added
     * @param studentID ID of the student
     */
    private static void addStudentGrades(Scanner input, Student student1, String studentID) {
        System.out.println("\nADD STUDENT GRADES");

        Student student = student1;

        ArrayList<Course> courseList = student.getRegisteredCourses();
        if (courseList == null || courseList.isEmpty()) {
            System.out.println("Student has not been enrolled in any course.");
            studentManagement(input);
            return;
        }

        HashMap<Course, String> gradePairs = student.getCourseGradePair();
        System.out.println("Courses taken:");
        System.out.println(".............................");
        int optionNumber = 1;
        for (Course course : courseList) {
            System.out.println(optionNumber + ". " + course.getName());
            optionNumber++;
        }

        int courseChoice;
        while (true) {
            System.out.print("Enter the number corresponding to your choice: ");
            courseChoice = 0;
            try {
                courseChoice = input.nextInt();
                if (courseChoice == 0) {
                    input.nextLine();
                    printExistingStudent(input, student, studentID);
                    return;
                }
                if (courseChoice < 1 || courseChoice > courseList.size()) {
                    throw new IllegalArgumentException("Invalid course number.");
                }
                input.nextLine(); // Clear buffer after integer input
                break;
            } catch (Exception e) {
                input.nextLine(); // Ensure buffer is cleared after error
                System.out.println("Please enter a valid course number between 1 and " + courseList.size() + ".");
            }
        }

        Course selectedCourse = courseList.get(courseChoice - 1);
        System.out.println("GRADES (A+,A,B+,B,C+,C,D+,D,E,F)");
        System.out.printf("Enter %s grade: ", selectedCourse.getName());
        String grade = input.nextLine().toUpperCase();
        while (!Transcript.gradePattern.matcher(grade).matches()) {
            System.out.println("Invalid grade entered. Please enter a valid grade.");
            grade = input.nextLine().toUpperCase();
        }
        gradePairs.put(selectedCourse, grade);

        student.setCourseGradePair(gradePairs);
        DataManager.storeAcademicEntity(student);
        System.out.println("Grade added successfully");
        printExistingStudent(input, student, studentID); return;
    }
    
    /**
     * Displays the transcript of a student.
     *
     * @param input Scanner object for user input
     * @param student Student object representing the student whose transcript is to be viewed
     * @param studentID ID of the student
     */
    public static void viewStudentTranscript(Scanner input, Student student, String studentID){
        System.out.println("\nVIEW TRANSCRIPT");

        Transcript transcript = new Transcript(studentID);
        transcript.displayTranscript(studentID);
        printExistingStudent(input, student, studentID);
    }

    /**
     * Displays all enrolled students.
     *
     * @param input Scanner object for user input
     */
    public static void viewAllStudents(Scanner input){
        List<AcademicEntity> studentList =  DataManager.retrieveAcademicEntities(DataManager.STUDENT_FILE);
        if (studentList == null || studentList.isEmpty()){
            System.out.println("No students enrolled");
            studentManagement(input);
            return;
        }
        System.out.println("STUDENTS");
        System.out.println(".....................");
        for (AcademicEntity student : studentList){
            Student student1 = (Student) student;
            System.out.println("ID: " + student1.getID() +" , Name: " + student1.getName());
        }
        studentManagement(input);
        return;
    }

    // FACULTY MANAGEMENT METHODS
    /**
     * Allows adding a new faculty member to the system.
     *
     * @param input Scanner object for user input
     */
    public static void addNewFaculty(Scanner input){
        System.out.println("\nADD NEW FACULTY");
        System.out.println("\nAt any point, you can enter '0' to cancel the addition of new faculty");
        System.out.println("Enter the following details to add new faculty\n");

        // Prompt user for faculty name
        System.out.print("Faculty name: ");
        String name = input.nextLine();
        if (name.equals("0")) {facultyManagement(input); return;} // Check if user wants to cancel

        // Prompt user for faculty nationality
        System.out.print("Faculty Nationality: ");
        String nationality = input.nextLine();
        if (nationality.equals("0")) {facultyManagement(input); return;} // Check if user wants to cancel

        // Prompt user for faculty ID
        System.out.print("Faculty ID: ");
        String ID = input.nextLine();
        if (ID.equals("0")) {facultyManagement(input); return;} // Check if user wants to cancel


        // Prompt user for faculty age and validate input
        int age = 0;
        while (true) {
            System.out.print("Faculty age: ");
            try {
                age = input.nextInt();
                if (age == 0) {input.nextLine(); facultyManagement(input); return;} // Check if user wants to cancel
                input.nextLine(); // Consume the newline character
                break; // Exit the loop if age input is successful
            } catch (Exception e) {
                input.nextLine(); // Clear the input buffer
                System.out.println("\nPlease enter a valid age (eg. 18)");
            }
        }

        // Prompt user for faculty gender and validate input
        char gender;
        while (true) {
            System.out.print("Faculty gender (F / M): ");
            String genderInput = input.nextLine().toUpperCase(); // Convert input to uppercase for case-insensitive comparison
            if (genderInput.equals("0")) {facultyManagement(input); return;} // Check if user wants to cancel

            // Check if the input is valid (either "F" or "M")
            if (genderInput.length() == 1 && (genderInput.charAt(0) == 'F' || genderInput.charAt(0) == 'M')) {
                gender = genderInput.charAt(0);
                break; // Exit the loop if valid input is provided
            } else {
                System.out.println("\nInvalid input. Please enter either 'F' or 'M'.");
            }
        }

        // Prompt user for faculty department and validate input
        int department = 0;
        System.out.print("\nDepartments:\n" +
                "1. Humanities & Social Sciences\n" +
                "2. Engineering\n" +
                "3. Economics & Business Management\n" +
                "4. Computer Science & Information Systems\n" +
                "Select the faculty's department by entering the corresponding number: ");
        while (true) {
            try {
                department = input.nextInt();
                if (department == 0) {input.nextLine(); facultyManagement(input); return;}// Check if user wants to cancel
                if (department<1 || department >4){
                    throw new IllegalArgumentException();
                }
                input.nextLine(); // Consume the newline character
                break; // Exit the loop if major selection is successful
            } catch (Exception e){
                input.nextLine(); // Consume the newline character
                System.out.print("Please enter a valid option: ");
            }
        }
        // Perform actions based on user choice
        String selectedDepartment="";
        switch (department) {
            case 1:
                selectedDepartment = "Humanities & Social Sciences";
                break;
            case 2:
                selectedDepartment = "Engineering";
                break;
            case 3:
                selectedDepartment = "Economics & Business Management";
                break;
            case 4:
                selectedDepartment = "Computer Science & Information Systems";
                break;
        }

        // Create a new Faculty object with collected information
        Faculty faculty = new Faculty(ID, name, age, gender, nationality,selectedDepartment);

        // Store the faculty object in the data manager
        DataManager.storeAcademicEntity(faculty);

        facultyManagement(input); // Return to faculty management menu
    }

    /**
     * Displays information about a faculty member.
     *
     * @param input Scanner object for user input
     * @param faculty Faculty object representing the faculty whose information is to be viewed
     * @param facultyID ID of the faculty
     */
    public static void viewFacultyInformation(Scanner input, Faculty faculty, String facultyID){
        System.out.println("\nVIEW FACULTY INFORMATION");
        faculty.getInfo();
        printExistingFaculty(input, faculty, facultyID);return;
    }

    /**
     * Allows updating information about a faculty member.
     *
     * @param input Scanner object for user input
     * @param faculty Faculty object representing the faculty whose information is to be updated
     * @param facultyID ID of the faculty
     */
    public static void updateFacultyInformation(Scanner input, Faculty faculty, String facultyID){
        System.out.println("\nUPDATE FACULTY INFORMATION\n");
        System.out.println("\n At any point during the update, enter '0' to cancel and return to the previous menu");

        boolean updating = true;
        while(updating) {
            System.out.print("""
                    
                    1. Update Faculty Age
                    2. Update Faculty Gender
                    3. Update Faculty Nationality
                    4. Back to Previous Menu

                    Please select an option by entering the corresponding number:\s""");

            int updateFaculty = 0;
            try{
                updateFaculty = input.nextInt();
                if (updateFaculty < 1 || updateFaculty > 4) {
                    throw new IllegalArgumentException();
                }input.nextLine(); // clear input buffer
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number 1-4");
                input.nextLine(); // clear input buffer
                continue;
            }

            switch (updateFaculty) {
                case 1:
                    // Update faculty age
                    System.out.printf("\nCurrent faculty age: %s\n", faculty.getAge());
                    System.out.print("Enter Faculty Age: ");

                    int facultyAge = 0;
                    try {
                        facultyAge = input.nextInt();
                        if (facultyAge == 0){input.nextLine(); printExistingFaculty(input, faculty, facultyID); return;}
                        input.nextLine(); // clear input buffer
                    } catch (Exception e){
                        System.out.println("Invalid input. Please enter a valid age");
                        input.nextLine(); // clear input buffer
                        continue;
                    }
                    faculty.setAge(facultyAge);
                    System.out.println("\nFaculty age updated successfully");
                    break;
                case 2:
                    // Update faculty gender
                    System.out.printf("\nCurrent faculty gender: %c\n",faculty.getGender());
                    System.out.print("Enter Faculty Gender (F / M): ");

                    char facultyGender;
                    try {
                        facultyGender = input.next().toUpperCase().charAt(0);
                        if (facultyGender == '0'){input.nextLine(); printExistingFaculty(input, faculty, facultyID); return;}
                        if (facultyGender != 'F' && facultyGender != 'M') {
                            throw new IllegalArgumentException();
                        }input.nextLine(); // Clear the input buffer
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter either 'F' or 'M'.");
                        input.nextLine(); // Clear the input buffer
                        continue;
                    }
                    faculty.setGender(facultyGender);
                    System.out.println("\nFaculty gender updated successfully");
                    break;
                case 3:
                    // Update faculty nationality
                    System.out.printf("\nCurrent faculty nationality: %s\n", faculty.getNationality());
                    System.out.print("Enter Faculty Nationality: ");
                    String facultyNationality = input.nextLine();
                    if (facultyNationality.equals("0")){printExistingFaculty(input, faculty, facultyID); return;}
                    faculty.setNationality(facultyNationality);
                    System.out.println("\nFaculty nationality updated successfully");
                    break;
                case 4:
                    // Back to main menu
                    updating = false;
                    printExistingFaculty(input, faculty, facultyID);
                    return;
            } DataManager.storeAcademicEntity(faculty); // Write update to file
        } printExistingFaculty(input, faculty, facultyID); return;
    }

    /**
     * Removes a faculty member from the system.
     *
     * @param input Scanner object for user input
     * @param faculty Faculty object representing the faculty to be removed
     * @param facultyID ID of the faculty
     */
    public static void removeFaculty(Scanner input, Faculty faculty, String facultyID){
        System.out.println("\nREMOVE FACULTY\n");

        while (true){
            System.out.print("Are you sure you want to remove faculty (y / n): ");
            String removeFaculty = input.nextLine().toLowerCase();
            if (removeFaculty.equals("y")){
                DataManager.removeAcademicEntity(faculty);
                System.out.println("Faculty removed");
                break;
            } else if (removeFaculty.equals("n")){
                System.out.println("Faculty not removed");
                break;
            } else {
                System.out.println("Invalid input. Enter 'y' or 'n' ");
            }
        }
        printExistingFaculty(input, faculty, facultyID); return;
    }

    /**
     * Displays the courses assigned to a faculty member.
     *
     * @param input Scanner object for user input
     * @param faculty Faculty object representing the faculty
     * @param facultyID ID of the faculty
     */
    public static void viewCoursesAssignedToFaculty(Scanner input, Faculty faculty, String facultyID){
        System.out.println("\nVIEW COURSES ASSIGNED TO FACULTY");

        // Retrieve the list of courses taught by the faculty
        ArrayList<Course> courseList =  faculty.getCoursesTaught();
        if (courseList == null || courseList.isEmpty()){
            System.out.println("Faculty does not teach any course.");
        }else {
            System.out.println("Courses:");
            System.out.println(".............................");
            for (Course course: courseList){
                System.out.println(course);
            }
        }
        // Return to the previous menu
        printExistingFaculty(input, faculty, facultyID);
        return;
    }

    /**
     * Assigns courses to a faculty member.
     *
     * @param input Scanner object for user input
     * @param faculty Faculty object representing the faculty member
     * @param facultyID ID of the faculty member
     */
    public static void assignCoursesToFaculty(Scanner input, Faculty faculty, String facultyID){
        System.out.println("\nASSIGN COURSES TO FACULTY");

        

        // Retrieve available courses for the faculty's department
        HashMap<String,String> courseInfo = DataManager.retrieveFacultyCourseInfo(faculty.getDepartment());

        // Display available courses
        int optionNumber = 1; // Start numbering options from 1
        System.out.println("COURSES AVAILABLE");
        for (Map.Entry<String, String> entry : courseInfo.entrySet()) {
            System.out.println(optionNumber + ". " + entry.getKey() + " - " + entry.getValue());
            optionNumber++;
        }
        System.out.print("Enter the number corresponding to your choice: ");

        // Prompt for course selection
        int courseChoice = 0;
        while (true) {
            try {
                courseChoice = input.nextInt();
                if (courseChoice == 0) {input.nextLine(); printExistingFaculty(input, faculty, facultyID); return;} // Check if user wants to cancel
                if (courseChoice < 1 || courseChoice > optionNumber - 1) {
                    throw new IllegalArgumentException();
                }
                input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e) {
                input.nextLine(); // Clear the input buffer
                System.out.print("Please enter a valid course number between 1 and " + (optionNumber - 1) + ": ");
            }
        }

        // Retrieve the chosen course ID
        String chosenCourseID = null;
        String chosenCourseName;

        int optionNumber2 = 1;
        for (Map.Entry<String, String> entry : courseInfo.entrySet()) {
            if (optionNumber2 == courseChoice){
                chosenCourseID = entry.getKey();
                chosenCourseName = entry.getValue();
                break;
            } optionNumber2++;
        }


        Course tempCourse = DataManager.retrieveCourse(chosenCourseID);
        for (Course course : faculty.getCoursesTaught()) {
            if (tempCourse.equals(course)) {
                System.out.println("Faculty is already enrolled in course " + tempCourse.getName());
                printExistingFaculty(input, faculty, facultyID);
                return;
            }
        }
        faculty.assignToCourse(tempCourse);
        assert tempCourse != null;

        tempCourse.assignFaculty(faculty);
        DataManager.storeAcademicEntity(tempCourse);
        DataManager.storeAcademicEntity(faculty);
        System.out.println("Course assigned successfully.");
        printExistingFaculty(input, faculty, facultyID);
        return;
    }

    /**
     * Displays information about all faculty members.
     *
     * @param input Scanner object for user input
     */
    public static void viewAllFaculty(Scanner input){
        List<AcademicEntity> facultyList =  DataManager.retrieveAcademicEntities(DataManager.FACULTY_FILE);
        if (facultyList == null || facultyList.isEmpty()){
            System.out.println("No faculty enrolled");
            courseManagement(input);
            return;
        }
        System.out.println("FACULTY MEMBERS");
        System.out.println(".....................");
        for (AcademicEntity faculty : facultyList){
            Faculty faculty1 = (Faculty) faculty;
            System.out.println("ID: " + faculty1.getID() +" , Name: " + faculty1.getName());
        }
        facultyManagement(input);
        return;

    }

    // COURSE MANAGEMENT METHODS
    /**
     * Allows the user to add a new course to the system.
     *
     * @param input Scanner object for user input
     */
    public static void addNewCourse(Scanner input){
        System.out.println("\nENROLL NEW COURSE");
        System.out.println("\nAt any point in the enrollment, you can enter '0' to cancel new student enrollment");
        System.out.println("Enter the following details to enroll the new course\n");

        // Prompt user for course name
        System.out.print("Course name: ");
        String courseName = input.nextLine();
        if (courseName.equals("0")) {courseManagement(input); return;} // Check if user wants to cancel

        // Prompt user for course ID
        System.out.print("Course ID: ");
        String courseID = input.nextLine();
        if (courseID.equals("0")) {courseManagement(input);return;} // Check if user wants to cancel

        // Prompt user for course credit hours and validate input
        double creditHours = 1.0;
        while (true) {
            System.out.print("Credit hours: ");
            try {
                creditHours = input.nextDouble(); 
                if (creditHours == 0) {input.nextLine();courseManagement(input); return;} // Check if user wants to cancel
                input.nextLine(); // Consume the newline character
                break; // Exit the loop if age input is successful
            } catch (Exception e) {
                input.nextLine(); // Clear the input buffer
                System.out.println("\nPlease enter a credit (eg. 1.0)");
            }
        }

        // prompt user to set course type
        int courseType;
        System.out.print("\nCOURSE TYPE:\n" +
                "1. Major requirement\n" +
                "2. Elective\n" +
                "Select the course's type by entering the corresponding number: ");
        while (true) {
            try {
                courseType = input.nextInt();
                if (courseType == 0) {input.nextLine();courseManagement(input); return;} // Check if user wants to cancel
                if (courseType < 1 || courseType > 2){
                    throw new IllegalArgumentException();
                }
                input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e) {
                input.nextLine(); // Clear the input buffer
                System.out.print("Please enter a valid course type number: ");
            }
        }
        String courseTypeName = null;
        switch (courseType){
            case 1:
                courseTypeName = "Major requirement";
                break;
            case 2:
                courseTypeName = "Elective";
                break;
        }

        // Prompt user for course department
        int courseDepartment;
        System.out.print("\nCOURSE DEPARTMENT:\n" +
                "1. Engineering\n" +
                "2. Computer Science & Information Systems\n" +
                "3. Economics & Business Administration\n" +
                "4. Humanities & Social Sciences\n" +
                "Select the course's department by entering the corresponding number: ");
        while (true) {
            try {
                courseDepartment = input.nextInt(); 
                if (courseDepartment == 0) {input.nextLine();courseManagement(input); return;} // Check if user wants to cancel
                if (courseDepartment < 1 || courseDepartment > 4){
                    throw new IllegalArgumentException();
                } input.nextLine(); // Consume the newline character
                break;
            } catch (Exception e) {
                input.nextLine(); // Clear the input buffer
                System.out.print("Please enter a valid course department number: ");
            }
        }
        String courseDepartmentName = null;
        switch (courseDepartment){
            case 1:
                courseDepartmentName = "Engineering";
                break;
            case 2:
                courseDepartmentName = "Computer Science & Information Systems";
                break;
            case 3:
                courseDepartmentName = "Economics & Business Management";
                break;
            case 4:
                courseDepartmentName = "Humanities & Social Sciences";
                break;
        }

        // create a new course object in the data manager
        Course course = new Course(courseID,courseName,creditHours,courseTypeName,courseDepartmentName);

        // Store the course object in the data manager
        DataManager.storeAcademicEntity(course);
        System.out.println("\nCourse Enrolled Successfully");
        courseManagement(input); return;
    }

    /**
     * Displays information about a specific course.
     *
     * @param input    Scanner object for user input
     * @param course   Course object containing course information
     * @param courseID ID of the course
     */
    public static void viewCourseInformation(Scanner input, Course course, String courseID){
        System.out.println("\nVIEW COURSE INFORMATION");
        System.out.println(course);
        printExistingCourse(input, course, courseID);return;
    }

    /**
     * Allows the user to remove a course.
     *
     * @param input    Scanner object for user input
     * @param course   Course object representing the course to be removed
     * @param courseID ID of the course
     */
    public static void removeCourse(Scanner input, Course course, String courseID){
        System.out.println("\nREMOVE COURSE\n");

        while (true){
            System.out.print("Are you sure you want to remove course (y / n): ");
            String removeCourse = input.nextLine().toLowerCase();
            if (removeCourse.equals("y")){
                DataManager.removeAcademicEntity(course);
                System.out.println("Course removed");
                break;
            } else if (removeCourse.equals("n")){
                System.out.println("Course not removed");
                break;
            } else {
                System.out.println("Invalid input. Enter 'y' or 'n' ");
            }
        }
        printExistingCourse(input, course, courseID);
        return;
    }

    /**
     * Displays the list of students enrolled in a course.
     *
     * @param input    Scanner object for user input
     * @param course   Course object representing the course
     * @param courseID ID of the course
     */
    public static void viewEnrolledStudents(Scanner input, Course course, String courseID){
        System.out.println("\nVIEW ENROLLED STUDENTS");

        ArrayList<Student> studentsList = course.getEnrolledStudents();
        if (studentsList == null || studentsList.isEmpty()){
            System.out.println("No students enrolled in this course");
        }else {
            System.out.println("Enrolled students:");
            System.out.println(".............................");
            for (Student student: studentsList){
                System.out.println("ID: "+student.getID() + ", Name: " +student.getName() );
            }
        }
        printExistingCourse(input, course, courseID);
        return;
    }
    
    /**
     * Displays the list of faculty assigned to teach a course.
     *
     * @param input    Scanner object for user input
     * @param course   Course object representing the course
     * @param courseID ID of the course
     */
    public static void viewAssignedFaculty(Scanner input, Course course, String courseID){
        System.out.println("\nVIEW ASSIGNED FACULTY");

        ArrayList<Faculty> facultyArrayList =  course.getAssignedFaculty();

        if (facultyArrayList == null || facultyArrayList.isEmpty()){
            System.out.println("No assigned faculty");
            printExistingCourse(input, course, courseID);
            return;
        }

        for (Faculty faculty : facultyArrayList){
            System.out.println(faculty);
        }
        printExistingCourse(input, course, courseID);
        return;
    }
    
    /**
     * Displays information about all courses.
     *
     * @param input Scanner object for user input
     */
        public static void viewAllCourses(Scanner input){
        List<AcademicEntity> courseList =  DataManager.retrieveAcademicEntities(DataManager.COURSE_FILE);
        if (courseList == null || courseList.isEmpty()){
            System.out.println("No courses enrolled");
            courseManagement(input);
            return;
        }
        System.out.println("COURSES");
        System.out.println(".....................");
        for (AcademicEntity course : courseList){
            Course course1 = (Course) course;
            System.out.println("ID: " + course1.getID() +" , Name: " + course1.getName());
        }
        courseManagement(input);
        return;

    }

}