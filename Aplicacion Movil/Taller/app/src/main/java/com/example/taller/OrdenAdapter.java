package com.example.taller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Locale;

public class OrdenAdapter extends RecyclerView.Adapter<OrdenAdapter.ViewHolder> {

    private final List<Orden> ordenes;

    public OrdenAdapter(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_orden, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orden orden = ordenes.get(position);
        holder.txtIdOrden.setText("Orden #" + orden.getIdOrden());
        holder.txtVehiculo.setText("Placa del Vehículo: " + orden.getPlaca());
        holder.txtMecanico.setText("Mecánico: " + orden.getMecanico());
        holder.txtDescripcion.setText("Descripción: " + orden.getDescripcion());
        holder.txtFechaInicio.setText("Fechad de creación: " + orden.getFechaCreacion());
        holder.txtFechaFinal.setText("Fecha de finalización: " + (orden.getFechaFinal() == null ? "—" : orden.getFechaFinal()));
        holder.txtEstado.setText("Estado: " + orden.getEstado());
        holder.txtTotal.setText(String.format("Total: " + Locale.US, "Q%,.2f", orden.getTotal()));
    }

    @Override
    public int getItemCount() {
        return ordenes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView txtIdOrden;
        final TextView txtVehiculo;
        final TextView txtMecanico;
        final TextView txtDescripcion;
        final TextView txtFechaInicio;
        final TextView txtFechaFinal;
        final TextView txtEstado;
        final TextView txtTotal;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdOrden = itemView.findViewById(R.id.txt_id_orden);
            txtVehiculo = itemView.findViewById(R.id.txt_vehiculo);
            txtMecanico = itemView.findViewById(R.id.txt_mecanico);
            txtDescripcion = itemView.findViewById(R.id.txt_descripcion);
            txtFechaInicio = itemView.findViewById(R.id.txt_fecha_inicio);
            txtFechaFinal = itemView.findViewById(R.id.txt_fecha_final);
            txtEstado = itemView.findViewById(R.id.txt_estado);
            txtTotal = itemView.findViewById(R.id.txt_total);
        }
    }
}
