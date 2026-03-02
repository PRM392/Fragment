package com.example.fragment.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fragment.R;
import com.example.fragment.databinding.FragmentLoginBinding;
import com.example.fragment.ui.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Ẩn bottom navigation trên màn hình login
        BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
        if (navView != null) {
            navView.setVisibility(View.GONE);
        }

        binding.btnLogin.setOnClickListener(v -> {
            String username = "";
            String password = "";

            if (binding.etUsername.getText() != null) {
                username = binding.etUsername.getText().toString().trim();
            }
            if (binding.etPassword.getText() != null) {
                password = binding.etPassword.getText().toString().trim();
            }

            handleLogin(username, password, view);
        });
    }

    private void handleLogin(String username, String password, View view) {
        // Ẩn lỗi cũ
        binding.tvError.setVisibility(View.GONE);

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            binding.tvError.setText("Vui lòng nhập đầy đủ thông tin!");
            binding.tvError.setVisibility(View.VISIBLE);
            return;
        }

        NavController navController = Navigation.findNavController(view);

        // ---- DEMO CREDENTIALS ----
        // Admin: admin / admin123
        // Customer: customer / cust123
        if (username.equals("admin") && password.equals("admin123")) {
            // Đăng nhập thành công với vai trò Admin
            sharedViewModel.setUserRole(SharedViewModel.ROLE_ADMIN);
            navController.navigate(R.id.action_login_to_admin);

        } else if (username.equals("customer") && password.equals("cust123")) {
            // Đăng nhập thành công với vai trò Customer
            sharedViewModel.setUserRole(SharedViewModel.ROLE_CUSTOMER);
            navController.navigate(R.id.action_login_to_customer);

        } else {
            binding.tvError.setText("Tên đăng nhập hoặc mật khẩu không đúng!");
            binding.tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
