package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import taller.Conexion;

public class MecanicoModel extends PersonaModel {

    private String especialidad;

    public MecanicoModel() {
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
   
    public String insertar() {
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return Conexion.SIN_CONEXION;
        }
        String sql = "insert into mecanico (nombre, especialidad, telefono) values (?, ?, ?)";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setString(1, getNombre());
            ps.setString(2, especialidad);
            ps.setString(3, getTelefono());
            ps.executeUpdate();
            return "Guardado exitosamente\n" + mostrarInformacion();
        } catch (SQLException ex) {
            return Conexion.mensaje(ex);
        } finally {
            Conexion.desconectar(cx);
        }
    }

    public ArrayList<MecanicoModel> listar() {
        ArrayList<MecanicoModel> lista = new ArrayList<>();
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return lista;
        }
        String sql = "select id_mecanico, nombre, especialidad, telefono from mecanico order by id_mecanico";
        try (PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MecanicoModel m = new MecanicoModel();
                m.setId(rs.getInt("id_mecanico"));
                m.setNombre(rs.getString("nombre"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setTelefono(rs.getString("telefono"));
                lista.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MecanicoModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.desconectar(cx);
        }
        return lista;
    }

    public String modificar() {
        Connection cx = Conexion.conectar();
        if (cx == null) {
            return Conexion.SIN_CONEXION;
        }
        String sql = "update mecanico set nombre = ?, especialidad = ?, telefono = ? where id_mecanico = ?";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setString(1, getNombre());
            ps.setString(2, especialidad);
            ps.setString(3, getTelefono());
            ps.setInt(4, getId());
            ps.executeUpdate();
            return "Modificado exitosamente\n" + mostrarInformacion();
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
        String sql = "delete from mecanico where id_mecanico = ?";
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, getId());
            ps.executeUpdate();
            return "Eliminado exitosamente";
        } catch (SQLException ex) {
            return Conexion.mensaje(ex);
        } finally {
            Conexion.desconectar(cx);
        }
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + " | Especialidad: " + especialidad;
    }

}