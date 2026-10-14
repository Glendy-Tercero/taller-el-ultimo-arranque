package com.rest.oracle.model;

public record OrdenReporte(
        int idOrden,
        int idVehiculo,
        String placa,
        int idMecanico,
        String mecanico,
        String descripcion,
        String fechaCreacion,
        String fechaFinal,
        String estado,
        double total) {
}
