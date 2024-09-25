package com.android.kotlin.personaltrainer.view.PlanEntrenamiento;

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
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.PlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.view.Rutina.VDetalleRutina;
import com.android.kotlin.personaltrainer.view.Rutina.VEditarRutina;

import java.util.List;

public class PlanEntrenamientoAdapter extends RecyclerView.Adapter<PlanEntrenamientoAdapter.PlanEntrenamientoViewHolder> {

    private final Context context;
    private List<PlanEntrenamiento> listadoPlanes;

    public PlanEntrenamientoAdapter(Context context, List<PlanEntrenamiento> listadoPlanes) {
        this.context = context;
        this.listadoPlanes = listadoPlanes;
    }

    public static class PlanEntrenamientoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, descripcionTextView, fechaInicioTextView, fechaFinTextView, tipoTextView, clienteTextView;
        ImageView editarButton;
        CardView cardView;

        public PlanEntrenamientoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreTextView = itemView.findViewById(R.id.nombreTextView);
            this.descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            this.fechaInicioTextView = itemView.findViewById(R.id.fechaInicioTextView);
            this.fechaFinTextView = itemView.findViewById(R.id.fechaFinTextView);
            this.clienteTextView = itemView.findViewById(R.id.clienteTextView);
            this.tipoTextView = itemView.findViewById(R.id.tipoTextView);
            this.editarButton = itemView.findViewById(R.id.editarButton);
            this.cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @NonNull
    @Override
    public PlanEntrenamientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_plan_entrenamiento, parent, false);
        return new PlanEntrenamientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanEntrenamientoViewHolder holder, int position) {
        PlanEntrenamiento plan = this.listadoPlanes.get(position);
        holder.nombreTextView.setText(plan.getNombre());
        holder.descripcionTextView.setText(plan.getDescripcion());
        holder.fechaInicioTextView.setText(plan.getFechaInicio());
        holder.fechaFinTextView.setText(plan.getFechaFin());
        holder.tipoTextView.setText(plan.getTipo());
        holder.clienteTextView.setText(String.valueOf(plan.getIdCliente()));

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, VDetallePlanEntrenamiento.class);
            intent.putExtra("id", String.valueOf(plan.getId()));
            intent.putExtra("nombre", plan.getNombre());
            intent.putExtra("descripcion", plan.getDescripcion());
            intent.putExtra("fechaInicio", plan.getFechaInicio());
            intent.putExtra("fechaFin", plan.getFechaFin());
            intent.putExtra("tipo", plan.getTipo());
            intent.putExtra("idCliente", plan.getIdCliente());
            context.startActivity(intent);
        });

        holder.editarButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, VEditarPlanEntrenamiento.class);
            intent.putExtra("id", String.valueOf(plan.getId()));
            intent.putExtra("nombre", plan.getNombre());
            intent.putExtra("descripcion", plan.getDescripcion());
            intent.putExtra("fechaInicio", plan.getFechaInicio());
            intent.putExtra("fechaFin", plan.getFechaFin());
            intent.putExtra("tipo", plan.getTipo());
            intent.putExtra("idCliente", plan.getIdCliente());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.listadoPlanes.size();
    }

    public void refreshData(List<PlanEntrenamiento> listadoPlanes) {
        this.listadoPlanes = listadoPlanes;
        notifyDataSetChanged();
    }

}
