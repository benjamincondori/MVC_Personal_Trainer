package com.android.kotlin.personaltrainer.view.Rutina;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CRutina;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.google.android.material.textfield.TextInputLayout;

public class VCrearRutina extends AppCompatActivity {

    CRutina controller;

    TextInputLayout nombreInput, descripcionInput;
    Button guardarButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_rutina);

        initComponents();

        guardarButton.setOnClickListener(view -> {
            guardarRutina();
        });
    }

    public void initComponents() {
        this.controller = new CRutina(this);

        this.nombreInput = findViewById(R.id.nombre_input);
        this.descripcionInput = findViewById(R.id.descripcion_input);
        this.guardarButton = findViewById(R.id.guardar_button);
        this.toolbar = findViewById(R.id.toolbar_rutina);

        ToolbarUtils.setupToolbar(this, toolbar);
    }

    public void guardarRutina() {
        String nombre = nombreInput.getEditText().getText().toString().trim();
        String descripcion = descripcionInput.getEditText().getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            mostrarMensaje("Por favor, llene todos los campos");
            return;
        }

        Rutina rutina = new Rutina(nombre, descripcion);
        this.controller.guardarRutina(rutina);
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