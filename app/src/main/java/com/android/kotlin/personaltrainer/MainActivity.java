package com.android.kotlin.personaltrainer;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.kotlin.personaltrainer.view.Cliente.VCliente;
import com.android.kotlin.personaltrainer.view.Ejercicio.VEjercicio;
import com.android.kotlin.personaltrainer.view.EstadoFisico.VEstadoFisico;
import com.android.kotlin.personaltrainer.view.PlanEntrenamiento.VPlanEntrenamiento;
import com.android.kotlin.personaltrainer.view.Rutina.VRutina;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VCategoriaEjercicio;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
//    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        bottomNavigationView = findViewById(R.id.bottom_navigation);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_nav,
                R.string.close_nav
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new VEjercicio()).commit();
            navigationView.setCheckedItem(R.id.nav_clientes);
        }

        replaceFragment(new VCliente());
        getSupportActionBar().setTitle("Clientes");

        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavigation(item.getItemId());
            drawerLayout.closeDrawers();
            return true;
        });

//        bottomNavigationView.setBackground(null);
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            handleNavigation(item.getItemId());
//            return true;
//        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    private void handleNavigation(int itemId) {
        switch (itemId) {
            case R.id.nav_tipo_ejercicios:
                replaceFragment(new VCategoriaEjercicio());
                getSupportActionBar().setTitle("Categoría de Ejercicios");
                break;
            case R.id.nav_ejercicios:
                replaceFragment(new VEjercicio());
                getSupportActionBar().setTitle("Ejercicios");
                break;
            case R.id.nav_clientes:
                replaceFragment(new VCliente());
                getSupportActionBar().setTitle("Clientes");
                break;
            case R.id.nav_estado_fisico:
                replaceFragment(new VEstadoFisico());
                getSupportActionBar().setTitle("Estado Físico");
                break;
            case R.id.nav_rutinas:
                replaceFragment(new VRutina());
                getSupportActionBar().setTitle("Rutinas");
                break;
            case R.id.nav_planes:
                replaceFragment(new VPlanEntrenamiento());
                getSupportActionBar().setTitle("Planes de Rutina");
                break;
        }
    }

}