package controller;

import java.io.IOException;
import java.util.List;
import model.OrdenModel;
import model.ReporteModel;
import model.RepuestoModel;

public class ReporteController {

    private final ReporteModel modelo = new ReporteModel();

    public List<OrdenModel> porEstado(String estado)
            throws IOException, InterruptedException {
        return modelo.porEstado(estado);
    }

    public List<OrdenModel> porPlaca(String placa)
            throws IOException, InterruptedException {
        return modelo.porPlaca(placa);
    }

    public List<OrdenModel> porMecanico(String nombre)
            throws IOException, InterruptedException {
        return modelo.porMecanico(nombre);
    }

    public List<OrdenModel> porFechas(String inicio, String fin)
            throws IOException, InterruptedException {
        return modelo.porFechas(inicio, fin);
    }

    public List<RepuestoModel> repuestosBajoInventario()
            throws IOException, InterruptedException {
        return modelo.repuestosBajoInventario();
    }
}
