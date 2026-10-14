package com.rest.oracle.model;

public record IngresoReporte(
        int idOrden,
        String placa,
        String fechaFinal,
        double totalServicios,
        double totalRepuestos,
        double total) {
}
