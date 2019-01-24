package com.ingsw1718.ingswinspectorapp.logic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineDAOFactory;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineDatabaseManager;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineIdentificatorDAO;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineInspectorDAO;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineTicketDAO;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineDAOFactory;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineIdentificatorDAO;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineInspectorDAO;
import com.ingsw1718.ingswinspectorapp.dao.online.OnlineTicketDAO;
import com.ingsw1718.ingswinspectorapp.entity.Identificator;
import com.ingsw1718.ingswinspectorapp.entity.Ticket;
import com.ingsw1718.ingswinspectorapp.entity.TicketInspector;
import com.ingsw1718.ingswinspectorapp.dao.offline.sqlite.OfflineDatabaseManagerSQLite;
import com.ingsw1718.ingswinspectorapp.ui.fragments.uploadcontrols.*;

import com.ingsw1718.ingswinspectorapp.R;

import java.util.List;

public class UploadControlsActivity extends InspectorAppActivity implements UploadConfirmationFragmentEventsListener {

    UploadConfirmationFragment uploadConfirmationFragment = null;

    OfflineDAOFactory offlineDAOFactory = OfflineDAOFactory.getInstance(this);
    OnlineDAOFactory onlineDAOFactory = OnlineDAOFactory.getInstance();
    OfflineTicketDAO offlineTicketDAO = offlineDAOFactory.getOfflineTicketDAO();
    OnlineTicketDAO onlineTicketDAO = onlineDAOFactory.getOnlineTicketDAO();
    OfflineInspectorDAO offlineInspectorDAO = offlineDAOFactory.getOfflineInspectorDAO();
    OnlineInspectorDAO onlineInspectorDAO = onlineDAOFactory.getOnlineInspectorDAO();
    OfflineIdentificatorDAO offlineIdentificatorDAO = offlineDAOFactory.getOfflineIdentificatorDAO();
    OnlineIdentificatorDAO onlineIdentificatorDAO = onlineDAOFactory.getOnlineIdentificatorDAO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_tickets);

        int size = offlineTicketDAO.getControlledTicketsAmount();
        uploadConfirmationFragment = UploadConfirmationFragment.newInstance(size);
        openFragment(uploadConfirmationFragment);
    }

    @Override
    public void uploadControls() {
        if (isConnectionAvailable()) {
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle(this.getText(R.string.loading));
            progress.setMessage(this.getText(R.string.waitWhileUploading));
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();

            Thread mThread = new Thread() {
                @Override
                public void run() {

                    OfflineDatabaseManager offlineDatabaseManager = offlineDAOFactory.getOfflineDatabaseManager();

                    offlineDatabaseManager.beginTransaction();
                    onlineDAOFactory.getOnlineDatabaseManager().beginTransaction();

                    List<Ticket> tickets = offlineTicketDAO.extractControlledTickets();
                    List<TicketInspector> inspectors = offlineInspectorDAO.extractAllInspectors();
                    List<Identificator> identificators = offlineIdentificatorDAO.extractAllIdentificators();
                    System.out.println(identificators.size());
                    onlineIdentificatorDAO.addIdentificators(identificators);
                    onlineInspectorDAO.addInspectors(inspectors);
                    onlineTicketDAO.updateTicketsStatus(tickets);

                    offlineDatabaseManager.commitTransaction();
                    offlineDatabaseManager.close();
                    onlineDAOFactory.getOnlineDatabaseManager().commitTransaction();

                    //Dismiss the dialog
                    progress.dismiss();

                    runOnUiThread(new Thread() {
                        @Override
                        public void run() {
                            Toast.makeText(UploadControlsActivity.this, getText(R.string.uploadCompleted), Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(UploadControlsActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }
            };
            mThread.start();
        } else {
            showNoConnectionToast();
        }
    }
}
