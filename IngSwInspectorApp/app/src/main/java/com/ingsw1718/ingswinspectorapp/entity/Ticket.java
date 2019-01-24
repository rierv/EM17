package com.ingsw1718.ingswinspectorapp.entity;

import java.util.*;

/**
 * 
 */
public class Ticket {

    public static final String TICKET_NOT_YET_INSPECTED = "None";

    private String ticketCode;

    private String inspectorId;

    private String holderName;

    private String holderSurname;



    /**
     * @param ticketCode 
     * @param inspectorId
     */
    public Ticket(String ticketCode, String inspectorId, String holderName, String holderSurname) {
        this.ticketCode = ticketCode;
        this.inspectorId = inspectorId;
        this.holderName = holderName;
        this.holderSurname = holderSurname;
    }

    /**
     *
     */
    public String getTicketCode() {
        return ticketCode;
    }

    public String getInspectorId() {
        return inspectorId;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getHolderSurname() {
        return holderSurname;
    }

    @Override
    public String toString() {
        return "Ticket " + getTicketCode() + " owned by " + getHolderName() + " " + getHolderSurname() + (this.inspectorId.equals(TICKET_NOT_YET_INSPECTED) ? "" : " inspected by " + this.getInspectorId());
    }
}