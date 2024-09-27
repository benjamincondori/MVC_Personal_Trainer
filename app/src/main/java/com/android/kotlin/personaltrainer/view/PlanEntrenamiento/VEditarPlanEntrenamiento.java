package com.android.kotlin.personaltrainer.view.PlanEntrenamiento;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CPlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.PlanEntrenamiento;
import com.android.kotlin.personaltrainer.utils.DatePickerDialog;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

public class VEditarPlanEntrenamiento extends AppCompatActivity {

    CPlanEntrenamiento controller;
    List<Cliente> listadoClientes;

    TextInputLayout nombreInput, descripcionInput, fechaInicioInput, fechaFinInput, tipoInput;
    AutoCompleteTextView clienteInput;
    ImageButton datePickerButton1, datePickerButton2;
    Button actualizarButton, eliminarButton;
    Toolbar toolbar;

    PlanEntrenamiento planActual;
    ArrayAdapter<Cliente> adapter;
    Cliente clienteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_plan_entrenamiento);

        initComponents();
        getAndSetIntentData();

        datePickerButton1.setOnClickListener(view -> {
            DatePickerDialog.showDatePickerDialog(this, fechaInicioInput);
        });

        datePickerButton2.setOnClickListener(view -> {
            DatePickerDialog.showDatePickerDialog(this, fechaFinInput);
        });

        actualizarButton.setOnClickListener(view -> {
            actualizarPlanEntrenamiento();
        });

        eliminarButton.setOnClickListener(view -> {
            eliminarPlanEntrenamiento(planActual.getId());
        });

        clienteInput.setOnItemClickListener((adapterView, view, i, l) -> {
            clienteSeleccionado = (Cliente) adapterView.getItemAtPosition(i);
        });
    }

    public void initComponents() {
        this.controller = new CPlanEntrenamiento(this);

        this.nombreInput = findViewById(R.id.nombre_input);
        this.descripcionInput = findViewById(R.id.descripcion_input);
        this.fechaInicioInput = findViewById(R.id.fecha_inicio_input);
        this.fechaFinInput = findViewById(R.id.fecha_fin_input);
        this.clienteInput = findViewById(R.id.cliente_input);
        this.datePickerButton1 = findViewById(R.id.datePickerButton1);
        this.datePickerButton2 = findViewById(R.id.datePickerButton2);
        this.tipoInput = findViewById(R.id.tipo_input);
        this.actualizarButton = findViewById(R.id.actualizar_button);
        this.eliminarButton = findViewById(R.id.eliminar_button);
        this.toolbar = findViewById(R.id.toolbar_plan_entrenamiento);

        ToolbarUtils.setupToolbar(this, toolbar);

        controller.cargarClientes();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoClientes);
        clienteInput.setAdapter(adapter);
    }

    public void actualizarPlanEntrenamiento() {
        String nombre = nombreInput.getEditText().getText().toString().trim();
        String descripcion = descripcionInput.getEditText().getText().toString().trim();
        String fechaInicio = fechaInicioInput.getEditText().getText().toString().trim();
        String fechaFin = fechaFinInput.getEditText().getText().toString().trim();
        String tipo = tipoInput.getEditText().getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty() || tipo.isEmpty()) {
            mostrarMensaje("Por favor, llene todos los campos");
            return;
        }

        if (clienteSeleccionado == null) {
            mostrarMensaje("Por favor, seleccione un cliente");
            return;
        }

        PlanEntrenamiento planEntrenamiento = new PlanEntrenamiento(planActual.getId(), nombre, descripcion, fechaInicio, fechaFin, tipo, clienteSeleccionado.getId());
        this.controller.actualizarPlanEntrenamiento(planEntrenamiento);
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("descripcion") && getIntent().hasExtra("fechaInicio") &&
                getIntent().hasExtra("fechaFin") && getIntent().hasExtra("tipo") &&
                getIntent().hasExtra("idCliente")
        ) {
            int id = Integer.parseInt(getIntent().getStringExtra("id"));
            String nombre = getIntent().getStringExtra("nombre");
            String descripcion = getIntent().getStringExtra("descripcion");
            String fechaInicio = getIntent().getStringExtra("fechaInicio");
            String fechaFin = getIntent().getStringExtra("fechaFin");
            String tipo = getIntent().getStringExtra("tipo");
            int idCliente = getIntent().getIntExtra("idCliente", 0);

            this.nombreInput.getEditText().setText(nombre);
            this.descripcionInput.getEditText().setText(descripcion);
            this.fechaInicioInput.getEditText().setText(fechaInicio);
            this.fechaFinInput.getEditText().setText(fechaFin);
            this.tipoInput.getEditText().setText(tipo);

            clienteSeleccionado = obtenerCliente(idCliente);
            clienteInput.setText(clienteSeleccionado != null ? clienteSeleccionado.getNombreCompleto() : null, false);

            this.planActual = new PlanEntrenamiento(id, nombre, descripcion, fechaInicio, fechaFin, tipo, idCliente);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarPlanEntrenamiento(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar plan de entrenamiento");
        builder.setMessage("¿Estás seguro de que deseas eliminar este plan de entrenamiento?");
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            this.controller.eliminarPlanEntrenamiento(id);
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();
    }

    private Cliente obtenerCliente(int idCliente) {
        for (Cliente cliente : listadoClientes) {
            if (cliente.getId() == idCliente) {
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