package com.ingsw1718.ingswinspectorapp.ui.fragments.downloadtickets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ingsw1718.ingswinspectorapp.R;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;

/**
 * Activities that contain this fragment must implement the
 * {@link SelectEventFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link SelectEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectEventFragment extends Fragment {
    private Button mConfirm;
    private EditText mEventID;

    private SelectEventFragmentEventsListener mListener;

    public SelectEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectEventFragment newInstance() {
        SelectEventFragment fragment = new SelectEventFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_event, container, false);

        //Find the event ID textfield
        mEventID = view.findViewById(R.id.eventIdTextField);

        //Find the confirm event id button
        mConfirm = view.findViewById(R.id.confirmButton);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmEventIdButtonPressed(mEventID.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectEventFragmentEventsListener) {
            mListener = (SelectEventFragmentEventsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectEventFragmentEventsListener");
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

    public void onConfirmEventIdButtonPressed(String eventId) {
        if (mListener != null) {
            try {
                mListener.eventIdConfirmed(eventId);
            }
            catch (IncompleteDataException e) {
                Toast.makeText(this.getContext(), getText(R.string.allFieldsAreRequired), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
