import java.util.ArrayList;
import java.io.Serializable;
import java.util.Objects;

public class Course extends AcademicEntity implements Serializable{
    private String ID;
    private String courseName;
    private double creditHours;
    private String courseType;
    private String department;
    private ArrayList<Student> enrolledStudents;
    private ArrayList<Faculty> assignedFaculty;
    private ArrayList<String> assignedFacultyIntern;

    //CONSTRUCTORS
    public Course() {}

    // Assuming Students are not yet enrolled into the course
    public Course(String ID, String name, double creditHours, String courseType,
                    ArrayList<Faculty> assignedFaculty, ArrayList<String> assignedFacultyIntern){
        super();
        this.ID = ID;
        this.courseName = name;
        this.creditHours = creditHours;
        this.courseType = courseType;
        this.assignedFaculty = assignedFaculty;
        this.assignedFacultyIntern = assignedFacultyIntern;
        this.enrolledStudents = new ArrayList<Student>();
    }
    public Course(String ID, String courseName,double creditHours, String courseType, String department) {
        super();
        this.ID = ID;
        this.courseName = courseName;
        this.creditHours = creditHours; // Default for a double field
        this.courseType = courseType; // Default boolean value
        this.department = department;
        this.assignedFaculty = new ArrayList<>();
        this.assignedFacultyIntern = new ArrayList<>();
        this.enrolledStudents = new ArrayList<Student>();
    }

    // Getters
    public String getName(){return this.courseName;}
    public String getID(){return this.ID;}
    public double getCreditHours() {
        return creditHours;
    }

    public String getDepartment(){return department;}
    public String getCourseType(){return courseType;}

    public ArrayList<Faculty> getAssignedFaculty() {
        return assignedFaculty;
    }

    public ArrayList<String> getAssignedFacultyIntern() {
        return assignedFacultyIntern;
    }

    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    // Setters
    public void setCreditHours(double creditHours) {
        this.creditHours = creditHours;
    }

    public void setDepartment(String department){this.department = department;}
    public void setCourseType(String courseType){this.courseType = courseType;}

    public void setAssignedFaculty(ArrayList<Faculty> assignedFaculty) {
        this.assignedFaculty = assignedFaculty;
    }

    public void setAssignedFacultyIntern(ArrayList<String> assignedFacultyIntern) {
        this.assignedFacultyIntern = assignedFacultyIntern;
    }

    // add enrolled students to list...change method name
    public void setEnrolledStudents(Student student) {
        enrolledStudents.add(student);
    }

    /**
     *This method handles assigning a faculty to a course
     *
     * @param faculty the faculty object that the user will like to assign to the course.
     * @return a boolean indicating whether or not the assignment was sucessful.
     */
    public void assignFaculty(Faculty faculty){
        if (this.assignedFaculty == null) {
            this.assignedFaculty = new ArrayList<>(); // Safeguard initialization
        }
        assignedFaculty.add(faculty);
    }

    /**
     *.....
     *This method handles removing a faculty from a course
     *
     * @param faculty the faculty object that the user will like to remove.
     * @return a boolean indicating wheather or not the removal was sucessful.
     *
     */
    public boolean removeFaculty(Faculty faculty){
        if (assignedFaculty.contains(faculty)){
            assignedFaculty.remove(faculty);
            return true;
        } return false;
    }

    /**
     *.....
     *This method handles removing a student from a course
     *
     * @param student the student object that the user will like to remove.
     * @return a boolean indicating wheather or not the removal was sucessful.
     *
     */
    public boolean removeStudent(Student student){
        if (enrolledStudents.contains(student)){
            enrolledStudents.remove(student);
            return true;
        } return false;
    }

    public String toString(){
        int longestWidth = 20;
        return String.format("%-" + longestWidth + "s %s\n" + "%-" + longestWidth + "s %s\n" + "%-" + longestWidth + "s %.2f\n" +
                          "%-" + longestWidth + "s %s" + "%-" + longestWidth + "s %s",
                "\nCourse ID:", this.getID(), "Course Name:", this.getName(), "Credit Hours:", this.getCreditHours()
                , "Course type:", this.getCourseType(), "\nDepartment: " , this.getDepartment()
        );
    }

    public boolean equals(Course course){
        return Objects.equals(this.getID(), course.getID());
    }
}