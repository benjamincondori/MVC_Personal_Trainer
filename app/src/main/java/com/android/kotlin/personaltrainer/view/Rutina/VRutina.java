package com.android.kotlin.personaltrainer.view.Rutina;

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
import com.android.kotlin.personaltrainer.controller.CRutina;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VRutina extends Fragment {

    CRutina controller;
    FloatingActionButton fab;
    RutinaAdapter listAdapter;
    RecyclerView recyclerView;
    List<Rutina> listadoRutinas;
    LinearLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_rutina, container, false);

        this.controller = new CRutina(this);

        this.fab = view.findViewById(R.id.btn_add_rutina);
        this.recyclerView = view.findViewById(R.id.recyclerview_rutinas);
        this.emptyLayout = view.findViewById(R.id.empty_layout);

        controller.cargarRutinas();

        this.listAdapter = new RutinaAdapter(getActivity(), this.listadoRutinas);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.listAdapter);

        this.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VCrearRutina.class);
            startActivity(intent);
        });

        return view;
    }

    public void cargarRutinas(List<Rutina> listado) {
        this.listadoRutinas = listado;
    }

    public void verificarVacio() {
        if (this.listadoRutinas.isEmpty()) {
            this.emptyLayout.setVisibility(LinearLayout.VISIBLE);
        } else {
            this.emptyLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.cargarRutinas();
        this.listAdapter.refreshData(this.listadoRutinas);
        verificarVacio();
    }
}