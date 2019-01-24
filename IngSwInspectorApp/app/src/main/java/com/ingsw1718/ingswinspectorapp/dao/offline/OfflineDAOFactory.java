package com.ingsw1718.ingswinspectorapp.dao.offline;

import android.content.Context;

import com.ingsw1718.ingswinspectorapp.dao.offline.sqlite.OfflineDAOFactorySQLite;

public abstract class OfflineDAOFactory {
    public static OfflineDAOFactory getInstance(Context context) {
        return new OfflineDAOFactorySQLite(context);
    }

    public abstract OfflineInspectorDAO getOfflineInspectorDAO();

    public abstract OfflineIdentificatorDAO getOfflineIdentificatorDAO();

    public abstract OfflineTicketDAO getOfflineTicketDAO();

    public abstract OfflineDatabaseManager getOfflineDatabaseManager();
}