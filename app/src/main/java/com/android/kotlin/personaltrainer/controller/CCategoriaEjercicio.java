package com.android.kotlin.personaltrainer.controller;

import android.content.Context;

import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.MCategoriaEjercicio;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VAgregarCategoriaEjercicio;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VEditarCategoriaEjercicio;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VCategoriaEjercicio;

import java.util.List;

public class CCategoriaEjercicio {

    private final MCategoriaEjercicio MCategoriaEjercicio;
    private VCategoriaEjercicio viewListar;
    private VAgregarCategoriaEjercicio viewAgregar;
    private VEditarCategoriaEjercicio viewEditar;

    // Constructor para Fragments
    public CCategoriaEjercicio(VCategoriaEjercicio fragment) {
        this.viewListar = fragment;
        this.MCategoriaEjercicio = new MCategoriaEjercicio(fragment.getContext());
    }

    // Constructor para activities
    public CCategoriaEjercicio(Context context) {
        this.MCategoriaEjercicio = new MCategoriaEjercicio(context);

        if (context instanceof VAgregarCategoriaEjercicio) {
            this.viewAgregar = (VAgregarCategoriaEjercicio) context;
        } else if (context instanceof VEditarCategoriaEjercicio) {
            this.viewEditar = (VEditarCategoriaEjercicio) context;
        }
    }

    public void guardarCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
        long resultado = this.MCategoriaEjercicio.insertarCategoriaEjercicio(categoriaEjercicio);
        this.viewAgregar.mostrarMensaje(resultado > 0 ? "Categoria de ejercicio guardado" : "Error al guardar la categoria de ejercicio");
        this.viewAgregar.finish();
    }

    public void actualizarCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
        int resultado = this.MCategoriaEjercicio.actualizarCategoriaEjercicio(categoriaEjercicio);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Categoria de ejercicio actualizado" : "Error al actualizar la categoria de ejercicio");
        this.viewEditar.finish();
    }

    public void eliminarCategoriaEjercicio(int idCategoriaEjercicio) {
        int resultado = this.MCategoriaEjercicio.eliminarCategoriaEjercicio(idCategoriaEjercicio);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Categoria de ejercicio eliminado" : "Error al eliminar la categoria de ejercicio");
        this.viewEditar.finish();
    }

    public void cargarCategoriaEjercicios() {
        List<CategoriaEjercicio> listado = this.MCategoriaEjercicio.listarCategoriaEjercicio();
        this.viewListar.cargarCategoriaEjercicios(listado);
    }

}
