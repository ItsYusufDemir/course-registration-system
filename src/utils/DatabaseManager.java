package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import models.Advisor;
import models.Course;
import models.Student;
import models.User;

public class DatabaseManager  {

    private ObjectMapper objectMapper = null;
    private static DatabaseManager instance = null;
    private List<User> userList;
    private List<Course> courseList;
    private List<Student> studentList;
    private List<Advisor> advisorList;

    //Singleton pattern
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
            instance.initialize();
        }
        return instance;
    }

    private void initialize() {

        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        courseList = jsonToCOurseList(readFile("data/courses.json"));
        advisorList = jsonToAdvisorList(readFile("data/advisors.json"));
        studentList = jsonToStudentList(readFile("data/students.json"));

        
    }



    //TODO: This function is for testing, it can be deleted later
    public void test() {

        try {

            getJsonString(studentList);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("HATA" +e);
            // TODO: handle exception
        }

    }

    public String readFile(String relativePath) {

        String filePath = relativePath;
        Path path = Paths.get(filePath);

        try {
            String jsonContent = Files.lines(path).collect(Collectors.joining("\n"));
            return jsonContent;
        } catch (Exception e) {
            //TODO: handle exception   
            System.out.println("File not found!");
            return "";
        }

    }
    

    private List<Student> jsonToStudentList(String jsonString) {
        
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<Student>>() {});
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
    private List<Course> jsonToCOurseList(String jsonString) {
        
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<Course>>() {});
        } catch (IOException e) {
            System.out.println("Error while converting JSON String to List of objects!");
            e.printStackTrace();
            return Collections.emptyList(); // or throw an exception if needed
        }        
    }

    //Convert the list of objects to JSON String
    public <T> String getJsonString(List<T> testObjects) {

        String jsonString = "[]";
        try {
            jsonString = objectMapper.writeValueAsString(testObjects);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error while converting object to JSON String!");
            System.out.println(e);
        }

        return jsonString;
    }

    public static void writeFile(String relativePath, String jsonString) {
        
        try (FileWriter writer = new FileWriter(relativePath)) {  //relative path can be like: "data/Course.json"
            // Write the JSON string directly to the file
            writer.write(jsonString);

        } catch (IOException e) {
            //TODO: handle exception
            System.out.println("Error while writing to file!");
        }
    }

    
    //Save all instances to the database
    public void saveToDatabase() {
        writeFile("data/courses.json", getJsonString(courseList));
        writeFile("data/students.json", getJsonString(studentList));
        writeFile("data/advisors.json", getJsonString(advisorList));
    }    

    public List<Student> fetchAdvisedStudents(Advisor advisor) {
        return studentList.stream().filter(student -> student.getAdvisorOfStudent().getUserId().equals(advisor.getUserId())).collect(Collectors.toList());
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

     public List<User> getUsers() {
        return userList;
    }


    /*
    TODO:Do we need this? should we read transcripts from files?

    public Transcript getTranskript(String studentNumber) {
        
        String jsonString = readFile("data/transcripts/" + studentNumber + ".json");

        List<Transcript> transcriptList = jsonToList(jsonString, User.class);

        return transcriptList.get(0);
    }
    */


    



















}
