package com.ingsw1718.ingswinspectorapp.ui.fragments.mainmenu;

/**
 * Created by Raffolox on 08/02/2018.
 */
/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 */
public interface MainMenuEventsListener {
    void startDownloadingTickets();
    void startControllingTickets();
    void startUploadingTickets();
}