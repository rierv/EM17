package com.ingsw1718.ingswinspectorapp.logic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.ingsw1718.ingswinspectorapp.R;

import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineDAOFactory;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineIdentificatorDAO;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineInspectorDAO;
import com.ingsw1718.ingswinspectorapp.dao.offline.OfflineTicketDAO;
import com.ingsw1718.ingswinspectorapp.entity.Document;
import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum;
import com.ingsw1718.ingswinspectorapp.entity.Fingerprint;
import com.ingsw1718.ingswinspectorapp.entity.Identificator;
import com.ingsw1718.ingswinspectorapp.entity.TicketInspector;
import com.ingsw1718.ingswinspectorapp.exceptions.DocumentExpiredException;
import com.ingsw1718.ingswinspectorapp.exceptions.FingerprintProblemException;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;
import com.ingsw1718.ingswinspectorapp.exceptions.InvalidBirthDateException;
import com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.*;
import com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification.AnagraphicDataFragment;
import com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification.AnagraphicDataFragmentEventsListener;
import com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification.InsertDocumentEventsListener;
import com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification.InsertDocumentFragment;
import com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification.ScanFingerprintEventsListener;
import com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification.ScanFingerprintFragment;

import org.joda.time.LocalDate;

import java.text.ParseException;
import java.util.Date;
import java.util.Random;

public class ControlTicketsActivity extends InspectorAppActivity implements InsertDocumentEventsListener, ScanFingerprintEventsListener, ControlFragmentEventsListener, AnagraphicDataFragmentEventsListener, ControlDoneEventsListener {

    private static final int INSPECTOR_ID_LENGTH = 12;
    private static final String INSPECTOR_ID_ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int IDENTIFICATOR_ID_LENGTH = 12;
    private static final String IDENTIFICATOR_ID_ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final Date MOST_RECENT_ALLOWED_BIRTH_DATE = new Date(new Date().getTime() - 473_364_000_000L);//473364000000 = millis in 15 years = 1000*60*60*24*365.25*15
    public static final Date LEAST_RECENT_ALLOWED_BIRTH_DATE = new Date(0);//01/01/1970;

    OfflineDAOFactory offlineDAOFactory = OfflineDAOFactory.getInstance(this);
    OfflineTicketDAO offlineTicketDAO = offlineDAOFactory.getOfflineTicketDAO();
    OfflineInspectorDAO offlineInspectorDAO = offlineDAOFactory.getOfflineInspectorDAO();
    OfflineIdentificatorDAO offlineIdentificatorDAO = offlineDAOFactory.getOfflineIdentificatorDAO();




    private String activeInspectorID;
    private AnagraphicDataFragment anagraphicDataFragment = null;
    private InsertDocumentFragment insertDocumentFragment = null;
    private ScanFingerprintFragment scanFingerprintFragment = null;
    private String deviceID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_tickets);
        checkForCameraPermission();
        showIdentificationScreen();
    }

    private void checkForCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (! ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 12345);
            }
        }
    }

    /**
     * Shows the identification screen
     */
    private void showIdentificationScreen() {
        removeAllFragments();
        anagraphicDataFragment = AnagraphicDataFragment.newInstance();
        openFragment(anagraphicDataFragment, R.id.anagraphicDataFragmentContainer);
        showIdentificatorMethod();
    }

    /**
     * Shows the most suitable identificator method
     */
    private void showIdentificatorMethod() {
        if (isFingerprintAuthenticationPossible()) {
            showScanFingerprintFragment();
        }
        else {
            showInsertDocumentFragment();
        }
    }

    /**
     * @return true if there is the fingerprint API can be used, a fingerprint sensor is available and a fingerprint is registered
     */
    private boolean isFingerprintAuthenticationPossible() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Fingerprint API only available on from Android 6.0 (M)
            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
            if (fingerprintManager != null) {
                if (!fingerprintManager.isHardwareDetected()) { // Device doesn't support fingerprint authentication
                    return false;
                } else if (!fingerprintManager.hasEnrolledFingerprints()) { // User hasn't enrolled any fingerprints to authenticate with
                    return false;
                } else { // Everything is ready for fingerprint authentication
                    return true;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }


    @Override
    public void identifyInspectorUsingDocument() {
        showInsertDocumentFragment();
    }

    /**
     * Shows the InsertDocumentFragment
     */
    private void showInsertDocumentFragment() {
        insertDocumentFragment = InsertDocumentFragment.newInstance();
        openFragment(insertDocumentFragment, R.id.identificatorContainer);
    }

    /**
     * Shows the ScanFingerprintFragment
     */
    private void showScanFingerprintFragment() {
        scanFingerprintFragment = ScanFingerprintFragment.newInstance();
        openFragment(scanFingerprintFragment, R.id.identificatorContainer);
    }


    /**
     * Identifies the inspector using a document
     * @param documentType
     * @param documentNumber
     * @param documentExpirationDate
     * @throws InvalidBirthDateException if the inserted birth date is not valid
     * @throws DocumentExpiredException if the document is expired
     * @throws IncompleteDataException if not all fields were filled
     * @throws ParseException if the data is not valid
     */
    @Override
    public void identifyInspector(DocumentTypeEnum.DocumentType documentType, String documentNumber, Date documentExpirationDate) throws InvalidBirthDateException, DocumentExpiredException, IncompleteDataException {
        if (documentType != null && documentNumber != null && !documentNumber.isEmpty() && documentExpirationDate != null) {
            if (documentExpirationDate.before(new Date())) {
                throw new DocumentExpiredException();
            }
            else {
                Identificator identificator = new Document(generateNewIdentificatorID(), documentType, documentNumber, new com.ingsw1718.ingswinspectorapp.common.Date(documentExpirationDate.getTime()));
                identificatorCreated(identificator);
            }
        }
        else {
            throw new IncompleteDataException();
        }
    }

    /**
     * Identifies the inspector using the fingerprint
     * @param deviceID
     * @throws IncompleteDataException if not all fields were filled
     * @throws FingerprintProblemException if there is a generic fingerprint problem
     * @throws InvalidBirthDateException if the inserted birth date is not valid
     */
    @Override
    public void identifyInspector(String deviceID) throws IncompleteDataException, FingerprintProblemException, InvalidBirthDateException {
        this.deviceID = deviceID;
        if (deviceID != null && deviceID.matches("[0-9A-Fa-f]{16}")) {
            Identificator identificator = new Fingerprint(generateNewIdentificatorID(), deviceID);
            identificatorCreated(identificator);
        }
        else {
            throw new FingerprintProblemException();
        }
    }

    /**
     * Called when the identificator is created: adds it to the local database and creates the TicketInspector
     * @param identificator
     * @throws InvalidBirthDateException
     * @throws IncompleteDataException
     */
    protected void identificatorCreated(Identificator identificator) throws InvalidBirthDateException, IncompleteDataException {
        offlineDAOFactory.getOfflineDatabaseManager().beginTransaction();
        offlineIdentificatorDAO.addIdentificator(identificator);
        try {
            String name = anagraphicDataFragment.getNameText();
            String surname = anagraphicDataFragment.getSurnameText();
            Date dateOfBirth = anagraphicDataFragment.getBirthDate();
            TicketInspector inspector = createInspector(name, surname, dateOfBirth, identificator.getId());
            inspectorIdentified(inspector);
        }
        catch (IncompleteDataException | InvalidBirthDateException exception) {
            throw exception;
        }
    }

    /**
     * @return a new randomly-generated ID for the inspector
     */
    private String generateNewInspectorID() {
        Random r = new Random();
        StringBuilder inspectorId = new StringBuilder();
        for (int i = 0; i< INSPECTOR_ID_LENGTH; i++) {
            inspectorId.append(INSPECTOR_ID_ALLOWED_CHARACTERS.charAt(r.nextInt(INSPECTOR_ID_ALLOWED_CHARACTERS.length())));
        }
        return inspectorId.toString();
    }

    /**
     * @return a new randomly-generated ID for the inspector
     */
    private String generateNewIdentificatorID() {
        Random r = new Random();
        StringBuilder identificatorId = new StringBuilder();
        for (int i = 0; i< IDENTIFICATOR_ID_LENGTH; i++) {
            identificatorId.append(IDENTIFICATOR_ID_ALLOWED_CHARACTERS.charAt(r.nextInt(IDENTIFICATOR_ID_ALLOWED_CHARACTERS.length())));
        }
        return identificatorId.toString();
    }

    /**
     * @param identificatorID
     * @return a new TicketInspector whose name, surname and date of birth are the ones specified in AnagraphicDataFragment and whose identificator is the provided one
     * @throws IncompleteDataException if not all fields were filled
     * @throws InvalidBirthDateException if the inserted birth date is not valid
     */
    private TicketInspector createInspector(String name, String surname, Date dateOfBirth, String identificatorID) throws IncompleteDataException, InvalidBirthDateException {
        if (name != null && !name.isEmpty() && surname != null && !surname.isEmpty() && dateOfBirth != null && identificatorID != null && !identificatorID.isEmpty()) {
            if (dateOfBirth.before(new Date(MOST_RECENT_ALLOWED_BIRTH_DATE.getTime() + 86_400_000L)) && dateOfBirth.after(LEAST_RECENT_ALLOWED_BIRTH_DATE)) {
                String id = generateNewInspectorID();
                activeInspectorID = id;
                TicketInspector inspector = new TicketInspector(id, name, surname, new com.ingsw1718.ingswinspectorapp.common.Date(dateOfBirth.getTime()), identificatorID);
                return inspector;
            }
            else {
                throw new InvalidBirthDateException();
            }
        }
        else {
            throw new IncompleteDataException();
        }
    }

    /**
     * Called when the inspector has been successfully identified, adds it to the offline database and shows the ControlFragment
     * @param inspector
     */
    private void inspectorIdentified(TicketInspector inspector) {
        offlineInspectorDAO.addInspector(inspector);
        offlineDAOFactory.getOfflineDatabaseManager().commitTransaction();
        offlineDAOFactory.getOfflineDatabaseManager().close();
        showControlScreen();
    }

    /**
     * Shows the ControlFragment
     */
    private void showControlScreen() {
        removeAllFragments();
        openFragment(ControlFragment.newInstance());
    }

    /**
     * Shows the ControlFragment
     */
    private void showErrorScreen() {
        removeAllFragments();
        openFragment(ErrorFragment.newInstance());
    }

    /**
     * Shows the ControlFragment
     */
    private void showSuccessScreen(String ticketCode, String ticketHolder) {
        removeAllFragments();
        openFragment(SuccessFragment.newInstance(ticketCode, ticketHolder));
    }


    /**
     * Called when the ControlFragment's logout button gets pressed
     */
    @Override
    public void onLogoutButtonPressed() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    /**
     * Called when a QR code is read
     * @param ticketCode the read code
     */
    @Override
    public void detectedQRCode(String ticketCode) {
        System.out.println("mi trovo dentro detectedQRCode");
        if (ticketCode != null && offlineTicketDAO.containsTicketWithCode(ticketCode)) {
            System.out.println("ticketCode: "+ ticketCode);
            System.out.println("verifico se è presente il ticket: "+ ticketCode);
            offlineTicketDAO.controlTicket(ticketCode, activeInspectorID);
            String ticketHolderToString = offlineTicketDAO.getTicketHolderToString(ticketCode);
            System.out.println("ticketHolder: "+ ticketHolderToString);
            System.out.println("devo mostrare la schermata di successo");
            showSuccessScreen(ticketCode, ticketHolderToString);
        }
        else {
            System.out.println("devo mostrare la schermata di errore");
            showErrorScreen();
        }
    }

    /**
     * Called when the OK button in SuccessFragment or ErrorFragment is pressed, shows the ControlFragment
     */
    @Override
    public void onOkButtonInControlDonePressed() {
        showControlScreen();
    }
}

