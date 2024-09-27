package com.android.kotlin.personaltrainer.view.EstadoFisico;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VCrearEstadoFisico extends AppCompatActivity {

    CEstadoFisico controller;
    List<Cliente> listadoClientes;

    AutoCompleteTextView clienteInput;
    TextInputLayout alturaInput, pesoInput, enfermedadesInput;
    Button guardarButton;
    Toolbar toolbar;
    ArrayAdapter<Cliente> adapter;
    Cliente clienteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_estado_fisico);

        initComponents();

        guardarButton.setOnClickListener(view -> {
            guardarEstadoFisico();
        });

        clienteInput.setOnItemClickListener((adapterView, view, i, l) -> {
            clienteSeleccionado = (Cliente) adapterView.getItemAtPosition(i);
        });

    }

    public void initComponents() {
        this.controller = new CEstadoFisico(this);

        this.clienteInput = findViewById(R.id.cliente_input);
        this.alturaInput = findViewById(R.id.altura_input);
        this.pesoInput = findViewById(R.id.peso_input);
        this.enfermedadesInput = findViewById(R.id.enfermedades_input);
        this.guardarButton = findViewById(R.id.guardar_button);
        this.toolbar = findViewById(R.id.toolbar_estado_fisico);

        controller.cargarClientes();

        ToolbarUtils.setupToolbar(this, toolbar);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoClientes);
        clienteInput.setAdapter(adapter);
    }

    public void guardarEstadoFisico() {
        String estaturaStr = alturaInput.getEditText().getText().toString().trim();
        String pesoStr = pesoInput.getEditText().getText().toString().trim();
        String enfermedades = enfermedadesInput.getEditText().getText().toString().trim();
        String fecha = obtenerFechaActual();

        float estatura = estaturaStr.isEmpty() ? 0 : Float.parseFloat(estaturaStr);
        float peso = pesoStr.isEmpty() ? 0 : Float.parseFloat(pesoStr);

        if (estatura == 0 || peso == 0) {
            mostrarMensaje("Por favor, complete todos los campos");
            return;
        }

        if (clienteSeleccionado == null) {
            mostrarMensaje("Por favor, seleccione un cliente");
            return;
        }

        EstadoFisico estadoFisico = new EstadoFisico(estatura, peso, fecha, enfermedades, clienteSeleccionado.getId());
        this.controller.guardarEstadoFisico(estadoFisico);
    }

    private String obtenerFechaActual() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
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