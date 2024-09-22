package com.android.kotlin.personaltrainer.view.Cliente;

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
import com.android.kotlin.personaltrainer.controller.CCliente;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VCliente extends Fragment {

    CCliente controller;
    FloatingActionButton fab;
    ClienteAdapter listAdapter;
    RecyclerView recyclerView;
    List<Cliente> listadoClientes;
    LinearLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_cliente, container, false);

        this.controller = new CCliente(this);

        this.fab = view.findViewById(R.id.btn_agregar_cliente);
        this.recyclerView = view.findViewById(R.id.recyclerview_cliente);
        this.emptyLayout = view.findViewById(R.id.empty_layout);

        controller.cargarClientes();

        this.listAdapter = new ClienteAdapter(getActivity(), this.listadoClientes);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.listAdapter);

        this.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VAgregarCliente.class);
            startActivity(intent);
        });

        return view;
    }

    public void cargarClientes(List<Cliente> listado) {
        this.listadoClientes = listado;
    }


    public void verificarVacio() {
        if (this.listadoClientes.isEmpty()) {
            this.emptyLayout.setVisibility(LinearLayout.VISIBLE);
        } else {
            this.emptyLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.cargarClientes();
        this.listAdapter.refreshData(this.listadoClientes);
        verificarVacio();
    }
}