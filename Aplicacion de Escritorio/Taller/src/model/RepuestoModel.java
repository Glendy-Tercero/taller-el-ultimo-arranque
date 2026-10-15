package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import taller.Conexion;

public class RepuestoModel {

    private int idRepuesto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int existencia;

    public RepuestoModel() {
    }

    public int getIdRepuesto() {
        return idRepuesto;
    }

    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }
    
    
    public String insertar() {
        if (precio < 0) {
            return "El precio no puede ser negativo";
        }
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return Conexion.SIN_CONEXION;
        }
        String sql = "insert into repuesto (nombre, descripcion, precio, existencia) values (?, ?, ?, 0)";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setDouble(3, precio);
            ps.executeUpdate();
            return "Guardado exitosamente";
        } catch (SQLException ex) {
            return Conexion.mensaje(ex);
        } finally {
            Conexion.desconectar(cx);
        }
    }

    public ArrayList<RepuestoModel> listar() {
        ArrayList<RepuestoModel> lista = new ArrayList<>();
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return lista;
        }
        String sql = "select id_repuesto, nombre, descripcion, precio, existencia from repuesto order by id_repuesto";
        try (PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RepuestoModel m = new RepuestoModel();
                m.setIdRepuesto(rs.getInt("id_repuesto"));
                m.setNombre(rs.getString("nombre"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setPrecio(rs.getDouble("precio"));
                m.setExistencia(rs.getInt("existencia"));
                lista.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepuestoModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.desconectar(cx);
        }
        return lista;
    }

    public String modificar() {
        if (precio < 0) {
            return "El precio no puede ser negativo";
        }
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return Conexion.SIN_CONEXION;
        }
        String sql = "update repuesto set nombre = ?, descripcion = ?, precio = ? where id_repuesto = ?";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setDouble(3, precio);
            ps.setInt(4, idRepuesto);
            ps.executeUpdate();
            return "Modificado exitosamente";
        } catch (SQLException ex) {
            return Conexion.mensaje(ex);
        } finally {
            Conexion.desconectar(cx);
        }
    }

    public String eliminar() {
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return Conexion.SIN_CONEXION;
        }
        String sql = "delete from repuesto where id_repuesto = ?";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, idRepuesto);
            ps.executeUpdate();
            return "Eliminado exitosamente";
        } catch (SQLException ex) {
            return Conexion.mensaje(ex);
        } finally {
            Conexion.desconectar(cx);
        }
    }

    public static boolean ajustarExistencia(Connection cx, int idRepuesto, int cambio) throws SQLException {
        String sql = "update repuesto set existencia = existencia + ? where id_repuesto = ? and existencia + ? >= 0";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, cambio);
            ps.setInt(2, idRepuesto);
            ps.setInt(3, cambio);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean existe() {
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return false;
        }
        String sql = "select count(*) from repuesto where lower(nombre) = lower(?)";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepuestoModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexion.desconectar(cx);
        }
    }

}
