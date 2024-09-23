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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class VEditarEjercicio extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_ejercicio);

        this.initComponents();

        ToolbarUtils.setupToolbar(this, toolbar);

        controller.cargarCategorias();

        ArrayAdapter<CategoriaEjercicio> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoCategorias);
        categoriaInput.setAdapter(adapter);

        getAndSetIntentData();
        setListeners();
    }

    // Método para inicializar los elementos de la vista
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
    }

    // Método para configurar los listeners de los elementos de la vista
    private void setListeners() {

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

        // Listener para el botón de actualizar
        this.actualizarButton.setOnClickListener(view -> {
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

//            if (imageToStore == null) {
//                mostrarMensaje("Por favor, seleccione una imagen");
//                return;
//            }

            Ejercicio ejercicio = new Ejercicio(ejercicioActual.getId(), nombre, descripcion, imageToStore, urlVideo, categoria.getId());
            this.controller.actualizarEjercicio(ejercicio);
        });

        this.eliminarButton.setOnClickListener(view -> {
            confirmDialog(ejercicioActual.getId());
        });

    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("descripcion") && getIntent().hasExtra("imagen") &&
                getIntent().hasExtra("urlVideo") && getIntent().hasExtra("categoriaId")
        ) {
            // Getting data from intent
            int id = Integer.parseInt(getIntent().getStringExtra("id"));
            String nombre = getIntent().getStringExtra("nombre");
            String descripcion = getIntent().getStringExtra("descripcion");
            String imagen = getIntent().getStringExtra("imagen");
            String urlVideo = getIntent().getStringExtra("urlVideo");
            int categoriaId = getIntent().getIntExtra("categoriaId", 0);

            // Setting data
            nombreInput.getEditText().setText(nombre);
            descripcionInput.getEditText().setText(descripcion);
            urlVideoInput.getEditText().setText(urlVideo);

            CategoriaEjercicio categoria = obtenerCategoria(categoriaId);
            categoriaInput.setText(categoria != null ? categoria.getNombre() : null, false);
//            mediaPreview.setImageURI(Uri.parse(imagen));

            this.ejercicioActual = new Ejercicio(id, nombre, descripcion, imagen, urlVideo, categoriaId);
        } else {
            Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDialog(int id) {
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

    private CategoriaEjercicio obtenerCategoria(int id) {
        for (CategoriaEjercicio categoria : listadoCategorias) {
            if (categoria.getId() == id) {
                return categoria;
            }
        }
        return null;
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