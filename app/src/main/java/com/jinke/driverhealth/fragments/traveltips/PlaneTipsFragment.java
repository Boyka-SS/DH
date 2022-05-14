package com.jinke.driverhealth.fragments.traveltips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jinke.driverhealth.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaneTipsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaneTipsFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plane_tips, container, false);
    }
}