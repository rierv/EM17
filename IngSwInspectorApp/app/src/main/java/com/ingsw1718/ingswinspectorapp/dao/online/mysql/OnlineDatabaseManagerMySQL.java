package com.ingsw1718.ingswinspectorapp.dao.online.mysql;

import android.os.StrictMode;

import com.ingsw1718.ingswinspectorapp.dao.online.OnlineDatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class OnlineDatabaseManagerMySQL implements OnlineDatabaseManager {

    private static OnlineDatabaseManagerMySQL instance = null;
    private static Connection connection = null;
    public static final String ONLINE_DATE_FORMAT = "yyyy-mm-dd";
    private final String user;
    private final String password;
    private static final String DATABASE_NAME = "em17db";
    private static final String END_POINT = "em17db.ccitbqoejkbc.eu-west-1.rds.amazonaws.com";

    /**
     * Private constructor
     */
    private OnlineDatabaseManagerMySQL() {
        this.user = "gruppo3";
        this.password = "progettoem17";

    }
    /**
     *
     */
    public void connect() {
        String connectionString = "jdbc:mysql://" + END_POINT + ":3306/" + DATABASE_NAME + "?user="+ user +"&password="+ password;
        Connection conn = null;
        if(connection == null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
                return;
            }
            try{
                conn = DriverManager.getConnection(connectionString);
            }
            catch(SQLException sqlex){
                sqlex.printStackTrace();
                System.out.println("Connessione fallita!");
            }
            OnlineDatabaseManagerMySQL.connection = conn;
        }
    }

    /**
     *
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlex) {
                return;
            }
            connection = null;
        }
    }

    /**
     * @return
     */
    public boolean isConnected() {
        System.out.println("Sono connesso? " + connection != null);
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
            Statement stmn;
            try{
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                stmn = connection.createStatement();
                resultSet = stmn.executeQuery(statement);
                System.out.println("statement eseguito");
                return resultSet;
            }
            catch(SQLException sqlex){
                System.out.println("EXCEPTION");
                sqlex.printStackTrace();
            }
        }
        else {
            System.out.println("ERROOOOOOOREEEEEEEE");
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
            Statement stmn;
            try{
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                stmn = connection.createStatement();
            }
            catch(SQLException sqlex){
                return;
            }
            try{
                stmn.executeUpdate(statement);
            }
            catch(SQLException sqlex){
                System.out.println(sqlex.getMessage());
            }
        }
    }

    /**
     */
    public void beginTransaction() {
        String statement = "START TRANSACTION;";
        OnlineDatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     */
    public void commitTransaction() {
        String statement = "COMMIT;";
        OnlineDatabaseManagerMySQL.getInstance().update(statement);
    }

    public void rollbackToSafeState(){
        String statement = "ROLLBACK;";
        OnlineDatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * Returns the DatabaseManager's unique instance
     * @return
     */
    public static OnlineDatabaseManagerMySQL getInstance() {
        if (instance == null || !instance.isConnected()) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            instance = new OnlineDatabaseManagerMySQL();
            instance.connect();
        }
        return instance;
    }

}