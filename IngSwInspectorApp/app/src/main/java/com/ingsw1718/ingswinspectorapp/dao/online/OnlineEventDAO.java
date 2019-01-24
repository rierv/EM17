package com.ingsw1718.ingswinspectorapp.dao.online;

import java.util.*;

public abstract class OnlineEventDAO {

    public OnlineEventDAO() {
    }

    public abstract boolean existsEventWithId(String eventId);

    public abstract Map<String, List<String>> getSectorsAndTurnstilesOfEventLocation(String eventId);
}