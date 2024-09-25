package com.android.kotlin.personaltrainer.controller;

import android.content.Context;

import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.model.Cliente.MCliente;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.DetallePlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.MPlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.PlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.Rutina.MRutina;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.view.PlanEntrenamiento.VCrearPlanEntrenamiento;
import com.android.kotlin.personaltrainer.view.PlanEntrenamiento.VDetallePlanEntrenamiento;
import com.android.kotlin.personaltrainer.view.PlanEntrenamiento.VEditarPlanEntrenamiento;
import com.android.kotlin.personaltrainer.view.PlanEntrenamiento.VPlanEntrenamiento;

import java.util.List;

public class CPlanEntrenamiento {

    private final MPlanEntrenamiento modeloPlanEntrenamiento;
    private final MRutina modeloRutina;
    private final MCliente modeloCliente;
    private VPlanEntrenamiento viewListar;
    private VCrearPlanEntrenamiento viewAgregar;
    private VEditarPlanEntrenamiento viewEditar;
    private VDetallePlanEntrenamiento viewDetalle;

    // Constructor para Fragments
    public CPlanEntrenamiento(VPlanEntrenamiento fragment) {
        this.viewListar = fragment;
        this.modeloPlanEntrenamiento = new MPlanEntrenamiento(fragment.getContext());
        this.modeloRutina = new MRutina(fragment.getContext());
        this.modeloCliente = new MCliente(fragment.getContext());
    }

    // Constructor para activities
    public CPlanEntrenamiento(Context context) {
        this.modeloPlanEntrenamiento = new MPlanEntrenamiento(context);
        this.modeloRutina = new MRutina(context);
        this.modeloCliente = new MCliente(context);

        if (context instanceof VCrearPlanEntrenamiento) {
            this.viewAgregar = (VCrearPlanEntrenamiento) context;
        } else if (context instanceof VEditarPlanEntrenamiento) {
            this.viewEditar = (VEditarPlanEntrenamiento) context;
        } else if (context instanceof VDetallePlanEntrenamiento) {
            this.viewDetalle = (VDetallePlanEntrenamiento) context;
        }
    }

    public void guardarPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        long resultado = this.modeloPlanEntrenamiento.insertarPlanEntrenamiento(planEntrenamiento);
        this.viewAgregar.mostrarMensaje(resultado > 0 ? "Plan de entrenamiento guardado" : "Error al guardar el plan de entrenamiento");
        this.viewAgregar.finish();
    }

    public void actualizarPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        int resultado = this.modeloPlanEntrenamiento.actualizarPlanEntrenamiento(planEntrenamiento);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Plan de entrenamiento actualizado" : "Error al actualizar el plan de entrenamiento");
        this.viewEditar.finish();
    }

    public void eliminarPlanEntrenamiento(int idPlanEntrenamiento) {
        int resultado = this.modeloPlanEntrenamiento.eliminarPlanEntrenamiento(idPlanEntrenamiento);
        this.viewEditar.mostrarMensaje(resultado > 0 ? "Plan de entrenamiento eliminado" : "Error al eliminar el plan de entrenamiento");
        this.viewEditar.finish();
    }

    public void cargarClientes() {
        List<Cliente> listado = this.modeloCliente.obtenerTodosLosClientes();
        if (this.viewEditar != null) {
            this.viewEditar.cargarClientes(listado);
        } else if (this.viewAgregar != null) {
            this.viewAgregar.cargarClientes(listado);
        }
    }

    public void cargarRutinas() {
        List<Rutina> rutinas = this.modeloRutina.obtenerTodasLasRutinas();
        this.viewDetalle.cargarRutinas(rutinas);
    }

    public void cargarPlanesEntrenamiento() {
        List<PlanEntrenamiento> listado = this.modeloPlanEntrenamiento.obtenerTodosLosPlanesEntrenamiento();
        this.viewListar.cargarPlanesEntrenamiento(listado);
    }

    public void guardarDetallePlanEntrenamiento(DetallePlanEntrenamiento detallePlanEntrenamiento) {
        long resultado = this.modeloPlanEntrenamiento.insertarDetallePlanEntrenamiento(detallePlanEntrenamiento);
        this.viewDetalle.mostrarMensaje(resultado > 0 ? "Detalle de plan de entrenamiento guardado" : "Error al guardar el detalle del plan de entrenamiento");
    }

    public void eliminarDetallePlanEntrenamiento(int idPlanEntrenamiento, int idRutina) {
        int resultado = this.modeloPlanEntrenamiento.eliminarRutinaDetallePlanEntrenamiento(idPlanEntrenamiento, idRutina);
        this.viewDetalle.mostrarMensaje(resultado > 0 ? "Detalle de plan de entrenamiento eliminado" : "Error al eliminar el detalle del plan de entrenamiento");
    }

    public void cargarDetallePlanEntrenamiento(int idPlanEntrenamiento) {
        List<DetallePlanEntrenamiento> listado = this.modeloPlanEntrenamiento.obtenerDetallePlanEntrenamiento(idPlanEntrenamiento);
        this.viewDetalle.cargarDetallePlanEntrenamiento(listado);
    }

    public void cargarCliente(int idCliente) {
        Cliente cliente = this.modeloCliente.obtenerClientePorId(idCliente);
        this.viewDetalle.cargarCliente(cliente);
    }

}
