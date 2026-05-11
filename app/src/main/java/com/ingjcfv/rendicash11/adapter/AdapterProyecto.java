package com.ingjcfv.rendicash11.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingjcfv.rendicash11.R;
import com.ingjcfv.rendicash11.modelo.Proyecto;

import java.util.ArrayList;

public class AdapterProyecto extends RecyclerView.Adapter<AdapterProyecto.ViewHolderProyecto> {
    private ArrayList<Proyecto> lista;
    public AdapterProyecto(ArrayList<Proyecto> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public AdapterProyecto.ViewHolderProyecto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto, parent, false);
        return new ViewHolderProyecto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProyecto.ViewHolderProyecto holder, int position) {
        Proyecto proyecto = lista.get(position);
        holder.txtTitulo.setText(proyecto.getNombre());
        holder.txtDescripcion.setText(proyecto.getDetalles());
        holder.txtPrecio.setText(""+proyecto.getPrecioEstimado());
        //holder.txtVenta.setText(proyecto.getVenta());
        //holder.imgIcono.setImageResource(proyecto.getIcono());

    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public class ViewHolderProyecto extends RecyclerView.ViewHolder{
        TextView txtTitulo, txtDescripcion, txtPrecio, txtVenta;
        ImageView imgIcono, btnVerMas;
        public ViewHolderProyecto(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.it_proyecto_txtnombre);
            txtDescripcion = itemView.findViewById(R.id.it_proyecto_txtdescripcion);
            txtPrecio = itemView.findViewById(R.id.it_proyecto_txtprecio);
            txtVenta = itemView.findViewById(R.id.it_proyecto_txtventa);
            imgIcono = itemView.findViewById(R.id.it_proyecto_imgproyecto);
            btnVerMas = itemView.findViewById(R.id.it_proyecto_vermas);
        }
    }
}
