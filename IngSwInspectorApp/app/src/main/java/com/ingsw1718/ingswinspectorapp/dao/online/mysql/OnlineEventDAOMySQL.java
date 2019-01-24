package com.ingsw1718.ingswinspectorapp.dao.online.mysql;

import com.ingsw1718.ingswinspectorapp.dao.online.OnlineEventDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public class OnlineEventDAOMySQL extends OnlineEventDAO {

    /**
     * Default constructor
     */
    public OnlineEventDAOMySQL() {

    }



    /**
     * @param eventId 
     * @return
     */
    public boolean existsEventWithId(String eventId) {
        Integer count;
        ResultSet resultSet;
        String statement ="SELECT COUNT(*)"
                        +" FROM "+ OnlineDatabaseSchemaMySQL.EVENT_TABLE
                        +" WHERE "+ OnlineDatabaseSchemaMySQL.EVENT_ID_COLUMN +"=\""+ eventId +"\";";

        resultSet = OnlineDatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                System.out.println("1");
                resultSet.first();
                count = resultSet.getInt(1);
                return count != 0;
            }
            else {
                System.out.println("2");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param eventId 
     * @return
     */
    public Map<String, List<String>> getSectorsAndTurnstilesOfEventLocation(String eventId) {
        HashMap<String, List<String>> map = new HashMap<>();
        ResultSet resultSet;
        String statementEventLocationByEventId = "SELECT "+ OnlineDatabaseSchemaMySQL.EVENT_LOCATION_DESCRIPTION_COLUMN
                                               +" FROM "+ OnlineDatabaseSchemaMySQL.EVENT_TABLE
                                               +" WHERE "+ OnlineDatabaseSchemaMySQL.EVENT_ID_COLUMN +"=\""+ eventId +"\"";

        resultSet = OnlineDatabaseManagerMySQL.getInstance().query(statementEventLocationByEventId);
        try {
            resultSet.first();
            String eventLocation = resultSet.getString(1);

            String statementSectorsAndTurnstileList = "SELECT "+ OnlineDatabaseSchemaMySQL.TURNSTILE_SECTOR_NAME_COLUMN +", "+ OnlineDatabaseSchemaMySQL.TURNSTILE_TURNSTILE_NAME_COLUMN
                                             +" FROM "+ OnlineDatabaseSchemaMySQL.TURNSTILE_TABLE
                                             +" WHERE "+ OnlineDatabaseSchemaMySQL.TURNSTILE_LOCATION_DESCRIPTION_COLUMN +"=\""+ eventLocation +"\";";

            resultSet = OnlineDatabaseManagerMySQL.getInstance().query(statementSectorsAndTurnstileList); //retrieve the sectorsName and turnstlesName
            if (resultSet != null) {
                resultSet.beforeFirst();
                while(resultSet.next()){
                    String sectorName = resultSet.getString(OnlineDatabaseSchemaMySQL.TURNSTILE_SECTOR_NAME_COLUMN);
                    String turnstileName = resultSet.getString(OnlineDatabaseSchemaMySQL.TURNSTILE_TURNSTILE_NAME_COLUMN);
                    addIntoMap(map, sectorName, turnstileName);
                }
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addIntoMap(Map<String, List<String>> map, String key, String value) {
        if(!map.containsKey(key)) {
            map.put(key, new ArrayList<String>());
        }
        map.get(key).add(value);
    }

}