package com.ingsw1718.ingswinspectorapp.dao.offline.sqlite;

import android.content.Context;

import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineDAOFactory;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineDatabaseManager;

public class OfflineDAOFactorySQLite extends OfflineDAOFactory {
    private Context context;

    public OfflineDAOFactorySQLite(Context context) {
        this.context = context;
    }

    public OfflineInspectorDAOSQLite getOfflineInspectorDAO() {
        return new OfflineInspectorDAOSQLite(context);
    }

    public OfflineIdentificatorDAOSQLite getOfflineIdentificatorDAO() {
        return new OfflineIdentificatorDAOSQLite(context);
    }

    public OfflineTicketDAOSQLite getOfflineTicketDAO() {
        return new OfflineTicketDAOSQLite(context);
    }

    public OfflineDatabaseManager getOfflineDatabaseManager() {
        return OfflineDatabaseManagerSQLite.getInstance(context);
    }
}