package com.example.taller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Locale;

public class RepuestoAdapter extends RecyclerView.Adapter<RepuestoAdapter.ViewHolder> {

    private final List<Repuesto> repuestos;

    public RepuestoAdapter(List<Repuesto> repuestos) {
        this.repuestos = repuestos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_repuesto, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Repuesto repuesto = repuestos.get(position);
        holder.txtIdRepuesto.setText("Repuesto #" + repuesto.getIdRepuesto());
        holder.txtNombre.setText("Nombre: " + repuesto.getNombre());
        holder.txtDescripcion.setText("Descripción: " + repuesto.getDescripcion());
        holder.txtPrecio.setText(String.format(Locale.US, "Precio: Q%,.2f", repuesto.getPrecio()));
        holder.txtExistencia.setText(repuesto.getExistencia() == 0
                ? "Agotado"
                : "Existencia: " + repuesto.getExistencia());
    }

    @Override
    public int getItemCount() {
        return repuestos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView txtIdRepuesto;
        final TextView txtNombre;
        final TextView txtDescripcion;
        final TextView txtPrecio;
        final TextView txtExistencia;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdRepuesto = itemView.findViewById(R.id.txt_id_repuesto);
            txtNombre = itemView.findViewById(R.id.txt_nombre);
            txtDescripcion = itemView.findViewById(R.id.txt_descripcion_rep);
            txtPrecio = itemView.findViewById(R.id.txt_precio);
            txtExistencia = itemView.findViewById(R.id.txt_existencia);
        }
    }
}
