package com.android.kotlin.personaltrainer.view.Rutina;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CRutina;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.model.Rutina.DetalleRutinaEjercicio;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class VDetalleRutina extends AppCompatActivity {

    CRutina controller;
    List<Ejercicio> listadoEjercicios;
    List<DetalleRutinaEjercicio> listadoDetalleRutina;
    Rutina rutinaActual;
    RutinaDetalleAdapter listAdapter;

    RecyclerView recyclerView;
    TextView textViewNombre, textViewDescripcion;
    Toolbar toolbar;
    AutoCompleteTextView spinnerEjercicios;
    MaterialButton agregarButton;
    LinearLayout emptyLayout;
    Ejercicio ejercicioSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_rutina);

        initComponents();
        getAndSetIntentData();

        if (this.rutinaActual != null) {
            this.controller.cargarDetalleRutina(this.rutinaActual.getId());
        }

        if (this.listadoDetalleRutina != null) {
            this.listAdapter = new RutinaDetalleAdapter(this, this.listadoDetalleRutina, this.listadoEjercicios, controller);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerView.setAdapter(this.listAdapter);
        }

        agregarButton.setOnClickListener(view -> {
            agregarEjercicio();
        });

    }

    public void initComponents() {
        this.agregarButton = findViewById(R.id.agregar_ejercicio_button);
        this.toolbar = findViewById(R.id.toolbar_detalle_rutina);
        this.textViewNombre = findViewById(R.id.textViewNombre);
        this.textViewDescripcion = findViewById(R.id.textViewDescripcion);
        this.recyclerView = findViewById(R.id.recyclerDetalleRutina);
        this.emptyLayout = findViewById(R.id.empty_layout);

        ToolbarUtils.setupToolbar(this, toolbar);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        this.controller = new CRutina(this);
        this.controller.cargarEjercicios();
    }

    public void agregarEjercicio() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(VDetalleRutina.this);
        View view1 = LayoutInflater.from(VDetalleRutina.this).inflate(R.layout.bottom_sheet_detalle_rutina, null);
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();

        TextInputEditText editTextSeries = view1.findViewById(R.id.editTextSeries);
        TextInputEditText editTextRepeticiones = view1.findViewById(R.id.editTextRepeticiones);
        TextInputEditText editTextDescanso = view1.findViewById(R.id.editTextDescanso);
        MaterialButton agregarButton = view1.findViewById(R.id.guardar_button);
        spinnerEjercicios = bottomSheetDialog.findViewById(R.id.spinnerEjercicios);

        if (spinnerEjercicios != null) {
            ArrayAdapter<Ejercicio> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoEjercicios);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEjercicios.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No se encontraron ejercicios", Toast.LENGTH_SHORT).show();
        }

        spinnerEjercicios.setOnItemClickListener((adapterView, view, i, l) -> {
            ejercicioSeleccionado = (Ejercicio) adapterView.getItemAtPosition(i);
        });

        agregarButton.setOnClickListener(view2 -> {
            String series = editTextSeries.getText().toString().trim();
            String repeticiones = editTextRepeticiones.getText().toString().trim();
            String descanso = editTextDescanso.getText().toString().trim();

            if (series.isEmpty() || repeticiones.isEmpty() || descanso.isEmpty()) {
                Toast.makeText(VDetalleRutina.this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ejercicioSeleccionado == null) {
                Toast.makeText(VDetalleRutina.this, "Por favor, seleccione un ejercicio", Toast.LENGTH_SHORT).show();
                return;
            }

            DetalleRutinaEjercicio detalle = new DetalleRutinaEjercicio(rutinaActual.getId(), ejercicioSeleccionado.getId(), Integer.parseInt(series), Integer.parseInt(repeticiones), Integer.parseInt(descanso));

            controller.guardarDetalleRutina(detalle);
            bottomSheetDialog.dismiss();
            onResume();
        });

        bottomSheetDialog.setOnDismissListener(dialogInterface -> {
        });
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("descripcion")
        ) {
            int id = Integer.parseInt(getIntent().getStringExtra("id"));
            String nombre = getIntent().getStringExtra("nombre");
            String descripcion = getIntent().getStringExtra("descripcion");

            textViewNombre.setText(nombre);
            textViewDescripcion.setText(descripcion);

            this.rutinaActual = new Rutina(id, nombre, descripcion);
        } else {
            Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarEjercicios(List<Ejercicio> listado) {
        this.listadoEjercicios = listado;
    }

    public void cargarDetalleRutina(List<DetalleRutinaEjercicio> listado) {
        this.listadoDetalleRutina = listado;
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void verificarVacio() {
        if (this.listadoDetalleRutina.isEmpty()) {
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
        controller.cargarDetalleRutina(this.rutinaActual.getId());
        this.listAdapter.refreshData(this.listadoDetalleRutina);
        verificarVacio();
    }
}