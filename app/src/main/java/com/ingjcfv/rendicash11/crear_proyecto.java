package com.ingjcfv.rendicash11;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link crear_proyecto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class crear_proyecto extends Fragment {
    private CardView btnphone;
    private CardView btncar;
    private CardView btnanimal;
    private CardView btntag;
    private CardView btnCrear;
    private EditText txtItem;
    private EditText txtPrecioEst;
    private EditText txtDetalles;
    private int icono;
    private ImageView btnRegresar;
    private NavController nav;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnphone = view.findViewById(R.id.np_btnphone);
        btncar = view.findViewById(R.id.np_btncar);
        btnanimal = view.findViewById(R.id.np_btnanimal);
        btntag = view.findViewById(R.id.np_btntag);
        btnRegresar = view.findViewById(R.id.np_btn_regresar);
        nav = Navigation.findNavController(view);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.action_crear_proyecto_to_mis_proyectos);
            }
        });
        btnphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icono = 1;
                ManejadorDeFondos(1);
            }
        });
        btncar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icono = 2;
                ManejadorDeFondos(2);
            }
        });
        btnanimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icono = 3;
                ManejadorDeFondos(3);
            }
        });
        btntag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icono = 4;
                ManejadorDeFondos(4);
            }
        });


    }
    private void ManejadorDeFondos(int icono) {
        List<CardView> buttons = Arrays.asList(btnphone, btncar, btnanimal, btntag);

        for (int i = 0; i < buttons.size(); i++) {
            CardView card = buttons.get(i);

            int colorRes = ((i + 1) == icono) ? R.color.green : R.color.white;
            int color = ContextCompat.getColor(requireContext(), colorRes);

            card.setCardBackgroundColor(color);
            card.invalidate(); // fuerza redraw
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public crear_proyecto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment crear_proyecto.
     */
    // TODO: Rename and change types and number of parameters
    public static crear_proyecto newInstance(String param1, String param2) {
        crear_proyecto fragment = new crear_proyecto();
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
        return inflater.inflate(R.layout.fragment_crear_proyecto, container, false);
    }
}