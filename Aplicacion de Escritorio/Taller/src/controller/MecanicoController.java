package controller;

import java.util.ArrayList;
import model.MecanicoModel;

public class MecanicoController {

    private MecanicoModel modelo;

    public MecanicoController() {
        modelo = new MecanicoModel();
    }

    public String insertar(String nombre, String especialidad, String telefono) {
        modelo = new MecanicoModel();
        modelo.setNombre(nombre);
        modelo.setEspecialidad(especialidad);
        modelo.setTelefono(telefono);
        return modelo.insertar();
    }

    public ArrayList<MecanicoModel> listar() {
        return new MecanicoModel().listar();
    }
    
    public String modificar(int id, String nombre, String especialidad, String telefono) {
        modelo = new MecanicoModel();
        modelo.setId(id);
        modelo.setNombre(nombre);
        modelo.setEspecialidad(especialidad);
        modelo.setTelefono(telefono);
        return modelo.modificar();
    }

    public String eliminar(int id) {
        modelo = new MecanicoModel();
        modelo.setId(id);
        return modelo.eliminar();
    }
    
}
