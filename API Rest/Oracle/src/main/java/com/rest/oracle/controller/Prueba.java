package com.rest.oracle.controller;

import javax.sql.DataSource;
import java.sql.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Prueba {

    private final DataSource dataSource;

    public Prueba(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/prueba")
    public String probarConexion() {

        try (Connection conexion = dataSource.getConnection()) {
            return "Conexión exitosa a Oracle";
        } catch (Exception e) {
            return "Error de conexión: " + e.getMessage();
        }
    }
}