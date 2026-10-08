package model;

public class RepuestoUtilizadoModel {

    private int idRepuestoUtilizado;
    private int idOrden;
    private int idRepuesto;
    private int cantidad;
    private double precio;
    private double subtotal;

    public RepuestoUtilizadoModel() {
    }

    public int getIdRepuestoUtilizado() {
        return idRepuestoUtilizado;
    }

    public void setIdRepuestoUtilizado(int idRepuestoUtilizado) {
        this.idRepuestoUtilizado = idRepuestoUtilizado;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdRepuesto() {
        return idRepuesto;
    }

    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}

