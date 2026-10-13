package controller;

import java.util.ArrayList;
import model.RepuestoUtilizadoModel;

public class RepuestoUtilizadoController {

    private RepuestoUtilizadoModel modelo;

    public RepuestoUtilizadoController() {
        modelo = new RepuestoUtilizadoModel();
    }

    public String insertar(int idOrden, int idRepuesto, int cantidad) {
        modelo = new RepuestoUtilizadoModel();
        modelo.setIdOrden(idOrden);
        modelo.setIdRepuesto(idRepuesto);
        modelo.setCantidad(cantidad);
        return modelo.insertar();
    }

    public ArrayList<RepuestoUtilizadoModel> listar() {
        return new RepuestoUtilizadoModel().listar();
    }
    
    public String modificar(int id, int cantidad) {
        modelo = new RepuestoUtilizadoModel();
        modelo.setIdRepuestoUtilizado(id);
        modelo.setCantidad(cantidad);
        return modelo.modificar();
    }

    public String eliminar(int id) {
        modelo = new RepuestoUtilizadoModel();
        modelo.setIdRepuestoUtilizado(id);
        return modelo.eliminar();
    }
}
