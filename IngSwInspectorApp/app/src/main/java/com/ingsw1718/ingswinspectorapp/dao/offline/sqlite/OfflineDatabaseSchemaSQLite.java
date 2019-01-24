package com.ingsw1718.ingswinspectorapp.dao.offline.sqlite;

import android.provider.BaseColumns;

import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum;
import com.ingsw1718.ingswinspectorapp.entity.Identificator;

/**
 * Created by Raffolox on 17/02/2018.
 */

public final class OfflineDatabaseSchemaSQLite {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private OfflineDatabaseSchemaSQLite() {}

    /* Inner class that defines the table contents */
    public static class TicketInspectorEntry implements BaseColumns {
        public static final String TABLE_NAME = "TicketInspector";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_BIRTHDATE = "birthDate";
        public static final String COLUMN_IDENTIFICATOR = "identificator";
    }

    public static class IdentificatorEntry implements BaseColumns {
        public static final String TABLE_NAME = "Identificator";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_IDENTIFICATOR_TYPE = "identificatorType";
        public static final String COLUMN_DOCUMENT_TYPE = "documentType";
        public static final String COLUMN_DOCUMENT_NUMBER = "documentNumber";
        public static final String COLUMN_DOCUMENT_EXPIRATION_DATE = "documentExpirationDate";
        public static final String COLUMN_DEVICE_ID = "deviceId";
    }

    public static class TicketEntry implements BaseColumns {
        public static final String TABLE_NAME = "Ticket";
        public static final String COLUMN_CODE = "ticketCode";
        public static final String COLUMN_INSPECTOR = "inspectorId";
        public static final String COLUMN_HOLDER_NAME = "holderName";
        public static final String COLUMN_HOLDER_SURNAME = "holderSurname";
    }

    static final String SQL_CREATE_TABLE_TICKET_INSPECTOR =
        "CREATE TABLE " + TicketInspectorEntry.TABLE_NAME + " (" +
            TicketInspectorEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
            TicketInspectorEntry.COLUMN_NAME + " TEXT," +
            TicketInspectorEntry.COLUMN_SURNAME + " TEXT," +
            TicketInspectorEntry.COLUMN_BIRTHDATE + " TEXT," +
            TicketInspectorEntry.COLUMN_IDENTIFICATOR + " TEXT," +
            "FOREIGN KEY(" + TicketInspectorEntry.COLUMN_IDENTIFICATOR + ") REFERENCES " + IdentificatorEntry.TABLE_NAME + "(" + IdentificatorEntry.COLUMN_ID +"))";

    static final String SQL_CREATE_TABLE_TICKET =
        "CREATE TABLE " + TicketEntry.TABLE_NAME + " (" +
            TicketEntry.COLUMN_CODE + " TEXT PRIMARY KEY," +
                TicketEntry.COLUMN_INSPECTOR + " TEXT," +
                TicketEntry.COLUMN_HOLDER_NAME + " TEXT," +
                TicketEntry.COLUMN_HOLDER_SURNAME + " TEXT," +
            "FOREIGN KEY(" + TicketEntry.COLUMN_INSPECTOR + ") REFERENCES " + TicketInspectorEntry.TABLE_NAME + "(" + TicketInspectorEntry.COLUMN_ID +"))";

    static final String SQL_CREATE_TABLE_IDENTIFICATOR =
        "CREATE TABLE " + IdentificatorEntry.TABLE_NAME + " (" +
            IdentificatorEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
            IdentificatorEntry.COLUMN_IDENTIFICATOR_TYPE + " TEXT CHECK( " + IdentificatorEntry.COLUMN_IDENTIFICATOR_TYPE + " IN ('" + Identificator.TYPE_DOCUMENT + "', '" + Identificator.TYPE_FINGERPRINT + "') )," +
            IdentificatorEntry.COLUMN_DOCUMENT_TYPE + " TEXT CHECK( " + IdentificatorEntry.COLUMN_DOCUMENT_TYPE + " IS NULL OR " + IdentificatorEntry.COLUMN_DOCUMENT_TYPE + " IN ('" + DocumentTypeEnum.ID_CARD.toString() + "', '" + DocumentTypeEnum.PASSPORT.toString() + "', '" + DocumentTypeEnum.DRIVING_LICENSE.toString() + "') )," +
            IdentificatorEntry.COLUMN_DOCUMENT_NUMBER + " TEXT," +
            IdentificatorEntry.COLUMN_DOCUMENT_EXPIRATION_DATE + " TEXT," +
            IdentificatorEntry.COLUMN_DEVICE_ID + " TEXT)";

    static final String SQL_DELETE_CONTROLLED_TICKETS = "DELETE FROM " + TicketEntry.TABLE_NAME + " WHERE " + TicketEntry.COLUMN_INSPECTOR + " IS NOT NULL AND " + TicketEntry.COLUMN_INSPECTOR + " <> ''";

    static final String SQL_DELETE_TABLE_IDENTIFICATOR = "DROP TABLE IF EXISTS " + TicketInspectorEntry.TABLE_NAME;

    static final String SQL_DELETE_TABLE_TICKET_INSPECTOR = "DROP TABLE IF EXISTS " + IdentificatorEntry.TABLE_NAME;
}
