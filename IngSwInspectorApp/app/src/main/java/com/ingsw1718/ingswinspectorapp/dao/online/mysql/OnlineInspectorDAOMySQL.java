package com.ingsw1718.ingswinspectorapp.dao.online.mysql;

import com.ingsw1718.ingswinspectorapp.common.Date;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineInspectorDAO;
import com.ingsw1718.ingswinspectorapp.entity.TicketInspector;

import java.util.List;


/**
 * 
 */
public class OnlineInspectorDAOMySQL extends OnlineInspectorDAO {

    public OnlineInspectorDAOMySQL() {
    }


    /**
     * @param ticketInspectors
     * @return
     */
    public void addInspectors(List<TicketInspector> ticketInspectors) {
        if (ticketInspectors != null) {
            String statement;
            for (TicketInspector singleTicketInspector : ticketInspectors) {
                String formattedBirthDate = singleTicketInspector.getBirthDate().toString(Date.DATE_PATTERN_YYYYMMDD);
                statement = "INSERT INTO " + OnlineDatabaseSchemaMySQL.TICKET_INSPECTOR_TABLE + "(" + OnlineDatabaseSchemaMySQL.TICKET_INSPECTOR_ID_COLUMN + "," + OnlineDatabaseSchemaMySQL.TICKET_INSPECTOR_NAME_COLUMN + "," + OnlineDatabaseSchemaMySQL.TICKET_INSPECTOR_SURNAME_COLUMN + "," + OnlineDatabaseSchemaMySQL.TICKET_INSPECTOR_BIRTH_DATE_COLUMN + "," + OnlineDatabaseSchemaMySQL.TICKET_INSPECTOR_IDENTIFICATOR_ID_COLUMN + ")"
                        + " VALUES (\"" + singleTicketInspector.getInspectorID() + "\", \"" + singleTicketInspector.getName() + "\", \"" + singleTicketInspector.getSurname() + "\", \"" + formattedBirthDate + "\", \"" + singleTicketInspector.getIdentificatorID() + "\");";
                OnlineDatabaseManagerMySQL.getInstance().update(statement);
            }
        }
    }

}