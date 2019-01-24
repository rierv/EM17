package em17.logic;


import em17.database.mysql.UserDAOMySQL;
import em17.gui.SceneManager;
import javafx.event.ActionEvent;

/**
 * Manages the user session
 */
public class SessionManager {

    /**
     * The unique existing instance of SessionManager
     */
    private static SessionManager instance = null;
    
    /**
     * The DAO object used to access the users informations
     */
    UserDAOMySQL userDAO = new UserDAOMySQL();
    
    /**
     * Default constructor
     */
    private SessionManager() {
        
    }
    
    /**
     * @return the unique instance of SessionManager
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * The active user's name
     */
    private String activeUserName = null;

    /**
     * Logs the user out of the application
     * @param event
     */
    public void logout(ActionEvent event) {
        activeUserName = null;
        showAuthenticationScreen(event);
    }

    /**
     * Shows the authentication screen
     * @param event
     */
    public void showAuthenticationScreen(ActionEvent event) {
        SceneManager.changeScene(event, "/em17/gui/authentication/AuthenticationScreen.fxml");
    }
    
    /**
     * Shows the main screen (OuterView with EventManagementScreen)
     * @param event
     */
    public void showMainScreen(ActionEvent event) {
        SceneManager.changeScene(event, "/em17/gui/OuterView.fxml");
    }

    /**
     * Tries to authenticate an user with the given email and password.
     * If the credentials are correct, authenticates it and goes to the OuterView screen.
     * Otherwise shows an error popup
     * @param email
     * @param password 
     * @param event 
     */
    public void authenticate(String email, String password, ActionEvent event) {
        if (userDAO.exists(email, password)) {
            String userName = userDAO.getUserNameFromEmail(email);
            setActiveUserName(userName);
        }
    }
    
    /**
     * @return whether or not there is an authenticated user
     */
    public boolean isUserAuthenticated() {
        return activeUserName != null;
    }

    /**
     * Sets the active user's name to the given one
     * @param userName 
     */
    public void setActiveUserName(String userName) {
        this.activeUserName = userName;
    }

    /**
     * @return the active user's name to the given one
     */
    public String getActiveUserName() {
        return activeUserName;
    }

}