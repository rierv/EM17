package com.ingsw1718.ingswinspectorapp.dao.online;

import java.sql.ResultSet;
import java.util.*;public interface OnlineDatabaseManager {

    public void connect();

    public void disconnect();

    public boolean isConnected();

    public ResultSet query(String statement);

    public void update(String statement);

    public void beginTransaction();

    public void commitTransaction();

    public void rollbackToSafeState();
}

/**
 * 
 */
