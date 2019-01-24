package com.ingsw1718.ingswinspectorapp.dao.online.mysql;

import com.ingsw1718.ingswinspectorapp.dao.online.OnlineIdentificatorDAO;
import com.ingsw1718.ingswinspectorapp.entity.Document;
import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum;
import com.ingsw1718.ingswinspectorapp.entity.Fingerprint;
import com.ingsw1718.ingswinspectorapp.entity.Identificator;
import com.ingsw1718.ingswinspectorapp.common.Date;

import java.util.List;

/**
 * 
 */
public class OnlineIdentificatorDAOMySQL extends OnlineIdentificatorDAO {

    /**
     * Default constructor
     */
    public OnlineIdentificatorDAOMySQL() {

    }


    /**
     * @param identificators
     */
    public void addIdentificators(List<Identificator> identificators) {
        if (identificators != null) {
            DocumentTypeEnum.DocumentType documentType;
            String statement, identificatorType = null, documentNumber = null, documentExpirationDate = null, deviceId = null;
            for (Identificator singleIdentificator : identificators) {
                identificatorType = singleIdentificator.getIdentificatorType();
                if ((identificatorType.compareTo("DOCUMENT")) == 0) {
                    documentType = ((Document) singleIdentificator).getDocumentType();
                    documentNumber = ((Document) singleIdentificator).getDocumentNumber();
                    documentExpirationDate = ((Document) singleIdentificator).getDocumentExpirationDate().toString(Date.DATE_PATTERN_YYYYMMDD);
                    statement = "INSERT INTO " + OnlineDatabaseSchemaMySQL.IDENTIFICATOR_TABLE + "(" + OnlineDatabaseSchemaMySQL.IDENTIFICATOR_ID_COLUMN + ", " + OnlineDatabaseSchemaMySQL.IDENTIFICATOR_TYPE_COLUMN + ","+ OnlineDatabaseSchemaMySQL.IDENTIFICATOR_DOCUMENT_TYPE_COLUMN + "," + OnlineDatabaseSchemaMySQL.IDENTIFICATOR_DOCUMENT_NUMBER_COLUMN + "," + OnlineDatabaseSchemaMySQL.IDENTIFICATOR_DOCUMENT_EXPIRATION_DATE_COLUMN +")"
                              +" VALUES (\"" + singleIdentificator.getId() + "\", \"" + identificatorType + "\",\""+ documentType.toString() + "\",\"" + documentNumber + "\",\"" + documentExpirationDate + "\");";
                } else {
                    deviceId = ((Fingerprint) singleIdentificator).getDeviceID();
                    statement = "INSERT INTO " + OnlineDatabaseSchemaMySQL.IDENTIFICATOR_TABLE + "(" + OnlineDatabaseSchemaMySQL.IDENTIFICATOR_ID_COLUMN + ", " + OnlineDatabaseSchemaMySQL.IDENTIFICATOR_TYPE_COLUMN + "," + OnlineDatabaseSchemaMySQL.IDENTIFICATOR_DEVICE_ID_COLUMN + ")"
                              +" VALUES (\"" + singleIdentificator.getId() + "\", \"" + identificatorType + "\",\"" + deviceId + "\");";
                }
                OnlineDatabaseManagerMySQL.getInstance().update(statement);
            }
        }
    }

}


