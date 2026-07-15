package com.ingjcfv.rendicash11;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detalles_proyecto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detalles_proyecto extends Fragment {
    private ImageView btnRegresar;
    private CardView btnNuevoMovimeinto;
    private TextView txtInversionTotal;
    private NavController nav;
    private Bundle paquete;
    private int interfaz;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        btnRegresar = view.findViewById(R.id.dp_btn_regresar);
        btnNuevoMovimeinto = view.findViewById(R.id.dp_btn_nuevo_movimeinto);
        paquete = new Bundle();
        paquete = getArguments();
        interfaz = paquete.getInt("interfaz");
        btnRegresar.setOnClickListener( v ->{
            if(interfaz == 1){
                nav.navigate(R.id.dashboard);
            }else if(interfaz == 2){
                nav.navigate(R.id.mis_proyectos);
            }
        });
        btnNuevoMovimeinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.nuevo_movimiento,paquete);
            }
        });
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public detalles_proyecto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detalles_proyecto.
     */
    // TODO: Rename and change types and number of parameters
    public static detalles_proyecto newInstance(String param1, String param2) {
        detalles_proyecto fragment = new detalles_proyecto();
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
        return inflater.inflate(R.layout.fragment_detalles_proyecto, container, false);
    }
}