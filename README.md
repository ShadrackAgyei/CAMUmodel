# CAMUmodel
Modeling The Campus Allocation and Management System (CAMU)  CAMU is a Java-based application designed to manage and facilitate various academic processes within an educational institution.  It provides comprehensive functionalities for managing students, faculty, and courses.
 Project Structure
	- AcademicEntity (Abstract Class)
	- Course (Class)
	- DataManager (Class)
	- Faculty (Class)
	- Student (Class)
	- Transcript (Class)
	- CAMUapplication (Main Class)

1. How to Compile the Program
	i. Open your command line interface (CLI).
	ii. Navigate to the directory containing the project files.
	iii. Compile the Java classes with the following command:

	  javac AcademicEntity.java Course.java DataManager.java Faculty.java Student.java Transcript.java CAMUapplication.java
2. How to Run the Program
	- To run the compiled application, use the command:

	  java CAMUapplication

 This will start the application, and you can interact with it via the command line interface.

3. Functionalities
	3.1. Student Management
	 - Enroll new students
	 - Update student information
	 - Remove students
	 - View students' course enrollments
 	 - Allocate courses to students
	 - Add grades
	 - View student transcripts

	3.2. Faculty Management
	 - Add new faculty members
	 - Update faculty information
	 - Remove faculty members
	 - View courses assigned to faculty
	 - Assign courses to faculty

	3.3. Course Management
	 - Add new courses
	 - View and remove existing courses
	 - View enrollment and faculty assigned to courses

	3.4. Data Management
	 - Perform Create, Read, Update, and Delete (CRUD) operations on students, faculty, and courses through the DataManager
