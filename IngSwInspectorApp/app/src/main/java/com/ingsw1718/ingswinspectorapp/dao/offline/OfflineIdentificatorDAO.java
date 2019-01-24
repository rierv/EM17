package com.ingsw1718.ingswinspectorapp.dao.offline;

import com.ingsw1718.ingswinspectorapp.entity.Identificator;

import java.util.*;

public abstract class OfflineIdentificatorDAO {

    public OfflineIdentificatorDAO() {
    }

    public abstract Identificator getIdentificatorById(String identificatorId);

    public abstract List<Identificator> extractAllIdentificators();

    public abstract void addIdentificator(Identificator identificator);
}