import java.util.ArrayList;
import java.io.Serializable;

public class Faculty extends AcademicEntity implements Serializable{
    private String ID;
    private String name;
    private int age;
    private char gender;
    private String nationality;
    private String department;
    private ArrayList<Course> coursesTaught;

    public Faculty(){
    }

    public Faculty(String ID, String name, int age, char gender, String nationality,String department){
        super();
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.department = department;
        this.coursesTaught = new ArrayList<Course>();
    }


    // Setters
    public void setAge(int age){
        this.age = age;
    }
    public void setGender(char gender){
        this.gender = gender;
    }
    public void setNationality(String nationality){
        this.nationality = nationality;
    }
    public void setDepartment(String department){this.department = department;}

    // Getters
    public String getID() {return this.ID;}
    public String getName() {return this.name;}
    public int getAge(){
        return this.age;
    }
    public char getGender(){
        return this.gender;
    }
    public String getNationality(){
        return this.nationality;
    }
    public String getDepartment(){return this.department;}
    public ArrayList<Course> getCoursesTaught(){
        return this.coursesTaught;
    }

    // Methods
    public void assignToCourse(Course course){
        /*if (!coursesTaught.contains(course)){
            coursesTaught.add(course);
            course.assignFaculty(this);
            return true;
        }
        return false;*/
        if (this.coursesTaught == null) {
            this.coursesTaught = new ArrayList<>(); // Safeguard initialization
        }
        coursesTaught.add(course);
        course.assignFaculty(this);
    }

    public void getInfo(){
        System.out.printf("Faculty name: %s\nFaculty ID: %s\nDepartment: %s",this.getName(),this.getID(),this.getDepartment());
        System.out.println("\nCOURSES TAUGHT:");
        for(Course course:coursesTaught){
            System.out.println(course.getID() + " " + course.getName());
        }
    }

    public boolean equals(Faculty faculty){
        // Check for null references
        if (this.getID() == null || this.getName() == null || faculty.getID() == null || faculty.getName() == null) {
            return false;
        }
        // Compare facultyID and name
        return this.getID().equals(faculty.getID()) && this.getName().equals(faculty.getName());
    }

    public String toString(){
        return "ID: " + getID() + "Name: " + getName();
    }
}