/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.database;

import em17.database.mysql.LocationDAOMySQL;
import em17.database.mysql.CustomerDAOMySQL;
import em17.database.mysql.UserDAOMySQL;
import em17.database.mysql.EventDAOMySQL;
import em17.database.mysql.SoldTicketDAOMySQL;
import em17.database.mysql.DAOFactoryMySQL;

/**
 *
 * @author Raffolox
 */
public abstract class DAOFactory {
    public static DAOFactory getInstance() {
        return new DAOFactoryMySQL();
    }
    public abstract EventDAO getEventDAO();
    public abstract SoldTicketDAO getSoldTicketDAO();
    public abstract CustomerDAO getCustomerDAO();
    public abstract LocationDAO getLocationDAO();
    public abstract UserDAO getUserDAO();
    public abstract DatabaseManager getDatabaseManager();
}
