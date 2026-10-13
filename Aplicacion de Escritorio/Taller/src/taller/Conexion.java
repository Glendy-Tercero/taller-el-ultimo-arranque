
package taller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    public static final String SIN_CONEXION = "No se pudo conectar a la base de datos";

    private static final String URL = "jdbc:oracle:thin:@localhost:1521/freepdb1";
    private static final String USUARIO = "system";
    private static final String PASSWORD = "1234";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void desconectar(Connection cx) {
        try {
            if (cx != null) {
                cx.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void revertir(Connection cx) {
        try {
            if (cx != null) {
                cx.rollback();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String mensaje(SQLException ex) {
        Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        switch (ex.getErrorCode()) {
            case 1:
                return "Ya existe un registro con ese valor";
            case 2290:
                return "Valor no permitido";
            case 2291:
                return "El registro relacionado no existe";
            case 2292:
                return "No se puede eliminar: tiene registros asociados";
            default:
                return ex.getMessage();
        }
    }

    
}
