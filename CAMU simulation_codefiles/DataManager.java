import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

/**
 * DataManager class handles storage, retrieval, and removal of Student, Faculty and Course (Academic Entity) objects in files.
 */
public class DataManager {
    // File names for storing data
    protected static final String STUDENT_FILE = "students.dat";
    protected static final String FACULTY_FILE = "faculty.dat";
    protected static final String COURSE_FILE = "course.dat";
    private static final String COURSE_INFO_FILE = "coursesInfo.txt";

    /**
     * Stores an Academic Entity object into the file.
     * @param entity The Academic Entity object to be stored
     */
    public static void storeAcademicEntity(AcademicEntity entity) {
        String filename = "";
        if (entity instanceof Student){
            filename = STUDENT_FILE;
        } else if (entity instanceof Faculty){
            filename = FACULTY_FILE;
        } else if (entity instanceof Course){
            filename = COURSE_FILE;
        }

        List<AcademicEntity> entities = retrieveAcademicEntities(filename);
        if (entities.isEmpty()){
            //Add new entity
            entities.add(entity);
            storeAcademicEntities(entities, filename);
            return;
        }

        for (AcademicEntity a : entities) {
            //System.out.println("checking id: "+a.getID());
            if (a.getID() == null){
                break;
            }
            else if (a.getID().equals(entity.getID())) {
                if (a.getName().equals(entity.getName())) {
                    // Update existing record
                    entities.set(entities.indexOf(a), entity);
                    storeAcademicEntities(entities, filename);
                    return;
                } else {
                    // An entity with the same ID already exists
                    System.out.println("This ID already exists." + a.getID());
                    return;
                }
            }
        }

        //Add new entity
        entities.add(entity);
        storeAcademicEntities(entities, filename);
    }
    public static void storeFacultyEntity(Faculty entity) {

        String filename = FACULTY_FILE;

        List<AcademicEntity> entities = retrieveAcademicEntities(filename);
        if (entities.isEmpty()){
            System.out.println("empty file");
            //Add new entity
            entities.add(entity);
            storeAcademicEntities(entities, filename);
            return;
        }

        for (AcademicEntity a : entities) {
            System.out.println("checking id: "+a.getID());
            if (a.getID() == null){
                break;
            }
            else if (a.getID().equals(entity.getID())) {
                if (a.getName().equals(entity.getName())) {
                    // Update existing record
                    entities.set(entities.indexOf(a), entity);
                    storeAcademicEntities(entities, filename);
                    return;
                } else {
                    // An entity with the same ID already exists
                    System.out.println("This ID already exists.");
                    return;
                }
            }
        }

        //Add new entity
        entities.add(entity);
        storeAcademicEntities(entities, filename);
    }


    /**
     * Removes an Academic Entity object from the file given the object.
     * @param entity The Academic Entity object to be removed
     */
    public static void removeAcademicEntity(AcademicEntity entity) {

        String filename = "";
        if (entity instanceof Student){
            filename = STUDENT_FILE;
        } else if (entity instanceof Faculty){
            filename = FACULTY_FILE;
        } else if (entity instanceof Course){
            filename = COURSE_FILE;
        }

        List<AcademicEntity> entities = retrieveAcademicEntities(filename);
        
        storeAcademicEntities(entities, filename);

        for (AcademicEntity a : entities) {
            if (a.getID().equals(entity.getID())) {
                if (a.getName().equals(entity.getName())) {
                    // Update existing record
                    entities.remove(a);
                    storeAcademicEntities(entities, filename);
                    return;
                }
            }
        }
    }

    /**
     * Stores the list of Academic Entities into the file.
     * @param entities The list of Academic Entity objects to be stored
     */
    private static void storeAcademicEntities(List<AcademicEntity> entities, String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(entities);
        } catch (Exception e) {
            // Handle Exceptions
        }
    }

    /**
     * Retrieves all Academic Entity objects from the file.
     * @return List of Academic Entity objects
     */
    public static List<AcademicEntity> retrieveAcademicEntities(String filename) {
        List<AcademicEntity> entities = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            entities = (List<AcademicEntity>) inputStream.readObject();
        } catch (Exception e) {
            // Handle exception
        }
        return entities;
    }

    /**
     * Retrieves a Student object from the list given the student ID.
     * @param studentID The ID of the student to retrieve
     * @return The Student object if found, otherwise null
     */
    public static Student retrieveStudent(String studentID) {
        List<AcademicEntity> students = retrieveAcademicEntities(STUDENT_FILE);

        if (students == null || students.isEmpty()) {
            System.out.println("No student data available.");
            return null;
        }

        for (AcademicEntity s : students) {
            if (s.getID().equals(studentID)) {
                return (Student) s;
            }
        } return null;
    }

    /**
     * Retrieves a Faculty object from the list given the Faculty ID.
     * @param facultyID The ID of the faculty to retrieve
     * @return The Faculty object if found, otherwise null
     */
    public static Faculty retrieveFaculty(String facultyID) {
        List<AcademicEntity> faculties = retrieveAcademicEntities(FACULTY_FILE);

        // check if file is empty so you don't even need to look through it
        if (faculties == null || faculties.isEmpty()) {
            System.out.println("No faculty data available.");
            return null;
        }

        for (AcademicEntity f : faculties) {
            if (f.getID() == null)continue;
            if (f.getID().equals(facultyID)) {
                return (Faculty) f;
            }
        } return null;
    }

    /**
     * Retrieves a Course object from the list given the Course ID.
     * @param courseID The ID of the course to retrieve
     * @return The Course object if found, otherwise null
     */
    public static Course retrieveCourse(String courseID) {
        List<AcademicEntity> courses = retrieveAcademicEntities(COURSE_FILE);
        for (AcademicEntity c : courses) {
            if (c.getID().equals(courseID)) {
                return (Course) c;
            } 
        } return null;
    }

    public static HashMap<String, String> retrieveCourseInfo(String major){
        HashMap<String,String> majorDepartmentMap = new HashMap<>();
        majorDepartmentMap.put("Humanities","Humanities & Social Sciences");
        majorDepartmentMap.put("Economics","Economics & Business Administration");
        majorDepartmentMap.put("Computer Science","Computer Science & Information Systems");
        majorDepartmentMap.put("Mechanical Engineering","Engineering");
        majorDepartmentMap.put("Computer Engineering","Engineering");
        majorDepartmentMap.put("Electrical Engineering","Engineering");

        HashMap<String,String> courseDictionary = new HashMap<>();
        List<AcademicEntity> entities = retrieveAcademicEntities(COURSE_FILE);

        if (entities == null){
            System.out.println("No courses created.");
            return null;
        }
        String desiredDepartment = majorDepartmentMap.get(major);

        for (AcademicEntity course : entities){
            Course course1 = (Course) course;

            if (major.equals("Computer Engineering")){
                if (course1.getDepartment().equals("Computer Science & Information Systems")
                        || course1.getDepartment().equals("Engineering") || course1.getDepartment().equals("Humanities & Social Sciences")){
                    String courseName = course1.getName();
                    String courseID = course1.getID();
                    courseDictionary.put(courseID,courseName);
                }
            } else if (course1.getDepartment().equals(desiredDepartment) || course1.getDepartment().equals("Humanities & Social Sciences")){
                String courseName = course1.getName();
                String courseID = course1.getID();
                courseDictionary.put(courseID,courseName);
            }
        }
        return courseDictionary;
    }

    public static HashMap<String, String> retrieveFacultyCourseInfo(String department){

        HashMap<String,String> courseDictionary = new HashMap<>();
        List<AcademicEntity> entities = retrieveAcademicEntities(COURSE_FILE);

        if (entities == null){
            System.out.println("No courses created.");
            return null;
        }
        String desiredDepartment = department;

        for (AcademicEntity course : entities){
            Course course1 = (Course) course;

        if (course1.getDepartment().equals(desiredDepartment)){
            String courseName = course1.getName();
            String courseID = course1.getID();
            courseDictionary.put(courseID,courseName);
        }
        }
        return courseDictionary;
    }

}