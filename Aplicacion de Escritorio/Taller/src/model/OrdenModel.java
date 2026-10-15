package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import taller.Conexion;
import util.Exportador;

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
    
    
    public String insertar() {
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return Conexion.SIN_CONEXION;
        }
        String sql = "insert into orden (id_vehiculo, id_mecanico, descripcion, fecha_creacion, estado, total) "
                + "values (?, ?, ?, sysdate, 'PENDIENTE', 0)";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            ps.setInt(2, idMecanico);
            ps.setString(3, descripcion);
            ps.executeUpdate();
            return "Orden creada en estado PENDIENTE";
        } catch (SQLException ex) {
            return Conexion.mensaje(ex);
        } finally {
            Conexion.desconectar(cx);
        }
    }

    public ArrayList<OrdenModel> listar() {
        ArrayList<OrdenModel> lista = new ArrayList<>();
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return lista;
        }
        String sql = "select id_orden, id_vehiculo, id_mecanico, descripcion, fecha_creacion, fecha_final, estado, total "
                + "from orden order by id_orden";
        try (PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrdenModel m = new OrdenModel();
                m.setIdOrden(rs.getInt("id_orden"));
                m.setIdVehiculo(rs.getInt("id_vehiculo"));
                m.setIdMecanico(rs.getInt("id_mecanico"));
                m.setDescripcion(rs.getString("descripcion"));
                Date creacion = rs.getDate("fecha_creacion");
                Date fin = rs.getDate("fecha_final");
                m.setFechaCreacion(creacion == null ? null : creacion.toLocalDate());
                m.setFechaFinal(fin == null ? null : fin.toLocalDate());
                m.setEstado(rs.getString("estado"));
                m.setTotal(rs.getDouble("total"));
                lista.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdenModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.desconectar(cx);
        }
        return lista;
    }

    public static String validarEnProceso(Connection cx, int idOrden) throws SQLException {
        try (PreparedStatement ps = cx.prepareStatement("select estado from orden where id_orden = ? for update")) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return "La orden no existe";
                }
                if (!"EN PROCESO".equals(rs.getString(1))) {
                    return "La orden debe estar EN PROCESO";
                }
            }
        }
        return null;
    }

    public static void sumarTotal(Connection cx, int idOrden, double monto) throws SQLException {
        try (PreparedStatement ps = cx.prepareStatement("update orden set total = total + ? where id_orden = ?")) {
            ps.setDouble(1, monto);
            ps.setInt(2, idOrden);
            ps.executeUpdate();
        }
    }

    public String modificar() {
        if (descripcion == null || descripcion.isEmpty()) {
            return "La descripcion es obligatoria";
        }
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return Conexion.SIN_CONEXION;
        }
        String sql = "update orden set id_vehiculo = case when estado = 'PENDIENTE' then ? else id_vehiculo end, "
                + "id_mecanico = ?, descripcion = ? where id_orden = ? and estado in ('PENDIENTE', 'EN PROCESO')";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            ps.setInt(2, idMecanico);
            ps.setString(3, descripcion);
            ps.setInt(4, idOrden);
            if (ps.executeUpdate() == 0) {
                return "La orden ya no se puede modificar";
            }
            return "Modificado exitosamente";
        } catch (SQLException ex) {
            return Conexion.mensaje(ex);
        } finally {
            Conexion.desconectar(cx);
        }
    }

    public String cambiarEstado(String accion) {
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return Conexion.SIN_CONEXION;
        }
        try {
            cx.setAutoCommit(false);
            String actual;
            try (PreparedStatement ps = cx.prepareStatement("select estado from orden where id_orden = ? for update")) {
                ps.setInt(1, idOrden);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        Conexion.revertir(cx);
                        return "La orden no existe";
                    }
                    actual = rs.getString(1);
                }
            }
            String nuevo;
            String sql;
            switch (accion) {
                case "INICIAR":
                    if (!"PENDIENTE".equals(actual)) {
                        Conexion.revertir(cx);
                        return "Solo se puede iniciar una orden PENDIENTE";
                    }
                    nuevo = "EN PROCESO";
                    sql = "update orden set estado = 'EN PROCESO' where id_orden = ?";
                    break;
                case "FINALIZAR":
                    if (!"EN PROCESO".equals(actual)) {
                        Conexion.revertir(cx);
                        return "Solo se puede finalizar una orden EN PROCESO";
                    }
                    if (contarServicios(cx) == 0) {
                        Conexion.revertir(cx);
                        return "No se puede finalizar una orden sin servicios";
                    }
                    nuevo = "FINALIZADA";
                    sql = "update orden set estado = 'FINALIZADA', fecha_final = sysdate where id_orden = ?";
                    break;
                case "CANCELAR":
                    if (!"PENDIENTE".equals(actual) && !"EN PROCESO".equals(actual)) {
                        Conexion.revertir(cx);
                        return "Esta orden ya no se puede cancelar";
                    }
                    devolverRepuestos(cx);
                    nuevo = "CANCELADA";
                    sql = "update orden set estado = 'CANCELADA' where id_orden = ?";
                    break;
                default:
                    Conexion.revertir(cx);
                    return "Accion no valida";
            }
            try (PreparedStatement ps = cx.prepareStatement(sql)) {
                ps.setInt(1, idOrden);
                ps.executeUpdate();
            }
            cx.commit();
            return "Modificado exitosamente\nEstado: " + nuevo;
        } catch (SQLException ex) {
            Conexion.revertir(cx);
            return Conexion.mensaje(ex);
        } finally {
            Conexion.desconectar(cx);
        }
    }

    private int contarServicios(Connection cx) throws SQLException {
        try (PreparedStatement ps = cx.prepareStatement("select count(*) from servicio_realizado where id_orden = ?")) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private void devolverRepuestos(Connection cx) throws SQLException {
        String sql = "update repuesto r set existencia = existencia + "
                + "(select sum(u.cantidad) from repuesto_utilizado u where u.id_orden = ? and u.id_repuesto = r.id_repuesto) "
                + "where r.id_repuesto in (select id_repuesto from repuesto_utilizado where id_orden = ?)";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            ps.setInt(2, idOrden);
            ps.executeUpdate();
        }
    }
    
    public String comprobante() {
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return null;
        }
        String sql = "select o.descripcion, to_char(o.fecha_creacion, 'DD/MM/YYYY') as fecha_creacion, "
                + "to_char(o.fecha_final, 'DD/MM/YYYY') as fecha_final, o.estado, o.total, "
                + "c.nombre as cliente, c.telefono as telefono_cliente, c.direccion, "
                + "v.placa, v.marca, v.modelo, v.anio, v.color, "
                + "m.nombre as mecanico, m.especialidad "
                + "from orden o "
                + "join vehiculo v on v.id_vehiculo = o.id_vehiculo "
                + "join cliente c on c.id_cliente = v.id_cliente "
                + "join mecanico m on m.id_mecanico = o.id_mecanico "
                + "where o.id_orden = ?";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || !"FINALIZADA".equals(rs.getString("estado"))) {
                    return null;
                }
                double[] manoObra = new double[1];
                double[] repuestosSuma = new double[1];
                StringBuilder h = new StringBuilder();
                h.append("<html><head><meta charset='UTF-8'><title>Comprobante orden ").append(idOrden).append("</title><style>")
                        .append("body{background:#fff;font-family:Arial,sans-serif;margin:32px;max-width:800px}")
                        .append("h1{color:#081d56;margin:0;font:bold 22px Arial,sans-serif}")
                        .append("h2{display:inline-block;background:#ffcc00;color:#fff;font:bold 14px Arial,sans-serif;")
                        .append("padding:6px 24px;border-radius:20px;margin:24px 0 8px}")
                        .append(".enc{display:flex;justify-content:space-between;align-items:flex-end}")
                        .append(".enc div{color:#333;font:bold 12px Bahnschrift,'Segoe UI',Arial,sans-serif}")
                        .append("table{border-collapse:collapse;width:100%;margin-bottom:12px}")
                        .append("th{background:#fff;color:#081d56;text-align:left;vertical-align:top;")
                        .append("font:bold 13px Arial,sans-serif;height:40px;padding:0 12px 0 0}")
                        .append("td{background:#fff;color:#333;font:bold 12px Bahnschrift,'Segoe UI',Arial,sans-serif;")
                        .append("height:40px;padding:0 12px 0 0}")
                        .append(".n{text-align:right}")
                        .append(".dato td:first-child{color:#081d56;width:180px}")
                        .append(".tot{width:50%;margin-left:auto}")
                        .append(".fin td{font-size:16px;color:#081d56}")
                        .append("</style></head><body>")
                        .append("<div class='enc'><div><h1>Taller El &Uacute;ltimo Arranque</h1>")
                        .append("<div>Comprobante de reparaci&oacute;n</div></div>")
                        .append("<div><b>Orden No. ").append(idOrden).append("</b></div></div>");
                h.append(datos("Datos de la orden",
                        "Fecha de ingreso", rs.getString("fecha_creacion"),
                        "Fecha de finalizaci&oacute;n", rs.getString("fecha_final"),
                        "Estado", rs.getString("estado"),
                        "Problema reportado", rs.getString("descripcion")));
                h.append(datos("Cliente",
                        "Nombre", rs.getString("cliente"),
                        "Tel&eacute;fono", rs.getString("telefono_cliente"),
                        "Direcci&oacute;n", rs.getString("direccion")));
                h.append(datos("Veh&iacute;culo",
                        "Placa", rs.getString("placa"),
                        "Marca", rs.getString("marca"),
                        "Modelo", rs.getString("modelo"),
                        "A&ntilde;o", String.valueOf(rs.getInt("anio")),
                        "Color", rs.getString("color")));
                h.append(datos("Mec&aacute;nico asignado",
                        "Nombre", rs.getString("mecanico"),
                        "Especialidad", rs.getString("especialidad")));
                h.append(detalle(cx, "Servicios realizados", "Servicio",
                        "select s.descripcion, r.cantidad, r.precio, r.subtotal from servicio_realizado r "
                        + "join servicio s on s.id_servicio = r.id_servicio where r.id_orden = ? "
                        + "order by r.id_servicio_realizado", manoObra));
                h.append(detalle(cx, "Repuestos utilizados", "Repuesto",
                        "select p.nombre, u.cantidad, u.precio, u.subtotal from repuesto_utilizado u "
                        + "join repuesto p on p.id_repuesto = u.id_repuesto where u.id_orden = ? "
                        + "order by u.id_repuesto_utilizado", repuestosSuma));
                h.append("<table class='tot'>")
                        .append("<tr><td>Mano de obra</td><td class='n'>").append(dinero(manoObra[0])).append("</td></tr>")
                        .append("<tr><td>Repuestos</td><td class='n'>").append(dinero(repuestosSuma[0])).append("</td></tr>")
                        .append("<tr class='fin'><td>TOTAL A PAGAR</td><td class='n'>").append(dinero(rs.getDouble("total"))).append("</td></tr>")
                        .append("</table><p>Glendy Tercero :v</p></body></html>");
                return h.toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdenModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            Conexion.desconectar(cx);
        }
    }

    private String detalle(Connection cx, String titulo, String columna, String sql, double[] suma) throws SQLException {
        StringBuilder t = new StringBuilder("<h2>" + titulo + "</h2><table><tr><th>" + columna
                + "</th><th class='n'>Cantidad</th><th class='n'>Precio</th><th class='n'>Subtotal</th></tr>");
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                boolean hay = false;
                while (rs.next()) {
                    hay = true;
                    suma[0] += rs.getDouble(4);
                    t.append("<tr><td>").append(Exportador.html(rs.getString(1))).append("</td>")
                            .append("<td class='n'>").append(rs.getInt(2)).append("</td>")
                            .append("<td class='n'>").append(dinero(rs.getDouble(3))).append("</td>")
                            .append("<td class='n'>").append(dinero(rs.getDouble(4))).append("</td></tr>");
                }
                if (!hay) {
                    t.append("<tr><td colspan='4'>Sin registros</td></tr>");
                }
            }
        }
        return t.append("</table>").toString();
    }

    private String datos(String titulo, String... pares) {
        StringBuilder t = new StringBuilder("<h2>" + titulo + "</h2><table class='dato'>");
        for (int i = 0; i < pares.length; i += 2) {
            t.append("<tr><td>").append(pares[i]).append("</td><td>").append(Exportador.html(pares[i + 1])).append("</td></tr>");
        }
        return t.append("</table>").toString();
    }

    private String dinero(double valor) {
        return String.format(Locale.US, "Q%,.2f", valor);
    }


}
