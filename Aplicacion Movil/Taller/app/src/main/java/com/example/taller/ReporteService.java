package com.example.taller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReporteService {
    @GET("reportes/ordenes-por-estado")
    Call<List<Orden>> ordenesPorEstado(@Query("estado") String estado);

    @GET("reportes/ingresos")
    Call<List<Orden>> ordenesPorFechas(@Query("inicio") String inicio, @Query("fin") String fin);

    @GET("reportes/reparaciones-por-vehiculo")
    Call<List<Orden>> ordenesPorPlaca(@Query("placa") String placa);

    @GET("reportes/reparaciones-por-mecanico")
    Call<List<Orden>> ordenesPorMecanico(@Query("nombre") String nombre);

    @GET("reportes/repuestos-bajo-inventario")
    Call<List<Repuesto>> repuestosBajoInventario();
}
