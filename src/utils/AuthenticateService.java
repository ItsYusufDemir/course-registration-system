package utils;
import java.util.List;

import models.User;

public class AuthenticateService {
    
    public User authenticateUser(String userID, String password){
        
        DatabaseManager database = DatabaseManager.getInstance();
        List <User> users = null; 
        users = database.getUsers();

        if(users == null){
            return null;
        }
        else{

            for(User user : users){
                
                if(user.getPassword().equals(password) && user.getUserId().equals(userID)){
                    return user;
                }
                else{
                    return null;

                }
            }
        }
        return null;

        
    }








}
