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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nuevo_movimiento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nuevo_movimiento extends Fragment {
    private ImageView btnRegresar;
    private NavController nav;
    private CardView btnGasto2, btnIngreso2;
    private TextView btnGasto, btnIngreso;
    private LinearLayout lnIngreso, lnGasto;
    private int tipoMovimiento;
    private List<String> categorias = List.of("Categoría 1", "Categoría 2", "Categoría 3");
    private Bundle paquete;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        paquete = new Bundle();
        paquete = getArguments();
        btnRegresar = view.findViewById(R.id.nm_btn_regresar);
        btnGasto = view.findViewById(R.id.nm_btn_gasto);
        btnIngreso = view.findViewById(R.id.nm_btn_ingreso);
        btnGasto2 = view.findViewById(R.id.nm_btn_gasto2);
        btnIngreso2 = view.findViewById(R.id.nm_btn_ingreso2);
        lnGasto = view.findViewById(R.id.ln_gasto);
        lnIngreso = view.findViewById(R.id.ln_ing);
        tipoMovimiento = 1;
        btnRegresar.setOnClickListener( v ->{
            nav.navigate(R.id.detalles_proyecto, paquete);
        });

        /* MANEJAMOS LA VISTA DE LOS BOTONES DE GASTO Y INGRESO **/
        btnGasto.setOnClickListener(v -> {
            lnGasto.setVisibility(View.GONE);
            lnIngreso.setVisibility(View.VISIBLE);
            btnGasto2.setVisibility(View.VISIBLE);
            btnIngreso2.setVisibility(View.GONE);
            tipoMovimiento = 1;
        });
        btnIngreso.setOnClickListener(v -> {
           tipoMovimiento = 2;
            lnGasto.setVisibility(View.VISIBLE);
            lnIngreso.setVisibility(View.GONE);
            btnGasto2.setVisibility(View.GONE);
            btnIngreso2.setVisibility(View.VISIBLE);
        });
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nuevo_movimiento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nuevo_movimiento.
     */
    // TODO: Rename and change types and number of parameters
    public static nuevo_movimiento newInstance(String param1, String param2) {
        nuevo_movimiento fragment = new nuevo_movimiento();
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
        return inflater.inflate(R.layout.fragment_nuevo_movimiento, container, false);
    }
}