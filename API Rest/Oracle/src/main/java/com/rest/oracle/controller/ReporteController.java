package com.rest.oracle.controller;

import com.rest.oracle.model.OrdenReporte;
import com.rest.oracle.model.RepuestoReporte;
import com.rest.oracle.service.ReporteService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @GetMapping("/ordenes-por-estado")
    public List<OrdenReporte> ordenesPorEstado(@RequestParam String estado) {
        return service.ordenesPorEstado(estado);
    }

    @GetMapping("/reparaciones-por-vehiculo")
    public List<OrdenReporte> reparacionesPorVehiculo(@RequestParam String placa) {
        return service.reparacionesPorVehiculo(placa);
    }

    @GetMapping("/repuestos-bajo-inventario")
    public List<RepuestoReporte> repuestosBajoInventario() {
        return service.repuestosBajoInventario();
    }

    @GetMapping("/reparaciones-por-mecanico")
    public List<OrdenReporte> reparacionesPorMecanico(@RequestParam String nombre) {
        return service.reparacionesPorMecanico(nombre);
    }

    @GetMapping("/ingresos")
    public List<OrdenReporte> ingresos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return service.ingresos(inicio, fin);
    }
}

