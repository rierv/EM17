/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.database;

import java.sql.ResultSet;

/**
 *
 * @author Raffolox
 */
public interface DatabaseManager {

    public void connect();

    public void disconnect();

    public boolean isConnected();

    public ResultSet query(String statement);

    public void update(String statement);

    public void startTransaction();

    public void commitTransaction();

    public void rollbackToSafeState();
}
