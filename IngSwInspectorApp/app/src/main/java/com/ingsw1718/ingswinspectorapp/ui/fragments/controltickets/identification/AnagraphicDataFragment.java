package com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets.identification;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ingsw1718.ingswinspectorapp.R;
import com.ingsw1718.ingswinspectorapp.logic.ControlTicketsActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;

/**
 * Activities that contain this fragment must implement the
 * {@link AnagraphicDataFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link AnagraphicDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnagraphicDataFragment extends Fragment {

    private AnagraphicDataFragmentEventsListener mListener;
    private EditText nameTextField;
    private EditText surnameTextField;
    private DatePickerEditText dateOfBirthTextField;

    public AnagraphicDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    public static AnagraphicDataFragment newInstance() {
        AnagraphicDataFragment fragment = new AnagraphicDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anagraphic_data, container, false);

        nameTextField = view.findViewById(R.id.nameTextField);

        surnameTextField = view.findViewById(R.id.surnameTextField);

        dateOfBirthTextField = view.findViewById(R.id.dateOfBirthTextField);
        dateOfBirthTextField.setManager(getChildFragmentManager());
        dateOfBirthTextField.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(ControlTicketsActivity.MOST_RECENT_ALLOWED_BIRTH_DATE));
        dateOfBirthTextField.setMinDate(new SimpleDateFormat("dd/MM/yyyy").format(ControlTicketsActivity.LEAST_RECENT_ALLOWED_BIRTH_DATE));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AnagraphicDataFragmentEventsListener) {
            mListener = (AnagraphicDataFragmentEventsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AnagraphicDataFragmentEventsListener");
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

    public String getNameText() {
        return nameTextField.getText().toString();
    }

    public String getSurnameText() {
        return surnameTextField.getText().toString();
    }

    public Date getBirthDate() {
        return dateOfBirthTextField.getDate().getTime();
    }
}
