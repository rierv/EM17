package com.ingsw1718.ingswinspectorapp.dao.offline;

import com.ingsw1718.ingswinspectorapp.entity.TicketInspector;

import java.util.*;

/**
 * 
 */
public abstract class OfflineInspectorDAO {

    public OfflineInspectorDAO() {
    }

    public abstract void addInspector(TicketInspector inspector);

    public abstract List<TicketInspector> extractAllInspectors();
}