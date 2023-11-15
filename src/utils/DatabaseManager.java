package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


import interfaces.Saveable;
import models.Advisor;
import models.Course;
import models.Student;
import models.User;

import java.lang.reflect.Type;
import model.User;
import models.Course;
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
        }
        return instance;
    }


    private DatabaseManager() {
        //Read all files and store them in the memory
        userList = jsonToList(readFile("data/users.json"), User.class);
        courseList = jsonToList(readFile("data/courses.json"), Course.class);
        studentList = jsonToList(readFile("data/students.json"), Student.class);
        advisorList = jsonToList(readFile("data/advisors.json"), Advisor.class);

        objectMapper = new ObjectMapper();
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
        .allowIfSubType("User")
        .build();

        objectMapper.activateDefaultTyping(ptv,ObjectMapper.DefaultTyping.NON_FINAL);
    }


    private <T> List<T> readJsonFile(String absolutePath, Class<T> valueType) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(absolutePath));
            return objectMapper.readValue(jsonData, objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + filePath);
            return List.of(); // or handle the exception as needed
        }
    }

    /*
    public String readFile(String relativePath) {

        String filePath = relativePath;
        Path path = Paths.get(filePath);

        try {
            String jsonContent = Files.lines(path).collect(Collectors.joining("\n"));
            return jsonContent;
        } catch (Exception e) {
            //TODO: handle exception   
            System.out.println("File not found");
            return "";
        }
    */


    private <T> List<T> jsonToList(String jsonString, Class<T> typeClass) {

        //Define the type of the object
        Type type = TypeToken.getParameterized(List.class, typeClass).getType();

        //Convert the JSON String to a list of objects
        return new Gson().fromJson(jsonString, type);
    }


    public String getJsonString(List<Saveable> testObjects) {

        //Convert the list of objects to JSON String
        String jsonString = new Gson().toJson(testObjects);

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
        writeFile("data/users.json", getJsonString(userList));
        writeFile("data/courses.json", getJsonString(courseList));
        writeFile("data/students.json", getJsonString(studentList));
        writeFile("data/advisors.json", getJsonString(advisorList));
    }

    public List<Student> getStudentsOfAdvisor(String advisorID) {



    }

    //Getters
    public List<Course> getCourses() {
        return courseList;
    }

    public List<Student> getStudents() {
        return studentList;
    }

    public List<Advisor> getAdvisors(String fileName) {
        return advisorList;
    }

     public List<User> getUsers(String fileName) {
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
