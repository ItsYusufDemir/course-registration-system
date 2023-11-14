import java.util.List;

public class AuthenticateService {
    
    public User authenticateUser(String username, String password){
        
        DatabaseManager database = DatabaseManager.getInstance();
        List <User> users = null; 
        users = database.getUsers();

        if(users == null){
            return null;
        }
        else{

            for(User user : users){
                
                if(user.getPassword().equals(password) && user.getUsername().equals(username)){
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
