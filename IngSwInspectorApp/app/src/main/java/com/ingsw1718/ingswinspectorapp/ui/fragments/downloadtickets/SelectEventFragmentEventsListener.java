package com.ingsw1718.ingswinspectorapp.ui.fragments.downloadtickets;

/**
 * Created by Raffolox on 08/02/2018.
 */

import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 */
public interface SelectEventFragmentEventsListener {
    void eventIdConfirmed(String eventId) throws IncompleteDataException;
}