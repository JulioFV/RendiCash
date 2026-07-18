package com.ingjcfv.rendicash11;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingjcfv.rendicash11.modelo.Movimiento;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detalles_movimiento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detalles_movimiento extends Fragment {
    private Bundle paquete;
    private Movimiento movimiento;
    private TextView txtTitulo, txtDescripcion, txtMonto, txtFecha, txtIdRef, txtConcepto;
    private CardView btnEliminar;
    private ImageView btnRegresar;
    private LinearLayout bgIcono;
    private ImageView icono;
    private static final String[] conceptos = {"Compra Inicial","Refacciones","Alimentos","Mano de obra","Tramites/Impuestos",
            "Veterinario","Suplementos","Traslado", "Otros"};

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtDescripcion = view.findViewById(R.id.dproyecto_txtdescripcion);
        txtMonto = view.findViewById(R.id.dproyecto_txtmonto);
        txtFecha = view.findViewById(R.id.dproyecto_txtfecha);
        txtIdRef = view.findViewById(R.id.dproyecto_txtid);
        txtConcepto = view.findViewById(R.id.dproyecto_txtcategoria);
        btnEliminar = view.findViewById(R.id.dproyecto_btn_eliminar);
        btnRegresar = view.findViewById(R.id.dproyecto_btn_regresar);
        bgIcono = view.findViewById(R.id.ln_bg_circulo);
        icono = view.findViewById(R.id.img_icon);

        paquete = getArguments();
        movimiento = new Movimiento();
        if(paquete != null){
            movimiento = (Movimiento) paquete.getSerializable("movimiento");
            if(movimiento != null){
                Log.e("QUE TIENE EL OBJETO", movimiento.toString());
                txtDescripcion.setText((movimiento.getDescripcion() == null) ? "" : movimiento.getDescripcion());
                txtMonto.setText(""+movimiento.getMonto());
                txtFecha.setText(movimiento.getFecha());
                txtIdRef.setText("#00"+movimiento.getId());
                for(int i = 0; i < conceptos.length; i++){
                    txtConcepto.setText(conceptos[movimiento.getCategoria()]);
                }
                switch (movimiento.getTipo()){
                    case 1:
                        bgIcono.setBackgroundColor(getResources().getColor(R.color.rojo_claro, null));
                        icono.setImageResource(R.drawable.ic_file_text_red);
                        txtMonto.setText("-$" + movimiento.getMonto());
                        break;
                    case 2:
                        bgIcono.setBackgroundColor(getResources().getColor(R.color.green, null));
                        icono.setImageResource(R.drawable.ic_file_text);
                        txtMonto.setText("+$" + movimiento.getMonto());
                        break;
                    default:
                        //NO SE QUE PONER
                        break;
                }
            }
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public detalles_movimiento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detalles_movimiento.
     */
    // TODO: Rename and change types and number of parameters
    public static detalles_movimiento newInstance(String param1, String param2) {
        detalles_movimiento fragment = new detalles_movimiento();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalles_movimiento, container, false);
    }
}