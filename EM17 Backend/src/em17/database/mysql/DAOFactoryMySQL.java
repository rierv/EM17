/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.database.mysql;

import em17.database.CustomerDAO;
import em17.database.DAOFactory;
import em17.database.DatabaseManager;
import em17.database.EventDAO;
import em17.database.LocationDAO;
import em17.database.SoldTicketDAO;
import em17.database.UserDAO;

/**
 *
 * @author Raffolox
 */
public class DAOFactoryMySQL extends DAOFactory {

    @Override
    public EventDAO getEventDAO() {
        return new EventDAOMySQL();
    }

    @Override
    public SoldTicketDAO getSoldTicketDAO() {
        return new SoldTicketDAOMySQL();
    }

    @Override
    public CustomerDAO getCustomerDAO() {
        return new CustomerDAOMySQL();
    }

    @Override
    public LocationDAO getLocationDAO() {
        return new LocationDAOMySQL();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOMySQL();
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return DatabaseManagerMySQL.getInstance();
    }
    
    
    
}
