package com.android.kotlin.personaltrainer.view.Ejercicio;

import android.app.AlertDialog;
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
import com.android.kotlin.personaltrainer.utils.UploadImage;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class VEditarEjercicio extends AppCompatActivity {

    private final int SELECT_ACTIVITY = 100;

    CEjercicio controller;
    List<CategoriaEjercicio> listadoCategorias;
    Ejercicio ejercicioActual;

    AutoCompleteTextView categoriaInput;
    TextInputLayout nombreInput, descripcionInput, urlVideoInput;
    Button actualizarButton, eliminarButton;
    Toolbar toolbar;
    MaterialButton btnSelectImage, btnClearImage;
    ImageView mediaPreview;
    String imageToStore;
    ArrayAdapter<CategoriaEjercicio> adapter;
    CategoriaEjercicio categoriaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_ejercicio);

        this.initComponents();
        getAndSetIntentData();

        btnSelectImage.setOnClickListener(view -> {
            UploadImage.seledtPhotoFromGallery(this, SELECT_ACTIVITY);
        });

        btnClearImage.setOnClickListener(view -> {
            mediaPreview.setImageDrawable(null);
            mediaPreview.setImageResource(R.drawable.ic_placeholder_image);
            imageToStore = null;
        });

        this.actualizarButton.setOnClickListener(view -> {
            actualizarEjercicio();
        });

        this.eliminarButton.setOnClickListener(view -> {
            eliminarEjercicio(ejercicioActual.getId());
        });

        categoriaInput.setOnItemClickListener((adapterView, view, i, l) -> {
            categoriaSeleccionada = (CategoriaEjercicio) adapterView.getItemAtPosition(i);
        });
    }

    private void initComponents() {
        this.controller = new CEjercicio(this);

        this.nombreInput = findViewById(R.id.nombre_input);
        this.descripcionInput = findViewById(R.id.descripcion_input);
        this.urlVideoInput = findViewById(R.id.url_video_input);
        this.categoriaInput = findViewById(R.id.categoria_input);
        this.actualizarButton = findViewById(R.id.actualizar_button);
        this.eliminarButton = findViewById(R.id.eliminar_button);
        this.toolbar = findViewById(R.id.toolbar_ejercicio_edit);
        this.btnSelectImage = findViewById(R.id.btn_subir_imagen);
        this.btnClearImage = findViewById(R.id.btn_remover_imagen);
        this.mediaPreview = findViewById(R.id.media_preview);

        ToolbarUtils.setupToolbar(this, toolbar);

        controller.cargarCategorias();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoCategorias);
        categoriaInput.setAdapter(adapter);

    }

    public void actualizarEjercicio() {
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

//            if (imageToStore == null) {
//                mostrarMensaje("Por favor, seleccione una imagen");
//                return;
//            }

        Ejercicio ejercicio = new Ejercicio(ejercicioActual.getId(), nombre, descripcion, imageToStore, urlVideo, categoriaSeleccionada.getId());
        this.controller.actualizarEjercicio(ejercicio);
    }

    public void eliminarEjercicio(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar ejercicio");
        builder.setMessage("¿Estás seguro de que deseas eliminar este ejercicio?");
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            this.controller.eliminarEjercicio(id);
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();
    }


    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("descripcion") && getIntent().hasExtra("imagen") &&
                getIntent().hasExtra("urlVideo") && getIntent().hasExtra("categoriaId")
        ) {
            int id = Integer.parseInt(getIntent().getStringExtra("id"));
            String nombre = getIntent().getStringExtra("nombre");
            String descripcion = getIntent().getStringExtra("descripcion");
            String imagen = getIntent().getStringExtra("imagen");
            String urlVideo = getIntent().getStringExtra("urlVideo");
            int categoriaId = getIntent().getIntExtra("categoriaId", 0);

            nombreInput.getEditText().setText(nombre);
            descripcionInput.getEditText().setText(descripcion);
            urlVideoInput.getEditText().setText(urlVideo);

            categoriaSeleccionada = obtenerCategoria(categoriaId);
            categoriaInput.setText(categoriaSeleccionada != null ? categoriaSeleccionada.getNombre() : null, false);
//            mediaPreview.setImageURI(Uri.parse(imagen));

            this.ejercicioActual = new Ejercicio(id, nombre, descripcion, imagen, urlVideo, categoriaSeleccionada.getId());
        } else {
            Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
        }
    }

    private CategoriaEjercicio obtenerCategoria(int id) {
        for (CategoriaEjercicio categoria : listadoCategorias) {
            if (categoria.getId() == id) {
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