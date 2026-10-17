package com.example.taller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.widget.Toast;

import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReporteActivity extends AppCompatActivity {

    public static final String TIPO = "tipo";
    public static final String ESTADO = "estado";
    public static final String FECHA = "fecha";
    public static final String PLACA = "placa";
    public static final String MECANICO = "mecanico";
    public static final String REPUESTO = "repuesto";

    private ReporteService api;
    private RecyclerView recyclerResultado;
    private TextView txtTituloRep;
    private CardView cardEstado;
    private CardView cardFechaInicio;
    private CardView cardFechaFinal;
    private CardView cardPlaca;
    private CardView cardMecanico;
    private EditText edtxtFechaInicio;
    private EditText edtxtFechaFinal;
    private EditText edtxtPlaca;
    private EditText edtxtMecanico;
    private Spinner spinnerEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reporte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_reporte), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        enlazarVistas();
        api = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ReporteService.class);

        findViewById(R.id.btn_regresar).setOnClickListener(v -> finish());
        recyclerResultado.setLayoutManager(new LinearLayoutManager(this));

        String tipo = getIntent().getStringExtra(TIPO);
        if (tipo == null) {
            tipo = ESTADO;
        }
        switch (tipo) {
            case FECHA:
                configurarFechas();
                break;
            case PLACA:
                configurarPlaca();
                break;
            case MECANICO:
                configurarMecanico();
                break;
            case REPUESTO:
                configurarRepuesto();
                break;
            default:
                configurarEstado();
                break;
        }
    }

    private void enlazarVistas() {
        recyclerResultado = findViewById(R.id.recycler_resultado);
        txtTituloRep = findViewById(R.id.txt_titulo_rep);
        cardEstado = findViewById(R.id.card_estado);
        cardFechaInicio = findViewById(R.id.card_buscar_fecha_inicio);
        cardFechaFinal = findViewById(R.id.card_buscar_fecha_final);
        cardPlaca = findViewById(R.id.card_buscar_placa);
        cardMecanico = findViewById(R.id.card_buscar_mecanico);
        edtxtFechaInicio = findViewById(R.id.edtxt_fecha_inicio);
        edtxtFechaFinal = findViewById(R.id.edtxt_fecha_final);
        edtxtPlaca = findViewById(R.id.edtxt_placa);
        edtxtMecanico = findViewById(R.id.edtxt_mecanico);
        spinnerEstado = findViewById(R.id.spinner_estado);

        txtTituloRep.setVisibility(View.GONE);
        cardEstado.setVisibility(View.GONE);
        cardFechaInicio.setVisibility(View.GONE);
        cardFechaFinal.setVisibility(View.GONE);
        cardPlaca.setVisibility(View.GONE);
        cardMecanico.setVisibility(View.GONE);
    }

    private void configurarEstado() {
        cardEstado.setVisibility(View.VISIBLE);
        String[] estados = {"PENDIENTE", "EN PROCESO", "FINALIZADA", "CANCELADA"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                estados
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView texto = (TextView) super.getView(position, convertView, parent);
                texto.setTextColor(getResources().getColor(R.color.dark_grey));
                return texto;
            }
        };

        spinnerEstado.setAdapter(adaptador);
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicion, long id) {
                cargarOrdenes(api.ordenesPorEstado(estados[posicion]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void configurarFechas() {
        cardFechaInicio.setVisibility(View.VISIBLE);
        cardFechaFinal.setVisibility(View.VISIBLE);
        alBuscar(edtxtFechaInicio, this::buscarPorFechas);
        alBuscar(edtxtFechaFinal, this::buscarPorFechas);
    }

    private void configurarPlaca() {
        cardPlaca.setVisibility(View.VISIBLE);
        alBuscar(edtxtPlaca, () -> {
            String placa = edtxtPlaca.getText().toString().trim().toUpperCase();
            if (placa.isEmpty()) {
                mensaje("Escriba una placa");
            } else {
                cargarOrdenes(api.ordenesPorPlaca(placa));
            }
        });
    }

    private void configurarMecanico() {
        cardMecanico.setVisibility(View.VISIBLE);
        alBuscar(edtxtMecanico, () -> {
            String nombre = edtxtMecanico.getText().toString().trim();
            if (nombre.isEmpty()) {
                mensaje("Escriba el nombre del mecánico");
            } else {
                cargarOrdenes(api.ordenesPorMecanico(nombre));
            }
        });
    }

    private void configurarRepuesto() {
        txtTituloRep.setVisibility(View.VISIBLE);
        api.repuestosBajoInventario().enqueue(new Callback<List<Repuesto>>() {
            @Override
            public void onResponse(Call<List<Repuesto>> call, Response<List<Repuesto>> respuesta) {
                if (respuesta.isSuccessful() && respuesta.body() != null) {
                    recyclerResultado.setAdapter(new RepuestoAdapter(respuesta.body()));
                    if (respuesta.body().isEmpty()) {
                        mensaje("No hay repuestos con bajo inventario");
                    }
                } else {
                    mensaje("No se pudieron cargar los repuestos");
                }
            }

            @Override
            public void onFailure(Call<List<Repuesto>> call, Throwable error) {
                Log.e("API_ERROR", "Error al conectar con la API", error);
                Toast.makeText(
                        ReporteActivity.this,
                        error.getClass().getSimpleName() + ": " + error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                mensaje("No se pudo conectar: " + error.getMessage());
            }
        });
    }

    private void buscarPorFechas() {
        String inicioTexto = edtxtFechaInicio.getText().toString().trim();
        String finTexto = edtxtFechaFinal.getText().toString().trim();
        if (inicioTexto.isEmpty() || finTexto.isEmpty()) {
            mensaje("Escriba las dos fechas");
            return;
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        formato.setLenient(false);
        try {
            Date inicio = formato.parse(inicioTexto);
            Date fin = formato.parse(finTexto);
            if (fin.before(inicio)) {
                mensaje("La fecha final no puede ser menor a la inicial");
                return;
            }
            cargarOrdenes(api.ordenesPorFechas(formato.format(inicio), formato.format(fin)));
        } catch (ParseException ex) {
            mensaje("Fecha inválida. Use yyyy-MM-dd");
        }
    }

    private void cargarOrdenes(Call<List<Orden>> llamada) {
        llamada.enqueue(new Callback<List<Orden>>() {
            @Override
            public void onResponse(Call<List<Orden>> call, Response<List<Orden>> respuesta) {
                if (respuesta.isSuccessful() && respuesta.body() != null) {
                    recyclerResultado.setAdapter(new OrdenAdapter(respuesta.body()));
                    if (respuesta.body().isEmpty()) {
                        mensaje("Sin resultados");
                    }
                } else {
                    mensaje("Revise los datos ingresados");
                }
            }

            @Override
            public void onFailure(Call<List<Orden>> call, Throwable error) {
                mensaje("No se pudo conectar con el servidor");
            }
        });
    }

    private void alBuscar(EditText campo, Runnable accion) {
        campo.setOnEditorActionListener((vista, accionId, evento) -> {
            if (accionId == EditorInfo.IME_ACTION_SEARCH) {
                accion.run();
                return true;
            }
            return false;
        });
    }

    private void mensaje(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

}
