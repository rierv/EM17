package em17.database;

import java.util.*;

/**
 * 
 */
public abstract class UserDAO {

    /**
     * Default constructor
     */
    public UserDAO() {
    }


    /**
     * @param email 
     * @param password 
     * @return
     */
    public abstract boolean exists(String email, String password);

    /**
     * @param email 
     * @return
     */
    public abstract String getUserNameFromEmail(String email);

}