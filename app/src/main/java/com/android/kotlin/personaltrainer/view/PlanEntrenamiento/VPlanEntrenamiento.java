package com.android.kotlin.personaltrainer.view.PlanEntrenamiento;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CPlanEntrenamiento;
import com.android.kotlin.personaltrainer.controller.CRutina;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.PlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.view.Rutina.RutinaAdapter;
import com.android.kotlin.personaltrainer.view.Rutina.VCrearRutina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VPlanEntrenamiento extends Fragment {

    CPlanEntrenamiento controller;
    FloatingActionButton fab;
    PlanEntrenamientoAdapter listAdapter;
    RecyclerView recyclerView;
    List<PlanEntrenamiento> listadoPlanes;
    LinearLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.index_plan_entrenamiento, container, false);

        this.controller = new CPlanEntrenamiento(this);

        this.fab = view.findViewById(R.id.btn_add_plan);
        this.recyclerView = view.findViewById(R.id.recyclerview_plan_entrenamiento);
        this.emptyLayout = view.findViewById(R.id.empty_layout);

        controller.cargarPlanesEntrenamiento();

        this.listAdapter = new PlanEntrenamientoAdapter(getActivity(), this.listadoPlanes);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.listAdapter);

        this.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VCrearPlanEntrenamiento.class);
            startActivity(intent);
        });

        return view;
    }

    public void cargarPlanesEntrenamiento(List<PlanEntrenamiento> listado) {
        this.listadoPlanes = listado;
    }

    public void verificarVacio() {
        if (this.listadoPlanes.isEmpty()) {
            this.emptyLayout.setVisibility(LinearLayout.VISIBLE);
        } else {
            this.emptyLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.cargarPlanesEntrenamiento();
        this.listAdapter.refreshData(this.listadoPlanes);
        verificarVacio();
    }
}