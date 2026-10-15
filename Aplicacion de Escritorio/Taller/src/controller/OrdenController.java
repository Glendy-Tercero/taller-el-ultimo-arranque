package controller;

import java.util.ArrayList;
import model.OrdenModel;

public class OrdenController {

    private OrdenModel modelo;

    public OrdenController() {
        modelo = new OrdenModel();
    }

    public String insertar(int idVehiculo, int idMecanico, String descripcion) {
        modelo = new OrdenModel();
        modelo.setIdVehiculo(idVehiculo);
        modelo.setIdMecanico(idMecanico);
        modelo.setDescripcion(descripcion);
        return modelo.insertar();
    }

    public ArrayList<OrdenModel> listar() {
        return new OrdenModel().listar();
    }
    
    public String modificar(int id, int idVehiculo, int idMecanico, String descripcion) {
        modelo = new OrdenModel();
        modelo.setIdOrden(id);
        modelo.setIdVehiculo(idVehiculo);
        modelo.setIdMecanico(idMecanico);
        modelo.setDescripcion(descripcion);
        return modelo.modificar();
    }

    public String cambiarEstado(int id, String accion) {
        modelo = new OrdenModel();
        modelo.setIdOrden(id);
        return modelo.cambiarEstado(accion);
    }
    
    public String comprobante(int id) {
        modelo = new OrdenModel();
        modelo.setIdOrden(id);
        return modelo.comprobante();
    }
    
}
