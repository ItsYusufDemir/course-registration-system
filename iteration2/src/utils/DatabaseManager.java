package iteration2.src.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import iteration2.src.models.Admin;
import iteration2.src.models.Advisor;
import iteration2.src.models.Constraint;
import iteration2.src.models.Course;
import iteration2.src.models.Student;
import iteration2.src.models.Transcript;

public class DatabaseManager {

    private ObjectMapper objectMapper = null;
    private static DatabaseManager instance = null;
    private List<Course> courseList;
    private List<Student> studentList;
    private List<Advisor> advisorList;
    private List<Constraint> constraints;
    private List<Admin> adminList;


    //Singleton pattern
    public static DatabaseManager getInstance() {
        if (instance 
        == null) {
            instance = new DatabaseManager();
            instance.initialize();
        }
        return instance;
    }

    private void initialize() {

        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        //Read the JSON files and convert them to list of objects
        courseList = jsonToCourseList(readFile("iteration2/data/courses.json"));
        advisorList = jsonToAdvisorList(readFile("iteration2/data/advisors.json"));
        studentList = jsonToStudentList(readFile("iteration2/data/students.json"));
        constraints = jsonToConstraints(readFile("iteration2/data/constraints.json"));
        adminList = jsonToAdminList(readFile("iteration2/data/admins.json"));
    }


    private String readFile(String relativePath) {

        String filePath = relativePath;
        Path path = Paths.get(filePath);

        try {
            String jsonContent = Files.lines(path, Charset.forName("UTF8")).collect(Collectors.joining("\n"));
            return jsonContent;
        } catch (Exception e) {
            //TODO: handle exception   
            System.out.println("File not found!");
            System.out.println(e);
            System.exit(0);
            return "";
        }

    }
    

    private List<Student> jsonToStudentList(String jsonString) {
        
        try {
            List<Student> students = objectMapper.readValue(jsonString, new TypeReference<List<Student>>() {});
            for(Student student: students){
                for(Advisor advisor: advisorList){
                    if(Objects.equals(student.getAdvisorOfStudent().getUserId(), advisor.getUserId())){
                        student.setAdvisorOfStudent(advisor);
                    }
                }
            }

            return students;

        } catch (IOException e) {
            System.out.println("Error while converting JSON String to List of objects!" + jsonString);
            e.printStackTrace();
            return Collections.emptyList(); // or throw an exception if needed
        }        
    }

    private List<Advisor> jsonToAdvisorList(String jsonString) {
        
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<Advisor>>() {});
        } catch (IOException e) {
            System.out.println("Error while converting JSON String to List of objects!");
            e.printStackTrace();
            return Collections.emptyList(); // or throw an exception if needed
        }        
    }
    private List<Course> jsonToCourseList(String jsonString) {
        
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<Course>>() {});
        } catch (IOException e) {
            System.out.println("Error while converting JSON String to List of objects!");
            e.printStackTrace();
            return Collections.emptyList(); // or throw an exception if needed
        }        
    }

    private List<Constraint> jsonToConstraints(String jsonString){
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<Constraint>>() {});
        } catch (IOException e) {
            System.out.println("Error while converting JSON String to List of objects!");
            e.printStackTrace();
            return Collections.emptyList(); // or throw an exception if needed
        }
    }


    private List<Admin> jsonToAdminList(String jsonString){
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<Admin>>() {});
        } catch (IOException e) {
            System.out.println("Error while converting JSON String to List of objects!");
            e.printStackTrace();
            return Collections.emptyList(); // or throw an exception if needed
        }
    }



    //Convert the list of objects to JSON String
    private <T> String getJsonString(List<T> list) {

        String jsonString = "[]";
        try {
            jsonString = objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error while converting object to JSON String!");
            System.out.println(e);
        }

        return jsonString;
    }

    private static void writeFile(String relativePath, String jsonString) {
        
        try (FileWriter writer = new FileWriter(relativePath)) {  
            
            writer.write(jsonString);

        } catch (IOException e) {
            //TODO: handle exception
            System.out.println("Error while writing to file!");
        }
    }

    
    //Save all instances to the database
    public void saveToDatabase() {
        //writeFile("data/courses.json", getJsonString(courseList));
        writeFile("iteration2/data/students.json", getJsonString(studentList));
        writeFile("iteration2/data/advisors.json", getJsonString(advisorList));
        writeFile("iteration2/data/courses.json", getJsonString(courseList));
        writeFile("iteration2/data/constraints.json", getJsonString(constraints));

        saveTranscriptsToDatabase(); //Save transcripts to database
    }    

    private void saveTranscriptsToDatabase() {

        for(int i = 0; i < studentList.size(); i++){
            List<Transcript> transcript = new ArrayList<Transcript>();
            transcript.add(studentList.get(i).getTranscript());
            
            writeFile("iteration2/data/transcripts/" + studentList.get(i).getUserId() + ".json", getJsonString(transcript));
        }
    }

    //Get students of an advisor
    public List<Student> fetchAdvisedStudents(Advisor advisor) {

        List<Student> studentsOfAdvisor = new ArrayList<Student>();
         for (int i = 0; i < studentList.size(); i++) {
            if(Objects.equals(studentList.get(i).getAdvisorOfStudent().getUserId(), advisor.getUserId())){
                studentsOfAdvisor.add(studentList.get(i));
            }
        }
        return studentsOfAdvisor;
    }

    //Getters
    public List<Course> getCourses() {
        return courseList;
    }

    public List<Student> getStudents() {
        return studentList;
    }

    public List<Advisor> getAdvisors() {
        return advisorList;
    }

    public List<Admin> getAdmins() {
        return adminList;
    }

    public HashMap<Integer,String> getConstraints(){
        Constraint constraint = constraints.get(0);
        HashMap<Integer,String> constraintsMap = new HashMap<Integer,String>();
        constraintsMap.put(1, String.valueOf(constraint.getMaxNumberOfCoursesStudentTake()));
        constraintsMap.put(2, String.valueOf(constraint.getAddDropWeek()));
        constraintsMap.put(3, String.valueOf(constraint.getMinRequiredECTSForTermProject()));
        
        return constraintsMap;

    }
    
    public boolean editConstraint(HashMap<Integer, String> editedAttributes){
        constraints.get(0).editConstraint(editedAttributes);
        return true;
    }

    public Course findCourseByCourseCode(String courseCode) {
        for (Course course : courseList) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    

}
