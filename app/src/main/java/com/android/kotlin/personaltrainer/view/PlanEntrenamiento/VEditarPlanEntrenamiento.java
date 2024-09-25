package com.android.kotlin.personaltrainer.view.PlanEntrenamiento;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_plan_entrenamiento);

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

        datePickerButton1.setOnClickListener(view -> {
            showDatePickerDialog(fechaInicioInput);
        });

        datePickerButton2.setOnClickListener(view -> {
            showDatePickerDialog(fechaFinInput);
        });

        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoClientes);
        clienteInput.setAdapter(adapter);

        getAndSetIntentData();

        this.actualizarButton.setOnClickListener(view -> {
            String nombre = nombreInput.getEditText().getText().toString().trim();
            String descripcion = descripcionInput.getEditText().getText().toString().trim();
            String fechaInicio = fechaInicioInput.getEditText().getText().toString().trim();
            String fechaFin = fechaFinInput.getEditText().getText().toString().trim();
            String tipo = tipoInput.getEditText().getText().toString().trim();
            String clienteSeleccionado = clienteInput.getText().toString();
            Cliente cliente = obtenerClienteSeleccionado(clienteSeleccionado);

            if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty() || tipo.isEmpty()) {
                mostrarMensaje("Por favor, llene todos los campos");
                return;
            }

            if (cliente == null) {
                mostrarMensaje("Por favor, seleccione un cliente");
                return;
            }

            PlanEntrenamiento planEntrenamiento = new PlanEntrenamiento(planActual.getId(), nombre, descripcion, fechaInicio, fechaFin, tipo, cliente.getId());
            this.controller.actualizarPlanEntrenamiento(planEntrenamiento);
        });

        this.eliminarButton.setOnClickListener(view -> {
            confirmDialog(planActual.getId());
        });
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("descripcion") && getIntent().hasExtra("fechaInicio") &&
                getIntent().hasExtra("fechaFin") && getIntent().hasExtra("tipo") &&
                getIntent().hasExtra("idCliente")
        ) {
            // Getting data from intent
            int id = Integer.parseInt(getIntent().getStringExtra("id"));
            String nombre = getIntent().getStringExtra("nombre");
            String descripcion = getIntent().getStringExtra("descripcion");
            String fechaInicio = getIntent().getStringExtra("fechaInicio");
            String fechaFin = getIntent().getStringExtra("fechaFin");
            String tipo = getIntent().getStringExtra("tipo");
            int idCliente = getIntent().getIntExtra("idCliente", 0);

            // Setting intent data
            this.nombreInput.getEditText().setText(nombre);
            this.descripcionInput.getEditText().setText(descripcion);
            this.fechaInicioInput.getEditText().setText(fechaInicio);
            this.fechaFinInput.getEditText().setText(fechaFin);
            this.tipoInput.getEditText().setText(tipo);

            Cliente cliente = obtenerCliente(idCliente);
            clienteInput.setText(cliente != null ? cliente.getNombreCompleto() : null, false);

            this.planActual = new PlanEntrenamiento(id, nombre, descripcion, fechaInicio, fechaFin, tipo, idCliente);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDialog(int id) {
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

    private Cliente obtenerClienteSeleccionado(String clienteSeleccionado) {
        for (Cliente cliente : listadoClientes) {
            if (cliente.getNombreCompleto().equals(clienteSeleccionado)) {
                return cliente;
            }
        }
        return null;
    }

    private void showDatePickerDialog(TextInputLayout input) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            input.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);

        datePickerDialog.show();
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