package com.android.kotlin.personaltrainer.controller;

import android.content.Context;

import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.MCategoriaEjercicio;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VCrearCategoriaEjercicio;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VEditarCategoriaEjercicio;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VCategoriaEjercicio;

import java.util.List;

public class CCategoriaEjercicio {

    private final MCategoriaEjercicio modeloCategoriaEjercicio;
    private VCategoriaEjercicio viewListar;
    private VCrearCategoriaEjercicio viewAgregar;
    private VEditarCategoriaEjercicio viewEditar;

    // Constructor para Fragments
    public CCategoriaEjercicio(VCategoriaEjercicio fragment) {
        this.viewListar = fragment;
        this.modeloCategoriaEjercicio = new MCategoriaEjercicio(fragment.getContext());
    }

    // Constructor para activities
    public CCategoriaEjercicio(Context context) {
        this.modeloCategoriaEjercicio = new MCategoriaEjercicio(context);

        if (context instanceof VCrearCategoriaEjercicio) {
            this.viewAgregar = (VCrearCategoriaEjercicio) context;
        } else if (context instanceof VEditarCategoriaEjercicio) {
            this.viewEditar = (VEditarCategoriaEjercicio) context;
        }
    }

    public void guardarCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
        long resultado = this.modeloCategoriaEjercicio.insertarCategoriaEjercicio(categoriaEjercicio);
        if (resultado > 0) {
            this.viewAgregar.mostrarMensaje("Categoria de ejercicio guardado");
            this.viewAgregar.finish();
        } else {
            this.viewAgregar.mostrarMensaje("Error al guardar la categoria de ejercicio");
        }
    }

    public void actualizarCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
        int resultado = this.modeloCategoriaEjercicio.actualizarCategoriaEjercicio(categoriaEjercicio);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Categoria de ejercicio actualizado" : "Error al actualizar la categoria de ejercicio");
        this.viewEditar.finish();
    }

    public void eliminarCategoriaEjercicio(int idCategoriaEjercicio) {
        int resultado = this.modeloCategoriaEjercicio.eliminarCategoriaEjercicio(idCategoriaEjercicio);
        if (resultado > 0) {
            this.viewEditar.mostrarMensaje("Categoria de ejercicio eliminado");
            this.viewEditar.finish();
        } else {
            this.viewEditar.mostrarMensaje("Error al eliminar la categoria de ejercicio");
        }
    }

    public void cargarCategoriaEjercicios() {
        List<CategoriaEjercicio> listado = this.modeloCategoriaEjercicio.obtenerTodasLasCategorias();
        this.viewListar.cargarCategoriaEjercicios(listado);
    }

}
