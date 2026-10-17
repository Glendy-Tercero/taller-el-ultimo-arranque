package com.example.taller;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        abrir(R.id.layout_estado, ReporteActivity.ESTADO);
        abrir(R.id.layout_fecha, ReporteActivity.FECHA);
        abrir(R.id.layout_vehiculo, ReporteActivity.PLACA);
        abrir(R.id.layout_mecanico, ReporteActivity.MECANICO);
        abrir(R.id.layout_repuesto, ReporteActivity.REPUESTO);

    }

    private void abrir(int idLayout, String tipo) {
        findViewById(idLayout).setOnClickListener(v -> {
            Intent intent = new Intent(this, ReporteActivity.class);
            intent.putExtra(ReporteActivity.TIPO, tipo);
            startActivity(intent);
        });
    }
}