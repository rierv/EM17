package com.ingsw1718.ingswinspectorapp.dao.offline;

import android.content.Context;


public interface OfflineDatabaseManager {
    public void beginTransaction();

    public void commitTransaction();

    public void rollbackToSafeState();

    public void close();
}