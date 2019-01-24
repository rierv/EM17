package com.ingsw1718.ingswinspectorapp.dao.offline;

import com.ingsw1718.ingswinspectorapp.entity.Ticket;

import java.util.List;

public abstract class OfflineTicketDAO {

    public OfflineTicketDAO() {
    }


    public abstract void addTickets(List<Ticket> tickets);

    public abstract boolean containsTicketWithCode(String ticketCode);

    public abstract String getTicketHolderToString(String ticketCode);

    public abstract void controlTicket(String ticketCode, String inspectorId);

    public abstract int getControlledTicketsAmount();

    public abstract List<Ticket> extractControlledTickets();
}