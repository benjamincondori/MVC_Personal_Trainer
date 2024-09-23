package com.android.kotlin.personaltrainer.view.CategoriaEjercicio;

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
import com.android.kotlin.personaltrainer.controller.CCategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VCategoriaEjercicio extends Fragment {

    CCategoriaEjercicio controller;
    FloatingActionButton fab;
    CategoriaEjercicioAdapter listAdapter;
    RecyclerView recyclerView;
    List<CategoriaEjercicio> listadoCategoriaEjercicios;
    LinearLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_categoria_ejercicio, container, false);

        this.controller = new CCategoriaEjercicio(this);

        this.fab = view.findViewById(R.id.btn_add_tipo);
        this.recyclerView = view.findViewById(R.id.recyclerview_tipo_ejercicios);
        this.emptyLayout = view.findViewById(R.id.empty_layout);

        controller.cargarCategoriaEjercicios();

        this.listAdapter = new CategoriaEjercicioAdapter(getActivity(), this.listadoCategoriaEjercicios);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.listAdapter);

        this.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VCrearCategoriaEjercicio.class);
            startActivity(intent);
        });

        return view;
    }

    public void cargarCategoriaEjercicios(List<CategoriaEjercicio> listado) {
        this.listadoCategoriaEjercicios = listado;
    }

    public void verificarVacio() {
        if (this.listadoCategoriaEjercicios.isEmpty()) {
            this.emptyLayout.setVisibility(LinearLayout.VISIBLE);
        } else {
            this.emptyLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.cargarCategoriaEjercicios();
        this.listAdapter.refreshData(this.listadoCategoriaEjercicios);
        verificarVacio();
    }
}