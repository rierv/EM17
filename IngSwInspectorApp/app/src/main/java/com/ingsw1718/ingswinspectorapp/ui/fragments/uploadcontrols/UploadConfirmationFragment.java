package com.ingsw1718.ingswinspectorapp.ui.fragments.uploadcontrols;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ingsw1718.ingswinspectorapp.R;

/**
 * Activities that contain this fragment must implement the
 * {@link UploadConfirmationFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link UploadConfirmationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadConfirmationFragment extends Fragment {
    private Button mConfirmButton;
    private TextView mUploadControlsHowManyText;
    private UploadConfirmationFragmentEventsListener mListener;

    private int size;

    public UploadConfirmationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    public static UploadConfirmationFragment newInstance(int size) {
        UploadConfirmationFragment fragment = new UploadConfirmationFragment();
        fragment.size = size;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_confirmation, container, false);

        //Find the confirm button
        mConfirmButton = view.findViewById(R.id.confirmButton);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmButtonPressed();
            }
        });

        //Find the upload controls text
        mUploadControlsHowManyText = view.findViewById(R.id.uploadControlsHowManyText);
        mUploadControlsHowManyText.setText("" + size + " " + getText(R.string.uploadControlsHowMany));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UploadConfirmationFragmentEventsListener) {
            mListener = (UploadConfirmationFragmentEventsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UploadConfirmationFragmentEventsListener");
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
            mListener.uploadControls();
        }
    }

}
