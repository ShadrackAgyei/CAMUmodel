import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;

public class Student extends AcademicEntity implements Serializable{
    private String ID;
    private String name;
    private int age;
    private char gender;
    private String nationality;
    private String major;
    private int yearGroup;
    private double creditsToDate;
    private double gpa;
    private ArrayList<Course> registeredCourses;
    HashMap<Course, String> courseGradePair;
    //Transcript transcript;

    // Constructors
    public Student(){}
    public Student(String ID, String name, int age, char gender, int yearGroup, String major){
        super();
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.yearGroup = yearGroup;
        this.major = major;
        this.registeredCourses = new ArrayList<>();
        this.courseGradePair = new HashMap<>();
    }
    public Student(String ID, String name, int age, char gender, int yearGroup, String major, String nationality){
        this(ID, name, age, gender, yearGroup, major);
        this.nationality = nationality;
    }
    
     // Setters
    public void setID(String ID){
        this.ID = ID;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setGender(char gender){
        this.gender = gender;
    }
    public void setNationality(String nationality){
        this.nationality = nationality;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setYearGroup(int yearGroup) {
        this.yearGroup = yearGroup;
    }
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    public void setCreditsToDate(double creditsToDate) {
        this.creditsToDate = creditsToDate;
    }

    // Getters
    public String getID(){
        return this.ID;
    }
    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
    public char getGender(){
        return this.gender;
    }
    public String getNationality(){
        return this.nationality;
    }
    public String getMajor() {
        return major;
    }
    public int getYearGroup() {
        return yearGroup;
    }
    public double getGpa() {
        return gpa;
    }
    public double getCreditsToDate() {
        creditsToDate = 0;
        for (Course course: registeredCourses){
            if (course.getName().contains("Leadership")){
                creditsToDate += 0.5;
            }else {
                creditsToDate += 1;
            }
        }
        return creditsToDate;
    }

    // Register course
    public void registerCourse(Course course){
        if (this.registeredCourses == null) {
            this.registeredCourses = new ArrayList<>(); // Safeguard initialization
        }
        registeredCourses.add(course);
    }
    // Drop course
    public void dropCourse(Course course){
        registeredCourses.remove(course);
    }

    // Get registered courses
    public ArrayList<Course> getRegisteredCourses(){
        return registeredCourses;
    }
    public HashMap<Course,String> getCourseGradePair(){return courseGradePair;}

    @Override
    public String toString(){
        int longestWidth = 15;
        return String.format(
                "%-" + longestWidth + "s %s\n" + "%-" + longestWidth + "s %s\n" + "%-" + longestWidth + "s %d\n" +
                        "%-" + longestWidth + "s %s\n" + "%-" + longestWidth + "s %s",
                "\nStudentID:", this.getID(), "Name:", this.getName(), "Year Group:", this.getYearGroup(), "Major:", this.getMajor(),
                "Nationality:", this.getNationality()
        );
    }

    public void setCourseGradePair(HashMap<Course, String> gradePairs) {this.courseGradePair=gradePairs;
    }
}