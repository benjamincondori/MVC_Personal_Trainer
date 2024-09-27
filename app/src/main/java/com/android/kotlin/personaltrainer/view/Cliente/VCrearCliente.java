package com.android.kotlin.personaltrainer.view.Cliente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CCliente;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.utils.DatePickerDialog;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.android.kotlin.personaltrainer.utils.UploadImage;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class VCrearCliente extends AppCompatActivity {

    private final int SELECT_ACTIVITY = 100;
    CCliente controller;

    TextInputLayout nombreInput, apellidoInput, emailInput, telefonoInput, fechaNacimientoInput;
    Button guardarButton;
    ImageButton datePickerButton;
    Toolbar toolbar;
    MaterialButton btnSelectImage, btnClearImage;
    ImageView mediaPreview;
    String imageToStore;
    RadioGroup genderRadioGroup;

    String generoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_cliente);

        initComponents();

        datePickerButton.setOnClickListener(view -> {
            DatePickerDialog.showDatePickerDialog(this, fechaNacimientoInput);
        });

        genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioMale:
                    generoSeleccionado = "Masculino";
                    break;
                case R.id.radioFemale:
                    generoSeleccionado = "Femenino";
                    break;
                case R.id.radioOther:
                    generoSeleccionado = "Otro";
                    break;
            }
        });

        btnSelectImage.setOnClickListener(view -> {
            UploadImage.seledtPhotoFromGallery(this, SELECT_ACTIVITY);
        });

        btnClearImage.setOnClickListener(view -> {
            mediaPreview.setImageDrawable(null);
            mediaPreview.setImageResource(R.drawable.ic_placeholder_image);
            imageToStore = null;
        });

        guardarButton.setOnClickListener(view -> {
            guardarCliente();
        });

    }

    private void initComponents() {
        this.controller = new CCliente(this);

        this.nombreInput = findViewById(R.id.nombre_input);
        this.apellidoInput = findViewById(R.id.apellido_input);
        this.emailInput = findViewById(R.id.email_input);
        this.telefonoInput = findViewById(R.id.telefono_input);
        this.fechaNacimientoInput = findViewById(R.id.fecha_nacimiento_input);
        this.guardarButton = findViewById(R.id.guardar_button);
        this.toolbar = findViewById(R.id.toolbar_cliente);
        this.btnSelectImage = findViewById(R.id.btn_subir_imagen);
        this.btnClearImage = findViewById(R.id.btn_remover_imagen);
        this.mediaPreview = findViewById(R.id.media_preview);
        this.datePickerButton = findViewById(R.id.datePickerButton);
        this.genderRadioGroup = findViewById(R.id.genderRadioGroup);

        ToolbarUtils.setupToolbar(this, toolbar);
    }

    public void guardarCliente() {
        String nombre = nombreInput.getEditText().getText().toString().trim();
        String apellido = apellidoInput.getEditText().getText().toString().trim();
        String email = emailInput.getEditText().getText().toString().trim();
        String telefono = telefonoInput.getEditText().getText().toString().trim();
        String fechaNacimiento = fechaNacimientoInput.getEditText().getText().toString().trim();


        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() || fechaNacimiento.isEmpty()) {
            mostrarMensaje("Por favor, llene todos los campos");
            return;
        }

        if (generoSeleccionado == null) {
            mostrarMensaje("Por favor, seleccione un género");
            return;
        }

        Cliente cliente = new Cliente(nombre, apellido, fechaNacimiento, telefono, email, generoSeleccionado, imageToStore);
        this.controller.guardarCliente(cliente);
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_ACTIVITY) {
            Uri uri = data.getData();
            imageToStore = uri.toString();
            mediaPreview.setImageURI(uri);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}