package com.ingsw1718.ingswinspectorapp.ui.fragments.downloadtickets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ingsw1718.ingswinspectorapp.R;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;

import java.util.List;
import java.util.Map;


/**
 * Activities that contain this fragment must implement the
 * {@link SelectTurnstileFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link SelectTurnstileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectTurnstileFragment extends Fragment {
    private Button mConfirmButton;
    private Spinner mSectorSpinner;
    private Spinner mTurnstileSpinner;
    private SelectTurnstileFragmentEventsListener mListener;
    private Map<String, List<String>> sectorsAndTurnstiles;

    public SelectTurnstileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTurnstileFragment newInstance(Map<String, List<String>> sectorsAndTurnstiles) {
        SelectTurnstileFragment fragment = new SelectTurnstileFragment();
        fragment.sectorsAndTurnstiles = sectorsAndTurnstiles;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_turnstile, container, false);

        //Find the download tickets button
        mConfirmButton = view.findViewById(R.id.confirmButton);
        mConfirmButton.setEnabled(false);
        mConfirmButton.setClickable(false);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmTurnstileButtonPressed(((String)(mSectorSpinner.getSelectedItem())), ((String)(mTurnstileSpinner.getSelectedItem())));
            }
        });

        //Sector spinner
        final String[] sectors = new String[sectorsAndTurnstiles.keySet().size()];
        int i=0;
        for (String s: sectorsAndTurnstiles.keySet()) {
            sectors[i] = s;
            i++;
        }
        ArrayAdapter<String> sectorAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, sectors);
        sectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSectorSpinner = view.findViewById(R.id.sectorSpinner);
        mSectorSpinner.setAdapter(sectorAdapter);
        mSectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String[] turnstiles = new String[sectorsAndTurnstiles.get(sectors[position]).size()];
                int i=0;
                for (String s: sectorsAndTurnstiles.get(sectors[position])) {
                    turnstiles[i] = s;
                    i++;
                }
                ArrayAdapter<String> turnstileAdapter = new ArrayAdapter<String>(SelectTurnstileFragment.this.getActivity(), android.R.layout.simple_spinner_item, turnstiles);
                turnstileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mTurnstileSpinner.setAdapter(turnstileAdapter);
                mTurnstileSpinner.setEnabled(true);
                mTurnstileSpinner.setClickable(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mTurnstileSpinner.setEnabled(false);
                mTurnstileSpinner.setClickable(false);
            }
        });

        //Turnstile spinner
        mTurnstileSpinner = view.findViewById(R.id.turnstileSpinner);
        mTurnstileSpinner.setEnabled(false);
        mTurnstileSpinner.setClickable(false);
        mTurnstileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mConfirmButton.setEnabled(true);
                mConfirmButton.setClickable(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mConfirmButton.setEnabled(false);
                mConfirmButton.setClickable(false);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectTurnstileFragmentEventsListener) {
            mListener = (SelectTurnstileFragmentEventsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectTurnstileFragmentEventsListener");
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

    public void onConfirmTurnstileButtonPressed(String sectorName, String turnstileName) {
        if (mListener != null) {
            try {
                mListener.downloadTickets(sectorName, turnstileName);
            }
            catch (IncompleteDataException ide) {
                Toast.makeText(this.getContext(), getText(R.string.allFieldsAreRequired), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
