package com.android.kotlin.personaltrainer.view.Ejercicio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CEjercicio;
import com.android.kotlin.personaltrainer.model.CategoriaEjercicio.CategoriaEjercicio;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;


import java.util.List;

public class VCrearEjercicio extends AppCompatActivity {

    CEjercicio controller;
    List<CategoriaEjercicio> listadoCategorias;

    AutoCompleteTextView categoriaInput;
    TextInputLayout nombreInput, descripcionInput, urlVideoInput;
    Button guardarButton;
    Toolbar toolbar;
    MaterialButton btnSelectImage, btnClearImage;
    ImageView mediaPreview;
    String imageToStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_ejercicio);

        this.controller = new CEjercicio(this);

        this.nombreInput = findViewById(R.id.nombre_input);
        this.descripcionInput = findViewById(R.id.descripcion_input);
        this.urlVideoInput = findViewById(R.id.url_video_input);
        this.categoriaInput = findViewById(R.id.categoria_input);
        this.guardarButton = findViewById(R.id.guardar_button);
        this.toolbar = findViewById(R.id.toolbar_ejercicio);
        this.btnSelectImage = findViewById(R.id.btn_subir_imagen);
        this.btnClearImage = findViewById(R.id.btn_remover_imagen);
        this.mediaPreview = findViewById(R.id.media_preview);

        controller.cargarCategorias();

        ToolbarUtils.setupToolbar(this, toolbar);

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

        ArrayAdapter<CategoriaEjercicio> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoCategorias);
        categoriaInput.setAdapter(adapter);


        guardarButton.setOnClickListener(view -> {
            String nombre = nombreInput.getEditText().getText().toString().trim();
            String descripcion = descripcionInput.getEditText().getText().toString().trim();
            String urlVideo = urlVideoInput.getEditText().getText().toString().trim();
            CategoriaEjercicio categoria = obtenerCategoriaSeleccionada();

            if (nombre.isEmpty() || descripcion.isEmpty() || urlVideo.isEmpty()) {
                mostrarMensaje("Por favor, llene todos los campos");
                return;
            }

            if (categoria == null) {
                mostrarMensaje("Por favor, seleccione una categoría");
                return;
            }

            if (imageToStore == null) {
                mostrarMensaje("Por favor, seleccione una imagen");
                return;
            }

            Ejercicio ejercicio = new Ejercicio(nombre, descripcion, imageToStore, urlVideo, categoria.getId());
            this.controller.guardarEjercicio(ejercicio);
        });

    }

    private CategoriaEjercicio obtenerCategoriaSeleccionada() {
        for (CategoriaEjercicio categoria : listadoCategorias) {
            if (categoria.getNombre().equals(categoriaInput.getText().toString())) {
                return categoria;
            }
        }
        return null;
    }

    public void cargarCategorias(List<CategoriaEjercicio> listado) {
        this.listadoCategorias = listado;
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void seleccionarImagenDesdeGaleria() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        this.startActivityForResult(intent, 100);
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