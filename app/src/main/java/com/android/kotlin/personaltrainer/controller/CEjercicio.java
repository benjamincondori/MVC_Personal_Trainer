package com.android.kotlin.personaltrainer.controller;

import android.content.Context;

import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.MCategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.model.Ejercicio.MEjercicio;
import com.android.kotlin.personaltrainer.view.Ejercicio.VCrearEjercicio;
import com.android.kotlin.personaltrainer.view.Ejercicio.VEditarEjercicio;
import com.android.kotlin.personaltrainer.view.Ejercicio.VEjercicio;

import java.util.List;

public class CEjercicio {

    private final MEjercicio modeloEjercicio;
    private final MCategoriaEjercicio modeloCategoriaEjercicio;
    private VEjercicio viewListar;
    private VCrearEjercicio viewAgregar;
    private VEditarEjercicio viewEditar;

    // Constructor para Fragments
    public CEjercicio(VEjercicio fragment) {
        this.viewListar = fragment;
        this.modeloEjercicio = new MEjercicio(fragment.getContext());
        this.modeloCategoriaEjercicio = new MCategoriaEjercicio(fragment.getContext());
    }

    // Constructor para activities
    public CEjercicio(Context context) {
        this.modeloEjercicio = new MEjercicio(context);
        this.modeloCategoriaEjercicio = new MCategoriaEjercicio(context);

        if (context instanceof VCrearEjercicio) {
            this.viewAgregar = (VCrearEjercicio) context;
        } else if (context instanceof VEditarEjercicio) {
            this.viewEditar = (VEditarEjercicio) context;
        }
    }

    public void guardarEjercicio(Ejercicio ejercicio) {
        long resultado = this.modeloEjercicio.insertarEjercicio(ejercicio);
        this.viewAgregar.mostrarMensaje(resultado > 0 ? "Ejercicio guardado" : "Error al guardar el ejercicio");
        this.viewAgregar.finish();
    }

    public void actualizarEjercicio(Ejercicio ejercicio) {
        int resultado = this.modeloEjercicio.actualizarEjercicio(ejercicio);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Ejercicio actualizado" : "Error al actualizar el ejercicio");
        this.viewEditar.finish();
    }

    public void eliminarEjercicio(int idEjercicio) {
        int resultado = this.modeloEjercicio.eliminarEjercicio(idEjercicio);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Ejercicio eliminado" : "Error al eliminar el ejercicio");
        this.viewEditar.finish();
    }

    public void cargarEjercicios() {
        List<Ejercicio> listado = this.modeloEjercicio.obtenerTodosLosEjercicios();
        this.viewListar.cargarEjercicios(listado);
    }

    public void cargarCategorias() {
        List<CategoriaEjercicio> listadoCategorias = this.modeloCategoriaEjercicio.obtenerTodasLasCategorias();
        if (this.viewAgregar != null) {
            this.viewAgregar.cargarCategorias(listadoCategorias);
        } else if (this.viewEditar != null) {
            this.viewEditar.cargarCategorias(listadoCategorias);
        }
    }

}
