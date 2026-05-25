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
import android.widget.ImageView;

import com.ingjcfv.rendicash11.adapter.AdapterProyecto;
import com.ingjcfv.rendicash11.modelo.Proyecto;
import com.ingjcfv.rendicash11.repositorio.RepoProyectos;
import com.ingjcfv.rendicash11.utils.Alerta;
import com.ingjcfv.rendicash11.utils.CallbackResultado;
import com.ingjcfv.rendicash11.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mis_proyectos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mis_proyectos extends Fragment {
    private ImageView btnMas;
    private NavController nav;
    private RecyclerView recProyectos;
    private ArrayList<Proyecto> lista;
    private RepoProyectos repoProyectos;
    private AdapterProyecto adapter;
    private SessionManager session;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        btnMas = view.findViewById(R.id.mis_proyectos_btnmas);
        recProyectos = view.findViewById(R.id.mis_proyectos_recycler);
        recProyectos.setHasFixedSize(true);
        recProyectos.setLayoutManager(new LinearLayoutManager(requireContext()));
        lista = new ArrayList<>();
        repoProyectos = new RepoProyectos(requireContext());
        session = new SessionManager(requireContext());
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.action_mis_proyectos_to_crear_proyecto);
            }
        });
        obtenerProyectos();

    }
    private void obtenerProyectos() {

        if (!isAdded()) return;

        Alerta alertas = new Alerta(requireContext());
        alertas.AlertaCarga("Cargando", "Por favor espere...");

        repoProyectos.obtenerProyectosPorUsuario(
                session.getSession().getId(),

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

                            adapter = new AdapterProyecto(lista);
                            recProyectos.setAdapter(adapter);

                        } else {

                            adapter.notifyDataSetChanged();

                        }
                    }


                    @Override
                    public void onError(Exception e) {

                        if (!isAdded()) return;

                        alertas.cerrarDialogo();

                        Log.e("ERROR", e.getMessage());

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

    public mis_proyectos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mis_proyectos.
     */
    // TODO: Rename and change types and number of parameters
    public static mis_proyectos newInstance(String param1, String param2) {
        mis_proyectos fragment = new mis_proyectos();
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
        return inflater.inflate(R.layout.fragment_mis_proyectos, container, false);
    }
}