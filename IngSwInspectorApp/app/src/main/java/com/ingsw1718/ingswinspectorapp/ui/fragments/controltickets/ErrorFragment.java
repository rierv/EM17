package com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingsw1718.ingswinspectorapp.R;

/**
 * Activities that contain this fragment must implement the
 * {@link ControlDoneEventsListener} interface
 * to handle interaction events.
 * Use the {@link ErrorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ErrorFragment extends Fragment {

    private ControlDoneEventsListener mListener;
    private Button mOkButton;

    public ErrorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ErrorFragment newInstance() {
        ErrorFragment fragment = new ErrorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_error, container, false);

        //Find the ok button
        mOkButton = (Button) view.findViewById(R.id.confirmButton);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOkButtonPressed();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ControlDoneEventsListener) {
            mListener = (ControlDoneEventsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ControlDoneEventsListener");
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

    public void onOkButtonPressed() {
        if (mListener != null) {
            mListener.onOkButtonInControlDonePressed();
        }
    }
}
