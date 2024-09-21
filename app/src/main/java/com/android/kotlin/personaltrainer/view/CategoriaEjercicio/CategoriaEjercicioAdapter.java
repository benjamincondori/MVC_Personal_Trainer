package com.android.kotlin.personaltrainer.view.CategoriaEjercicio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.android.kotlin.personaltrainer.R;

import java.util.List;

public class CategoriaEjercicioAdapter extends RecyclerView.Adapter<CategoriaEjercicioAdapter.CategoriaEjercicioViewHolder> {

    private Context context;
    private List<CategoriaEjercicio> categoriaEjercicioList;

    public CategoriaEjercicioAdapter(Context context, List<CategoriaEjercicio> categoriaEjercicioList) {
        this.context = context;
        this.categoriaEjercicioList = categoriaEjercicioList;
    }

    public static class CategoriaEjercicioViewHolder extends RecyclerView.ViewHolder {
        TextView nombreCategoriaEjercicio, descripcionCategoriaEjercicio;
        ImageView editarButton;

        public CategoriaEjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreCategoriaEjercicio = itemView.findViewById(R.id.nombreTextView);
            this.descripcionCategoriaEjercicio = itemView.findViewById(R.id.descripcionTextView);
            this.editarButton = itemView.findViewById(R.id.editarButton);
        }
    }

    @NonNull
    @Override
    public CategoriaEjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_categoria_ejercicio, parent, false);
        return new CategoriaEjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaEjercicioViewHolder holder, int position) {
        CategoriaEjercicio categoriaEjercicio = this.categoriaEjercicioList.get(position);
        holder.nombreCategoriaEjercicio.setText(categoriaEjercicio.getNombre());
        holder.descripcionCategoriaEjercicio.setText(categoriaEjercicio.getDescripcion());

        holder.editarButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, VEditarCategoriaEjercicio.class);
            intent.putExtra("id", String.valueOf(categoriaEjercicio.getId()));
            intent.putExtra("nombre", categoriaEjercicio.getNombre());
            intent.putExtra("descripcion", categoriaEjercicio.getDescripcion());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.categoriaEjercicioList.size();
    }

    public void refreshData(List<CategoriaEjercicio> listadoCategoriaEjercicios) {
        this.categoriaEjercicioList = listadoCategoriaEjercicios;
        notifyDataSetChanged();
    }

}
