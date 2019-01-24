package com.ingsw1718.ingswinspectorapp.dao.online;

import com.ingsw1718.ingswinspectorapp.entity.Ticket;

import java.util.*;

public abstract class OnlineTicketDAO {
    public OnlineTicketDAO() {
    }

    public abstract List<Ticket> getTickets(String eventId, String sectorName, String turnstileName);

    public abstract void updateTicketsStatus(List<Ticket> tickets);
}