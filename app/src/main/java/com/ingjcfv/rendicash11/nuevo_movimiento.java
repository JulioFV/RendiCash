package com.ingjcfv.rendicash11;

import android.os.Build;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ingjcfv.rendicash11.modelo.Movimiento;
import com.ingjcfv.rendicash11.modelo.Proyecto;
import com.ingjcfv.rendicash11.repositorio.RepoMovimiento;
import com.ingjcfv.rendicash11.utils.Alerta;
import com.ingjcfv.rendicash11.utils.CallbackResultado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nuevo_movimiento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nuevo_movimiento extends Fragment {
    private EditText txtMonto;
    private EditText txtDescripcion;
    private ImageView btnRegresar;
    private NavController nav;
    private CardView btnGasto2, btnIngreso2, btnCrear;
    private TextView btnGasto, btnIngreso;
    private LinearLayout lnIngreso, lnGasto;
    private Spinner spinnerCategoria;
    private Movimiento objMovimiento;
    private Proyecto objProyecto;
    private RepoMovimiento repoMovimiento;
    private static final String[] opcionesSpinner = {"Refacciones","Alimentos","Mano de obra","Tramites/Impuestos",
            "Veterinario","Suplementos","Traslado", "Otros"};
    private Bundle paquete;
    private static final int MODO_GASTO = 1;
    private static final int MODO_INGRESO = 2;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        objMovimiento = new Movimiento();
        objProyecto = new Proyecto();
        repoMovimiento = new RepoMovimiento(requireContext());
        paquete = new Bundle();
        paquete = getArguments();
        objProyecto = (Proyecto)paquete.getSerializable("proyecto");
        spinnerCategoria = view.findViewById(R.id.nm_spinner_gasto);
        btnRegresar = view.findViewById(R.id.nm_btn_regresar);
        btnGasto = view.findViewById(R.id.nm_btn_gasto);
        btnCrear = view.findViewById(R.id.nm_btn_guardar);
        btnIngreso = view.findViewById(R.id.nm_btn_ingreso);
        btnGasto2 = view.findViewById(R.id.nm_btn_gasto2);
        btnIngreso2 = view.findViewById(R.id.nm_btn_ingreso2);
        lnGasto = view.findViewById(R.id.ln_gasto);
        lnIngreso = view.findViewById(R.id.ln_ing);
        txtMonto = view.findViewById(R.id.nm_txt_monto);
        txtDescripcion = view.findViewById(R.id.nm_txt_descripcion);

        establecerModo(MODO_GASTO);
        btnRegresar.setOnClickListener( v ->{
            nav.navigate(R.id.detalles_proyecto, paquete);
        });
        /* MANEJAMOS LA VISTA DE LOS BOTONES DE GASTO Y INGRESO **/
        btnGasto.setOnClickListener(v -> {
            establecerModo(MODO_GASTO);
        });
        btnIngreso.setOnClickListener(v -> {
           establecerModo(MODO_INGRESO);
        });

        /**
         *
         * Funcionalidad del Spinner
         */

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.item_spinner_selected, opcionesSpinner);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                objMovimiento.setCategoria(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**
         *
         * CREAMOS EL MOVIEMIENTO
         */
        btnCrear.setOnClickListener(v -> {
          crearMovimiento();
        });

    }
    private void establecerModo(int modo){
        if(modo == MODO_GASTO){
            lnGasto.setVisibility(View.GONE);
            lnIngreso.setVisibility(View.VISIBLE);
            btnGasto2.setVisibility(View.VISIBLE);
            btnIngreso2.setVisibility(View.GONE);
        } else if (modo == MODO_INGRESO) {
            lnGasto.setVisibility(View.VISIBLE);
            lnIngreso.setVisibility(View.GONE);
            btnGasto2.setVisibility(View.GONE);
            btnIngreso2.setVisibility(View.VISIBLE);
        }
        objMovimiento.setTipo (modo);
    }
    private void crearMovimiento(){
            try {
                Alerta alertas = new Alerta(requireContext());
                alertas.AlertaCarga("Creando Movimiento", "Por favor espere...");
                if(validarCampos()){
                    objMovimiento.setMonto(Double.parseDouble(txtMonto.getText().toString()));
                    objMovimiento.setDescripcion(txtDescripcion.getText().toString());
                    objMovimiento.setId_proyecto(objProyecto.getId());
                    objMovimiento.setId_usuario(objProyecto.getId_usuario());
                    LocalDate fechaActual = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        fechaActual = LocalDate.now();
                    }
                    DateTimeFormatter formatoFecha = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    }
                    String fechaFormateada = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        fechaFormateada = fechaActual.format(formatoFecha);
                    }
                    objMovimiento.setFecha(fechaFormateada);
                }else{
                    new Alerta(requireContext()).AlertaError("Error", "Por favor revise los campos");
                }
                repoMovimiento.crearMovimiento(objMovimiento, new CallbackResultado<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(!isAdded()) return;
                        alertas.cerrarDialogo();
                        new Alerta(requireContext()).AlertaExitosa();
                        nav.navigate(R.id.detalles_proyecto, paquete);
                    }
                    @Override
                    public void onError(Exception e) {
                        if(!isAdded()) return;
                        alertas.cerrarDialogo();
                        new Alerta(requireContext()).AlertaError("Error", "No se pudo crear el movimiento");
                    }
                });
            }catch (Exception e){
                new Alerta(requireContext()).AlertaError("Error", "Algo salio mal intenta más tarde");
            }
    }
    private boolean validarCampos(){
        if(txtMonto.getText().toString().isEmpty()){
            txtMonto.setError("Ingrese un monto");
            return false;
        }
        return true;
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