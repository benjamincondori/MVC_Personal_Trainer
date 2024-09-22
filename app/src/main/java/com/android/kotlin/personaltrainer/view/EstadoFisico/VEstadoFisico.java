package com.android.kotlin.personaltrainer.view.EstadoFisico;

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
import com.android.kotlin.personaltrainer.controller.CEstadoFisico;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.model.EstadoFisico.EstadoFisico;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class VEstadoFisico extends Fragment {

    CEstadoFisico controller;
    FloatingActionButton fab;
    EstadoFisicoAdapter listAdapter;
    RecyclerView recyclerView;
    List<EstadoFisico> listadoEstadoFisico;
    List<Cliente> listaClientes;
    LinearLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_estado_fisico, container, false);

        this.controller = new CEstadoFisico(this);

        this.fab = view.findViewById(R.id.btn_agregar_estado_fisico);
        this.recyclerView = view.findViewById(R.id.recyclerview_estado_fisico);
        this.emptyLayout = view.findViewById(R.id.empty_layout);

        controller.cargarEstadosFisicos();
        controller.cargarClientes();

        this.listAdapter = new EstadoFisicoAdapter(getActivity(), this.listadoEstadoFisico, this.listaClientes);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.listAdapter);

        this.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VAgregarEstadoFisico.class);
            startActivity(intent);
        });

        return view;
    }

    public void cargarEstadosFisicos(List<EstadoFisico> listado) {
        this.listadoEstadoFisico = listado;
    }

    public void cargarClientes(List<Cliente> listado) {
        this.listaClientes = listado;
    }

    public void verificarVacio() {
        if (this.listadoEstadoFisico.isEmpty()) {
            this.emptyLayout.setVisibility(LinearLayout.VISIBLE);
        } else {
            this.emptyLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.cargarEstadosFisicos();
        controller.cargarClientes();
        this.listAdapter.refreshData(this.listadoEstadoFisico);
        verificarVacio();
    }
}