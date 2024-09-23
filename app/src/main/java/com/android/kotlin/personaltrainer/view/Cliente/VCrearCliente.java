package com.android.kotlin.personaltrainer.view.Cliente;

import android.app.DatePickerDialog;
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
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class VCrearCliente extends AppCompatActivity {

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

        ToolbarUtils.setupToolbar(this, toolbar);

        datePickerButton.setOnClickListener(view -> {
            showDatePickerDialog();
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

        // Listener para el botón de seleccionar imagen
        btnSelectImage.setOnClickListener(view -> {
            seleccionarImagenDesdeGaleria();
        });

        // Listener para el botón de limpiar imagen
        btnClearImage.setOnClickListener(view -> {
            // Elimina la imagen seleccionada, cargando un placeholder
            mediaPreview.setImageDrawable(null);
            mediaPreview.setImageResource(R.drawable.ic_placeholder_image);
            imageToStore = null;
        });

        guardarButton.setOnClickListener(view -> {
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
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            fechaNacimientoInput.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);

        datePickerDialog.show();

//        DatePickerDialog dialog = new DatePickerDialog(VAgregarCliente.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                fechaNacimientoInput.getEditText().setText(MessageFormat.format("{0}/{1}/{2}", String.valueOf(i2), String.valueOf(i1 + 1), String.valueOf(i)));
//            }
//        }, year, month, day);
    }

    private void seleccionarImagenDesdeGaleria() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        this.startActivityForResult(intent, 100);
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {
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