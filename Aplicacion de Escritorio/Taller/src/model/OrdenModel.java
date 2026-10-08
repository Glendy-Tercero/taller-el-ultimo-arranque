package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrdenModel {

    private int idOrden;
    private int idVehiculo;
    private int idMecanico;
    private String descripcion;
    private LocalDate fechaCreacion;
    private LocalDate fechaFinal;
    private String estado;
    private double total;
    private ArrayList<ServicioRealizadoModel> servicios;
    private ArrayList<RepuestoUtilizadoModel> repuestos;

    public OrdenModel() {
        servicios = new ArrayList<>();
        repuestos = new ArrayList<>();
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(int idMecanico) {
        this.idMecanico = idMecanico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<ServicioRealizadoModel> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<ServicioRealizadoModel> servicios) {
        this.servicios = servicios;
    }

    public ArrayList<RepuestoUtilizadoModel> getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(ArrayList<RepuestoUtilizadoModel> repuestos) {
        this.repuestos = repuestos;
    }
}

