package model;

public class ClienteModel extends PersonaModel {

    private String direccion;

    public ClienteModel() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
