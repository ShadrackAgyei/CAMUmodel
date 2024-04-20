import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Transcript {
    private String studentID;
    // private ArrayList<Character> gradesList; ...I took this out since we needed the
    // course-grades grades and not just an array of grades.

    private HashMap<Course, String> gradePairs; //i added this datafield to store the courses
    // and the corresponding grades, i'm assuming it will take it from the fileManager?
    private double GPA;
    public static final Pattern gradePattern = Pattern.compile("^([ABCD][+]?|[EF])$");

    //also initialised the gradesMap as a datafield so that we just initial it once, while giving it the largest scope,

    private static final HashMap<String, Double> gradesMap = new HashMap<>();
    static {
        gradesMap.put("A+", 4.0);
        gradesMap.put("A", 4.0);
        gradesMap.put("B+", 3.5);
        gradesMap.put("B", 3.0);
        gradesMap.put("C+", 2.5);
        gradesMap.put("C", 2.0);
        gradesMap.put("D+", 1.5);
        gradesMap.put("D", 1.0);
        gradesMap.put("E", 0.5);
        gradesMap.put("F", 0.0);
    }


    // Constructors
    public Transcript(){
        this.gradePairs = new HashMap<>();
    }

    public Transcript(String studentID){
        this.studentID = studentID;
        this.gradePairs = new HashMap<>();
    }

    public void displayTranscript(String studentID){
        Student student = DataManager.retrieveStudent(studentID);
        //System.out.println("STUDENT TRANSCRIPT");
        // System.out.println(student);  //in place of this, i just felt would be better just
        // printing the student details using accessor methods

        int yearGroup = student.getYearGroup();
        String yearGroupName = "";
        switch (yearGroup){
            case 1:
                yearGroupName = "Freshman";
                break;
            case 2:
                yearGroupName = "Sophomore";
                break;
            case 3:
                yearGroupName = "Junior";
            case 4:
                yearGroupName = "Senior";
                break;
        }

        System.out.println();
        System.out.println("-----STUDENT TRANSCRIPT-----");
        System.out.println("Student ID:  " + student.getID());
        System.out.println("Name:        " + student.getName());
        System.out.println("Major:       " + student.getMajor());
        System.out.println("Year Group:  " + yearGroupName);
        System.out.println("--------------------------------");
        System.out.println("Course               Credit\tGrade");
        System.out.println("--------------------------------");

        gradePairs = student.getCourseGradePair();

        for (Course course : student.getRegisteredCourses()) {
            String grade = gradePairs.get(course);
            if (course.getName().contains("Leadership")){
                course.setCreditHours(0.5);
            }
            System.out.printf("%-20s %.1f   \t%s\n",course.getName(), course.getCreditHours(),grade);
        }

        double gpa = calculateGPA(gradePairs);
        student.setGpa(gpa);

        System.out.println("--------------------------------");
        System.out.println("GPA: " + String.format("%.2f", gpa));
        System.out.println("Total Credits Earned: " + student.getCreditsToDate()); //we can also create a
        // getTotalCredits(gradePairs) method that gets the total credits of the student based on the courses
        // they take. but we need to ensure that the gradePairs hashmap is always updated.
        System.out.println("--------------------------------");

    }


    //i created a convertGradeToPoint to help convert the letter grade to the corresponding grade point.
    private double convertGradeToPoint(String grade) {
        return gradesMap.get(grade);
    }

    public double calculateGPA(HashMap<Course, String> gradePairs) {
        double totalGradePoints = 0;
        double totalCreditHours = 0;

        for (Course course : gradePairs.keySet()) {
            String grade = gradePairs.get(course);
            double gradePoint = convertGradeToPoint(grade);
            totalGradePoints += gradePoint * course.getCreditHours();
            totalCreditHours += course.getCreditHours();
        }
        GPA = totalGradePoints / totalCreditHours;
        return GPA;
    }


    // //Calculate CGPA   ...This assuming we have a hashmap with
    // public double calculateCGPA(HashMap<Course, String> cumulativegradePairs) {
    //     return calculateGPA(cumulativegradePairs);
    // }

    public Course[] listCoursesTaken(Student student){
        ArrayList<Course> registeredCourses= student.getRegisteredCourses();
        Course[] regCourses= new Course[registeredCourses.size()];
        registeredCourses.toArray(regCourses); // Add couses in the arraylist to the regCourses array
        return regCourses;
    }

    public void addCourseGrade(Course course, String studentID, HashMap<Course, String> gradePairs, String grade) {
        gradePairs.put(course, grade);
    }
}