package com.ingjcfv.rendicash11;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView btnMenu;
    private NavController nav;
    private LinearLayout btnDashboard, btnProjects, btnLogout,btnAddProject;
    private LinearLayout btnCerrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activarModoInmersivo();

        drawerLayout = findViewById(R.id.main);
        btnMenu = findViewById(R.id.main_btnmenu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View menuView = navigationView.getHeaderView(0);
        //AQUI COMIENZAN LOS FIND BY ID DE LOS BOTONES Y DE LOS LISTENERS
        btnDashboard = menuView.findViewById(R.id.menu_btn_home);
        btnProjects = menuView.findViewById(R.id.menu_btn_projects);
        btnLogout = menuView.findViewById(R.id.menu_btn_cerrar_sesion);
        btnAddProject = menuView.findViewById(R.id.menu_btn_add_project);
        btnCerrar = menuView.findViewById(R.id.it_menu_cerrar);
        btnCerrar.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        btnDashboard.setOnClickListener(v -> {
            nav.navigate(R.id.dashboard);
            drawerLayout.closeDrawer(GravityCompat.START);
            btnDashboard.setBackgroundColor(getResources().getColor(R.color.green));
            btnProjects.setBackgroundColor(getResources().getColor(R.color.white));
            btnAddProject.setBackgroundColor(getResources().getColor(R.color.white));
        });
        btnProjects.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            nav.navigate(R.id.mis_proyectos);
            btnDashboard.setBackgroundColor(getResources().getColor(R.color.white));
            btnProjects.setBackgroundColor(getResources().getColor(R.color.green));
            btnAddProject.setBackgroundColor(getResources().getColor(R.color.white));
        });
        btnAddProject.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            nav.navigate(R.id.crear_proyecto);
            btnDashboard.setBackgroundColor(getResources().getColor(R.color.white));
            btnProjects.setBackgroundColor(getResources().getColor(R.color.white));
            btnAddProject.setBackgroundColor(getResources().getColor(R.color.green));
        });
        btnLogout.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            nav.navigate(R.id.bienvenida);
        });

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        nav = navHostFragment.getNavController();
        btnMenu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationUI.setupWithNavController(navigationView, nav);
        setupDrawer();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            activarModoInmersivo();
        }
    }
    private void activarModoInmersivo() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }
    private void setupDrawer(){
        nav.addOnDestinationChangedListener((controller,destination,arguments)->{
            if(destination.getId() == R.id.login || destination.getId() == R.id.nuevo_movimiento || destination.getId() == R.id.detalles_movimiento
                    || destination.getId() == R.id.bienvenida || destination.getId() == R.id.registro || destination.getId() == R.id.detalles_proyecto
                     ){
                btnMenu.setVisibility(View.GONE);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }else {
                btnMenu.setVisibility(View.VISIBLE);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
    }
}