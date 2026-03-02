package com.example.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.fragment.databinding.ActivityMainBinding;
import com.example.fragment.ui.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo SharedViewModel (scoped to Activity)
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // Lấy NavController từ NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);

        if (navHostFragment == null)
            return;
        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNavView = binding.navView;

        // ---- Ẩn BottomNav khi đang ở màn hình Login ----
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_login) {
                bottomNavView.setVisibility(View.GONE);
            } else {
                bottomNavView.setVisibility(View.VISIBLE);
            }
        });

        // ---- Lắng nghe userRole để đổi menu BottomNav theo vai trò ----
        sharedViewModel.getUserRole().observe(this, role -> {
            if (role == null)
                return;

            if (SharedViewModel.ROLE_ADMIN.equals(role)) {
                // Admin: Dashboard | Tour Management | User Management
                bottomNavView.getMenu().clear();
                bottomNavView.inflateMenu(R.menu.bottom_nav_admin);
                NavigationUI.setupWithNavController(bottomNavView, navController);

            } else if (SharedViewModel.ROLE_CUSTOMER.equals(role)) {
                // Customer: Home | Booking | Tracking | SOS
                bottomNavView.getMenu().clear();
                bottomNavView.inflateMenu(R.menu.bottom_nav_customer);
                NavigationUI.setupWithNavController(bottomNavView, navController);
            }
        });
    }
}