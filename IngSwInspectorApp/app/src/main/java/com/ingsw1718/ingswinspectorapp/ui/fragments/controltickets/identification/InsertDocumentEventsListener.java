package com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification;

/**
 * Created by Raffolox on 08/02/2018.
 */

import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum;
import com.ingsw1718.ingswinspectorapp.exceptions.DocumentExpiredException;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;
import com.ingsw1718.ingswinspectorapp.exceptions.InvalidBirthDateException;

import java.text.ParseException;
import java.util.Date;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 */
public interface InsertDocumentEventsListener {
    void identifyInspector(DocumentTypeEnum.DocumentType documentType, String documentNumber, Date documentExpirationDate) throws DocumentExpiredException, IncompleteDataException, InvalidBirthDateException;
}