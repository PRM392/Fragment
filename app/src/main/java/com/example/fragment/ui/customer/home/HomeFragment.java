package com.example.fragment.ui.customer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fragment.R;
import com.example.fragment.databinding.FragmentHomeBinding;
import com.example.fragment.ui.SharedViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private SharedViewModel sharedViewModel;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Lắng nghe biến đếm để cập nhật UI
        sharedViewModel.getCounter().observe(getViewLifecycleOwner(), count -> {
            binding.tvCounterResult.setText("Biến đếm hiện tại: " + count);
        });

        binding.btnLogout.setOnClickListener(v -> {
            sharedViewModel.setUserRole(null); // Xóa role
            androidx.navigation.Navigation.findNavController(v).navigate(com.example.fragment.R.id.navigation_login);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
