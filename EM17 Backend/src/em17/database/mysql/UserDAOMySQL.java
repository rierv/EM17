package em17.database.mysql;


import em17.database.DBSchema;
import em17.database.UserDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a DAO object used to access Users in the underlying database
 */
public class UserDAOMySQL extends UserDAO {

    /**
     * Default constructor
     */
    public UserDAOMySQL() {
    }



    /**
     * @param email
     * @param password 
     * @return whether or not an user with the given email and password exists in the application
     */
    public boolean exists(String email, String password) {
        int counter;
        ResultSet resultSet;
        String statement = "SELECT COUNT(*) "
                         +" FROM "+ DBSchema.USER_TABLE
                         +" WHERE "+ DBSchema.USER_EMAIL_ADDRESS_COLUMN +"=\""+ email +"\" AND "+ DBSchema.USER_PASSWORD_COLUMN +"=\""+ password +"\";"; 
       
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.first();
                counter = resultSet.getInt(1);
                System.out.println("counter ottenuto "+counter);
                return counter!=0;
            }
            else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * @param email
     * @return the name of the user with the given email
     */
    public String getUserNameFromEmail(String email) {
        String username;
        ResultSet resultSet;
        String statement = "SELECT "+ DBSchema.USER_USERNAME_COLUMN
                         +" FROM "+ DBSchema.USER_TABLE
                         +" WHERE "+ DBSchema.USER_EMAIL_ADDRESS_COLUMN +"=\""+ email +"\";"; 
       
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.first();
                username = resultSet.getString(1);
                return username;
            }
            else {
                return "User";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}