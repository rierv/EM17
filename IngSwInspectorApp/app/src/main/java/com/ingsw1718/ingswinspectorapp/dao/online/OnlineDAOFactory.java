package com.ingsw1718.ingswinspectorapp.dao.online;

import com.ingsw1718.ingswinspectorapp.dao.online.mysql.OnlineDAOFactoryMySQL;

public abstract class OnlineDAOFactory {
    public static OnlineDAOFactory getInstance() {
        return new OnlineDAOFactoryMySQL();
    }

    public abstract OnlineTicketDAO getOnlineTicketDAO();

    public abstract OnlineInspectorDAO getOnlineInspectorDAO();

    public abstract OnlineIdentificatorDAO getOnlineIdentificatorDAO();

    public abstract OnlineEventDAO getOnlineEventDAO();

    public abstract OnlineDatabaseManager getOnlineDatabaseManager();
}