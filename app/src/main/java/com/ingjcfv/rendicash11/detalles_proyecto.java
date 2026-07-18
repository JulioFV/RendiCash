package com.ingjcfv.rendicash11;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingjcfv.rendicash11.adapter.AdapterMovimientos;
import com.ingjcfv.rendicash11.adapter.AdapterProyecto;
import com.ingjcfv.rendicash11.modelo.Movimiento;
import com.ingjcfv.rendicash11.modelo.Proyecto;
import com.ingjcfv.rendicash11.repositorio.RepoMovimiento;
import com.ingjcfv.rendicash11.repositorio.RepoProyectos;
import com.ingjcfv.rendicash11.utils.Alerta;
import com.ingjcfv.rendicash11.utils.CallbackResultado;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detalles_proyecto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detalles_proyecto extends Fragment {
    private ImageView btnRegresar;
    private CardView btnNuevoMovimeinto, btnTerminar;
    private CardView etiquetaProgreso, etiquetaTerminado;
    private TextView txtTitulo, txtDescripcion;
    private NavController nav;
    private Bundle paquete;
    private int interfaz;
    private Proyecto objProyecto;
    private RecyclerView recMovimientos;
    private RepoMovimiento repoMovimiento;
    private RepoProyectos repoProyectos;
    private ArrayList<Movimiento> lista;
    private AdapterMovimientos adapter;
    private TextView lblIngresos,lblInversionTotal, lblRentabilidad;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        objProyecto = new Proyecto();
        btnRegresar = view.findViewById(R.id.dp_btn_regresar);
        recMovimientos = view.findViewById(R.id.rec_dp_proyecto);
        lblIngresos = view.findViewById(R.id.dp_lbl_ingresos);
        lblInversionTotal = view.findViewById(R.id.dp_lbl_inversiontotal);
        lblRentabilidad = view.findViewById(R.id.dp_lbl_rentabilidad);
        etiquetaProgreso = view.findViewById(R.id.dpetioqueta_progreso);
        etiquetaTerminado = view.findViewById(R.id.dpetioqueta_terminado);
        btnTerminar = view.findViewById(R.id.dp_btn_finalizar);
        txtTitulo = view.findViewById(R.id.dp_txt_titulo);
        txtDescripcion = view.findViewById(R.id.dp_txt_descripcion);
        lista = new ArrayList<>();
        btnNuevoMovimeinto = view.findViewById(R.id.dp_btn_nuevo_movimeinto);
        paquete = new Bundle();
        paquete = getArguments();
        repoMovimiento = new RepoMovimiento(requireContext());
        repoProyectos = new RepoProyectos(requireContext());
        interfaz = paquete.getInt("interfaz");
        objProyecto = (Proyecto)paquete.getSerializable("proyecto");
        System.out.println("Contenido de los objetos"+objProyecto.toString());
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
        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminarProyecto();
            }
        });
        txtTitulo.setText(objProyecto.getNombre());
        txtDescripcion.setText(objProyecto.getDetalles());
        contenidoEtiquetas();
        obtenerMovimientos();
    }

    private void obtenerMovimientos() {
        if (!isAdded()) return;

        Alerta alertas = new Alerta(requireContext());
        alertas.AlertaCarga("Cargando", "Por favor espere...");

        repoMovimiento.obtenerMovimientosPorProyecto(objProyecto.getId(), new CallbackResultado<List<Movimiento>>() {
            @Override
            public void onSuccess(List<Movimiento> data) {
                if(!isAdded()) return;
                alertas.cerrarDialogo();
                lista.clear();
                if (data != null) {
                    lista.addAll(data);
                }
                if (recMovimientos.getLayoutManager() == null) {
                    recMovimientos.setHasFixedSize(true);
                    recMovimientos.setLayoutManager(new LinearLayoutManager(requireContext()));
                }
                if (adapter == null) {
                    adapter = new AdapterMovimientos(lista);
                    recMovimientos.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onError(Exception e) {
                if(!isAdded()) return;
                alertas.cerrarDialogo();
                Log.e("ERROR", e.getMessage());
                new Alerta(requireContext()).AlertaError("Error", "No se pudo obtener los proyectos");

            }
        });
    }
    private void contenidoEtiquetas(){
        double gastosTotales = repoMovimiento.obtenerGastosPorProyecto(objProyecto.getId());
        double ingresosTotales = repoMovimiento.obtenerIngresosPorProyecto(objProyecto.getId());

        lblInversionTotal.setText(""+gastosTotales);
        lblIngresos.setText(""+ingresosTotales);


        if(objProyecto.getStatus() == 2) {
            lblRentabilidad.setText("$"+ (ingresosTotales - gastosTotales));
            if((ingresosTotales - gastosTotales) > 0){
                lblRentabilidad.setTextColor(getResources().getColor(R.color.verde, null));
            }else{
                lblRentabilidad.setTextColor(getResources().getColor(R.color.rojo_oscuro, null));
            }
            etiquetaProgreso.setVisibility(View.GONE);
            etiquetaTerminado.setVisibility(View.VISIBLE);
            btnNuevoMovimeinto.setVisibility(View.GONE);
            btnTerminar.setVisibility(View.GONE);

        }
    }
    private void terminarProyecto(){
        Alerta alertas = new Alerta(requireContext());
        alertas.AlertaConfirmacion("CONFIRMA","¿Estas seguro de finalizar el proyecto?",
                () ->{
            try{
                repoProyectos.finalizarProyecto(objProyecto.getId(), new CallbackResultado<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(!isAdded()) return;
                        nav.navigate(R.id.mis_proyectos);
                        new Alerta(requireContext()).AlertaExitosa();
                    }

                    @Override
                    public void onError(Exception e) {
                        if(!isAdded()) return;
                        new Alerta(requireContext()).AlertaError("Error", "No se pudo finalizar el proyecto");
                    }
                });
            }catch (Exception e){
                new Alerta(requireContext()).AlertaError("Error", "Ocurrio un error inesperado");
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