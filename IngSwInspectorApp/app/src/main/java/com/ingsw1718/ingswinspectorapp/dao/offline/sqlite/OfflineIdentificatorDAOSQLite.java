package com.ingsw1718.ingswinspectorapp.dao.offline.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ingsw1718.ingswinspectorapp.common.Date;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineIdentificatorDAO;
import com.ingsw1718.ingswinspectorapp.entity.Document;
import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum;
import com.ingsw1718.ingswinspectorapp.entity.Fingerprint;
import com.ingsw1718.ingswinspectorapp.entity.Identificator;

import java.util.ArrayList;
import java.util.List;

public class OfflineIdentificatorDAOSQLite extends OfflineIdentificatorDAO {

    private Context context;

    /**
     * Default constructor
     */
    public OfflineIdentificatorDAOSQLite(Context context) {
        this.context = context;
    }


    /**
     * @param identificator
     */
    public void addIdentificator(Identificator identificator) {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getWritableDatabase();

        ContentValues identificatorValues = new ContentValues();
        identificatorValues.put(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_ID, identificator.getId());
        identificatorValues.put(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_IDENTIFICATOR_TYPE, identificator.getIdentificatorType());
        if (identificator.getIdentificatorType().equals(Identificator.TYPE_DOCUMENT)) {
            Document doc = ((Document)(identificator));
            identificatorValues.putNull(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DEVICE_ID);
            identificatorValues.put(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_EXPIRATION_DATE, doc.getDocumentExpirationDate().toString(Date.DATE_PATTERN_DDMMYYYY));
            identificatorValues.put(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_NUMBER, doc.getDocumentNumber());
            identificatorValues.put(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_TYPE, doc.getDocumentType().toString());
        }
        else if(identificator.getIdentificatorType().equals(identificator.TYPE_FINGERPRINT)) {
            Fingerprint fp = ((Fingerprint)(identificator));
            identificatorValues.put(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DEVICE_ID, fp.getDeviceID());
            identificatorValues.putNull(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_EXPIRATION_DATE);
            identificatorValues.putNull(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_NUMBER);
            identificatorValues.putNull(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_TYPE);
        }
        db.insert(OfflineDatabaseSchemaSQLite.IdentificatorEntry.TABLE_NAME, null, identificatorValues);
    }

    public Identificator getIdentificatorById(String searchingIdentificatorID) {
        System.out.println("Searching for " + searchingIdentificatorID);
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getReadableDatabase();
        String[] projection = { OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_ID,                         OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_IDENTIFICATOR_TYPE,
                                OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_EXPIRATION_DATE,   OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_NUMBER,
                                OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_TYPE,              OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DEVICE_ID};
        Cursor cursor = db.query(OfflineDatabaseSchemaSQLite.IdentificatorEntry.TABLE_NAME, //The table to query
                projection, //The columns to return
                OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_ID + " = ?", //The columns for the where clause
                new String[] {searchingIdentificatorID}, //The values for the where clause
                null, //Group by
                null, //Having
                null); //Order by
        while(cursor.moveToNext()) {
            String identificatorID = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_ID));
            if (identificatorID==null) throw new RuntimeException();
            String identificatorType = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_IDENTIFICATOR_TYPE));
            if (identificatorType.equals(Identificator.TYPE_DOCUMENT)) {
                String documentExpirationDate = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_EXPIRATION_DATE));
                String documentNumber = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_NUMBER));
                String documentType = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_TYPE));
                Document document = new Document(identificatorID, DocumentTypeEnum.getDocumentTypeFromString(documentType), documentNumber, Date.parse(documentExpirationDate));
                cursor.close();
                return document;
            }
            else if (identificatorType.equals(Identificator.TYPE_FINGERPRINT)) {
                String deviceID = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DEVICE_ID));
                Fingerprint fingerprint = new Fingerprint(identificatorID, deviceID);
                cursor.close();
                return fingerprint;
            }
        }
        return null; //Should never be reached
    }

    /**
     * @return
     */
    public List<Identificator> extractAllIdentificators() {
        SQLiteDatabase db = OfflineDatabaseManagerSQLite.getInstance(context).getReadableDatabase();
        String[] projection = { OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_ID,                         OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_IDENTIFICATOR_TYPE,
                                OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_EXPIRATION_DATE,   OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_NUMBER,
                                OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_TYPE,              OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DEVICE_ID};
        Cursor cursor = db.query(OfflineDatabaseSchemaSQLite.IdentificatorEntry.TABLE_NAME, //The table to query
                                 projection, //The columns to return
                                 null, //The columns for the where clause
                                 null, //The values for the where clause
                                 null, //Group by
                                 null, //Having
                                 null); //Order by
        List<Identificator> identificators = new ArrayList<>();
        while(cursor.moveToNext()) {
            String identificatorID = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_ID));
            if (identificatorID==null) throw new RuntimeException();
            String identificatorType = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_IDENTIFICATOR_TYPE));
            if (identificatorType.equals(Identificator.TYPE_DOCUMENT)) {
                String documentExpirationDate = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_EXPIRATION_DATE));
                String documentNumber = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_NUMBER));
                String documentType = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DOCUMENT_TYPE));
                Document document = new Document(identificatorID, DocumentTypeEnum.getDocumentTypeFromString(documentType), documentNumber, Date.parse(documentExpirationDate));
                identificators.add(document);
            }
            else if (identificatorType.equals(Identificator.TYPE_FINGERPRINT)) {
                String deviceID = cursor.getString(cursor.getColumnIndexOrThrow(OfflineDatabaseSchemaSQLite.IdentificatorEntry.COLUMN_DEVICE_ID));
                Fingerprint fingerprint = new Fingerprint(identificatorID, deviceID);
                identificators.add(fingerprint);
            }
        }
        cursor.close();
        db.delete(OfflineDatabaseSchemaSQLite.IdentificatorEntry.TABLE_NAME, null, null);
        return identificators;
    }

}