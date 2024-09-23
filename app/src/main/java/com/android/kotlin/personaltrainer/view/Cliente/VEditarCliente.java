package com.android.kotlin.personaltrainer.view.Cliente;

import android.app.AlertDialog;
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

public class VEditarCliente extends AppCompatActivity {

    CCliente controller;

    TextInputLayout nombreInput, apellidoInput, emailInput, telefonoInput, fechaNacimientoInput;
    Button actualizarButton, eliminarButton;
    ImageButton datePickerButton;
    Toolbar toolbar;
    MaterialButton btnSelectImage, btnClearImage;
    ImageView mediaPreview;
    String imageToStore;
    RadioGroup genderRadioGroup;

    Cliente clienteActual;
    String generoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_cliente);

        initComponents();

        ToolbarUtils.setupToolbar(this, toolbar);

        getAndSetIntentData();

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
            mediaPreview.setImageDrawable(null);
            mediaPreview.setImageResource(R.drawable.ic_placeholder_image);
            imageToStore = null;
        });

        actualizarButton.setOnClickListener(view -> {
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

            Cliente cliente = new Cliente(clienteActual.getId(), nombre, apellido, fechaNacimiento, telefono, email, generoSeleccionado, imageToStore);
            this.controller.actualizarCliente(cliente);
        });

        this.eliminarButton.setOnClickListener(view -> {
            confirmDialog(clienteActual.getId());
        });
    }

    private void initComponents() {
        this.controller = new CCliente(this);

        this.nombreInput = findViewById(R.id.nombre_cliente_edit_input);
        this.apellidoInput = findViewById(R.id.apellido_cliente_edit_input);
        this.emailInput = findViewById(R.id.email_cliente_edit_input);
        this.telefonoInput = findViewById(R.id.telefono_cliente_edit_input);
        this.fechaNacimientoInput = findViewById(R.id.fecha_nacimiento_cliente_edit_input);
        this.actualizarButton = findViewById(R.id.actualizar_cliente_edit_button);
        this.eliminarButton = findViewById(R.id.eliminar_cliente_edit_button);
        this.toolbar = findViewById(R.id.toolbar_cliente);
        this.btnSelectImage = findViewById(R.id.btn_subir_imagen);
        this.btnClearImage = findViewById(R.id.btn_remover_imagen);
        this.mediaPreview = findViewById(R.id.media_preview_cliente_edit);
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
    }

    private void seleccionarImagenDesdeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        this.startActivityForResult(intent, 100);
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("apellido") && getIntent().hasExtra("email") &&
                getIntent().hasExtra("telefono") && getIntent().hasExtra("fechaNacimiento") &&
                getIntent().hasExtra("foto")
        ) {
            // Getting data from intent
            int id = Integer.parseInt(getIntent().getStringExtra("id"));
            String nombre = getIntent().getStringExtra("nombre");
            String apellido = getIntent().getStringExtra("apellido");
            String email = getIntent().getStringExtra("email");
            String telefono = getIntent().getStringExtra("telefono");
            String fechaNacimiento = getIntent().getStringExtra("fechaNacimiento");
            String genero = getIntent().getStringExtra("genero");
            String foto = getIntent().getStringExtra("foto");

            // Setting data
            nombreInput.getEditText().setText(nombre);
            apellidoInput.getEditText().setText(apellido);
            emailInput.getEditText().setText(email);
            telefonoInput.getEditText().setText(telefono);
            fechaNacimientoInput.getEditText().setText(fechaNacimiento);
            genderRadioGroup.check(genero.equals("Masculino") ? R.id.radioMale : genero.equals("Femenino") ? R.id.radioFemale : R.id.radioOther);
//            mediaPreview.setImageURI(Uri.parse(foto));

            generoSeleccionado = genero;
            clienteActual = new Cliente(id, nombre, apellido, fechaNacimiento, telefono, email, genero, foto);
        } else {
            Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar ejercicio");
        builder.setMessage("¿Estás seguro de que deseas eliminar este ejercicio?");
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            this.controller.eliminarCliente(id);
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();
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
