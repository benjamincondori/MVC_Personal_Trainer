package com.android.kotlin.personaltrainer.view.PlanEntrenamiento;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kotlin.personaltrainer.R;
import com.android.kotlin.personaltrainer.controller.CPlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;
import com.android.kotlin.personaltrainer.model.Ejercicio.Ejercicio;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.DetallePlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.PlanEntrenamiento.PlanEntrenamiento;
import com.android.kotlin.personaltrainer.model.Rutina.DetalleRutinaEjercicio;
import com.android.kotlin.personaltrainer.model.Rutina.Rutina;
import com.android.kotlin.personaltrainer.utils.TemplatePdf;
import com.android.kotlin.personaltrainer.utils.ToolbarUtils;
import com.android.kotlin.personaltrainer.view.Rutina.RutinaDetalleAdapter;
import com.android.kotlin.personaltrainer.view.Rutina.VDetalleRutina;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VDetallePlanEntrenamiento extends AppCompatActivity {

    CPlanEntrenamiento controller;
    List<Rutina> listadoRutinas;
    List<DetallePlanEntrenamiento> listadoDetallePlanes;
    List<DetalleRutinaEjercicio> listadoDetalleRutinas;
    List<Ejercicio> listadoEjercicios;
    PlanEntrenamiento planActual;
    Cliente clienteActual;
    PlanEntrenamientoDetalleAdapter listAdapter;

    RecyclerView recyclerView;
    TextView nombreTextView, descripcionTextView, clienteTextView, fechaInicioTextView, fechaFinTextView, tipoTextView;
    Toolbar toolbar;
    AutoCompleteTextView spinnerRutinas, spinnerDia;
    MaterialButton agregarButton, generarPdfButton;
    LinearLayout emptyLayout;
    Rutina rutinaSeleccionada;

    String nombrePdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_plan_entrenamiento);

        initComponets();
        getAndSetIntentData();

        if (this.planActual != null) {
            this.controller.cargarDetallePlanEntrenamiento(this.planActual.getId());
        }

        if (this.listadoDetallePlanes != null) {
            this.listAdapter = new PlanEntrenamientoDetalleAdapter(this, this.listadoDetallePlanes, this.listadoRutinas, controller);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerView.setAdapter(this.listAdapter);
        }

        agregarButton.setOnClickListener(view -> {
            agregarRutina();
        });

        generarPdfButton.setOnClickListener(view -> {
            verificarPermisosYGenerarPdf();
        });

    }

    public void initComponets() {
        this.agregarButton = findViewById(R.id.agregar_rutina_button);
        this.generarPdfButton = findViewById(R.id.generar_pdf_button);
        this.toolbar = findViewById(R.id.toolbar_detalle_plan_entrenamiento);
        this.nombreTextView = findViewById(R.id.textViewNombre);
        this.descripcionTextView = findViewById(R.id.textViewDescripcion);
        this.clienteTextView = findViewById(R.id.clienteTextView);
        this.fechaInicioTextView = findViewById(R.id.fechaInicioTextView);
        this.fechaFinTextView = findViewById(R.id.fechaFinTextView);
        this.tipoTextView = findViewById(R.id.tipoTextView);
        this.recyclerView = findViewById(R.id.recyclerDetallePlanes);
        this.emptyLayout = findViewById(R.id.empty_layout);

        ToolbarUtils.setupToolbar(this, toolbar);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        this.controller = new CPlanEntrenamiento(this);
        this.controller.cargarRutinas();
    }

    public void agregarRutina() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(VDetallePlanEntrenamiento.this);
        View view1 = LayoutInflater.from(VDetallePlanEntrenamiento.this).inflate(R.layout.bottom_sheet_detalle_plan_entrenamiento, null);
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();

        MaterialButton agregarButton = view1.findViewById(R.id.guardar_button);
        this.spinnerRutinas = view1.findViewById(R.id.spinnerRutinas);
        this.spinnerDia = view1.findViewById(R.id.spinnerDia);

        if (spinnerRutinas != null) {
            ArrayAdapter<Rutina> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoRutinas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRutinas.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No se encontraron rutinas", Toast.LENGTH_SHORT).show();
        }

        spinnerRutinas.setOnItemClickListener((adapterView, view, i, l) -> {
            rutinaSeleccionada = (Rutina) adapterView.getItemAtPosition(i);
        });

        // cargar dias de la semana
        String[] dias = new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

        if (spinnerDia != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDia.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No se encontraron días", Toast.LENGTH_SHORT).show();
        }

        agregarButton.setOnClickListener(view2 -> {
            String dia = spinnerDia.getText().toString();

            if (dia.isEmpty()) {
                Toast.makeText(VDetallePlanEntrenamiento.this, "Por favor, seleccione un día de la semana", Toast.LENGTH_SHORT).show();
                return;
            }

            if (rutinaSeleccionada == null) {
                Toast.makeText(VDetallePlanEntrenamiento.this, "Por favor, seleccione una rutina", Toast.LENGTH_SHORT).show();
                return;
            }

            DetallePlanEntrenamiento detalle = new DetallePlanEntrenamiento(planActual.getId(), rutinaSeleccionada.getId(), dia);

            controller.guardarDetallePlanEntrenamiento(detalle);
            bottomSheetDialog.dismiss();
            onResume();
        });

        bottomSheetDialog.setOnDismissListener(dialogInterface -> {
        });
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("descripcion") && getIntent().hasExtra("fechaInicio") &&
                getIntent().hasExtra("fechaFin") && getIntent().hasExtra("tipo") &&
                getIntent().hasExtra("idCliente")
        ) {
            int id = Integer.parseInt(getIntent().getStringExtra("id"));
            String nombre = getIntent().getStringExtra("nombre");
            String descripcion = getIntent().getStringExtra("descripcion");
            String fechaInicio = getIntent().getStringExtra("fechaInicio");
            String fechaFin = getIntent().getStringExtra("fechaFin");
            String tipo = getIntent().getStringExtra("tipo");
            int idCliente = getIntent().getIntExtra("idCliente", 0);

            this.nombreTextView.setText(nombre);
            this.descripcionTextView.setText(descripcion);
            this.fechaInicioTextView.setText(fechaInicio);
            this.fechaFinTextView.setText(fechaFin);
            this.tipoTextView.setText(tipo);

            controller.cargarCliente(idCliente);
            clienteTextView.setText(clienteActual != null ? clienteActual.getNombreCompleto() : "");

            this.planActual = new PlanEntrenamiento(id, nombre, descripcion, fechaInicio, fechaFin, tipo, idCliente);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarCliente(Cliente cliente) {
        this.clienteActual = cliente;
    }

    public void cargarRutinas(List<Rutina> rutinas) {
        this.listadoRutinas = rutinas;
    }

    public void cargarDetallePlanEntrenamiento(List<DetallePlanEntrenamiento> listado) {
        this.listadoDetallePlanes = listado;
    }

    public void cargarDetalleRutinas(List<DetalleRutinaEjercicio> listado) {
        this.listadoDetalleRutinas = listado;
    }

    public void cargarEjercicios(List<Ejercicio> listado) {
        this.listadoEjercicios = listado;
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void verificarVacio() {
        if (this.listadoDetallePlanes.isEmpty()) {
            this.emptyLayout.setVisibility(LinearLayout.VISIBLE);
            this.recyclerView.setVisibility(RecyclerView.GONE);
        } else {
            this.emptyLayout.setVisibility(LinearLayout.GONE);
            this.recyclerView.setVisibility(RecyclerView.VISIBLE);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isAceptado -> {
                if (isAceptado)
                    Toast.makeText(this, "Acceso concedido. Ahora puedes guardar archivos en tu dispositivo.", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "Permiso denegado. No podremos guardar archivos sin acceso al almacenamiento.", Toast.LENGTH_SHORT).show();
            });

    private void verificarPermisosYGenerarPdf() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            generarPDF();
        } else if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Para guardar tus archivos, necesitamos acceso al almacenamiento externo. Por favor, concede el permiso desde la configuración.", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void generarPDF() {
        String[] header = {"Imagen", "Ejercicio", "Serie", "Repeticiones", "Descanso [s]", "Url video"};

        TemplatePdf templatePDF = new TemplatePdf(this);
        nombrePdf = planActual.getNombre().replace(" ", "_");
        templatePDF.openDocument(nombrePdf);
        templatePDF.addHeaders(planActual.getNombre(), clienteActual.getNombreCompleto(), planActual.getFechaInicio(), planActual.getFechaFin(), planActual.getTipo());

        List<Rutina> rutinas = obtenerRutinasDelDetalle();

        for (Rutina rutina : rutinas) {
            String dia = obtenerDia(rutina.getId()).toUpperCase();
            templatePDF.addTextInParagraph(dia +": " + rutina.getNombre());
            controller.obtenerDetalleRutinas(rutina.getId());
            controller.obtenerEjerciciosDeLaRutina(rutina.getId());
            DetalleRutinaEjercicio nota = new DetalleRutinaEjercicio();
            ArrayList<String[]> body = new ArrayList<>();
            for (DetalleRutinaEjercicio detalle : listadoDetalleRutinas) {
                Ejercicio ejercicio = obtenerEjercicio(detalle.getIdEjercicio());
                body.add(new String[]{"imagen", ejercicio.getNombre(), String.valueOf(detalle.getSeries()),
                        String.valueOf(detalle.getRepeticiones()), String.valueOf(detalle.getDescanso()), ejercicio.getUrlVideo()});
                nota = detalle;
            }
            templatePDF.createTable(header, body);
            String notaStr = getDetalleEjercicio(nota);
            templatePDF.addParagraph(notaStr);
        }

        templatePDF.closeDocument();

        Toast.makeText(this, "PDF generado correctamente", Toast.LENGTH_SHORT).show();

        String nombreArchivo = nombrePdf + ".pdf";
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Personal_Trainer_PDF");
        File file = new File(dir, nombreArchivo);

        Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file, nombreArchivo);

        final Intent compartir = new Intent(android.content.Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_SUBJECT, "Envio de archivos PDF")
                .putExtra(Intent.EXTRA_TEXT, "Se adjunta archivo")
                .putExtra(Intent.EXTRA_STREAM, uri)
                .setType("application/pdf");
        startActivity(Intent.createChooser(compartir, "Compartir PDF"));
    }

    private List<Rutina> obtenerRutinasDelDetalle() {
        List<Rutina> rutinas = new ArrayList<>();
        for (DetallePlanEntrenamiento detalle : listadoDetallePlanes) {
            int idRutina = detalle.getIdRutina();
            for (Rutina rutina : listadoRutinas) {
                if (rutina.getId() == idRutina) {
                    rutinas.add(rutina);
                }
            }
        }
        return rutinas;
    }

    private String obtenerDia(int idRutina) {
        String dia = "";
        for (DetallePlanEntrenamiento detalle : listadoDetallePlanes) {
            if (detalle.getIdRutina() == idRutina) {
                dia = detalle.getDia();
            }
        }
        return dia;
    }

    private Ejercicio obtenerEjercicio(int id) {
        for (Ejercicio ejercicio : listadoEjercicios) {
            if (ejercicio.getId() == id) {
                return ejercicio;
            }
        }
        return null;
    }

    private String getDetalleEjercicio(DetalleRutinaEjercicio detalle) {
        String detalleEjercicio;
        if (detalle.getSeries() == 1) {
            detalleEjercicio = "Realizar 1 serie de " + detalle.getRepeticiones() + " repeticiones, con un descanso de "
                    + detalle.getDescanso() + " segundos.";
        } else {
            detalleEjercicio = "Realizar " + detalle.getSeries() + " series de " +
                    detalle.getRepeticiones() + " repeticiones cada serie, con un descanso de "
                    + detalle.getDescanso() + " segundos entre series.";
        }
        return "Nota: " + detalleEjercicio;
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.cargarDetallePlanEntrenamiento(this.planActual.getId());
        this.listAdapter.refreshData(this.listadoDetallePlanes);
        verificarVacio();
    }
}