package com.android.kotlin.personaltrainer.view.Ejercicio;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VEditarCategoriaEjercicio;

import java.util.List;

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder> {

    private Context context;
    private List<Ejercicio> ejercicioList;

    public EjercicioAdapter(Context context, List<Ejercicio> ejercicioList) {
        this.context = context;
        this.ejercicioList = ejercicioList;
    }

    public static class EjercicioViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenEjercicio;
        TextView nombreEjercicio, descripcionEjercicio;
        ImageView editarButton;

        public EjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreEjercicio = itemView.findViewById(R.id.nombreEjercicio);
            this.descripcionEjercicio = itemView.findViewById(R.id.descripcionEjercicio);
            this.imagenEjercicio = itemView.findViewById(R.id.imagenEjercicio);
            this.editarButton = itemView.findViewById(R.id.editarEjercicioButton);
        }
    }

    @NonNull
    @Override
    public EjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_ejercicio, parent, false);
        return new EjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioViewHolder holder, int position) {
        Ejercicio ejercicio = this.ejercicioList.get(position);
        holder.nombreEjercicio.setText(ejercicio.getNombre());
        holder.descripcionEjercicio.setText(ejercicio.getDescripcion());
//        holder.imagenEjercicio.setImageURI(Uri.parse(ejercicio.getImagen()));

        holder.editarButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, VEditarEjercicio.class);
            intent.putExtra("id", String.valueOf(ejercicio.getId()));
            intent.putExtra("nombre", ejercicio.getNombre());
            intent.putExtra("descripcion", ejercicio.getDescripcion());
            intent.putExtra("imagen", ejercicio.getImagen());
            intent.putExtra("urlVideo", ejercicio.getUrlVideo());
            intent.putExtra("categoriaId", ejercicio.getIdCategoria());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.ejercicioList.size();
    }

    public void refreshData(List<Ejercicio> listadoEjercicios) {
        this.ejercicioList = listadoEjercicios;
        notifyDataSetChanged();
    }

}
