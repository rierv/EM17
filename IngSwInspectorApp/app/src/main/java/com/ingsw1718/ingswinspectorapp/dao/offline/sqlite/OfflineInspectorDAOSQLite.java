package com.ingsw1718.ingswinspectorapp.dao.offline.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ingsw1718.ingswinspectorapp.common.Date;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineInspectorDAO;
import com.ingsw1718.ingswinspectorapp.entity.TicketInspector;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class OfflineInspectorDAOSQLite extends OfflineInspectorDAO {

    private Context context;

    /**
     * Default constructor
     */
    public OfflineInspectorDAOSQLite(Context context) {
        this.context = context;
    }


    /**
     * @param inspector
     */
    public void addInspector(TicketInspector inspector) {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getWritableDatabase();

        ContentValues inspectorValues = new ContentValues();
        inspectorValues.put(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_ID, inspector.getInspectorID());
        inspectorValues.put(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_NAME, inspector.getName());
        inspectorValues.put(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_SURNAME, inspector.getSurname());
        inspectorValues.put(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_BIRTHDATE, inspector.getBirthDate().toString(Date.DATE_PATTERN_DDMMYYYY));
        inspectorValues.put(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_IDENTIFICATOR, inspector.getIdentificatorID());

        db.insert(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.TABLE_NAME, null, inspectorValues);
    }

    /**
     * @return
     */
    public List<TicketInspector> extractAllInspectors() {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getReadableDatabase();
        String[] projection = { OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_ID,                         OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_NAME,
                                OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_SURNAME,                    OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_BIRTHDATE,
                                OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_IDENTIFICATOR};
        Cursor cursor = db.query(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.TABLE_NAME, //The table to query
                projection, //The columns to return
                null, //The columns for the where clause
                null, //The values for the where clause
                null, //Group by
                null, //Having
                null); //Order by
        List<TicketInspector> inspectors = new ArrayList<>();
        while(cursor.moveToNext()) {
            String inspectorID = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_NAME));
            String surname = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_SURNAME));
            String birthDate = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_BIRTHDATE));
            String identificatorID = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.COLUMN_IDENTIFICATOR));
            System.out.println("IDENTIFICATOR ID = " + identificatorID);
            TicketInspector inspector = new TicketInspector(inspectorID, name, surname, Date.parse(birthDate), identificatorID);
            inspectors.add(inspector);
        }
        cursor.close();
        db.delete(OfflineDatabaseSchemaSQLite.TicketInspectorEntry.TABLE_NAME, null, null);
        return inspectors;
    }

}