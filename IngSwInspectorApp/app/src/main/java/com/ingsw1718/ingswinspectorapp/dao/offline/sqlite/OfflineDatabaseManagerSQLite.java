package com.ingsw1718.ingswinspectorapp.dao.offline.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineDatabaseManager;

/**
 * Created by Raffolox on 17/02/2018.
 */

public class OfflineDatabaseManagerSQLite extends SQLiteOpenHelper implements OfflineDatabaseManager {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "IngSwInspectorDB.db";

    private static OfflineDatabaseManagerSQLite instance;
    private Context context;

    public static OfflineDatabaseManagerSQLite getInstance(Context context) {
        if ((instance != null && instance.context != context) || instance == null) {
            if (instance != null && instance.context != context) {
                instance.getWritableDatabase().close();
                instance.getReadableDatabase().close();
            }
            instance = new OfflineDatabaseManagerSQLite(context);
        }
        return instance;
    }

    private OfflineDatabaseManagerSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void beginTransaction() {
        this.getWritableDatabase().beginTransaction();
    }

    public void commitTransaction() {
        this.getWritableDatabase().setTransactionSuccessful();
        this.getWritableDatabase().endTransaction();
    }

    public void rollbackToSafeState() {
        this.getWritableDatabase().endTransaction();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OfflineDatabaseSchemaSQLite.SQL_CREATE_TABLE_IDENTIFICATOR);
        db.execSQL(OfflineDatabaseSchemaSQLite.SQL_CREATE_TABLE_TICKET_INSPECTOR);
        db.execSQL(OfflineDatabaseSchemaSQLite.SQL_CREATE_TABLE_TICKET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is to simply to discard the data and start over
        db.execSQL(OfflineDatabaseSchemaSQLite.SQL_DELETE_CONTROLLED_TICKETS);
        db.execSQL(OfflineDatabaseSchemaSQLite.SQL_DELETE_TABLE_TICKET_INSPECTOR);
        db.execSQL(OfflineDatabaseSchemaSQLite.SQL_DELETE_TABLE_IDENTIFICATOR);
    }
}
