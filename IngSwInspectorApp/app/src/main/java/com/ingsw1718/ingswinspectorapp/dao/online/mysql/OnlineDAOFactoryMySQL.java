package com.ingsw1718.ingswinspectorapp.dao.online.mysql;

import com.ingsw1718.ingswinspectorapp.dao.online.OnlineDAOFactory;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineDatabaseManager;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineEventDAO;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineIdentificatorDAO;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineInspectorDAO;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineTicketDAO;

import java.util.*;

/**
 * 
 */
public class OnlineDAOFactoryMySQL extends OnlineDAOFactory {

    /**
     * Default constructor
     */
    public OnlineDAOFactoryMySQL() {
    }


    @Override
    public OnlineTicketDAO getOnlineTicketDAO() {
        return new OnlineTicketDAOMySQL();
    }

    @Override
    public OnlineInspectorDAO getOnlineInspectorDAO() {
        return new OnlineInspectorDAOMySQL();
    }

    @Override
    public OnlineIdentificatorDAO getOnlineIdentificatorDAO() {
        return new OnlineIdentificatorDAOMySQL();
    }

    @Override
    public OnlineEventDAO getOnlineEventDAO() {
        return new OnlineEventDAOMySQL();
    }

    @Override
    public OnlineDatabaseManager getOnlineDatabaseManager() {
        return OnlineDatabaseManagerMySQL.getInstance();
    }
}