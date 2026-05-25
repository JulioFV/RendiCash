package com.ingjcfv.rendicash11.adapter;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ingjcfv.rendicash11.R;
import com.ingjcfv.rendicash11.modelo.Proyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterProyecto extends RecyclerView.Adapter<AdapterProyecto.ViewHolderProyecto> {
    private ArrayList<Proyecto> lista;
    private Bundle paquete;
    private final List<Integer> iconos = Arrays.asList(R.drawable.ic_phone, R.drawable.ic_car, R.drawable.ic_animal, R.drawable.ic_tag);

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
        paquete = new Bundle();
        holder.txtTitulo.setText(proyecto.getNombre());
        holder.txtDescripcion.setText(proyecto.getDetalles());
        holder.txtPrecio.setText("-"+proyecto.getPrecioEstimado());
        ///  MANEJAMOS EL ICONO QUE DEBE CONTENER EL ITEM
        int posIcono = proyecto.getIcono() -1;
        if(posIcono >= 0 && posIcono < iconos.size()){
            holder.imgIcono.setImageResource(iconos.get(posIcono));
        }
        if (proyecto.getStatus()== 1){
            holder.txtVenta.setVisibility(View.GONE);
        }
        holder.btnVerMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController nav = Navigation.findNavController(view);
                nav.navigate(R.id.detalles_proyecto);
            }
        });
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
