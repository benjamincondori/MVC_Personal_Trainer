package com.android.kotlin.personaltrainer.view.Rutina;

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
import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VEditarCategoriaEjercicio;

import java.util.List;

public class RutinaAdapter extends RecyclerView.Adapter<RutinaAdapter.RutinaViewHolder> {

    private final Context context;
    private List<Rutina> listadoRutinas;

    public RutinaAdapter(Context context, List<Rutina> listadoRutinas) {
        this.context = context;
        this.listadoRutinas = listadoRutinas;
    }

    public static class RutinaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreRutina, descripcionRutina;
        ImageView editarButton;
        CardView cardView;

        public RutinaViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreRutina = itemView.findViewById(R.id.nombreTextView);
            this.descripcionRutina = itemView.findViewById(R.id.descripcionTextView);
            this.editarButton = itemView.findViewById(R.id.editarButton);
            this.cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @NonNull
    @Override
    public RutinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_categoria_ejercicio, parent, false);
        return new RutinaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutinaViewHolder holder, int position) {
        Rutina rutina = this.listadoRutinas.get(position);
        holder.nombreRutina.setText(rutina.getNombre());
        holder.descripcionRutina.setText(rutina.getDescripcion());

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, VDetalleRutina.class);
            intent.putExtra("id", String.valueOf(rutina.getId()));
            intent.putExtra("nombre", rutina.getNombre());
            intent.putExtra("descripcion", rutina.getDescripcion());
            context.startActivity(intent);
        });

        holder.editarButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, VEditarRutina.class);
            intent.putExtra("id", String.valueOf(rutina.getId()));
            intent.putExtra("nombre", rutina.getNombre());
            intent.putExtra("descripcion", rutina.getDescripcion());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.listadoRutinas.size();
    }

    public void refreshData(List<Rutina> listadoRutinas) {
        this.listadoRutinas = listadoRutinas;
        notifyDataSetChanged();
    }

}
