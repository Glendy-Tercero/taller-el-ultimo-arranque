package com.example.taller;

public class Orden {

    private int idOrden;
    private int idVehiculo;
    private String placa;
    private int idMecanico;
    private String mecanico;
    private String descripcion;
    private String fechaCreacion;
    private String fechaFinal;
    private String estado;
    private double total;

    public int getIdOrden() {
        return idOrden;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public int getIdMecanico() {
        return idMecanico;
    }

    public String getMecanico() {
        return mecanico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public String getEstado() {
        return estado;
    }

    public double getTotal() {
        return total;
    }
}
