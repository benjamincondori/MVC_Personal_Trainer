package com.android.kotlin.personaltrainer.view.Rutina;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CRutina;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.model.Rutina.DetalleRutinaEjercicio;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;

import java.util.List;

public class RutinaDetalleAdapter extends RecyclerView.Adapter<RutinaDetalleAdapter.RutinaDetalleViewHolder> {

    private final Context context;
    private List<DetalleRutinaEjercicio> listadoDetalleRutina;
    private List<Ejercicio> listadoEjercicios;
    private CRutina controller;

    public RutinaDetalleAdapter(Context context, List<DetalleRutinaEjercicio> listadoDetalleRutina, List<Ejercicio> listadoEjercicios, CRutina controller) {
        this.context = context;
        this.listadoDetalleRutina = listadoDetalleRutina;
        this.listadoEjercicios = listadoEjercicios;
        this.controller = controller;
    }

    public static class RutinaDetalleViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, descripcionTextView, serieTextView, repeticionesTextView, descansoTextView, notaTextView;
        ImageView eliminarButton;

        public RutinaDetalleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreTextView = itemView.findViewById(R.id.nombreTextView);
            this.descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            this.serieTextView = itemView.findViewById(R.id.serieTextView);
            this.repeticionesTextView = itemView.findViewById(R.id.repeticionesTextView);
            this.descansoTextView = itemView.findViewById(R.id.descansoTextView);
            this.notaTextView = itemView.findViewById(R.id.notaTextView);
            this.eliminarButton = itemView.findViewById(R.id.eliminarButton);
        }
    }

    @NonNull
    @Override
    public RutinaDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_rutina_detalle, parent, false);
        return new RutinaDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutinaDetalleViewHolder holder, int position) {
        DetalleRutinaEjercicio detalle = this.listadoDetalleRutina.get(position);
        Ejercicio ejercicio = obtenerEjercicioPorId(detalle.getIdEjercicio());

        if (ejercicio != null) {
            holder.nombreTextView.setText(ejercicio.getNombre());
            holder.descripcionTextView.setText(ejercicio.getDescripcion());
        }

        holder.serieTextView.setText(String.valueOf(detalle.getSeries()));
        holder.repeticionesTextView.setText(String.valueOf(detalle.getRepeticiones()));
        holder.descansoTextView.setText(String.valueOf(detalle.getDescanso()));
        holder.notaTextView.setText(getDetalleEjercicio(detalle));

        holder.eliminarButton.setOnClickListener(view -> {
            confirmDialog(detalle.getIdRutina(), detalle.getIdEjercicio());
        });
    }

    private void confirmDialog(int idRutina, int idEjercicio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Quitar ejercicio");
        builder.setMessage("¿Estás seguro de que deseas quitar este ejercicio?");
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            this.controller.eliminarDetalleRutinaEjercicio(idRutina, idEjercicio);
            dialogInterface.dismiss();

            if (context instanceof VDetalleRutina) {
                ((VDetalleRutina) context).onResume();
            }
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();
    }

    private String getDetalleEjercicio(DetalleRutinaEjercicio detalle) {
        String detalleEjercicio;
        if (detalle.getSeries() == 1) {
            detalleEjercicio = "Realizar 1 serie de " + detalle.getRepeticiones() + " repeticiones, con un descanso de "
                    + detalle.getDescanso() + " segundos.";
        } else {
            detalleEjercicio = "Realizar " + detalle.getSeries() + " series de " +
                    detalle.getRepeticiones() + " repeticiones cada serie, con un descanso de "
                    + detalle.getDescanso() + " segundos entre series.";
        }
        return detalleEjercicio;
    }

    @Override
    public int getItemCount() {
        return this.listadoDetalleRutina.size();
    }

    public void refreshData(List<DetalleRutinaEjercicio> listadoDetalleRutina) {
        this.listadoDetalleRutina = listadoDetalleRutina;
        notifyDataSetChanged();
    }

    private Ejercicio obtenerEjercicioPorId(int idEjercicio) {
        for (Ejercicio ejercicio : this.listadoEjercicios) {
            if (ejercicio.getId() == idEjercicio) {
                return ejercicio;
            }
        }
        return null;
    }

}
