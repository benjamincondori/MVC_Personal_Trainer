package com.android.kotlin.personaltrainer.view.PlanEntrenamiento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CPlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.DetallePlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.PlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.Rutina.DetalleRutinaEjercicio;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.android.kotlin.personaltrainer.view.Rutina.RutinaDetalleAdapter;
import com.android.kotlin.personaltrainer.view.Rutina.VDetalleRutina;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class VDetallePlanEntrenamiento extends AppCompatActivity {

    CPlanEntrenamiento controller;
    List<Rutina> listadoRutinas;
    List<DetallePlanEntrenamiento> listadoDetallePlanes;
    PlanEntrenamiento planActual;
    Cliente clienteActual;
    PlanEntrenamientoDetalleAdapter listAdapter;

    RecyclerView recyclerView;
    TextView nombreTextView, descripcionTextView, clienteTextView, fechaInicioTextView, fechaFinTextView, tipoTextView;
    Toolbar toolbar;
    AutoCompleteTextView spinnerRutinas, spinnerDia;
    MaterialButton agregarButton;
    LinearLayout emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_plan_entrenamiento);

        this.agregarButton = findViewById(R.id.agregar_rutina_button);
        this.toolbar = findViewById(R.id.toolbar_detalle_plan_entrenamiento);
        this.nombreTextView = findViewById(R.id.textViewNombre);
        this.descripcionTextView = findViewById(R.id.textViewDescripcion);
        this.clienteTextView = findViewById(R.id.clienteTextView);
        this.fechaInicioTextView = findViewById(R.id.fechaInicioTextView);
        this.fechaFinTextView = findViewById(R.id.fechaFinTextView);
        this.tipoTextView = findViewById(R.id.tipoTextView);
        this.recyclerView = findViewById(R.id.recyclerDetallePlanes);
        this.emptyLayout = findViewById(R.id.empty_layout);

        ToolbarUtils.setupToolbar(this, toolbar);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        this.controller = new CPlanEntrenamiento(this);
        this.controller.cargarRutinas();
        getAndSetIntentData();

        if (this.planActual != null) {
            this.controller.cargarDetallePlanEntrenamiento(this.planActual.getId());
        }

        if (this.listadoDetallePlanes != null) {
            this.listAdapter = new PlanEntrenamientoDetalleAdapter(this, this.listadoDetallePlanes, this.listadoRutinas, controller);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerView.setAdapter(this.listAdapter);
        }

        agregarButton.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(VDetallePlanEntrenamiento.this);
            View view1 = LayoutInflater.from(VDetallePlanEntrenamiento.this).inflate(R.layout.bottom_sheet_detalle_plan_entrenamiento, null);
            bottomSheetDialog.setContentView(view1);
            bottomSheetDialog.show();

            MaterialButton agregarButton = view1.findViewById(R.id.guardar_button);
            this.spinnerRutinas = view1.findViewById(R.id.spinnerRutinas);
            this.spinnerDia = view1.findViewById(R.id.spinnerDia);

            if (spinnerRutinas != null) {
                ArrayAdapter<Rutina> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoRutinas);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRutinas.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron rutinas", Toast.LENGTH_SHORT).show();
            }

            // cargar dias de la semana
            String[] dias = new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

            if (spinnerDia != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dias);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDia.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron días", Toast.LENGTH_SHORT).show();
            }

            agregarButton.setOnClickListener(view2 -> {
                String dia = spinnerDia.getText().toString();
                Rutina rutina = obtenerRutina(spinnerRutinas.getText().toString());


                if (dia.isEmpty()) {
                    Toast.makeText(VDetallePlanEntrenamiento.this, "Por favor, seleccione un día de la semana", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (rutina == null) {
                    Toast.makeText(VDetallePlanEntrenamiento.this, "Por favor, seleccione una rutina", Toast.LENGTH_SHORT).show();
                    return;
                }

                DetallePlanEntrenamiento detalle = new DetallePlanEntrenamiento(planActual.getId(), rutina.getId(), dia);

                controller.guardarDetallePlanEntrenamiento(detalle);
                bottomSheetDialog.dismiss();
                onResume();
            });

            bottomSheetDialog.setOnDismissListener(dialogInterface -> {
//                    Toast.makeText(VDetalleRutina.this, "Bottom sheet dismissed", Toast.LENGTH_SHORT).show());
            });
        });

    }

    private Rutina obtenerRutina(String nombre) {
        for (Rutina rutina : listadoRutinas) {
            if (rutina.getNombre().equals(nombre)) {
                return rutina;
            }
        }
        return null;
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
            this.nombreTextView.setText(nombre);
            this.descripcionTextView.setText(descripcion);
            this.fechaInicioTextView.setText(fechaInicio);
            this.fechaFinTextView.setText(fechaFin);
            this.tipoTextView.setText(tipo);

            controller.cargarCliente(idCliente);
            clienteTextView.setText(clienteActual != null ? clienteActual.getNombreCompleto() : "");

            this.planActual = new PlanEntrenamiento(id, nombre, descripcion, fechaInicio, fechaFin, tipo, idCliente);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarCliente(Cliente cliente) {
        this.clienteActual = cliente;
    }

    public void cargarRutinas(List<Rutina> rutinas) {
        this.listadoRutinas = rutinas;
    }

    public void cargarDetallePlanEntrenamiento(List<DetallePlanEntrenamiento> listado) {
        this.listadoDetallePlanes = listado;
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void verificarVacio() {
        if (this.listadoDetallePlanes.isEmpty()) {
            this.emptyLayout.setVisibility(LinearLayout.VISIBLE);
            this.recyclerView.setVisibility(RecyclerView.GONE);
        } else {
            this.emptyLayout.setVisibility(LinearLayout.GONE);
            this.recyclerView.setVisibility(RecyclerView.VISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.cargarDetallePlanEntrenamiento(this.planActual.getId());
        this.listAdapter.refreshData(this.listadoDetallePlanes);
        verificarVacio();
    }
}