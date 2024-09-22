package com.android.kotlin.personaltrainer.controller;

import android.content.Context;

import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.model.Cliente.MCliente;
import com.android.kotlin.personaltrainer.model.EstadoFisico.EstadoFisico;
import com.android.kotlin.personaltrainer.model.EstadoFisico.MEstadoFisico;
import com.android.kotlin.personaltrainer.view.EstadoFisico.VAgregarEstadoFisico;
import com.android.kotlin.personaltrainer.view.EstadoFisico.VEditarEstadoFisico;
import com.android.kotlin.personaltrainer.view.EstadoFisico.VEstadoFisico;

import java.util.List;

public class CEstadoFisico {

    private final MEstadoFisico modeloEstadoFisico;
    private final MCliente modeloCliente;
    private VEstadoFisico viewListar;
    private VAgregarEstadoFisico viewAgregar;
    private VEditarEstadoFisico viewEditar;

    // Constructor para Fragments
    public CEstadoFisico(VEstadoFisico fragment) {
        this.viewListar = fragment;
        this.modeloEstadoFisico = new MEstadoFisico(fragment.getContext());
        this.modeloCliente = new MCliente(fragment.getContext());
    }

    // Constructor para activities
    public CEstadoFisico(Context context) {
        this.modeloEstadoFisico = new MEstadoFisico(context);
        this.modeloCliente = new MCliente(context);

        if (context instanceof VAgregarEstadoFisico) {
            this.viewAgregar = (VAgregarEstadoFisico) context;
        } else if (context instanceof VEditarEstadoFisico) {
            this.viewEditar = (VEditarEstadoFisico) context;
        }
    }

    public void guardarEstadoFisico(EstadoFisico estadoFisico) {
        long resultado = this.modeloEstadoFisico.insertarEstadoFisico(estadoFisico);
        this.viewAgregar.mostrarMensaje(resultado > 0 ? "Estado Físico guardado" : "Error al guardar el estado físico");
        this.viewAgregar.finish();
    }

    public void actualizarEstadoFisico(EstadoFisico estadoFisico) {
        int resultado = this.modeloEstadoFisico.actualizarEstadoFisico(estadoFisico);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Estado Físico actualizado" : "Error al actualizar el estado físico");
        this.viewEditar.finish();
    }

    public void eliminarEstadoFisico(int idEstadoFisico) {
        int resultado = this.modeloEstadoFisico.eliminarEstadoFisico(idEstadoFisico);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Estado Físico eliminado" : "Error al eliminar el estado físico");
        this.viewEditar.finish();
    }

    public void cargarEstadosFisicos() {
        List<EstadoFisico> listado = this.modeloEstadoFisico.obtenerTodosLosEstadosFisicos();
        this.viewListar.cargarEstadosFisicos(listado);
    }

    public void cargarClientes() {
        List<Cliente> listadoClientes = this.modeloCliente.obtenerTodosLosClientes();
        if (this.viewAgregar != null) {
            this.viewAgregar.cargarClientes(listadoClientes);
        } else if (this.viewEditar != null) {
            this.viewEditar.cargarClientes(listadoClientes);
        } else if (this.viewListar != null) {
            this.viewListar.cargarClientes(listadoClientes);
        }
    }

}
