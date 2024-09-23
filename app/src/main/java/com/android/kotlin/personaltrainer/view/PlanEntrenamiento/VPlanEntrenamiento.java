package com.android.kotlin.personaltrainer.view.PlanEntrenamiento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.kotlin.personaltrainer.R;

public class VPlanEntrenamiento extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.index_plan_entrenamiento, container, false);
    }
}