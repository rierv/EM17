package com.ingsw1718.ingswinspectorapp.dao.offline.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineTicketDAO;
import com.ingsw1718.ingswinspectorapp.entity.Ticket;

import java.util.*;

/**
 * 
 */
public class OfflineTicketDAOSQLite extends OfflineTicketDAO {

    private Context context;

    /**
     * Default constructor
     */
    public OfflineTicketDAOSQLite(Context context) {
        this.context = context;
    }

    /**
     * @param tickets
     */
    public void addTickets(List<Ticket> tickets) {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getWritableDatabase();
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                ContentValues ticketValues = new ContentValues();
                ticketValues.put(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_CODE, ticket.getTicketCode());
                ticketValues.put(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_NAME, ticket.getHolderName());
                ticketValues.put(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_SURNAME, ticket.getHolderSurname());
                ticketValues.putNull(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR);
                db.insert(OfflineDatabaseSchemaSQLite.TicketEntry.TABLE_NAME, null, ticketValues);
            }
        }
    }

    /**
     * @param ticketCode 
     * @return
     */
    public boolean containsTicketWithCode(String ticketCode) {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getReadableDatabase();
        String[] projection = { OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_CODE };
        Cursor cursor = db.query(OfflineDatabaseSchemaSQLite.TicketEntry.TABLE_NAME, //The table to query
                projection, //The columns to return
                OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_CODE + " = ?", //The columns for the where clause
                new String[] {ticketCode}, //The values for the where clause
                null, //Group by
                null, //Having
                null); //Order by
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    /**
     * @param ticketCode 
     * @return
     */
    public String getTicketHolderToString(String ticketCode) {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getReadableDatabase();
        String[] projection = { OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_CODE, OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_NAME, OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_SURNAME };
        Cursor cursor = db.query(OfflineDatabaseSchemaSQLite.TicketEntry.TABLE_NAME, //The table to query
                projection, //The columns to return
                OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_CODE + " = ?", //The columns for the where clause
                new String[] {ticketCode}, //The values for the where clause
                null, //Group by
                null, //Having
                null); //Order by
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_NAME));
        String surname = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_SURNAME));
        cursor.close();
        return name + " " + surname;
    }

    /**
     * @param ticketCode 
     * @param inspectorId 
     * @return
     */
    public void controlTicket(String ticketCode, String inspectorId) {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getWritableDatabase();
        System.out.println("Controlling the ticket with code " + ticketCode + " using the inspector id " + inspectorId);

        ContentValues values = new ContentValues();
        values.put(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR, inspectorId);

        String selection = OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_CODE + " = ?";

        String[] selectionArgs = { ticketCode };

        db.update(OfflineDatabaseSchemaSQLite.TicketEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    /**
     * @return
     */
    public int getControlledTicketsAmount() {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getReadableDatabase();
        String[] projection = { OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_CODE };
        Cursor cursor = db.query(OfflineDatabaseSchemaSQLite.TicketEntry.TABLE_NAME, //The table to query
                projection, //The columns to return
                OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR + " IS NOT NULL OR " + OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR + " <> ?", //The columns for the where clause
                new String[] {""}, //The values for the where clause
                null, //Group by
                null, //Having
                null); //Order by
        if (cursor != null) {
            cursor.moveToFirst();
            int result = cursor.getCount();
            cursor.close();
            return result;
        }
        else {
            return 0;
        }
    }

    /**
     * @return
     */
    public List<Ticket> extractControlledTickets() {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getReadableDatabase();
        String[] projection = { OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_CODE, OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR,
                                OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_NAME, OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_SURNAME};
        Cursor cursor = db.query(OfflineDatabaseSchemaSQLite.TicketEntry.TABLE_NAME, //The table to query
                projection, //The columns to return
                OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR + " IS NOT NULL OR " + OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR + " <> ?", //The columns for the where clause
                new String[] {""}, //The values for the where clause
                null, //Group by
                null, //Having
                null); //Order by
        List<Ticket> tickets = new ArrayList<>();
        while(cursor.moveToNext()) {
            String ticketCode = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_CODE));
            String inspectorId = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR));
            String holderName = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_NAME));
            String holderSurname = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_HOLDER_SURNAME));
            Ticket ticket = new Ticket(ticketCode, inspectorId, holderName, holderSurname);
            tickets.add(ticket);
        }
        cursor.close();
        db.delete(OfflineDatabaseSchemaSQLite.TicketEntry.TABLE_NAME, OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR + " IS NOT NULL OR " + OfflineDatabaseSchemaSQLite.TicketEntry.COLUMN_INSPECTOR + " <> ?", new String[] {Ticket.TICKET_NOT_YET_INSPECTED});
        System.out.println(tickets.size());
        return tickets;
    }
}