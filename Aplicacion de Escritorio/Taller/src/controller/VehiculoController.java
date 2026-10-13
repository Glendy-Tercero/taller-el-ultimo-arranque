package controller;

import java.util.ArrayList;
import model.VehiculoModel;

public class VehiculoController {

    private VehiculoModel modelo;

    public VehiculoController() {
        modelo = new VehiculoModel();
    }

    public String insertar(int idCliente, String placa, String marca, String modeloAuto, int anio, String color) {
        modelo = new VehiculoModel();
        modelo.setIdCliente(idCliente);
        modelo.setPlaca(placa);
        modelo.setMarca(marca);
        modelo.setModelo(modeloAuto);
        modelo.setAnio(anio);
        modelo.setColor(color);
        return modelo.insertar();
    }

    public ArrayList<VehiculoModel> listar() {
        return new VehiculoModel().listar();
    }
    
    public String modificar(int id, int idCliente, String placa, String marca, String modeloAuto, int anio, String color) {
        modelo = new VehiculoModel();
        modelo.setIdVehiculo(id);
        modelo.setIdCliente(idCliente);
        modelo.setPlaca(placa);
        modelo.setMarca(marca);
        modelo.setModelo(modeloAuto);
        modelo.setAnio(anio);
        modelo.setColor(color);
        return modelo.modificar();
    }

    public String eliminar(int id) {
        modelo = new VehiculoModel();
        modelo.setIdVehiculo(id);
        return modelo.eliminar();
    }
    
}
