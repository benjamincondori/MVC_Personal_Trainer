package com.android.kotlin.personaltrainer.controller;

import android.content.Context;

import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.MCategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.model.Ejercicio.MEjercicio;
import com.android.kotlin.personaltrainer.model.Rutina.DetalleRutinaEjercicio;
import com.android.kotlin.personaltrainer.model.Rutina.MRutina;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.view.Ejercicio.VEjercicio;
import com.android.kotlin.personaltrainer.view.Rutina.VCrearRutina;
import com.android.kotlin.personaltrainer.view.Rutina.VDetalleRutina;
import com.android.kotlin.personaltrainer.view.Rutina.VEditarRutina;
import com.android.kotlin.personaltrainer.view.Rutina.VRutina;

import java.util.List;

public class CRutina {

    private final MRutina modeloRutina;
    private final MEjercicio modeloEjercicio;
    private VRutina viewListar;
    private VCrearRutina viewAgregar;
    private VEditarRutina viewEditar;
    private VDetalleRutina viewDetalle;

    // Constructor para Fragments
    public CRutina(VRutina fragment) {
        this.viewListar = fragment;
        this.modeloRutina = new MRutina(fragment.getContext());
        this.modeloEjercicio = new MEjercicio(fragment.getContext());
    }

    // Constructor para activities
    public CRutina(Context context) {
        this.modeloRutina = new MRutina(context);
        this.modeloEjercicio = new MEjercicio(context);

        if (context instanceof VCrearRutina) {
            this.viewAgregar = (VCrearRutina) context;
        } else if (context instanceof VEditarRutina) {
            this.viewEditar = (VEditarRutina) context;
        } else if (context instanceof VDetalleRutina) {
            this.viewDetalle = (VDetalleRutina) context;
        }
    }

    public void guardarRutina(Rutina rutina) {
        long resultado = this.modeloRutina.insertarRutina(rutina);
        this.viewAgregar.mostrarMensaje(resultado > 0 ? "Rutina guardada" : "Error al guardar la rutina");
        this.viewAgregar.finish();
    }

    public void actualizarRutina(Rutina rutina) {
        int resultado = this.modeloRutina.actualizarRutina(rutina);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Rutina actualizada" : "Error al actualizar la rutina");
        this.viewEditar.finish();
    }

    public void eliminarRutina(int idRutina) {
        int resultado = this.modeloRutina.eliminarRutina(idRutina);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Rutina eliminada" : "Error al eliminar la rutina");
        this.viewEditar.finish();
    }

    public void cargarRutinas() {
        List<Rutina> listado = this.modeloRutina.obtenerTodasLasRutinas();
        this.viewListar.cargarRutinas(listado);
    }

    public void cargarEjercicios() {
        List<Ejercicio> listado = this.modeloEjercicio.obtenerTodosLosEjercicios();
        this.viewDetalle.cargarEjercicios(listado);
    }

    public void guardarDetalleRutina(DetalleRutinaEjercicio detalle) {
        long resultado = this.modeloRutina.insertarDetalleRutina(detalle);
        this.viewDetalle.mostrarMensaje(resultado > 0 ? "Ejercicio agregado" : "Error al agregar el ejercicio");
//        this.viewDetalle.finish();
    }

    public void eliminarDetalleRutinaEjercicio(int idRutina, int idEjercicio) {
        int resultado = this.modeloRutina.eliminarDetalleRutinaEjercicio(idRutina, idEjercicio);
        this.viewDetalle.mostrarMensaje(resultado > 0 ? "Ejercicio eliminado" : "Error al eliminar el ejercicio");
        // actualizar la lista de detalles
    }

    public void cargarDetalleRutina(int idRutina) {
        List<DetalleRutinaEjercicio> listado = this.modeloRutina.obtenerDetalleRutina(idRutina);
        this.viewDetalle.cargarDetalleRutina(listado);
    }

}
