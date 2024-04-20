import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        HashMap<String,String> courseDictionary = new HashMap<>();
        //File file = new File("coursesInfo.txt");
        String major = "Computer Engineering";


        boolean foundMajor = false; // This flag indicates when the major has been found


        //File file = new File();

        /*try(Scanner reader = new Scanner(file)) {
            String majorName = "";
            while (reader.hasNextLine()){
                majorName = reader.nextLine();
                if (majorName.equals(major)) {
                    break;
                }
            }

            while (true){
                String line = reader.nextLine();
                if (line.equals("end")) break;
                String[] courseArray = line.split(",");
                courseDictionary.put(courseArray[0], courseArray[1]);
            }

        }catch (FileNotFoundException e){
            //handle
        }*/
        //System.out.println(courseDictionary);
        File courseInfoFile = new File("coursesInfo.txt");
        /*try(Scanner readfile = new Scanner(courseInfoFile)) {
            while (readfile.hasNextLine()){
                String line = readfile.nextLine();
                if (line.contains(",")){
                    String[] courseInfo = line.split(",");
                    Course course = new Course(courseInfo[0],courseInfo[1]);
                    System.out.println(courseInfo[0] + " " + courseInfo[1]);
                    //DataManager.storeAcademicEntity(course);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        }*/

        CAMUapplication.createCoursesFromTextInfo("coursesInfo.txt");
        Course course = DataManager.retrieveCourse("CS 213");
        //System.out.println(course);

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("course.dat"))) {
            // Read the object as it was written. If it's a List, cast to List
            List<Course> courses = (List<Course>) ois.readObject();

            // Display each course in the list
            for (Course course2 : courses) {
               System.out.println(course2); // Assuming Course class has a sensible toString() method
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Student s1 = new Student("49792026","Baba",25,'M',1,"Computer engineering");
        s1.registerCourse(course);
        ArrayList<Course> corses = s1.getRegisteredCourses();
        //System.out.println(corses);

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("faculty.dat"))) {
            // Read the object as it was written. If it's a List, cast to List
            List<Faculty> faculty = (List<Faculty>) ois.readObject();

            // Display each course in the list
            for (Faculty faculty1 : faculty) {
                //System.out.println(faculty1); // Assuming Course class has a sensible toString() method
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AcademicEntity course2 = new AcademicEntity() {};

    }

}