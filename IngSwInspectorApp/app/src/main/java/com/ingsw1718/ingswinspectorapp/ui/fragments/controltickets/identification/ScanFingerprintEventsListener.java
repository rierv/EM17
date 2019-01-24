package com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification;

/**
 * Created by Raffolox on 08/02/2018.
 */

import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum;
import com.ingsw1718.ingswinspectorapp.exceptions.FingerprintProblemException;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;
import com.ingsw1718.ingswinspectorapp.exceptions.InvalidBirthDateException;

import java.util.Date;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 */
public interface ScanFingerprintEventsListener {
    /**
     * The user asks to be identified using a document rather than a fingerprint
     */
    void identifyInspectorUsingDocument();

    /**
     * Identifies the inspector using the fingerprint
     * @param deviceID
     * @throws IncompleteDataException if not all fields were filled
     * @throws FingerprintProblemException if there is a generic fingerprint problem
     * @throws InvalidBirthDateException if the inserted birth date is not valid
     */
    void identifyInspector(String deviceID) throws IncompleteDataException, FingerprintProblemException, InvalidBirthDateException;
}