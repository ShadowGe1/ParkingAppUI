package com.example.parkingsystemapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkingsystemapp.data.local.CoordinatePlacer;
import com.example.parkingsystemapp.data.remote.TakeLocationsClient;
import com.example.parkingsystemapp.manager.MapManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MapManager mapManager;
    private static final String TAG = "MainActivity";

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapManager = new MapManager(requireActivity(), view.findViewById(R.id.map));
        mapManager.setup();

        TakeLocationsClient takeLocationsClient = new TakeLocationsClient();
        takeLocationsClient.execute((List<CoordinatePlacer> coordinates) -> {
            if (coordinates != null) {
                for (CoordinatePlacer coordinate : coordinates) {
                    mapManager.addMarker(coordinate.getLatitude(), coordinate.getLongitude(), "Parking Spot");
                }
            } else {
                Log.e(TAG, "The list of coordinates is null");
            }
        });

        return view;
    }

}