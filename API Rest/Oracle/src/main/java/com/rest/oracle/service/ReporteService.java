package com.rest.oracle.service;

import com.rest.oracle.model.IngresoReporte;
import com.rest.oracle.model.OrdenReporte;
import com.rest.oracle.model.RepuestoReporte;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReporteService {

    private static final List<String> ESTADOS = List.of("PENDIENTE", "EN PROCESO", "FINALIZADA", "CANCELADA");

    private static final String ORDENES =
            "select o.id_orden, o.id_vehiculo, "
                    + "(select v.placa from vehiculo v where v.id_vehiculo = o.id_vehiculo) as placa, "
                    + "o.id_mecanico, "
                    + "(select m.nombre from mecanico m where m.id_mecanico = o.id_mecanico) as mecanico, "
                    + "o.descripcion, "
                    + "to_char(o.fecha_creacion, 'YYYY-MM-DD') as fecha_creacion, "
                    + "to_char(o.fecha_final, 'YYYY-MM-DD') as fecha_final, o.estado, o.total from orden o ";

    private final JdbcTemplate jdbc;

    private final RowMapper<OrdenReporte> ordenMapper = (rs, fila) -> new OrdenReporte(
            rs.getInt("id_orden"),
            rs.getInt("id_vehiculo"),
            rs.getString("placa"),
            rs.getInt("id_mecanico"),
            rs.getString("mecanico"),
            rs.getString("descripcion"),
            rs.getString("fecha_creacion"),
            rs.getString("fecha_final"),
            rs.getString("estado"),
            rs.getDouble("total"));

    public ReporteService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<OrdenReporte> ordenesPorEstado(String estado) {
        String valor = limpiar(estado, "El estado es obligatorio").toUpperCase();
        if (!ESTADOS.contains(valor)) {
            throw error("Estado no valido. Use: PENDIENTE, EN PROCESO, FINALIZADA o CANCELADA");
        }
        return jdbc.query(ORDENES + "where estado = ? order by id_orden", ordenMapper, valor);
    }

    public List<OrdenReporte> reparacionesPorVehiculo(String placa) {
        String valor = limpiar(placa, "La placa es obligatoria");
        String sql = ORDENES + "where id_vehiculo in (select id_vehiculo from vehiculo where upper(placa) = upper(?)) "
                + "order by id_orden";
        return jdbc.query(sql, ordenMapper, valor);
    }

    public List<OrdenReporte> reparacionesPorMecanico(String nombre) {
        String valor = limpiar(nombre, "El nombre del mecanico es obligatorio");
        String sql = ORDENES + "where id_mecanico in (select id_mecanico from mecanico where lower(nombre) like ?) "
                + "order by id_mecanico, id_orden";
        return jdbc.query(sql, ordenMapper, "%" + valor.toLowerCase() + "%");
    }

    public List<RepuestoReporte> repuestosBajoInventario() {
        String sql = "select id_repuesto, nombre, descripcion, precio, existencia "
                + "from repuesto where existencia <= 5 order by existencia, id_repuesto";
        return jdbc.query(sql, (rs, fila) -> new RepuestoReporte(
                rs.getInt("id_repuesto"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getInt("existencia")));
    }

    public List<OrdenReporte> ingresos(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) {
            throw error("La fecha final no puede ser menor a la inicial");
        }
        String sql = ORDENES + "where estado = 'FINALIZADA' and fecha_final >= ? and fecha_final < ? "
                + "order by fecha_final, id_orden";
        return jdbc.query(sql, ordenMapper, Date.valueOf(inicio), Date.valueOf(fin.plusDays(1)));
    }

    private String limpiar(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw error(mensaje);
        }
        return texto.trim();
    }

    private ResponseStatusException error(String mensaje) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje);
    }
}