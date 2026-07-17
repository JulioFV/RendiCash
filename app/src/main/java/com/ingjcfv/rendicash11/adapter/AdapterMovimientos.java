package com.ingjcfv.rendicash11.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ingjcfv.rendicash11.R;
import com.ingjcfv.rendicash11.modelo.Movimiento;

import java.util.ArrayList;
import java.util.List;

public class AdapterMovimientos extends RecyclerView.Adapter<AdapterMovimientos.ViewHolderMovimiento> {
    private ArrayList<Movimiento> lista;

    public AdapterMovimientos(ArrayList<Movimiento> lista){
        this.lista = lista;
    }
    private static final String [] categorias = {"Refacciones","Alimentos","Mano de obra","Tramites/Impuestos",
            "Veterinario","Suplementos","Traslado", "Otros"};

    @NonNull
    @Override
    public AdapterMovimientos.ViewHolderMovimiento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movimiento, parent, false);
        return new ViewHolderMovimiento(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMovimientos.ViewHolderMovimiento holder, int position) {
        Movimiento movimiento = lista.get(position);
        for(int i = 0; i < categorias.length; i++){
            holder.txtTitulo.setText(categorias[movimiento.getCategoria()]);
        }
        if(movimiento.getTipo() == 2){
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.green));
            holder.imgIcono.setImageResource(R.drawable.ic_arrowuprightv);
        }
        holder.txtDescripcion.setText(movimiento.getDescripcion());
        holder.txtPrecio.setText(""+movimiento.getMonto());
        holder.txtFecha.setText(movimiento.getFecha());

    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public class ViewHolderMovimiento extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        ImageView imgIcono;
        CardView cardView;
        TextView txtDescripcion;
        TextView txtPrecio;
        TextView txtFecha;
        public ViewHolderMovimiento(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.it_movimiento_txtnombre);
             txtDescripcion = itemView.findViewById(R.id.it_movimiento_txtdescripcion);
             txtPrecio = itemView.findViewById(R.id.it_movimiento_txtmonto);
             txtFecha = itemView.findViewById(R.id.it_movimeinto_txtfecha);
             imgIcono = itemView.findViewById(R.id.it_movimeinto_img);
             cardView = itemView.findViewById(R.id.it_movimeinto_bg_img);


        }
    }
}
