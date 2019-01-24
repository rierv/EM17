package com.ingsw1718.ingswinspectorapp.dao.online;

import com.ingsw1718.ingswinspectorapp.entity.TicketInspector;

import java.util.List;

public abstract class OnlineInspectorDAO {

    public OnlineInspectorDAO() {
    }

    public abstract void addInspectors(List<TicketInspector> ticketInspectors);
}