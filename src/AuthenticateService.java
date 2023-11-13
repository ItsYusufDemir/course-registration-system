public class AuthenticateService {
    
    public User authenticateUser(String username, String password){
        
        DatabaseManager database = DatabaseManager.getInstance();
        User user = null;
        if(database.get(username) == null){

            return null;
        }
        else{
            database.get(username);
            user = (User)(database.get(username));
            
            if(user.getPassword().equals(password)){
                return user;
            }
            else{
                return null;

            }
        }

        
    }








}
