package com.ingsw1718.ingswinspectorapp.dao.online;

import com.ingsw1718.ingswinspectorapp.entity.Identificator;

import java.util.List;

public abstract class OnlineIdentificatorDAO {

    public OnlineIdentificatorDAO() {
    }

    public abstract void addIdentificators(List<Identificator> identificators);
}