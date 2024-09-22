package com.android.kotlin.personaltrainer.view.Ejercicio;

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
import com.android.kotlin.personaltrainer.controller.CEjercicio;
import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.CategoriaEjercicioAdapter;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VAgregarCategoriaEjercicio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VEjercicio extends Fragment {

    CEjercicio controller;
    FloatingActionButton fab;
    EjercicioAdapter listAdapter;
    RecyclerView recyclerView;
    List<Ejercicio> listadoEjercicios;
    LinearLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_ejercicio, container, false);

        this.controller = new CEjercicio(this);

        this.fab = view.findViewById(R.id.btn_agregar_ejercicio);
        this.recyclerView = view.findViewById(R.id.recyclerview_ejercicio);
        this.emptyLayout = view.findViewById(R.id.empty_layout);

        controller.cargarEjercicios();

        this.listAdapter = new EjercicioAdapter(getActivity(), this.listadoEjercicios);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.listAdapter);

        this.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VAgregarEjercicio.class);
            startActivity(intent);
        });

        return view;
    }

    public void cargarEjercicios(List<Ejercicio> listado) {
        this.listadoEjercicios = listado;
    }


    public void verificarVacio() {
        if (this.listadoEjercicios.isEmpty()) {
            this.emptyLayout.setVisibility(LinearLayout.VISIBLE);
        } else {
            this.emptyLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.cargarEjercicios();
        this.listAdapter.refreshData(this.listadoEjercicios);
        verificarVacio();
    }
}