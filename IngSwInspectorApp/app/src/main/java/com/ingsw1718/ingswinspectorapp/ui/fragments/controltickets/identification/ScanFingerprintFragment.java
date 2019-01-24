package com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ingsw1718.ingswinspectorapp.R;
import com.ingsw1718.ingswinspectorapp.exceptions.FingerprintProblemException;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;
import com.ingsw1718.ingswinspectorapp.exceptions.InvalidBirthDateException;

import me.aflak.libraries.callback.FingerprintCallback;
import me.aflak.libraries.view.Fingerprint;

/**
 * Activities that contain this fragment must implement the
 * {@link ScanFingerprintEventsListener} interface
 * to handle interaction events.
 * Use the {@link ScanFingerprintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFingerprintFragment extends Fragment implements FingerprintCallback {

    private ScanFingerprintEventsListener mListener;

    private Button mUseDocumentButton;

    public ScanFingerprintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    public static ScanFingerprintFragment newInstance() {
        ScanFingerprintFragment fragment = new ScanFingerprintFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan_fingerprint, container, false);

        mUseDocumentButton = view.findViewById(R.id.useDocumentButton);
        mUseDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUseDocumentButtonPressed();
            }
        });

        Fingerprint fingerprint = view.findViewById(R.id.fingerprintReader);
        fingerprint.callback(this)
                .circleScanningColor(android.R.color.black)
                .fingerprintScanningColor(R.color.colorAccent)
                .authenticate();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ScanFingerprintEventsListener) {
            mListener = (ScanFingerprintEventsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ScanFingerprintEventsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onUseDocumentButtonPressed() {
        if (mListener != null) {
            mListener.identifyInspectorUsingDocument();
        }
    }

    @Override
    public void onAuthenticationSucceeded() {
        if (mListener != null) {
            CharSequence errorMessage = null;
            boolean errorStatus = false;
            String androidDeviceID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                mListener.identifyInspector(androidDeviceID);
            }
            catch (IncompleteDataException ide) {
                errorStatus = true;
                errorMessage = getText(R.string.allFieldsAreRequired);
            }
            catch (InvalidBirthDateException ibde) {
                errorStatus = true;
                errorMessage = getText(R.string.birthDateNotValid);
            }
            catch (FingerprintProblemException e) {
                errorStatus = true;
                errorMessage = getText(R.string.unknownFingerprintProblem);
            }
            if (errorStatus && errorMessage != null) {
                Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(this.getContext(), getText(R.string.failedToReadFingerprint) + ". " + getText(R.string.retryOrUseDocument), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationError(int errorCode, String error) {
        Toast.makeText(this.getContext(), "Error " + error + ". " + getText(R.string.retryOrUseDocument), Toast.LENGTH_SHORT).show();
    }
}
