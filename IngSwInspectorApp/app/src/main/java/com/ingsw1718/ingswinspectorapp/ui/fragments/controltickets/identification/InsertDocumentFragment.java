package com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ingsw1718.ingswinspectorapp.R;
import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum;
import com.ingsw1718.ingswinspectorapp.exceptions.DocumentExpiredException;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;
import com.ingsw1718.ingswinspectorapp.exceptions.InvalidBirthDateException;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;

/**
 * Activities that contain this fragment must implement the
 * {@link InsertDocumentEventsListener} interface
 * to handle interaction events.
 * Use the {@link InsertDocumentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertDocumentFragment extends Fragment {

    private InsertDocumentEventsListener mListener;
    private Spinner documentTypeSpinner;
    private EditText documentNumberTextField;
    private DatePickerEditText documentExpiryDateTextField;
    private Button mConfirmButton;

    public InsertDocumentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    public static InsertDocumentFragment newInstance() {
        InsertDocumentFragment fragment = new InsertDocumentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_document, container, false);

        documentTypeSpinner = view.findViewById(R.id.documentTypeSpinner);
        final String[] documentTypes = DocumentTypeEnum.values();
        ArrayAdapter<String> documentTypesAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, documentTypes);
        documentTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        documentTypeSpinner = view.findViewById(R.id.documentTypeSpinner);
        documentTypeSpinner.setAdapter(documentTypesAdapter);

        documentNumberTextField = view.findViewById(R.id.documentNumberTextField);

        documentExpiryDateTextField = view.findViewById(R.id.documentExpiryDateTextField);
        documentExpiryDateTextField.setManager(getChildFragmentManager());
        Date tomorrow = new Date();
        tomorrow.setTime(tomorrow.getTime() + 86_400_000); //86400000 = millis in a day = 1000*60*60*24
        String tomorrowAsString = new SimpleDateFormat("dd/MM/yyyy").format(tomorrow);
        documentExpiryDateTextField.setMinDate(tomorrowAsString);
        Date fifteenYearsFromNow = new Date();
        fifteenYearsFromNow.setTime(fifteenYearsFromNow.getTime() + 473_364_000_000L); //473364000000 = millis in 15 years = 1000*60*60*24*365.25*15
        String fifteenYearsFromNowAsString = new SimpleDateFormat("dd/MM/yyyy").format(fifteenYearsFromNow);
        documentExpiryDateTextField.setMaxDate(fifteenYearsFromNowAsString);

        //Find the confirm button
        mConfirmButton = (Button) view.findViewById(R.id.confirmButton);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmButtonPressed();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InsertDocumentEventsListener) {
            mListener = (InsertDocumentEventsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement InsertDocumentEventsListener");
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

    public void onConfirmButtonPressed() {
        if (mListener != null) {
            CharSequence errorMessage = null;
            boolean errorStatus = false;
            try {
                mListener.identifyInspector(DocumentTypeEnum.getDocumentTypeFromString(((String)(documentTypeSpinner.getSelectedItem()))),
                        documentNumberTextField.getText().toString(),
                        documentExpiryDateTextField.getDate().getTime());
            }
            catch (IncompleteDataException | NullPointerException exc) {
                errorStatus = true;
                errorMessage = getText(R.string.allFieldsAreRequired);
            }
            catch (InvalidBirthDateException ibde) {
                errorStatus = true;
                errorMessage = getText(R.string.birthDateNotValid);
            }
            catch (DocumentExpiredException dee) {
                errorStatus = true;
                errorMessage = getText(R.string.documentIsExpired);
            }
            if (errorStatus && errorMessage != null) {
                Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
