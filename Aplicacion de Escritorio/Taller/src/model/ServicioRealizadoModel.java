package model;

public class ServicioRealizadoModel {

    private int idServicioRealizado;
    private int idOrden;
    private int idServicio;
    private int cantidad;
    private double precio;
    private double subtotal;

    public ServicioRealizadoModel() {
    }

    public int getIdServicioRealizado() {
        return idServicioRealizado;
    }

    public void setIdServicioRealizado(int idServicioRealizado) {
        this.idServicioRealizado = idServicioRealizado;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
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

