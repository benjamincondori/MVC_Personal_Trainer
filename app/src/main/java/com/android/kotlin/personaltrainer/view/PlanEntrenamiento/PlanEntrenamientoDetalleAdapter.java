package com.android.kotlin.personaltrainer.view.PlanEntrenamiento;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CPlanEntrenamiento;
import com.android.kotlin.personaltrainer.controller.CRutina;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.DetallePlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.Rutina.DetalleRutinaEjercicio;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.view.Rutina.VDetalleRutina;

import java.util.List;

public class PlanEntrenamientoDetalleAdapter extends RecyclerView.Adapter<PlanEntrenamientoDetalleAdapter.PlanEntrenamientoDetalleViewHolder> {

    private final Context context;
    private List<DetallePlanEntrenamiento> listadoDetallePlanes;
    private List<Rutina> listadoRutinas;
    private CPlanEntrenamiento controller;

    public PlanEntrenamientoDetalleAdapter(Context context, List<DetallePlanEntrenamiento> listadoDetallePlanes, List<Rutina> listadoRutinas, CPlanEntrenamiento controller) {
        this.context = context;
        this.listadoDetallePlanes = listadoDetallePlanes;
        this.listadoRutinas = listadoRutinas;
        this.controller = controller;
    }

    public static class PlanEntrenamientoDetalleViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, descripcionTextView, diaTextView;
        ImageView eliminarButton;

        public PlanEntrenamientoDetalleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreTextView = itemView.findViewById(R.id.nombreTextView);
            this.descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            this.diaTextView = itemView.findViewById(R.id.diaTextView);
            this.eliminarButton = itemView.findViewById(R.id.eliminarButton);
        }
    }

    @NonNull
    @Override
    public PlanEntrenamientoDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_plan_entrenamiento_detalle, parent, false);
        return new PlanEntrenamientoDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanEntrenamientoDetalleViewHolder holder, int position) {
        DetallePlanEntrenamiento detalle = this.listadoDetallePlanes.get(position);
        Rutina rutina = obtenerRutinaPorId(detalle.getIdRutina());

        if (rutina != null) {
            holder.nombreTextView.setText(rutina.getNombre());
            holder.descripcionTextView.setText(rutina.getDescripcion());
        }

        holder.diaTextView.setText(detalle.getDia());
        holder.eliminarButton.setOnClickListener(view -> {
            confirmDialog(detalle.getIdPlanEntrenamiento(), detalle.getIdRutina());
        });
    }

    private void confirmDialog(int idPlanEntrenamiento, int idRutina) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Quitar ejercicio");
        builder.setMessage("¿Estás seguro de que deseas quitar este ejercicio?");
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            this.controller.eliminarDetallePlanEntrenamiento(idPlanEntrenamiento, idRutina);
            dialogInterface.dismiss();

            if (context instanceof VDetallePlanEntrenamiento) {
                ((VDetallePlanEntrenamiento) context).onResume();
            }
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return this.listadoDetallePlanes.size();
    }

    public void refreshData(List<DetallePlanEntrenamiento> listadoDetallePlanes) {
        this.listadoDetallePlanes = listadoDetallePlanes;
        notifyDataSetChanged();
    }

    private Rutina obtenerRutinaPorId(int idEjercicio) {
        for (Rutina rutina : this.listadoRutinas) {
            if (rutina.getId() == idEjercicio) {
                return rutina;
            }
        }
        return null;
    }

}
