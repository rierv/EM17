package com.ingsw1718.ingswinspectorapp.ui.fragments.controltickets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingsw1718.ingswinspectorapp.R;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

/**
 * Activities that contain this fragment must implement the
 * {@link ControlFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link ControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlFragment extends Fragment {

    private Button mLogoutButton;
    private SurfaceView mySurfaceView;
    private QREader qrEader;

    private ControlFragmentEventsListener mListener;

    public ControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlFragment newInstance() {
        ControlFragment fragment = new ControlFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void buildQRCodeReader(View view) {
        mySurfaceView = (SurfaceView) view.findViewById(R.id.camera_view);
        qrEader = new QREader.Builder(this.getContext(), mySurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                ControlFragment.this.onQRCodeDetected(data);
            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mySurfaceView.getHeight())
                .width(mySurfaceView.getWidth())
                .build();
        qrEader.initAndStart(mySurfaceView);
        qrEader.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);

        mLogoutButton = view.findViewById(R.id.logoutButton);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogoutButtonPressed();
            }
        });

        buildQRCodeReader(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ControlFragmentEventsListener) {
            mListener = (ControlFragmentEventsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ControlFragmentEventsListener");
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


    public void onLogoutButtonPressed() {
        if (mListener != null) {
            mListener.onLogoutButtonPressed();
        }
    }

    public void onQRCodeDetected(final String data) {
        if (mListener != null) {
            mListener.detectedQRCode(data);
        }
    }
}
