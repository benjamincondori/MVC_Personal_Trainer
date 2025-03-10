package com.android.kotlin.personaltrainer.view.Ejercicio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.android.kotlin.personaltrainer.utils.UploadImage;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;


import java.io.IOException;
import java.util.List;

public class VCrearEjercicio extends AppCompatActivity {

    private final int SELECT_ACTIVITY = 100;

    CEjercicio controller;
    List<CategoriaEjercicio> listadoCategorias;

    AutoCompleteTextView categoriaInput;
    TextInputLayout nombreInput, descripcionInput, urlVideoInput;
    Button guardarButton;
    Toolbar toolbar;
    MaterialButton btnSelectImage, btnClearImage;
    ImageView mediaPreview;
    String imageToStore;
    Bitmap bitmapImageSelected;
    ArrayAdapter<CategoriaEjercicio> adapter;
    CategoriaEjercicio categoriaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_ejercicio);

        initComponents();

        btnSelectImage.setOnClickListener(view -> {
            UploadImage.seledtPhotoFromGallery(this, SELECT_ACTIVITY);
        });

        btnClearImage.setOnClickListener(view -> {
            mediaPreview.setImageDrawable(null);
            mediaPreview.setImageResource(R.drawable.ic_placeholder_image);
            imageToStore = null;
        });

        guardarButton.setOnClickListener(view -> {
            guardarEjercicio();
        });

        categoriaInput.setOnItemClickListener((adapterView, view, i, l) -> {
            categoriaSeleccionada = (CategoriaEjercicio) adapterView.getItemAtPosition(i);
        });

    }

    public void initComponents() {
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

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoCategorias);
        categoriaInput.setAdapter(adapter);
    }

    public void guardarEjercicio() {
        String nombre = nombreInput.getEditText().getText().toString().trim();
        String descripcion = descripcionInput.getEditText().getText().toString().trim();
        String urlVideo = urlVideoInput.getEditText().getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || urlVideo.isEmpty()) {
            mostrarMensaje("Por favor, llene todos los campos");
            return;
        }

        if (categoriaSeleccionada == null) {
            mostrarMensaje("Por favor, seleccione una categoría");
            return;
        }

        if (bitmapImageSelected != null) {
            // Guardar la imagen en el almacenamiento y obtener la ruta
            imageToStore = UploadImage.saveImageToStorage(bitmapImageSelected, this);
        }

        Ejercicio ejercicio = new Ejercicio(nombre, descripcion, imageToStore, urlVideo, categoriaSeleccionada.getId());
        this.controller.guardarEjercicio(ejercicio);
    }

    public void cargarCategorias(List<CategoriaEjercicio> listado) {
        this.listadoCategorias = listado;
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_ACTIVITY) {
            Uri imagenSeleccionada = data.getData();

            try {
                // Convierte el Uri en un Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagenSeleccionada);

                // Obtén la ruta real de la imagen desde el Uri
                String rutaImagen = UploadImage.obtenerRutaImagenDesdeUri(imagenSeleccionada, this);

                // Aplica la rotación correcta según los metadatos EXIF
                bitmapImageSelected = UploadImage.rotarImagenSegunOrientacion(bitmap, rutaImagen);

                // Ahora puedes mostrar el Bitmap corregido en un ImageView o guardarlo
                mediaPreview.setImageBitmap(bitmapImageSelected);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}