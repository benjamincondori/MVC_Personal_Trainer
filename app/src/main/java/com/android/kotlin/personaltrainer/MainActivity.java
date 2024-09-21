package com.android.kotlin.personaltrainer;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.kotlin.personaltrainer.view.ClientesFragment;
import com.android.kotlin.personaltrainer.view.EjerciciosFragment;
import com.android.kotlin.personaltrainer.view.PlanesFragment;
import com.android.kotlin.personaltrainer.view.RutinasFragment;
import com.android.kotlin.personaltrainer.view.CategoriaEjercicio.VCategoriaEjercicio;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new EjerciciosFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_ejercicios);
        }

        replaceFragment(new EjerciciosFragment());
        getSupportActionBar().setTitle("Ejercicios");

        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavigation(item.getItemId());
            drawerLayout.closeDrawers();
            return true;
        });

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            handleNavigation(item.getItemId());
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();

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
            case R.id.btn_ejercicio:
                replaceFragment(new EjerciciosFragment());
                getSupportActionBar().setTitle("Ejercicios");
                break;
            case R.id.nav_clientes:
            case R.id.btn_cliente:
                replaceFragment(new ClientesFragment());
                getSupportActionBar().setTitle("Clientes");
                break;
            case R.id.nav_rutinas:
            case R.id.btn_rutina:
                replaceFragment(new RutinasFragment());
                getSupportActionBar().setTitle("Rutinas");
                break;
            case R.id.nav_planes:
            case R.id.btn_plan_rutina:
                replaceFragment(new PlanesFragment());
                getSupportActionBar().setTitle("Planes de Rutina");
                break;
        }
    }

}