package com.ingjcfv.rendicash11;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.ingjcfv.rendicash11.modelo.Usuario;
import com.ingjcfv.rendicash11.repositorio.RepoUsuario;
import com.ingjcfv.rendicash11.utils.Alerta;
import com.ingjcfv.rendicash11.utils.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {
    private CardView btnEntrar;
    private ImageView btnRegresar;
    private TextView btnRecoveryPassword;
    private TextInputEditText txtUsuario, txtPassword;
    private NavController nav;
    private RepoUsuario repoUsuario;
    private SessionManager sessionManager;




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        btnEntrar = view.findViewById(R.id.login_btn_entrar);
        btnRegresar = view.findViewById(R.id.login_btn_regresar);
        btnRecoveryPassword = view.findViewById(R.id.login_btn_recoverypassword);
        txtUsuario = view.findViewById(R.id.login_txtusuario);
        txtPassword = view.findViewById(R.id.login_txtpassword);
        sessionManager = new SessionManager(requireContext());

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.action_login_to_bienvenida);
            }
        });
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario();
            }
        });
        repoUsuario = new RepoUsuario(requireContext());

    }
    private void validarUsuario() {
        Alerta progress = new Alerta(requireContext());
        progress.AlertaCarga("Validando credenciales", "Por favor espere...");

        String usuario = txtUsuario.getText().toString();
        String password = txtPassword.getText().toString();
        Usuario usuarioEncontrado = repoUsuario.buscarUsuarioPorCorreo(usuario);
        try{
            if (usuarioEncontrado != null) {
                if (usuarioEncontrado.getPassword().equals(password)) {
                    sessionManager.saveSession(usuarioEncontrado);
                    nav.navigate(R.id.action_login_to_dashboard);
                    progress.cerrarDialogo();
                }
                else {
                    txtUsuario.setError("Credenciales incorrectas");
                    txtPassword.setError("Credenciales incorrectas");
                    progress.cerrarDialogo();
                }

            }else{
                progress.cerrarDialogo();
                new Alerta(requireContext()).AlertaError("Error", "El usuario no existe");
            }
        }catch (Exception e){
            progress.cerrarDialogo();
            new Alerta(requireContext()).AlertaError("Error", "No se pudo validar el usuario");
        }finally {
            progress.cerrarDialogo();
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}