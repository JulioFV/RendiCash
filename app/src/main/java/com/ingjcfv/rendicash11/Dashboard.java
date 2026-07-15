package com.ingjcfv.rendicash11;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingjcfv.rendicash11.adapter.AdapterProyecto;
import com.ingjcfv.rendicash11.modelo.Proyecto;
import com.ingjcfv.rendicash11.repositorio.RepoMovimiento;
import com.ingjcfv.rendicash11.repositorio.RepoProyectos;
import com.ingjcfv.rendicash11.utils.Alerta;
import com.ingjcfv.rendicash11.utils.CallbackResultado;
import com.ingjcfv.rendicash11.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {
    private TextView btnVerTodos;
    private NavController nav;
    private RepoProyectos repoProyectos;
    private RepoMovimiento repoMovimiento;
    private RecyclerView recProyectos;
    private ArrayList<Proyecto> lista;
    private AdapterProyecto adapter;
    private SessionManager session;
    private Bundle paquete;
    private TextView txtBalance,txtGastos,txtIngresos;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtBalance = view.findViewById(R.id.dash_txtbalance);
        txtGastos = view.findViewById(R.id.dash_txtGastos);
        txtIngresos = view.findViewById(R.id.dash_txtingresos);


        session = new SessionManager(requireContext());
        btnVerTodos = view.findViewById(R.id.dash_btn_ver_todos);
        paquete = new Bundle();
        paquete.putInt("interfaz", 1);
        nav = Navigation.findNavController(view);
        btnVerTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.action_dashboard_to_mis_proyectos);
            }
        });
        recProyectos = view.findViewById(R.id.dash_proyectos_activos);
        repoProyectos = new RepoProyectos(requireContext());
        repoMovimiento = new RepoMovimiento(requireContext());
        recProyectos.setHasFixedSize(true);
        recProyectos.setLayoutManager(new LinearLayoutManager(requireContext()));
        lista = new ArrayList<>();
        obtenerProyectosRecomendados();
        obtenerBalance();
    }
    private void obtenerBalance(){
        double gastos = repoMovimiento.obtenerGastosGenerales(session.getSession().getId());
        double ingresos = repoMovimiento.obtenerIngresosGenerales(session.getSession().getId());
        double balance = ingresos - gastos;
        txtBalance.setText("$"+balance);
        txtGastos.setText("$"+gastos);
        txtIngresos.setText("$"+ingresos);
    }
    private void obtenerProyectosRecomendados() {

        if (!isAdded()) return;

        Alerta alertas = new Alerta(requireContext());
        alertas.AlertaCarga("Cargando", "Por favor espere...");
        Log.e("ID DEL USUARIO", session.getSession().getId()+"");


        repoProyectos.obtenerProyectosRecomendados(session.getSession().getId(),

                new CallbackResultado<List<Proyecto>>() {

                    @Override
                    public void onSuccess(List<Proyecto> data) {

                        if (!isAdded()) return;

                        alertas.cerrarDialogo();

                        lista.clear();

                        if (data != null) {
                            lista.addAll(data);
                        }

                        if (recProyectos.getLayoutManager() == null) {

                            recProyectos.setHasFixedSize(true);

                            recProyectos.setLayoutManager(
                                    new LinearLayoutManager(requireContext())
                            );
                        }

                        if (adapter == null) {

                            adapter = new AdapterProyecto(lista, paquete);
                            recProyectos.setAdapter(adapter);

                        } else {

                            adapter.notifyDataSetChanged();

                        }
                    }


                    @Override
                    public void onError(Exception e) {

                        if (!isAdded()) return;

                        alertas.cerrarDialogo();

                        Log.e(
                                "PROYECTOS_RECOMENDADOS",
                                e.getMessage(),
                                e
                        );

                        new Alerta(requireContext())
                                .AlertaError(
                                        "Error",
                                        "No se pudo obtener los proyectos"
                                );
                    }
                }
        );
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }
}