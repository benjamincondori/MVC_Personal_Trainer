package com.android.kotlin.personaltrainer.view.EstadoFisico;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CEstadoFisico;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.model.EstadoFisico.EstadoFisico;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class VEditarEstadoFisico extends AppCompatActivity {

    CEstadoFisico controller;
    List<Cliente> listadoClientes;
    EstadoFisico estadoFisicoActual;

    AutoCompleteTextView clienteInput;
    TextInputLayout alturaInput, pesoInput, enfermedadesInput;
    Button actualizarButton, eliminarButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_estado_fisico);

        this.controller = new CEstadoFisico(this);

        this.clienteInput = findViewById(R.id.cliente_input);
        this.alturaInput = findViewById(R.id.altura_input);
        this.pesoInput = findViewById(R.id.peso_input);
        this.enfermedadesInput = findViewById(R.id.enfermedades_input);
        this.actualizarButton = findViewById(R.id.actualizar_button);
        this.eliminarButton = findViewById(R.id.eliminar_button);
        this.toolbar = findViewById(R.id.toolbar_estado_fisico);

        controller.cargarClientes();

        ToolbarUtils.setupToolbar(this, toolbar);

        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoClientes);
        clienteInput.setAdapter(adapter);

        getAndSetIntentData();

        actualizarButton.setOnClickListener(view -> {
            String estaturaStr = alturaInput.getEditText().getText().toString().trim();
            String pesoStr = pesoInput.getEditText().getText().toString().trim();
            String enfermedades = enfermedadesInput.getEditText().getText().toString().trim();
            String fecha = obtenerFechaActual();

            float estatura = estaturaStr.isEmpty() ? 0 : Float.parseFloat(estaturaStr);
            float peso = pesoStr.isEmpty() ? 0 : Float.parseFloat(pesoStr);

            Cliente cliente = obtenerClienteSeleccionado();

            if (estatura == 0 || peso == 0) {
                mostrarMensaje("Por favor, complete todos los campos");
                return;
            }

            if (cliente == null) {
                mostrarMensaje("Por favor, seleccione un cliente");
                return;
            }

            EstadoFisico estadoFisico = new EstadoFisico(estadoFisicoActual.getId(), estatura, peso, fecha, enfermedades, cliente.getId());
            this.controller.actualizarEstadoFisico(estadoFisico);
        });

        this.eliminarButton.setOnClickListener(view -> {
            confirmDialog(estadoFisicoActual.getId());
        });

    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("estatura") &&
                getIntent().hasExtra("peso") && getIntent().hasExtra("fecha") &&
                getIntent().hasExtra("enfermedades") && getIntent().hasExtra("clienteId")
        ) {
            // Getting data from intent
            int id = Integer.parseInt(getIntent().getStringExtra("id"));
            String estatura = getIntent().getStringExtra("estatura");
            String peso = getIntent().getStringExtra("peso");
            String fecha = obtenerFechaActual();
            String enfermedades = getIntent().getStringExtra("enfermedades");
            int clienteId = getIntent().getIntExtra("clienteId", 0);

            // Setting data
            alturaInput.getEditText().setText(estatura);
            pesoInput.getEditText().setText(peso);
            enfermedadesInput.getEditText().setText(enfermedades);

            Cliente cliente = obtenerCliente(clienteId);
            clienteInput.setText(cliente != null ? cliente.getNombreCompleto() : null, false);

            this.estadoFisicoActual = new EstadoFisico(id, Float.parseFloat(estatura), Float.parseFloat(peso), fecha, enfermedades, clienteId);
        } else {
            Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar estado físico");
        builder.setMessage("¿Estás seguro de que deseas eliminar este estado físico?");
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            this.controller.eliminarEstadoFisico(id);
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();
    }

    private String obtenerFechaActual() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private Cliente obtenerCliente(int clienteId) {
        for (Cliente cliente : listadoClientes) {
            if (cliente.getId() == clienteId) {
                return cliente;
            }
        }
        return null;
    }

    private Cliente obtenerClienteSeleccionado() {
        for (Cliente cliente : listadoClientes) {
            if (cliente.getNombreCompleto().equals(clienteInput.getText().toString())) {
                return cliente;
            }
        }
        return null;
    }

    public void cargarClientes(List<Cliente> listado) {
        this.listadoClientes = listado;
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}