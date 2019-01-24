package em17.database.mysql;


import em17.database.DatabaseManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DB Manager
 */
public class DatabaseManagerMySQL implements DatabaseManager {

    private static DatabaseManagerMySQL instance = null;
    private static Connection connection = null;
    private final String user;
    private final String password;
    private static final String DATABASE_NAME = "em17db";
    private static final String END_POINT = "em17db.ccitbqoejkbc.eu-west-1.rds.amazonaws.com";
    
    /**
     * Private constructor
     */
    private DatabaseManagerMySQL() {
        this.user = "gruppo3";
        this.password = "progettoem17";
        
    }
    /**
     * Connects to the database
     */
    public void connect() {
        String connectionString = "jdbc:mysql://" + END_POINT + ":3306/" + DATABASE_NAME + "?user="+ user +"&password="+ password;
        Connection conn = null;
        if(connection == null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
            }
            catch(ClassNotFoundException e){
                return;
            }
            try{
                conn = DriverManager.getConnection(connectionString);
            }
            catch(SQLException sqlex){
                System.out.println("Connessione fallita!");
                
            }
            DatabaseManagerMySQL.connection = conn;
        }
    }

    /**
     * Disconnects from the DB
     */
    public void disconnect() {
        if(connection != null){
            try{
                connection.close();
            }
            catch(SQLException sqlex){
                return;
            }
            connection = null;
        }
    }

    /**
     * @return
     */
    public boolean isConnected() {
        return connection!=null;
    }

    /**
     * @param statement 
     * @return
     */
    public ResultSet query(String statement) {
        System.out.println("Querying " + statement);
        ResultSet resultSet = null;
        if(isConnected()) {
            try {
                Statement stmn = connection.createStatement();
                resultSet = stmn.executeQuery(statement);
                return resultSet;
            }
            catch(SQLException sqlex){
                System.out.println(sqlex.getMessage());
            }
        }
        return resultSet;
    }

    /**
     * @param statement 
     * 
     */
    public void update(String statement) {
        System.out.println("Executing " + statement);
        if(isConnected()) {
            try {
                Statement stmn = connection.createStatement();
                stmn.executeUpdate(statement);
            }
            catch(SQLException sqlex){
                sqlex.printStackTrace();
            }
        }
    }

    /**
     * Starts a transaction
     */
    public void startTransaction() {
        String statement = "START TRANSACTION;";
        DatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * Commits a transaction
     */
    public void commitTransaction() {
        String statement = "COMMIT;";
        DatabaseManagerMySQL.getInstance().update(statement);
    }
    
    /**
     * Deletes a transaction
     */
    public void rollbackToSafeState(){
        String statement = "ROLLBACK;";
        DatabaseManagerMySQL.getInstance().update(statement);
    }
    
    /**
     * Returns the DatabaseManager's unique instance
     * @return 
     */    
    public static DatabaseManagerMySQL getInstance() {
        if (instance == null) {
            instance = new DatabaseManagerMySQL();
        }
        if (!instance.isConnected()) {
            instance.connect();
        }
        return instance;
    }
}