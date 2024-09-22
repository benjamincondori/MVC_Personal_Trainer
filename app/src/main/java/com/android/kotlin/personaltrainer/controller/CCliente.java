package com.android.kotlin.personaltrainer.controller;

import android.content.Context;

import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.model.Cliente.MCliente;
import com.android.kotlin.personaltrainer.view.Cliente.VAgregarCliente;
import com.android.kotlin.personaltrainer.view.Cliente.VCliente;
import com.android.kotlin.personaltrainer.view.Cliente.VEditarCliente;

public class CCliente {

    private final MCliente modeloCliente;
    private VCliente viewListar;
    private VAgregarCliente viewAgregar;
    private VEditarCliente viewEditar;

    // Constructor para Fragments
    public CCliente(VCliente fragment) {
        this.viewListar = fragment;
        this.modeloCliente = new MCliente(fragment.getContext());
    }

    // Constructor para activities
    public CCliente(Context context) {
        this.modeloCliente = new MCliente(context);

        if (context instanceof VAgregarCliente) {
            this.viewAgregar = (VAgregarCliente) context;
        } else if (context instanceof VEditarCliente) {
            this.viewEditar = (VEditarCliente) context;
        }
    }

    public void guardarCliente(Cliente cliente) {
        long resultado = this.modeloCliente.insertarCliente(cliente);
        this.viewAgregar.mostrarMensaje(resultado > 0 ? "Cliente guardado" : "Error al guardar el cliente");
        this.viewAgregar.finish();
    }

    public void actualizarCliente(Cliente cliente) {
        int resultado = this.modeloCliente.actualizarCliente(cliente);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Cliente actualizado" : "Error al actualizar el cliente");
        this.viewEditar.finish();
    }

    public void eliminarCliente(int idCliente) {
        int resultado = this.modeloCliente.eliminarCliente(idCliente);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Cliente eliminado" : "Error al eliminar el cliente");
        this.viewEditar.finish();
    }

    public void cargarClientes() {
        this.viewListar.cargarClientes(this.modeloCliente.obtenerTodosLosClientes());
    }

}
