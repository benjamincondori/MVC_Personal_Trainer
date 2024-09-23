package com.android.kotlin.personaltrainer.view.Rutina;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CRutina;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class VEditarRutina extends AppCompatActivity {

    CRutina controller;

    TextInputLayout nombreInput, descripcionInput;
    MaterialButton actualizarButton, eliminarButton;
    Toolbar toolbar;

    Rutina rutinaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_rutina);

        this.controller = new CRutina(this);

        this.nombreInput = findViewById(R.id.nombre_input_edit);
        this.descripcionInput = findViewById(R.id.descripcion_input_edit);
        this.actualizarButton = findViewById(R.id.actualizar_button);
        this.eliminarButton = findViewById(R.id.eliminar_button);
        this.toolbar = findViewById(R.id.toolbar_rutina);

        ToolbarUtils.setupToolbar(this, toolbar);

        this.getAndSetIntentData();

        this.actualizarButton.setOnClickListener(view -> {
            String nombre = nombreInput.getEditText().getText().toString().trim();
            String descripcion = descripcionInput.getEditText().getText().toString().trim();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                mostrarMensaje("Por favor, llene todos los campos");
                return;
            }

            Rutina rutina = new Rutina(rutinaActual.getId(), nombre, descripcion);
            this.controller.actualizarRutina(rutina);
        });

        this.eliminarButton.setOnClickListener(view -> {
            confirmDialog(rutinaActual.getId());
        });
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("descripcion")
        ) {
            // Getting data from intent
            int id = Integer.parseInt(getIntent().getStringExtra("id"));
            String nombre = getIntent().getStringExtra("nombre");
            String descripcion = getIntent().getStringExtra("descripcion");

            // Setting intent data
            this.nombreInput.getEditText().setText(nombre);
            this.descripcionInput.getEditText().setText(descripcion);

            this.rutinaActual = new Rutina(id, nombre, descripcion);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar rutina");
        builder.setMessage("¿Estás seguro de que deseas eliminar esta rutina?");
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            this.controller.eliminarRutina(id);
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