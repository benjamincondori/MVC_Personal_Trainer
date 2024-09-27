package com.android.kotlin.personaltrainer.view.CategoriaEjercicio;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CCategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class VEditarCategoriaEjercicio extends AppCompatActivity {

    CCategoriaEjercicio controller;

    TextInputLayout nombreInput, descripcionInput;
    MaterialButton actualizarButton, eliminarButton;
    Toolbar toolbar;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_categoria_ejercicio);

        initComponents();
        this.getAndSetIntentData();

        this.actualizarButton.setOnClickListener(view -> {
            actualizarCategoriaEjercicio();
        });

        this.eliminarButton.setOnClickListener(view -> {
            eliminarCategoriaEjercicio(this.id);
        });
    }

    public void initComponents() {
        this.controller = new CCategoriaEjercicio(this);

        this.nombreInput = findViewById(R.id.nombre_input_edit);
        this.descripcionInput = findViewById(R.id.descripcion_input_edit);
        this.actualizarButton = findViewById(R.id.actualizar_button);
        this.eliminarButton = findViewById(R.id.eliminar_button);
        this.toolbar = findViewById(R.id.toolbar_tipo_ejercicio);

        ToolbarUtils.setupToolbar(this, toolbar);
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("descripcion")
        ) {
            this.id = Integer.parseInt(getIntent().getStringExtra("id"));
            String nombre = getIntent().getStringExtra("nombre");
            String descripcion = getIntent().getStringExtra("descripcion");

            this.nombreInput.getEditText().setText(nombre);
            this.descripcionInput.getEditText().setText(descripcion);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizarCategoriaEjercicio() {
        String nombre = nombreInput.getEditText().getText().toString().trim();
        String descripcion = descripcionInput.getEditText().getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            mostrarMensaje("Por favor, llene todos los campos");
            return;
        }

        CategoriaEjercicio categoriaEjercicio = new CategoriaEjercicio(this.id, nombre, descripcion);
        this.controller.actualizarCategoriaEjercicio(categoriaEjercicio);
    }

    public void eliminarCategoriaEjercicio(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar categoria de ejercicio");
        builder.setMessage("¿Estás seguro de que deseas eliminar esta categoria de ejercicio?");
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            this.controller.eliminarCategoriaEjercicio(this.id);
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}