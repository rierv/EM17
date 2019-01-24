package com.ingsw1718.ingswinspectorapp.entity;

import com.ingsw1718.ingswinspectorapp.common.Date;

public class TicketInspector {

    private String inspectorID;

    private String name;

    private String surname;

    private Date birthDate;

    private String identificatorID;

    /**
     * @param inspectorID
     * @param name
     * @param surname
     * @param birthDate
     * @param identificatorID
     */
    public TicketInspector(String inspectorID, String name, String surname, Date birthDate, String identificatorID) {
        if (identificatorID == null) {
            throw new RuntimeException();
        }
        this.inspectorID = inspectorID;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.identificatorID = identificatorID;
        System.out.println(birthDate);
    }

    /**
     *
     */
    public String getInspectorID() {
        return inspectorID;
    }

    /**
     *
     */
    public Date getBirthDate() {
        return this.birthDate;
    }

    /**
     *
     */
    public String getIdentificatorID() {
        return identificatorID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}