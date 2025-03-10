package com.android.kotlin.personaltrainer.view.Cliente;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.utils.UploadImage;

import java.io.File;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    private final Context context;
    private List<Cliente> clienteList;

    public ClienteAdapter(Context context, List<Cliente> clienteList) {
        this.context = context;
        this.clienteList = clienteList;
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenCliente;
        TextView nombreCliente, emailCliente, telefonoCliente, fechaNacimientoCliente;
        ImageView editarButton;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreCliente = itemView.findViewById(R.id.nombreCliente);
            this.emailCliente = itemView.findViewById(R.id.emailCliente);
            this.imagenCliente = itemView.findViewById(R.id.imagenCliente);
            this.telefonoCliente = itemView.findViewById(R.id.telefonoCliente);
            this.fechaNacimientoCliente = itemView.findViewById(R.id.fechaNacimientoCliente);
            this.editarButton = itemView.findViewById(R.id.editarClienteButton);
        }
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = this.clienteList.get(position);
        holder.nombreCliente.setText(cliente.getNombreCompleto());
        holder.emailCliente.setText(cliente.getEmail());
        holder.telefonoCliente.setText(cliente.getTelefono());
        holder.fechaNacimientoCliente.setText(cliente.getFechaNacimiento());

        if (cliente.getFoto() != null) {
            UploadImage.cargarImagen(cliente.getFoto(), holder.imagenCliente, context);
        }

        holder.editarButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, VEditarCliente.class);
            intent.putExtra("id", String.valueOf(cliente.getId()));
            intent.putExtra("nombre", cliente.getNombre());
            intent.putExtra("apellido", cliente.getApellido());
            intent.putExtra("email", cliente.getEmail());
            intent.putExtra("genero", cliente.getGenero());
            intent.putExtra("telefono", cliente.getTelefono());
            intent.putExtra("fechaNacimiento", cliente.getFechaNacimiento());
            intent.putExtra("foto", cliente.getFoto());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.clienteList.size();
    }

    public void refreshData(List<Cliente> listadoClientes) {
        this.clienteList = listadoClientes;
        notifyDataSetChanged();
    }

}
