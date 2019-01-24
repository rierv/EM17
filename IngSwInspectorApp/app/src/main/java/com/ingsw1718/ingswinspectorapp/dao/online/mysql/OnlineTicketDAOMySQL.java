package com.ingsw1718.ingswinspectorapp.dao.online.mysql;

import com.ingsw1718.ingswinspectorapp.dao.online.OnlineTicketDAO;
import com.ingsw1718.ingswinspectorapp.entity.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class OnlineTicketDAOMySQL extends OnlineTicketDAO {

    /**
     * Default constructor
     */
    public OnlineTicketDAOMySQL() {
    }

    /**
     * 
     */
    private int size;


    /**
     * @param eventId 
     * @param sectorName 
     * @param turnstileName 
     * @return
     */
    public List<Ticket> getTickets(String eventId, String sectorName, String turnstileName) {
        List<Ticket> ticketsList = new ArrayList<>();
        ResultSet resultSet;
        String  code, name, surname, identificatorId,
                joinSoldTicketCustomer ="("+ OnlineDatabaseSchemaMySQL.SOLD_TICKET_TABLE +" INNER JOIN "+ OnlineDatabaseSchemaMySQL.CUSTOMER_TABLE
                                  +" ON "+ OnlineDatabaseSchemaMySQL.SOLD_TICKET_TABLE +"."+ OnlineDatabaseSchemaMySQL.SOLD_TICKET_EMAIL_ADDRESS_COLUMN +"="+ OnlineDatabaseSchemaMySQL.CUSTOMER_TABLE +"."+ OnlineDatabaseSchemaMySQL.CUSTOMER_EMAIL_ADDRESS_COLUMN +") ",

                statement = "SELECT "+ OnlineDatabaseSchemaMySQL.SOLD_TICKET_TABLE +"."+ OnlineDatabaseSchemaMySQL.SOLD_TICKET_CODE_COLUMN +", "+ OnlineDatabaseSchemaMySQL.CUSTOMER_TABLE +"."+ OnlineDatabaseSchemaMySQL.CUSTOMER_NAME_COLUMN +", "+ OnlineDatabaseSchemaMySQL.CUSTOMER_TABLE +"."+ OnlineDatabaseSchemaMySQL.CUSTOMER_SURNAME_COLUMN
                          +" FROM "+ joinSoldTicketCustomer
                          +" WHERE "+ OnlineDatabaseSchemaMySQL.SOLD_TICKET_EVENT_ID_COLUMN +"=\""+ eventId +"\" AND "+ OnlineDatabaseSchemaMySQL.SOLD_TICKET_SECTOR_NAME_COLUMN +"=\""+ sectorName +"\" AND "+ OnlineDatabaseSchemaMySQL.SOLD_TICKET_TURNSTILE_NAME_COLUMN +"=\""+ turnstileName +"\" AND " + OnlineDatabaseSchemaMySQL.SOLD_TICKET_TICKET_INSPECTOR_ID_COLUMN + " IS NULL;";

        resultSet = OnlineDatabaseManagerMySQL.getInstance().query(statement);
        try {
            if(resultSet != null) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    code = resultSet.getString(1);
                    name = resultSet.getString(2);
                    surname = resultSet.getString(3);

                    Ticket ticket = new Ticket(code, Ticket.TICKET_NOT_YET_INSPECTED, name, surname);
                    ticketsList.add(ticket);
                }

                System.out.println(ticketsList.size());
                return ticketsList;
            }
            else{
                return null;
                }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param tickets 
     */
    public void updateTicketsStatus(List<Ticket> tickets) {
        if (tickets != null) {
            String statement;
            for (Ticket singleTicket : tickets) {
                statement = "UPDATE " + OnlineDatabaseSchemaMySQL.SOLD_TICKET_TABLE
                        + " SET " + OnlineDatabaseSchemaMySQL.SOLD_TICKET_TICKET_INSPECTOR_ID_COLUMN + "=\"" + singleTicket.getInspectorId() + "\""
                        + " WHERE " + OnlineDatabaseSchemaMySQL.SOLD_TICKET_CODE_COLUMN + "=\"" + singleTicket.getTicketCode() + "\";";
                OnlineDatabaseManagerMySQL.getInstance().update(statement);
            }
        }
    }

}