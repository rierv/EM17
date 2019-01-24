package com.ingsw1718.ingswinspectorapp.ui.fragments.mainmenu;

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
 * {@link MainMenuEventsListener} interface
 * to handle interaction events.
 * Use the {@link MainMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {
    private Button mDownloadTicketsButton;
    private Button mControlTicketsButton;
    private Button mUploadControlsButton;

    private MainMenuEventsListener mListener;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        //Find the download tickets button
        mDownloadTicketsButton = view.findViewById(R.id.downloadTicketsButton);
        mDownloadTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDownloadTicketsButtonPressed();
            }
        });

        //Find the control tickets button
        mControlTicketsButton = view.findViewById(R.id.controlTicketsButton);
        mControlTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onControlTicketsButtonPressed();
            }
        });

        //Find the upload controls button
        mUploadControlsButton = view.findViewById(R.id.uploadControlsButton);
        mUploadControlsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUploadControlsButtonPressed();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainMenuEventsListener) {
            mListener = (MainMenuEventsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MainMenuEventsListener");
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

    public void onDownloadTicketsButtonPressed() {
        if (mListener != null) {
            mListener.startDownloadingTickets();
        }
    }

    public void onControlTicketsButtonPressed() {
        if (mListener != null) {
            mListener.startControllingTickets();
        }
    }

    public void onUploadControlsButtonPressed() {
        if (mListener != null) {
            mListener.startUploadingTickets();
        }
    }

}
