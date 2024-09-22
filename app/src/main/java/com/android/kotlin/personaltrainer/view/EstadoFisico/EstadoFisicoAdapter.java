package com.android.kotlin.personaltrainer.view.EstadoFisico;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.model.EstadoFisico.EstadoFisico;
import com.android.kotlin.personaltrainer.view.Ejercicio.VEditarEjercicio;

import java.util.List;

public class EstadoFisicoAdapter extends RecyclerView.Adapter<EstadoFisicoAdapter.EstadoFisicoViewHolder> {

    private Context context;
    private List<EstadoFisico> estadoFisicoList;
    private List<Cliente> listadoClientes;

    public EstadoFisicoAdapter(Context context, List<EstadoFisico> estadoFisicoList, List<Cliente> listadoClientes) {
        this.context = context;
        this.estadoFisicoList = estadoFisicoList;
        this.listadoClientes = listadoClientes;
    }

    public static class EstadoFisicoViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, estatura, peso, fechaRegistro;
        ImageView editarButton;

        public EstadoFisicoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.nombreCliente);
            this.estatura = itemView.findViewById(R.id.estatura);
            this.peso = itemView.findViewById(R.id.peso);
            this.fechaRegistro = itemView.findViewById(R.id.fecha);
            this.editarButton = itemView.findViewById(R.id.editarEstadoFisicoButton);
        }
    }

    @NonNull
    @Override
    public EstadoFisicoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_estado_fisico, parent, false);
        return new EstadoFisicoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstadoFisicoViewHolder holder, int position) {
        EstadoFisico estadoFisico = this.estadoFisicoList.get(position);
        Cliente cliente = obtenerCliente(estadoFisico.getIdCliente());

        holder.nombre.setText(cliente.getNombreCompleto());
        holder.estatura.setText(String.format("%s cm", estadoFisico.getEstatura()));
        holder.peso.setText(String.format("%s kg", estadoFisico.getPeso()));
        holder.fechaRegistro.setText(estadoFisico.getFecha());

        holder.editarButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, VEditarEstadoFisico.class);
            intent.putExtra("id", String.valueOf(estadoFisico.getId()));
            intent.putExtra("estatura", String.valueOf(estadoFisico.getEstatura()));
            intent.putExtra("peso", String.valueOf(estadoFisico.getPeso()));
            intent.putExtra("fecha", estadoFisico.getFecha());
            intent.putExtra("enfermedades", estadoFisico.getEnfermedades());
            intent.putExtra("clienteId", estadoFisico.getIdCliente());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.estadoFisicoList.size();
    }

    public void refreshData(List<EstadoFisico> listadoEstadoFisico) {
        this.estadoFisicoList = listadoEstadoFisico;
        notifyDataSetChanged();
    }

    private Cliente obtenerCliente(int idCliente) {
        for (Cliente cliente : this.listadoClientes) {
            if (cliente.getId() == idCliente) {
                return cliente;
            }
        }
        return null;
    }

}
