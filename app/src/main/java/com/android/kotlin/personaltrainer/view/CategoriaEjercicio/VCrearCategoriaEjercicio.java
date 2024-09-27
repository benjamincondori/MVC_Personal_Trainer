package com.android.kotlin.personaltrainer.view.CategoriaEjercicio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CCategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.google.android.material.textfield.TextInputLayout;

public class VCrearCategoriaEjercicio extends AppCompatActivity {

    CCategoriaEjercicio controller;

    TextInputLayout nombreInput, descripcionInput;
    Button guardarButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_categoria_ejercicio);

        initComponents();

        this.guardarButton.setOnClickListener(view -> {
            guardarCategoriaEjercicio();
        });
    }

    public void initComponents() {
        this.controller = new CCategoriaEjercicio(this);

        this.nombreInput = findViewById(R.id.nombre_input);
        this.descripcionInput = findViewById(R.id.descripcion_input);
        this.guardarButton = findViewById(R.id.guardar_button);
        this.toolbar = findViewById(R.id.toolbar_tipo_ejercicio);

        ToolbarUtils.setupToolbar(this, toolbar);
    }

    public void guardarCategoriaEjercicio() {
        String nombre = nombreInput.getEditText().getText().toString().trim();
        String descripcion = descripcionInput.getEditText().getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            mostrarMensaje("Por favor, llene todos los campos");
            return;
        }

        CategoriaEjercicio categoriaEjercicio = new CategoriaEjercicio(nombre, descripcion);
        this.controller.guardarCategoriaEjercicio(categoriaEjercicio);
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