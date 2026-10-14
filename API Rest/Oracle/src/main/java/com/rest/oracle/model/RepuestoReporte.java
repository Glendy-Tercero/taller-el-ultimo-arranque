package com.rest.oracle.model;

public record RepuestoReporte(
        int idRepuesto,
        String nombre,
        String descripcion,
        double precio,
        int existencia) {
}