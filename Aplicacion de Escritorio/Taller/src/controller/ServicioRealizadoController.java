package controller;

import java.util.ArrayList;
import model.ServicioRealizadoModel;

public class ServicioRealizadoController {

    private ServicioRealizadoModel modelo;

    public ServicioRealizadoController() {
        modelo = new ServicioRealizadoModel();
    }

    public String insertar(int idOrden, int idServicio, int cantidad) {
        modelo = new ServicioRealizadoModel();
        modelo.setIdOrden(idOrden);
        modelo.setIdServicio(idServicio);
        modelo.setCantidad(cantidad);
        return modelo.insertar();
    }

    public ArrayList<ServicioRealizadoModel> listar() {
        return new ServicioRealizadoModel().listar();
    }
    
    public String modificar(int id, int cantidad) {
        modelo = new ServicioRealizadoModel();
        modelo.setIdServicioRealizado(id);
        modelo.setCantidad(cantidad);
        return modelo.modificar();
    }

    public String eliminar(int id) {
        modelo = new ServicioRealizadoModel();
        modelo.setIdServicioRealizado(id);
        return modelo.eliminar();
    }
    
}
