package com.ingsw1718.ingswinspectorapp.logic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineDAOFactory;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineDatabaseManager;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineTicketDAO;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineDAOFactory;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineEventDAO;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineTicketDAO;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;
import com.ingsw1718.ingswinspectorapp.ui.fragments.downloadtickets.*;

import com.ingsw1718.ingswinspectorapp.R;
import com.ingsw1718.ingswinspectorapp.entity.Ticket;

import java.util.List;
import java.util.Map;


public class DownloadTicketsActivity extends InspectorAppActivity implements SelectEventFragmentEventsListener, SelectTurnstileFragmentEventsListener {


    OnlineDAOFactory onlineDAOFactory = OnlineDAOFactory.getInstance();
    OnlineEventDAO onlineEventDAO = onlineDAOFactory.getOnlineEventDAO();

    private String workingEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_download_tickets);
        openFragment(SelectEventFragment.newInstance());
    }

    private void showSelectTurnstileFragment(Map<String, List<String>> sectorsAndTurnstiles) {
        openFragment(SelectTurnstileFragment.newInstance(sectorsAndTurnstiles));
    }

    @Override
    public void eventIdConfirmed(String eventId) throws IncompleteDataException {
        if (isConnectionAvailable()) {
            if (eventId != null && !eventId.isEmpty()) {
                if (onlineEventDAO.existsEventWithId(eventId)) {
                    System.out.println("EXISTS");
                    this.workingEventId = eventId;
                    Map<String, List<String>> sectorsAndTurnstiles = onlineEventDAO.getSectorsAndTurnstilesOfEventLocation(eventId);
                    showSelectTurnstileFragment(sectorsAndTurnstiles);
                } else {
                    Toast.makeText(this, this.getText(R.string.eventIdNotExisting), Toast.LENGTH_SHORT).show();
                }
            } else {
                throw new IncompleteDataException();
            }
        }
        else {
            showNoConnectionToast();
        }
    }

    @Override
    public void downloadTickets(final String sectorName, final String turnstileName) throws IncompleteDataException {
        if (isConnectionAvailable()) {
            if (sectorName != null && !sectorName.isEmpty() && turnstileName != null && !turnstileName.isEmpty()) {
                final ProgressDialog progress = new ProgressDialog(this);
                progress.setTitle(this.getText(R.string.loading));
                progress.setMessage(this.getText(R.string.waitWhileDownloading));
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();

                Thread mThread = new Thread() {
                    @Override
                    public void run() {

                        //for (int i=0; i<999999999; i++); //waste time...

                        OfflineDAOFactory offlineDAOFactory = OfflineDAOFactory.getInstance(DownloadTicketsActivity.this);
                        OfflineDatabaseManager dm = offlineDAOFactory.getOfflineDatabaseManager();

                        onlineDAOFactory.getOnlineDatabaseManager().beginTransaction();
                        dm.beginTransaction();

                        OnlineTicketDAO onlineTicketDAO = onlineDAOFactory.getOnlineTicketDAO();
                        List<Ticket> ticketList = onlineTicketDAO.getTickets(workingEventId, sectorName, turnstileName);

                        OfflineTicketDAO offlineTicketDAO = offlineDAOFactory.getOfflineTicketDAO();
                        offlineTicketDAO.addTickets(ticketList);

                        dm.commitTransaction();
                        onlineDAOFactory.getOnlineDatabaseManager().commitTransaction();

                        //Dismiss the dialog
                        progress.dismiss();

                        runOnUiThread(new Thread() {
                            @Override
                            public void run() {
                                Toast.makeText(DownloadTicketsActivity.this, getText(R.string.downloadCompleted), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Intent intent = new Intent(DownloadTicketsActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                    }
                };
                mThread.start();
            }
            else {
                throw new IncompleteDataException();
            }
        }
        else {
            showNoConnectionToast();
        }
    }
}