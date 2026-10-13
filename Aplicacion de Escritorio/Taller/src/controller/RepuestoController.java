package controller;

import java.util.ArrayList;
import model.RepuestoModel;

public class RepuestoController {

    private RepuestoModel modelo;

    public RepuestoController() {
        modelo = new RepuestoModel();
    }

    public String insertar(String nombre, String descripcion, double precio) {
        modelo = new RepuestoModel();
        modelo.setNombre(nombre);
        modelo.setDescripcion(descripcion);
        modelo.setPrecio(precio);
        return modelo.insertar();
    }

    public ArrayList<RepuestoModel> listar() {
        return new RepuestoModel().listar();
    }
    
    public String modificar(int id, String nombre, String descripcion, double precio) {
        modelo = new RepuestoModel();
        modelo.setIdRepuesto(id);
        modelo.setNombre(nombre);
        modelo.setDescripcion(descripcion);
        modelo.setPrecio(precio);
        return modelo.modificar();
    }

    public String eliminar(int id) {
        modelo = new RepuestoModel();
        modelo.setIdRepuesto(id);
        return modelo.eliminar();
    }
}

